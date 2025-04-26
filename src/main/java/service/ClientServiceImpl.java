package service;

import dto.ClientDto;
import dto.RegistrRequest;
import lombok.RequiredArgsConstructor;
import model.Client;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.ClientRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public ClientDto registerClient(RegistrRequest registrRequest) {
        Client client = new Client();
        client.setFullName(registrRequest.getFullName());
        client.setEmail(registrRequest.getEmail());
        client.setPhone(registrRequest.getPhone());

        Client savedClient = clientRepository.save(client);

        String encodedPassword = passwordEncoder.encode(registrRequest.getPassword());
        client.setPassword(encodedPassword);


        return new ClientDto(savedClient.getId(), savedClient.getFullName(), savedClient.getEmail(), savedClient.getPhone());
    }

    @Override
    public Optional<ClientDto> getClientById(Long id) {
        return clientRepository.findById(id)
                .map(client -> new ClientDto(client.getId(), client.getFullName(), client.getEmail(), client.getPhone()));
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientDto(client.getId(), client.getFullName(), client.getEmail(), client.getPhone()))
                .toList();
    }
}
