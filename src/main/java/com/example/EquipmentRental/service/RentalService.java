package com.example.EquipmentRental.service;

import com.example.EquipmentRental.dto.RentalDto;
import java.util.List;

public interface RentalService {

    RentalDto createRental(RentalDto request);
    List<RentalDto> getAllRentals();
    RentalDto updateRental(Long id, RentalDto request);
    void deleteRental(Long id);
    public List<RentalDto> getRentalHistoryByUserId(Long userId);
}