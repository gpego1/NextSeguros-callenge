package com.example.nextseguros.service;
import com.example.nextseguros.dto.VehicleDTO;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.model.House;
import com.example.nextseguros.model.Vehicle;
import com.example.nextseguros.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
