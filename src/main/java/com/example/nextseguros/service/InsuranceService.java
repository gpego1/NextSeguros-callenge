package com.example.nextseguros.service;
import com.example.nextseguros.dto.InsuranceDTO;
import com.example.nextseguros.model.Insurance;
import com.example.nextseguros.repository.InsuranceRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository repository;

    public List<Insurance> getAll() {
        return repository.findAll();
    }

    public Optional<Insurance> getInsuranceById(Long id) {
        return repository.findById(id);
    }

    public Insurance create(@Valid InsuranceDTO dto) {
        Insurance insurance = dto.toEntity();
        insurance.setCreatedAt(LocalDateTime.now());
        return repository.save(insurance);
    }

    @Transactional
    public Insurance update(Long id, @Valid InsuranceDTO dto) {
        Insurance insurance = repository.findById(id).orElse(null);

        if(insurance != null) {
            Insurance updatedInsurance = dto.toEntityUpdate(insurance);
            return repository.save(updatedInsurance);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
