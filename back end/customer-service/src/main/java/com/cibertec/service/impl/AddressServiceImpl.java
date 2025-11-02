package com.cibertec.service.impl;

import com.cibertec.dto.AddressRequest;
import com.cibertec.dto.AddressResponse;
import com.cibertec.model.Address;
import com.cibertec.repository.AddressRepository;
import com.cibertec.service.AddressService;
import com.cibertec.util.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    @Override
    public List<AddressResponse> getAllAddresses() {
        return addressMapper.toDtoList(addressRepository.findAll());
    }
    @Override
    public AddressResponse getAddressById(Long id) {
        return addressMapper.toDto(addressRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Address not found with id: " + id)
        ));
    }
    @Override
    public AddressResponse createAddress(AddressRequest AddressRequest) {
        return addressMapper.toDto(addressRepository.save(addressMapper.toEntity(AddressRequest)));
    }

    @Override
    public AddressResponse updateAddress(Long id, AddressRequest addressRequest) {
        Address addressFund = addressRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Address not found with id: " + id)
        );
        addressFund.setCustomer(addressMapper.toEntity(addressRequest).getCustomer());
        addressFund.setLabel(addressRequest.label());
        addressFund.setStreet(addressRequest.street());
        addressFund.setExternalNumber(addressRequest.externalNumber());
        addressFund.setInternalNumber(addressRequest.internalNumber());
        addressFund.setNeighborhood(addressRequest.neighborhood());
        addressFund.setCity(addressRequest.city());
        addressFund.setState(addressRequest.state());
        addressFund.setZipCode(addressRequest.zipCode());
        addressFund.setCountry(addressRequest.country());
        addressFund.setReference(addressRequest.reference());
        addressFund.setIsDefault(addressRequest.isDefault());
        return addressMapper.toDto(addressRepository.save(addressFund));
    }

    @Override
    public void deleteAddress(Long id) {
        Address addressFund = addressRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Address not found with id: " + id)
        );
        addressRepository.delete(addressFund);
    }

    @Override
    public List<AddressResponse> getAddressesByUserId(Long id) {
        return addressMapper.toDtoList(addressRepository.findByCustomer_UserId(id));
    }
}
