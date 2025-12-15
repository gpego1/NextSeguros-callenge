package com.example.nextseguros.controller;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.service.ClientService;
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
    public Client createClient(@RequestBody Client client) {
        return service.create(client);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> optionalClient = service.getClientById(id);

        if(optionalClient.isEmpty()){
            throw new RuntimeException("Cannot find te client: " + client.getName());
        }

        Client updatedClient = optionalClient.get();
        updatedClient.setName(client.getName());
        updatedClient.setAge(client.getAge());
        updatedClient.setIncome(client.getIncome());
        updatedClient.setDependents(client.getDependents());
        updatedClient.setCreatedAt(client.getCreatedAt());
        updatedClient.setMarital_status(client.getMarital_status());
        updatedClient.setCreatedAt(client.getCreatedAt());
        return service.update(updatedClient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        service.deleteById(id);
    }




}
