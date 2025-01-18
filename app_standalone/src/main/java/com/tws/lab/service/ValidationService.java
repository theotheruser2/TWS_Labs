package com.tws.lab.service;
import com.tws.lab.model.dto.CarDto;
import com.tws.lab.soap.errorHandling.*;
public class ValidationService {
    private static final String PHONE_REGEX = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private static final String LICENSE_REGEX = "^[ABEKMHOPCTYX]{1}\\d{3}[ABEKMHOPCTYX]{2}\\d{2,3}$";

    public static void validateCarDto(CarDto carDto) throws CarCrudException {
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (carDto == null) {
            throw new CarCrudException("null CarDto", new ErrorBean("Значение CarDto - null."));
        }
        if (carDto.getBrand() == null || carDto.getBrand().isEmpty()) {
            errorMessageBuilder.append("Поле brand не может быть пустым. ");
        }
        if (carDto.getModel() == null || carDto.getModel().isEmpty()) {
            errorMessageBuilder.append("Поле model не может быть пустым. ");
        }
        if (carDto.getRelease_year().equals(null) || carDto.getRelease_year() < 1885) {
            errorMessageBuilder.append("Год выпуска не может быть пустым или ранее 1885. ");
        }
        if (!carDto.getLicense_plate().matches(LICENSE_REGEX)) {
            errorMessageBuilder.append("Регистрационный номер должен иметь следующий формат: А123ВК123 (латиница). ");
        }
        if (!carDto.getOwner_phone().matches(PHONE_REGEX)) {
            errorMessageBuilder.append("Номер телефона должен иметь следующий формат: +71231231231. ");
        }
        if (!errorMessageBuilder.isEmpty()) {
            throw new CarCrudException("Ошибка валидации", new ErrorBean(errorMessageBuilder.toString().trim()));
        }
    }
}