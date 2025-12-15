package com.example.nextseguros.service;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return repository.findById(id);
    }

    public Client create(Client client) {
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        return repository.save(client);
    }

    @Transactional
    public Client update(Client client) {
        if(!repository.existsById(client.getId())) {
            throw new RuntimeException("Cannot find the cleint: "+ client.getName());
        }
        client.setUpdatedAt(LocalDateTime.now());
        return repository.save(client);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
