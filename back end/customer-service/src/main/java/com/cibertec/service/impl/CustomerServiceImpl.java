package com.cibertec.service.impl;

import com.cibertec.dto.CustomerRequest;
import com.cibertec.dto.CustomerResponse;
import com.cibertec.model.Customer;
import com.cibertec.repository.CustomerRepository;
import com.cibertec.service.CustomerService;
import com.cibertec.util.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerMapper.toDtoList(customerRepository.findAll());
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        return customerMapper.toDto(customerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer not found with id: " + id)
        ));
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest CustomerRequest) {
        return customerMapper.toDto(customerRepository.save(customerMapper.toEntity(CustomerRequest)));
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest CustomerRequest) {
        Customer customerFound = customerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer not found with id: " + id)
        );
        customerFound.setUserId(CustomerRequest.userId());
        customerFound.setLoyaltyPoints(CustomerRequest.loyaltyPoints());
        customerFound.setBirthDate(CustomerRequest.birthDate());
        customerFound.setNotes(CustomerRequest.notes());
        return customerMapper.toDto(customerRepository.save(customerFound));
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customerFound = customerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer not found with id: " + id)
        );
        customerRepository.delete(customerFound);
    }
}
