package com.tws.lab.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.*;
import com.tws.lab.model.entity.Car;

import java.util.List;
import java.util.Stack;

public class CarRepository {
    private final EntityManagerFactory entityManagerFactory;

    public CarRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Car> findCar(String query, int limit, int offset) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
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
    }
    public Car readCar(int id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(Car.class, id);
        }
    }
    public int createCar(Car car) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(car);
            entityManager.getTransaction().commit();
            return car.getId();
        }
    }
    public boolean updateCar(int id, Car newDetails) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            Car existingCar = entityManager.find(Car.class, id);
            if (existingCar == null) {
                return false;
            }
            existingCar.setBrand(newDetails.getBrand());
            existingCar.setModel(newDetails.getModel());
            existingCar.setReleaseYear(newDetails.getReleaseYear());
            existingCar.setLicensePlate(newDetails.getLicensePlate());
            existingCar.setOwnerPhone(newDetails.getOwnerPhone());
            entityManager.getTransaction().commit();
            return true;
        }
    }
    public boolean deleteCarById(int id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            Car car = entityManager.find(Car.class, id);
            if (car == null) {
                return false;
            }
            entityManager.remove(car);
            entityManager.getTransaction().commit();
            return true;
        }
    }
    private Predicate parseQueryToPredicate(String query, CriteriaBuilder builder, Root<Car> root) {
        if (query == null || query.trim().isEmpty()) {
            return null;
        }

        Stack<Predicate> predicateStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        int index = 0;
        while (index < query.length()) {
            char currentChar = query.charAt(index);

            if (currentChar == '(') {
                operatorStack.push(String.valueOf(currentChar));
                index++;
            } else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    String operator = operatorStack.pop();
                    Predicate right = predicateStack.pop();
                    Predicate left = predicateStack.pop();
                    predicateStack.push(combinePredicates(builder, left, right, operator));
                }
                operatorStack.pop();
                index++;
            } else if (Character.isWhitespace(currentChar)) {
                index++;
            } else {
                StringBuilder conditionBuilder = new StringBuilder();
                while (index < query.length() && query.charAt(index) != ' ' && query.charAt(index) != '(' && query.charAt(index) != ')') {
                    conditionBuilder.append(query.charAt(index));
                    index++;
                }
                String condition = conditionBuilder.toString().trim();

                if (condition.equalsIgnoreCase("AND") || condition.equalsIgnoreCase("OR")) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") && precedence(operatorStack.peek()) >= precedence(condition)) {
                        String operator = operatorStack.pop();
                        Predicate right = predicateStack.pop();
                        Predicate left = predicateStack.pop();
                        predicateStack.push(combinePredicates(builder, left, right, operator));
                    }
                    operatorStack.push(condition);
                } else {
                    String[] parts = condition.split("=|!=|>=|<=|>|<|~|!~", 2);
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Некорректный формат запроса. Ожидаемый формат: 'поле оператор значение'");
                    }

                    String field = parts[0].trim();
                    String operator = condition.substring(parts[0].length(), condition.length() - parts[1].length()).trim();
                    String value = parts[1].trim().replace("\"", "");

                    Path<Object> path;
                    try {
                        path = root.get(field);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Не удалось определить поле '" + field + "' типа '" + root.getJavaType().getName() + "'");
                    }

                    Predicate predicate = switch (operator) {
                        case "=" -> builder.equal(path, value);
                        case "!=" -> builder.notEqual(path, value);
                        case ">" -> builder.greaterThan(path.as(String.class), value);
                        case ">=" -> builder.greaterThanOrEqualTo(path.as(String.class), value);
                        case "<" -> builder.lessThan(path.as(String.class), value);
                        case "<=" -> builder.lessThanOrEqualTo(path.as(String.class), value);
                        case "~" -> builder.like(path.as(String.class), value);
                        case "!~" -> builder.notLike(path.as(String.class), value);
                        default -> throw new IllegalArgumentException("Некорректный оператор: " + operator);
                    };

                    predicateStack.push(predicate);
                }
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
