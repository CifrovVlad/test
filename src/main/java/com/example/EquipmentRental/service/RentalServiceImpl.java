package com.example.EquipmentRental.service;
import com.example.EquipmentRental.dto.RentalDto;
import com.example.EquipmentRental.exeptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.EquipmentRental.model.Client;
import com.example.EquipmentRental.model.Equipment;
import com.example.EquipmentRental.model.Rental;
import org.springframework.stereotype.Service;
import com.example.EquipmentRental.repository.ClientRepository;
import com.example.EquipmentRental.repository.EquipmentRepository;
import com.example.EquipmentRental.repository.RentalRepository;g
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ClientRepository clientRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    public RentalDto createRental(RentalDto request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + request.getClientId()));

        Equipment equipment = equipmentRepository.findById(request.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found with ID: " + request.getEquipmentId()));

        Rental rental = new Rental();
        rental.setClient(client);
        rental.setEquipment(equipment);
        rental.setRental_date(request.getRentalDate());
        rental.setReturn_date(request.getReturnDate());
        rental.setStatus("ACTIVE");

        Rental savedRental = rentalRepository.save(rental);

        return new RentalDto(
                savedRental.getId(),
                savedRental.getClient().getId(),
                savedRental.getEquipment().getId(),
                savedRental.getRental_date(),
                savedRental.getReturn_date(),
                savedRental.getStatus()
        );
    }

    @Override
    public List<RentalDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rental -> new RentalDto(
                        rental.getId(),
                        rental.getClient().getId(),
                        rental.getEquipment().getId(),
                        rental.getRental_date(),
                        rental.getReturn_date(),
                        rental.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public RentalDto updateRental(Long id, RentalDto request) {
        Rental rental = rentalRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Rental not found with ID: " + id));

        rental.setReturn_date(request.getReturnDate());
        rental.setRental_date(request.getRentalDate());
        rental.setStatus(request.getStatus());

        Rental savedRental = rentalRepository.save(rental);

        return new RentalDto(savedRental.getId(),
                savedRental.getClient().getId(),
                savedRental.getEquipment().getId(),
                savedRental.getRental_date(),
                savedRental.getReturn_date(),
                savedRental.getStatus());
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    public List<RentalDto> getRentalHistoryByUserId(Long userId) {
        List<Rental> rentals = rentalRepository.findByClientId(userId);
        List<RentalDto> rentalDtos = new ArrayList<>();

        for (Rental rental : rentals) {
            Equipment equipment = equipmentRepository.findById(rental.getEquipment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Equipment not found for ID: " + rental.getEquipment().getId()));

            RentalDto rentalDto = convertToDto(rental);
            rentalDtos.add(rentalDto);
        }
        return rentalDtos;
    }

    private RentalDto convertToDto(Rental rental) {
        return new RentalDto(rental.getId(), rental.getEquipment().getId(), rental.getClient().getId(), rental.getRental_date(), rental.getReturn_date(), rental.getStatus());
    }
}