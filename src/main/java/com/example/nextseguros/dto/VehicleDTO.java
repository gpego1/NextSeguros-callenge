package com.example.nextseguros.dto;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.model.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {

    @NotBlank
    @Size(min = 2, max = 50)
    private String brand;

    @NotBlank
    @Size(min = 2, max = 100)
    private String model;

    @NotNull
    private Integer year;

    @NotNull
    private Client clientVehicle;

    public Vehicle toEntity() {
        return Vehicle.builder()
                .brand(this.brand)
                .model(this.model)
                .year(this.year)
                .clientVehicle(this.clientVehicle)
                .build();
    }

    public Vehicle toEntityUpdate(Vehicle vehicle) {
        return Vehicle.builder()
                .id(vehicle.getId())
                .brand(this.brand)
                .model(this.model)
                .year(this.year)
                .clientVehicle(vehicle.getClientVehicle())
                .build();
    }

}
