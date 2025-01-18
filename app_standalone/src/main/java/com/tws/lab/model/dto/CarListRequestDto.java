package com.tws.lab.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarListRequestDto {
    private String query;
    private Integer offset;
    private Integer limit;
}
