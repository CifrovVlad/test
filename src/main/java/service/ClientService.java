package service;
import dto.ClientDto;
import dto.RegistrRequest;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientDto registerClient(RegistrRequest registrRequest);
    Optional<ClientDto> getClientById(Long id);
    List<ClientDto> getAllClients();
}
