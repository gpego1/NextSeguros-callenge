package com.example.nextseguros.dto;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.model.Insurance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String type;

    @NotNull
    private Integer risk;

    @NotBlank
    @Size(min = 50, max = 400)
    private String analysis;

    @NotBlank
    @Size(min = 10, max = 400)
    private String observation;


    @NotNull
    private LocalDateTime validateAt;

    @NotNull
    private Client clientInsurance;

    public Insurance toEntity() {
        return Insurance.builder()
                .type(this.type)
                .risk(this.risk)
                .analysis(this.analysis)
                .observation(this.observation)
                .createdAt(LocalDateTime.now())
                .validateAt(this.validateAt)
                .clientInsurance(this.clientInsurance)
                .build();
    }

    public Insurance toEntityUpdate(Insurance insurance) {
        return Insurance.builder()
                .id(insurance.getId())
                .type(this.type)
                .risk(this.risk)
                .analysis(this.analysis)
                .observation(this.observation)
                .createdAt(insurance.getCreatedAt())
                .validateAt(this.validateAt)
                .clientInsurance(insurance.getClientInsurance())
                .build();
    }
}
