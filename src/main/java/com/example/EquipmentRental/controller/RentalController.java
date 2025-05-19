package com.example.EquipmentRental.controller;

import com.example.EquipmentRental.dto.RentalDto;
import com.example.EquipmentRental.exeptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.EquipmentRental.service.RentalService;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    @GetMapping
    public ResponseEntity<List<RentalDto>> getAllRentals() {
        List<RentalDto> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/history/user/{userId}")
    public ResponseEntity<List<RentalDto>> getRentalHistoryByUser(@PathVariable Long userId) {
        List<RentalDto> rentalHistory = rentalService.getRentalHistoryByUserId(userId);
        if (rentalHistory.isEmpty()) {
            throw new ResourceNotFoundException("Rental history not found for user with ID: " + userId);
        }
        return ResponseEntity.ok(rentalHistory);
    }

    @PostMapping
    public ResponseEntity<RentalDto> createRental(@RequestBody RentalDto rentalDto) {
        RentalDto rentalDto1 = rentalService.createRental(rentalDto);
        return ResponseEntity.ok(rentalDto1);

    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDto> updateRental(@PathVariable Long id, @RequestBody RentalDto rentalDto) {
        RentalDto updatedRental = rentalService.updateRental(id, rentalDto);
        return ResponseEntity.ok(updatedRental);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}