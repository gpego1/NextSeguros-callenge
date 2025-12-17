package com.example.nextseguros.controller;
import com.example.nextseguros.dto.VehicleDTO;
import com.example.nextseguros.model.Vehicle;
import com.example.nextseguros.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Vehicle> getAllVehicles() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Optional<Vehicle> getVehicleById(@PathVariable Long id){
        return service.getVehicleByID(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Vehicle createVehicle(@RequestBody VehicleDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Vehicle updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable Long id) {
        service.delete(id);
    }

}
