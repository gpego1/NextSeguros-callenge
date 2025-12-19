package com.example.nextseguros.controller;
import com.example.nextseguros.dto.InsuranceDTO;
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


//    @PostMapping("/life")
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public Insurance createInsuranceTypeLife(@Valid InsuranceDTO request){
//        InsuranceDTO dto = new InsuranceDTO();
//        if(request != null) {
//            Client clientObj = request.getClientInsurance();
//            dto.setClientInsurance(clientObj);
//            dto.setType(Insurance.InsuranceType.LIFE);
//            return service.create(dto);
//        }
//        return null;
//    }


}
