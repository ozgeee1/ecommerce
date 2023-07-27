package com.ecommerce.usermanagement.controller;

import com.ecommerce.usermanagement.domain.Address;
import com.ecommerce.usermanagement.request.AddressRequest;
import com.ecommerce.usermanagement.service.AddressService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> addAddressToUser(@PathVariable Long userId, @RequestBody AddressRequest addressRequest){
        return addressService.addAddress(userId,addressRequest);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateAddress(@RequestParam Long userId,@RequestParam Long addressId, @RequestBody AddressRequest addressRequest){
        return addressService.updateAddress(userId,addressId,addressRequest);
    }

    @GetMapping("/select-address")
    public ResponseEntity<?> selectAddressForUser(@RequestParam("userId") Long userId,@RequestParam("addressId") Long addressId){
        return addressService.selectAddress(userId,addressId);
    }
}
