package com.example.nextseguros.service;
import com.example.nextseguros.dto.ClientDTO;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.repository.ClientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    public Client create(@Valid ClientDTO dto) {
        Client client = dto.toEntity();
        return repository.save(client);
    }

    @Transactional
    public Client update(Long id,@Valid ClientDTO dto) {
        Client client = repository.findById(id).orElse(null);
        if(client != null) {
            Client updatedClient = dto.toEntityUpdate(client);
            return repository.save(updatedClient);
        }
        return null;
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
