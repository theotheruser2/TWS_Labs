package com.tws.lab.service;

import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

@Component
public class QueryParser {
    private CriteriaBuilder builder;
    private Root<?> root;

    public void initialize(CriteriaBuilder builder, Root<?> root) {
        this.builder = builder;
        this.root = root;
    }

    public Predicate parse(String query) {
        if (query == null || query.trim().isEmpty()) {
            return null;
        }

        List<String> tokens = tokenize(query);
        return parseExpression(tokens.iterator());
    }

    private List<String> tokenize(String query) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
                current.append(c);
            } else if (!inQuotes && (c == ' ' || c == '(' || c == ')')) {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current = new StringBuilder();
                }
                if (c != ' ') {
                    tokens.add(String.valueOf(c));
                }
            } else {
                current.append(c);
            }
        }
        if (current.length() > 0) {
            tokens.add(current.toString());
        }
        return tokens;
    }

    private Predicate parseExpression(Iterator<String> tokens) {
        Stack<Predicate> predicates = new Stack<>();
        Stack<String> operators = new Stack<>();

        while (tokens.hasNext()) {
            String token = tokens.next();
            if (token.equals("(")) {
                predicates.push(parseExpression(tokens));
            } else if (token.equals(")")) {
                break;
            } else if (token.equalsIgnoreCase("AND") || token.equalsIgnoreCase("OR")) {
                operators.push(token);
            } else {
                String[] parts = token.split("(?<=[=><~])|(?=[=><~])");
                if (parts.length < 3) continue;

                String field = parts[0];
                String operator = parts[1];
                String value = String.join("", Arrays.copyOfRange(parts, 2, parts.length));

                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }

                Path<?> path = root.get(field);
                Class<?> type = path.getJavaType();
                Predicate predicate;

                if (Number.class.isAssignableFrom(type)) {
                    predicate = handleNumericField(operator, value, (Path<Number>) path);
                } else {
                    predicate = handleStringField(operator, value, (Path<String>) path);
                }
                predicates.push(predicate);
            }

            if (predicates.size() == 2 && !operators.isEmpty()) {
                Predicate right = predicates.pop();
                Predicate left = predicates.pop();
                String op = operators.pop();
                if (op.equalsIgnoreCase("AND")) {
                    predicates.push(builder.and(left, right));
                } else {
                    predicates.push(builder.or(left, right));
                }
            }
        }

        return predicates.isEmpty() ? null : predicates.pop();
    }

    private Predicate handleNumericField(String operator, String value, Path<Number> path) {
        try {
            Number numericValue = NumberFormat.getInstance().parse(value);
            switch (operator) {
                case "=":
                    return builder.equal(path, numericValue);
                case ">":
                    return builder.gt(path, numericValue);
                case ">=":
                    return builder.ge(path, numericValue);
                case "<":
                    return builder.lt(path, numericValue);
                case "<=":
                    return builder.le(path, numericValue);
                default:
                    throw new IllegalArgumentException("Неподдерживаемый оператор для числового поля: " + operator);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Неверный формат числа: " + value);
        }
    }

    private Predicate handleStringField(String operator, String value, Path<String> path) {
        switch (operator) {
            case "=":
                return builder.equal(path, value);
            case "~":
                return builder.like(path, value);
            default:
                throw new IllegalArgumentException("Неподдерживаемый оператор для строкового поля: " + operator);
        }
    }
} 