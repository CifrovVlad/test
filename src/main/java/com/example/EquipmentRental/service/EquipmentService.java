package com.example.EquipmentRental.service;

import com.example.EquipmentRental.dto.EquipmentDto;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {

    List<EquipmentDto> getAllEquipment();
    Optional<EquipmentDto> getEquipmentById(Long id);
    EquipmentDto createEquipment(EquipmentDto equipmentDto);
    void deleteEquipment(Long id);
}