package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.AddressRequest;
import com.Ecommerce_app.entities.Address;
import com.Ecommerce_app.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService
            addressService;

    //http://localhost:8080/addresses
    @PostMapping
    public Address addAddress(

            @RequestBody
            AddressRequest request,

            Authentication authentication) {

        return addressService
                .addAddress(
                        authentication.getName(),
                        request);
    }

    @GetMapping
    public List<Address> getAddresses(
            Authentication authentication) {

        return addressService
                .getAddresses(
                        authentication.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(
            @PathVariable Long id) {

        addressService.deleteAddress(id);

        return "Address deleted successfully";
    }
}