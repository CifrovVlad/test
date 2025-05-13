package com.example.EquipmentRental.repository;

import com.example.EquipmentRental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
   List<Rental> findByClientId(Long clientId);
   List<Rental> findByEquipmentId(Long equipmentId);
}