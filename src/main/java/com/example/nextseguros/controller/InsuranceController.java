package com.example.nextseguros.controller;
import com.example.nextseguros.dto.InsuranceDTO;
import com.example.nextseguros.dto.InsuranceHouseDTO;
import com.example.nextseguros.dto.InsuranceRequestDTO;
import com.example.nextseguros.dto.InsuranceVehicleDTO;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.model.Insurance;
import com.example.nextseguros.service.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insurances")
public class InsuranceController {

    @Autowired
    private InsuranceService service;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Insurance> getAllInsurances() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Optional<Insurance> getInsuranceById(@PathVariable Long id){
        return service.getInsuranceById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Insurance createInsurance(@RequestBody @Valid InsuranceDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Insurance updateInsurance(@PathVariable Long id,@RequestBody @Valid InsuranceDTO dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInsurance(@PathVariable Long id) {
        service.delete(id);
    }


    @PostMapping("/life")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Insurance createInsuranceTypeLife(@Valid @RequestBody InsuranceRequestDTO request){
        return service.createInsuranceLife(request);
    }

    @PostMapping("/home")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Insurance createInsuranceTypeHome(@Valid @RequestBody InsuranceHouseDTO request){
        return service.createInsuranceHouse(request);
    }

    @PostMapping("/disability")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Insurance createInsuranceTypeDisability(@Valid @RequestBody InsuranceRequestDTO request){
        return service.createInsuranceDisability(request);
    }

    @PostMapping("/auto")
    public Insurance createInsuranceTypeAuto(@Valid @RequestBody InsuranceVehicleDTO request){
        return service.createInsuranceVehicle(request);
    }

}
