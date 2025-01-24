package com.tws.lab.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarValidationException.class)
    public ResponseEntity<ErrorResponse> handleCarValidationException(CarValidationException e) {
        ErrorResponse error = new ErrorResponse("Ошибка валидации", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        String message = e.getMessage();
        if (message.contains("Incorrect query format") || message.contains("Could not resolve field")) {
            message = "Некорректный формат запроса. Ожидаемый формат: 'поле оператор значение'";
        } else if (message.contains("Invalid operator")) {
            message = "Некорректный оператор. Допустимые операторы: =, !=, >, >=, <, <=";
        } else if (message.contains("Invalid numeric value")) {
            message = "Некорректное числовое значение для поля";
        }
        ErrorResponse error = new ErrorResponse("Ошибка при обработке запроса", message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        String message = e.getMessage();
        if (message.contains("Incorrect query format")) {
            message = "Некорректный формат запроса. Ожидаемый формат: 'поле оператор значение'";
            return new ResponseEntity<>(new ErrorResponse("Ошибка при обработке запроса", message), HttpStatus.BAD_REQUEST);
        }
        ErrorResponse error = new ErrorResponse("Внутренняя ошибка сервера", message);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 