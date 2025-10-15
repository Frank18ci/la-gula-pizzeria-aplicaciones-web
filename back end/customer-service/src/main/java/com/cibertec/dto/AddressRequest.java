package com.cibertec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddressRequest(
        @NotNull(message = "customerId no puede ser nulo")
        Long customerId,
        @NotBlank(message = "label no puede estar vacio")
        String label,
        @NotBlank(message = "street no puede estar vacio")
        String street,
        @NotBlank(message = "externalNumber no puede estar vacio")
        String externalNumber,
        @NotBlank(message = "internalNumber no puede estar vacio")
        String internalNumber,
        @NotBlank(message = "neighborhood no puede estar vacio")
        String neighborhood,
        @NotBlank(message = "city no puede estar vacio")
        String city,
        @NotBlank(message = "state no puede estar vacio")
        String state,
        @NotBlank(message = "zipCode no puede estar vacio")
        String zipCode,
        @NotBlank(message = "country no puede estar vacio")
        String country,
        @NotBlank(message = "reference no puede estar vacio")
        String reference,
        @NotNull(message = "isDefault no puede estar nulo")
        Boolean isDefault
) {

}
