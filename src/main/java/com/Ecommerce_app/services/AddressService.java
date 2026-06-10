package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.AddressRequest;
import com.Ecommerce_app.entities.Address;
import com.Ecommerce_app.entities.User;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.AddressRepository;
import com.Ecommerce_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public Address addAddress(
            String email,
            AddressRequest request) {

        User user =
                userRepository
                        .findByUsername(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        Address address =
                new Address();

        address.setFullName(
                request.getFullName());

        address.setMobile(
                request.getMobile());

        address.setAddressLine1(
                request.getAddressLine1());

        address.setAddressLine2(
                request.getAddressLine2());

        address.setCity(
                request.getCity());

        address.setState(
                request.getState());

        address.setPincode(
                request.getPincode());

        address.setUser(user);

        return addressRepository
                .save(address);
    }

    public List<Address> getAddresses(
            String email) {

        User user =
                userRepository
                        .findByUsername(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        return addressRepository
                .findByUser(user);
    }

    public void deleteAddress(
            Long addressId) {

        addressRepository
                .deleteById(addressId);
    }
}
