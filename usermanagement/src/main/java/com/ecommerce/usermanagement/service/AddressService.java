package com.ecommerce.usermanagement.service;

import com.ecommerce.usermanagement.domain.Address;
import com.ecommerce.usermanagement.domain.User;
import com.ecommerce.usermanagement.exception.BadRequestException;
import com.ecommerce.usermanagement.mapper.AddressMapper;
import com.ecommerce.usermanagement.repository.AddressRepository;
import com.ecommerce.usermanagement.repository.UserRepository;
import com.ecommerce.usermanagement.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserService userService;

    public ResponseEntity<?> addAddress(Long userId, AddressRequest request){
        if (isHeaderUsedBefore(userId,request.getHeader())){
            throw new BadRequestException("This header exists for user "+userId);
        }
        Address address = AddressMapper.MAPPER.requestToAddress(request);
        User userById = userService.getUserById(userId);
        address.setUser(userById);
        addressRepository.save(address);

        userById.getAddressSet().add(address);
        userService.saveUserToDB(userById);
        return ResponseEntity.ok("Address is created for the user ");
    }

    public ResponseEntity<?> updateAddress(Long userId,Long addressId,AddressRequest request){
        if (isHeaderUsedBefore(userId,request.getHeader())){
            throw new BadRequestException("This header exists for user "+userId);
        }
        Address beforeUpdate = getAddressById(addressId);
        Address afterUpdate = AddressMapper.MAPPER.updateAddress(request, beforeUpdate);
        addressRepository.save(afterUpdate);
        return ResponseEntity.ok("Address is updated");
    }

    public ResponseEntity<?> selectAddress(Long userId,Long addressId){
        List<Address> addressesByUserId = getAddressesByUserId(userId);
        boolean isValidId = addressesByUserId.stream().anyMatch(address -> Objects.equals(address.getId(), addressId));
        if(isValidId){
            Address addressById = getAddressById(addressId);
            User userById = userService.getUserById(userId);
            userById.setSelectedAddressId(addressId);
            userService.saveUserToDB(userById);
            return ResponseEntity.ok(String.format("Selected address information\n%s", addressById.toString()));
        }
        throw new BadRequestException("There is no such address for this user");
    }

    public List<Address> getAddressesByUserId(Long userId){
        return addressRepository.findByUserId(userId);
    }

    public boolean isHeaderUsedBefore(Long userId,String header){
        List<Address> addressesByUserId = getAddressesByUserId(userId);
        return addressesByUserId.stream().anyMatch(address -> address.getHeader().equalsIgnoreCase(header));
    }

    public Address getAddressById(Long id){
        Optional<Address> byId = addressRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new BadRequestException("There is no address with this id "+id);
    }
}
