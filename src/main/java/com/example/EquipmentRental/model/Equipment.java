package com.example.EquipmentRental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String model;
    private String brand;
    private String serialNumber;
    private BigDecimal rentalPricePerDay;
    private boolean active;

    @OneToMany(mappedBy = "equipment")
    private List<Rental> rentals;
}