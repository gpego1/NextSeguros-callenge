package com.example.nextseguros.service;
import com.example.nextseguros.dto.InsuranceVehicleDTO;
import com.example.nextseguros.dto.VehicleDTO;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.model.House;
import com.example.nextseguros.model.Insurance;
import com.example.nextseguros.model.Vehicle;
import com.example.nextseguros.repository.ClientRepository;
import com.example.nextseguros.repository.HouseRepository;
import com.example.nextseguros.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;

    public List<Vehicle> getAll() {
        return repository.findAll();
    }

    public Optional<Vehicle> getVehicleByID(Long id) {
        return repository.findById(id);
    }

    public Vehicle create(@Valid VehicleDTO dto) {
        Vehicle createdVehicle = dto.toEntity();
        return repository.save(createdVehicle);
    }


    @Transactional
    public Vehicle update(Long id, @Valid VehicleDTO dto) {
        Vehicle vehicle = repository.findById(id).orElse(null);
        if (vehicle != null) {
            Vehicle updatedVehicle = dto.toEntityUpdate(vehicle);
            return repository.save(updatedVehicle);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
