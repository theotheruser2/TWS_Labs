package com.tws.lab.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import com.tws.lab.model.entity.Car;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Stack;

@Repository
public class CarRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Car> findCar(String query, int limit, int offset) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = builder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);

        Predicate predicate = parseQueryToPredicate(query, builder, root);
        if (predicate != null) {
            criteriaQuery.where(predicate);
        }

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    private Predicate parseQueryToPredicate(String query, CriteriaBuilder builder, Root<Car> root) {
        if (query == null || query.trim().isEmpty()) {
            return null;
        }

        query = query.replace("+", " ");
        try {
            query = java.net.URLDecoder.decode(query, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid query format: " + e.getMessage());
        }

        query = query.replaceAll("([=><~!]+=?)", " $1 ")  // Handle =, >, <, >=, <=, ~, !=
                   .replaceAll("([()])", " $1 ")
                   .replaceAll("\\s+", " ")
                   .trim();

        Stack<Predicate> predicateStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        String[] tokens = query.split("\\s+");
        int i = 0;
        while (i < tokens.length) {
            String token = tokens[i];

            if (token.equalsIgnoreCase("AND") || token.equalsIgnoreCase("OR")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") && precedence(operatorStack.peek()) >= precedence(token)) {
                    String operator = operatorStack.pop();
                    Predicate right = predicateStack.pop();
                    Predicate left = predicateStack.pop();
                    predicateStack.push(combinePredicates(builder, left, right, operator));
                }
                operatorStack.push(token);
                i++;
            } else if (token.equals("(")) {
                operatorStack.push(token);
                i++;
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    String operator = operatorStack.pop();
                    Predicate right = predicateStack.pop();
                    Predicate left = predicateStack.pop();
                    predicateStack.push(combinePredicates(builder, left, right, operator));
                }
                operatorStack.pop(); // Remove "("
                i++;
            } else {
                if (i + 2 >= tokens.length) {
                    throw new IllegalArgumentException("Incorrect query format. Expected format: 'field=value' or 'field operator value' where operator can be =, !=, >, >=, <, <=, or ~ for LIKE queries");
                }

                String field = token;
                String operator = tokens[i + 1].toLowerCase();
                String value = tokens[i + 2];

                // match entity property name
                if (field.equals("release_year")) {
                    field = "releaseYear";
                } else if (field.equals("license_plate")) {
                    field = "licensePlate";
                } else if (field.equals("owner_phone")) {
                    field = "ownerPhone";
                }

                Path<Object> path;
                try {
                    path = root.get(field);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Could not resolve field '" + field + "' of type '" + root.getJavaType().getName() + "'");
                }

                // Handle numeric fields differently
                if (field.equals("releaseYear")) {
                    try {
                        Integer numericValue = Integer.parseInt(value);
                        Predicate predicate = switch (operator) {
                            case "=" -> builder.equal(path, numericValue);
                            case "!=" -> builder.notEqual(path, numericValue);
                            case ">" -> builder.greaterThan(path.as(Integer.class), numericValue);
                            case ">=" -> builder.greaterThanOrEqualTo(path.as(Integer.class), numericValue);
                            case "<" -> builder.lessThan(path.as(Integer.class), numericValue);
                            case "<=" -> builder.lessThanOrEqualTo(path.as(Integer.class), numericValue);
                            default -> throw new IllegalArgumentException("Invalid operator for numeric field: " + operator);
                        };
                        predicateStack.push(predicate);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid numeric value for field '" + field + "': " + value);
                    }
                } else {
                    Predicate predicate = switch (operator) {
                        case "=" -> builder.equal(path, value);
                        case "!=" -> builder.notEqual(path, value);
                        case ">" -> builder.greaterThan(path.as(String.class), value);
                        case ">=" -> builder.greaterThanOrEqualTo(path.as(String.class), value);
                        case "<" -> builder.lessThan(path.as(String.class), value);
                        case "<=" -> builder.lessThanOrEqualTo(path.as(String.class), value);
                        case "~" -> builder.like(path.as(String.class), "%" + value + "%");
                        case "!~" -> builder.notLike(path.as(String.class), "%" + value + "%");
                        default -> throw new IllegalArgumentException("Invalid operator: " + operator);
                    };
                    predicateStack.push(predicate);
                }
                i += 3;
            }
        }

        while (!operatorStack.isEmpty()) {
            String operator = operatorStack.pop();
            Predicate right = predicateStack.pop();
            Predicate left = predicateStack.pop();
            predicateStack.push(combinePredicates(builder, left, right, operator));
        }

        return predicateStack.isEmpty() ? null : predicateStack.pop();
    }

    private Predicate combinePredicates(CriteriaBuilder builder, Predicate left, Predicate right, String operator) {
        if (operator.equalsIgnoreCase("AND")) {
            return builder.and(left, right);
        } else if (operator.equalsIgnoreCase("OR")) {
            return builder.or(left, right);
        } else {
            throw new IllegalArgumentException("Некорректный логический оператор: " + operator);
        }
    }

    private int precedence(String operator) {
        if (operator.equalsIgnoreCase("AND")) {
            return 2;
        } else if (operator.equalsIgnoreCase("OR")) {
            return 1;
        } else {
            return 0;
        }
    }
}
