package service;
import dto.RentalDto;
import lombok.RequiredArgsConstructor;
import model.Client;
import model.Equipment;
import model.Rental;
import org.springframework.stereotype.Service;
import repository.ClientRepository;
import repository.EquipmentRepository;
import repository.RentalRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private RentalRepository rentalRepository;
    private ClientRepository clientRepository;
    private EquipmentRepository equipmentRepository;

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
    public List<RentalDto> getByClientId(Long clientId) {
        return rentalRepository.findByClientId(clientId).stream()
                .map(r -> new RentalDto(r.getId(),
                        r.getClient().getId(),
                        r.getEquipment().getId(),
                        r.getRental_date(),
                        r.getReturn_date(),
                        r.getStatus()))
                .toList();
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
}