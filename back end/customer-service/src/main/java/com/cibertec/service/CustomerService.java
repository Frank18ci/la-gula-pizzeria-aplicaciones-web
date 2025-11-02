package com.cibertec.service;

import com.cibertec.dto.CustomerRequest;
import com.cibertec.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long id);

    CustomerResponse createCustomer(CustomerRequest CustomerRequest);

    CustomerResponse updateCustomer(Long id, CustomerRequest CustomerRequest);

    void deleteCustomer(Long id);

    List<CustomerResponse> getCustomersById(Long id);

    CustomerResponse getCustomerByUserId(Long id);
}
