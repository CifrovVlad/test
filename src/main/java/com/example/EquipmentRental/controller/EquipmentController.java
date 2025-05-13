package com.example.EquipmentRental.controller;

import com.example.EquipmentRental.dto.EquipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.EquipmentRental.service.EquipmentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<List<EquipmentDto>> getAllEquipment() {
        List<EquipmentDto> equipmentDtoList = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipmentDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<EquipmentDto>> getEquipmentById(@PathVariable Long id) {
        Optional<EquipmentDto> equipmentDto = equipmentService.getEquipmentById(id);
        return ResponseEntity.ok(equipmentDto);
    }

    @PostMapping
    public ResponseEntity<EquipmentDto> createEquipment(@RequestBody EquipmentDto equipmentDto) {
        EquipmentDto equipmentDto1 = equipmentService.createEquipment(equipmentDto);
        return ResponseEntity.ok(equipmentDto1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}