package service;
import dto.AuthResponse;
import dto.LoginRequest;
import dto.RegistrRequest;
import lombok.RequiredArgsConstructor;
import model.Client;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.ClientRepository;
import security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegistrRequest registrRequest) {
           Client client = new Client();
           client.setFullName(registrRequest.getFullName());
           client.setPassword(passwordEncoder.encode(registrRequest.getPassword()));
           client.setEmail(registrRequest.getEmail());
           client.setPhone(registrRequest.getPhone());

           clientRepository.save(client);

           String token = jwtService.generateToken(client.getEmail());
           return new AuthResponse(token);
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
       Client client = clientRepository.findByEmail(loginRequest.getEmail())
               .orElseThrow(() -> new UsernameNotFoundException("User not found"));

       if (!passwordEncoder.matches(loginRequest.getPassword(), client.getPassword())) {
           throw new BadCredentialsException("Wrong password");
       }
       String token = jwtService.generateToken(client.getEmail());
       return new AuthResponse(token);
    }
}
