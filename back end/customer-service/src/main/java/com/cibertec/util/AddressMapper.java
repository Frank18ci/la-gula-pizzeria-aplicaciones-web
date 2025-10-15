package com.cibertec.util;

import com.cibertec.dto.AddressRequest;
import com.cibertec.dto.AddressResponse;
import com.cibertec.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { CustomerMapper.class})
public interface AddressMapper {
    @Mapping( source = "customerId", target = "customer.id" )
    Address toEntity(AddressRequest addressRequest);
    AddressResponse toDto(Address address);
    List<AddressResponse> toDtoList(List<Address> addressList);
}
