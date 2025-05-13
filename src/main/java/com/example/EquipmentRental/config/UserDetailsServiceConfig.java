package com.example.EquipmentRental.config;

import com.example.EquipmentRental.model.Client;
import com.example.EquipmentRental.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig {

    private final ClientRepository clientRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Client client = clientRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Client not found with email: " + username));
            return new com.example.EquipmentRental.config.CustomUserDetails(client);
        };
    }
}