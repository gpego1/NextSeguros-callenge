package com.example.nextseguros.controller;
import com.example.nextseguros.dto.ClientDTO;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Client> getAllClients() {
        return service.getAllClients();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Optional<Client> getClientById(@PathVariable Long id) {
        return service.getClientById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Client createClient(@RequestBody ClientDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Client updateClient(@PathVariable Long id, @RequestBody @Valid ClientDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {service.deleteById(id);}




}
