package service;

import dto.RentalDto;

import java.util.List;

public interface RentalService {

    RentalDto createRental(RentalDto request);
    List<RentalDto> getByClientId(Long clientId);
    List<RentalDto> getAllRentals();
    RentalDto updateRental(Long id, RentalDto request);
    void deleteRental(Long id);
}
