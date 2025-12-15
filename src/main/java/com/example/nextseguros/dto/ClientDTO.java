package com.example.nextseguros.dto;
import com.example.nextseguros.model.Client;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Min(0)
    private int age;

    @NotNull
    @Min(0)
    private int dependents;

    @NotNull
    @Min(0)
    private double income;

    @NotBlank
    @Size
    private String marital_status;

    public Client toEntity() {
        return Client.builder()
                .name(this.name)
                .age(this.age)
                .dependents(this.dependents)
                .income(this.income)
                .marital_status(this.marital_status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Client toEntityUpdate(Client client) {
        return Client.builder()
                .id(client.getId())
                .name(this.name)
                .age(this.age)
                .dependents(this.dependents)
                .income(this.income)
                .marital_status(this.marital_status)
                .createdAt(client.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
