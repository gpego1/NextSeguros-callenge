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
public class InsuranceHouseDTO {

    @NotNull
    private List<Boolean> riskQuestions;
    @Size(min = 0, max = 100)
    private String observation;
    @NotNull
    @Min(1)
    private Long clientId;

    @NotNull
    @Min(1)
    private Long houseId;
}
