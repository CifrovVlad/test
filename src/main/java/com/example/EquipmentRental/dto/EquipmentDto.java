package com.example.EquipmentRental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDto {

    private Long id;
    private String model;
    private String brand;
    private BigDecimal rentalPricePerDay;
    private boolean active;
}