package com.cibertec.service;

import com.cibertec.dto.AddressRequest;
import com.cibertec.dto.AddressResponse;

import java.util.List;

public interface AddressService {
    List<AddressResponse> getAllAddresses();

    AddressResponse getAddressById(Long id);

    AddressResponse createAddress(AddressRequest AddressRequest);

    AddressResponse updateAddress(Long id, AddressRequest addressRequest);

    void deleteAddress(Long id);

    List<AddressResponse> getAddressesByUserId(Long id);
}
