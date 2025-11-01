package com.cibertec.service.impl;

import com.cibertec.client.UserClient;
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

    private final UserClient userClient;

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
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        userClient.findById(customerRequest.userId());
        return customerMapper.toDto(customerRepository.save(customerMapper.toEntity(customerRequest)));
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer customerFound = customerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer not found with id: " + id)
        );

        userClient.findById(customerRequest.userId());

        customerFound.setUserId(customerRequest.userId());
        customerFound.setLoyaltyPoints(customerRequest.loyaltyPoints());
        customerFound.setBirthDate(customerRequest.birthDate());
        customerFound.setNotes(customerRequest.notes());
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
