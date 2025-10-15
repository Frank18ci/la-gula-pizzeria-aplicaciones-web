package com.cibertec.util;

import com.cibertec.dto.CustomerRequest;
import com.cibertec.dto.CustomerResponse;
import com.cibertec.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerRequest customerRequest);
    CustomerResponse toDto(Customer customer);
    List<CustomerResponse> toDtoList(List<Customer> customerList);
}
