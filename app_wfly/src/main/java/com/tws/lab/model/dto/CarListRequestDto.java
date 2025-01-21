package com.tws.lab.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarListRequestDto {
    private String query;
    private int limit;
    private int offset;
} 