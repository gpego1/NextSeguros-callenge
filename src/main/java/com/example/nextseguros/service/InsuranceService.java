package com.example.nextseguros.service;
import com.example.nextseguros.dto.InsuranceDTO;
import com.example.nextseguros.dto.InsuranceHouseDTO;
import com.example.nextseguros.dto.InsuranceRequestDTO;
import com.example.nextseguros.dto.InsuranceVehicleDTO;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.model.House;
import com.example.nextseguros.model.Insurance;
import com.example.nextseguros.model.Vehicle;
import com.example.nextseguros.repository.ClientRepository;
import com.example.nextseguros.repository.HouseRepository;
import com.example.nextseguros.repository.InsuranceRepository;
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
public class InsuranceService {

    private final InsuranceRepository repository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;
    private final HouseRepository houseRepository;

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

    public Insurance createInsuranceVehicle(InsuranceVehicleDTO dto) {
        Client client = clientRepository.findById(dto.getClient_id()).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicle_id()).orElse(null);
        if (client != null && (vehicle != null && client.getVehicles().contains(vehicle))) {
            int risk = this.calculateRiskVehicle(dto.getRisk_questions(), client, vehicle);
            Insurance insurance = Insurance.builder()
                    .clientInsurance(client)
                    .type(Insurance.InsuranceType.AUTO)
                    .risk(risk)
                    .analysis(this.evaluateScore(risk))
                    .observation(dto.getObservation())
                    .createdAt(LocalDateTime.now())
                    .validateAt(LocalDateTime.now().plusDays(30))
                    .build();
            return repository.save(insurance);
        }
        return null;
    }

    public Insurance createInsuranceHouse(InsuranceHouseDTO dto) {
        Client client = clientRepository.findById(dto.getClient_id()).orElse(null);
        House house = houseRepository.findById(dto.getHouse_id()).orElse(null);
        if(client != null && (house != null && client.getHouses().contains(house))) {
            int risk = this.calculateRiskHouse(dto.getRisk_questions(), client, house);
            Insurance insurance = Insurance.builder()
                    .clientInsurance(client)
                    .type(Insurance.InsuranceType.HOME)
                    .risk(risk)
                    .analysis(this.evaluateScore(risk))
                    .observation(dto.getObservation())
                    .createdAt(LocalDateTime.now())
                    .validateAt(LocalDateTime.now().plusDays(30))
                    .build();
            return repository.save(insurance);
        }
        return null;
    }

    public Insurance createInsuranceDisability(InsuranceRequestDTO requestDTO) {
        Client client = clientRepository.findById(requestDTO.getClient_id()).orElse(null);
        if (client.getId() != null && client.getIncome() > 0) {
            int risk = this.calculateRiskDisability(requestDTO.getRisk_questions(), client);
            Insurance insurance = Insurance.builder()
                    .clientInsurance(client)
                    .type(Insurance.InsuranceType.DISABILITY)
                    .risk(risk)
                    .analysis(this.evaluateScore(risk))
                    .observation(requestDTO.getObservation())
                    .createdAt(LocalDateTime.now())
                    .validateAt(LocalDateTime.now().plusDays(30))
                    .build();
            return repository.save(insurance);
        }
        return null;
    }

    public Insurance createInsuranceLife(InsuranceRequestDTO requestDTO) {
        Client client = clientRepository.findById(requestDTO.getClient_id()).orElse(null);
        if (client.getId() != null && client.getAge() < 60) {
            int risk = this.calculateRiskLife(requestDTO.getRisk_questions(), client);
            Insurance insurance = Insurance.builder()
                    .clientInsurance(client)
                    .type(Insurance.InsuranceType.LIFE)
                    .risk(risk)
                    .analysis(this.evaluateScore(risk))
                    .observation(requestDTO.getObservation())
                    .createdAt(LocalDateTime.now())
                    .validateAt(LocalDateTime.now().plusDays(30))
                    .build();
            return repository.save(insurance);
        }
        return null;
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


    private int calculateRiskLife(List<Boolean> riskQuestions, Client client) {
        int risk = 0;
        risk += this.calculateCommunRisk(riskQuestions, client);
        risk += this.calculateRiskByHouses(client);
        risk = this.calculateRiskByDependents(client);
        risk += this.calculateRiskByMaritalStatus(client);
        return risk;
    }


    private int calculateRiskDisability(List<Boolean> riskQuestions, Client client){
        int risk = 0;
        risk += this.calculateCommunRisk(riskQuestions, client);
        risk += this.calculateRiskByHouses(client);
        risk += this.calculateRiskByDependents(client);
        risk += this.calculateRiskByMaritalStatus(client);
        return risk;
    }

    private int calculateRiskVehicle(List<Boolean> riskQuestions, Client client, Vehicle vehicle) {
        int risk = 0;
        risk += this.calculateCommunRisk(riskQuestions, client);
        risk += this.calculateRiskPointByVehicle(vehicle);
        return risk;
    }

    private int calculateRiskHouse(List<Boolean> riskQuestions, Client client, House house) {
        int risk = 0;
        risk += this.calculateCommunRisk(riskQuestions, client);
        risk += this.calculateRiskPointsByHouse(house);
        return risk;
    }

    private int calculateCommunRisk(List<Boolean> riskQuestions, Client client) {
        int risk = 0;
        risk += this.calculateBaseScore(riskQuestions);
        risk += this.calculateRiskPointsByAge(client);
        risk += this.calculateRisPointsByIncome(client);
        return risk;
    }

    private int calculateBaseScore(List<Boolean> riskQuestions){
        int sum = 0;
        for (Boolean risk : riskQuestions) {
            sum += risk ? 1 : 0;
        }
        return sum;
    }

    private int calculateRiskPointsByAge(Client client) {
        if(client.getAge() < 30) {
            return -2;
        }
        else if (client.getAge() >= 30 && client.getAge() <= 40) {
            return -1;
        }
        else {
            return 0;
        }
    }

    private int calculateRiskByHouses(Client client) {
        int risk = 0;
        for(House house : client.getHouses()) {
            risk += this.calculateRiskPointsByHouse(house);
        }
        return risk;
    }

    private int calculateRiskPointsByHouse(House house) {
        if(house.getOwnershipStatus().equals("HIPOTECADA")){
            return 1;
        }
        return 0;
    }

    private int calculateRisPointsByIncome(Client client) {
        if(client.getIncome() > 200000) {
            return -1;
        }
        return 0;
    }

    private int calculateRiskPointByVehicle(Vehicle vehicle){
        if(vehicle.getYear() >= Calendar.getInstance().get(Calendar.YEAR) - 5){
            return 1;
        }
        return 0;
    }

    private int calculateRiskByDependents(Client client){
        if(client.getDependents() > 0) {
            return 1;
        }
        return 0;
    }

    private int calculateRiskByMaritalStatus(Client client) {
        if(client.getMarital_status().equals("CASADO")){
            return 1;
        }
        return 0;
    }

    private String evaluateScore(int score) {
        if (score >= 0){
            return "ECONOMIC";
        }
        else if (score >= 1 && score <= 2) {
            return "REGULAR";
        }
        else {
            return "RESPONSIBLE";
        }
    }
}
