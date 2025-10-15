package com.cibertec.dto;

import lombok.Builder;

@Builder
public record AddressResponse(
        Long id,
        CustomerResponse customer,
        String label,
        String street,
        String externalNumber,
        String internalNumber,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String country,
        String reference,
        Boolean isDefault,
        String createdAt,
        String updatedAt
) {
}
