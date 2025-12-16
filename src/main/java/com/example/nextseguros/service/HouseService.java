package com.example.nextseguros.service;
import com.example.nextseguros.dto.HouseDTO;
import com.example.nextseguros.model.House;
import com.example.nextseguros.repository.HouseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository repository;

    public List<House> getAll() {
        return repository.findAll();
    }

    public Optional<House> getById(Long id) {
        return repository.findById(id);
    }

    public House create(@Valid HouseDTO dto) {
        House createdHouse = dto.toEntity();
        return repository.save(createdHouse);
    }

    @Transactional
    public House update(Long id, @Valid HouseDTO dto) {
        House house = repository.findById(id).orElse(null);
        if (house != null) {
            House updatedHouse = dto.toEntityUpdate(house);
            return repository.save(updatedHouse);
        }
        return null;
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
