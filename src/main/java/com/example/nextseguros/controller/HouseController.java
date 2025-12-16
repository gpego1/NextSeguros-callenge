package com.example.nextseguros.controller;
import com.example.nextseguros.dto.HouseDTO;
import com.example.nextseguros.model.House;
import com.example.nextseguros.service.HouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseController {

    @Autowired
    private HouseService service;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<House> getAllHouses() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public House getHouseById(@PathVariable Long id){
        return service.getById(id).orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public House createHouse(@Valid @RequestBody HouseDTO dto){
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public House updateHouse(@PathVariable Long id, @Valid HouseDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteHouse(@PathVariable Long id) {service.deleteById(id);}
}
