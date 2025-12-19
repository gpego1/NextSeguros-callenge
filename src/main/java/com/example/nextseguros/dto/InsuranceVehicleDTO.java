package com.example.nextseguros.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceVehicleDTO {

    @NotNull
    private List<Boolean> risk_questions;
    @Size(min = 0, max = 100)
    private String observation;
    @NotNull
    @Min(1)
    private long client_id;

    @NotNull
    @Min(1)
    private long vehicle_id;
}
