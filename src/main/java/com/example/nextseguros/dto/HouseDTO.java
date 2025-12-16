package com.example.nextseguros.dto;
import com.example.nextseguros.model.Client;
import com.example.nextseguros.model.House;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String ownershipStatus;

    @NotBlank
    @Size(min = 2, max = 100)
    private String location;

    @NotBlank
    @Size(min = 2, max = 100)
    private String zipcode;

    @NotNull
    private Client client;

    public House toEntity() {
        return House.builder()
                .ownershipStatus(this.ownershipStatus)
                .location(this.location)
                .zipcode(this.zipcode)
                .client(this.client)
                .build();
    }

    public House toEntityUpdate(House house){
        return House.builder()
                .id(house.getId())
                .ownershipStatus(house.getOwnershipStatus())
                .location(this.location)
                .zipcode(this.zipcode)
                .client(this.client)
                .build();
    }
}
