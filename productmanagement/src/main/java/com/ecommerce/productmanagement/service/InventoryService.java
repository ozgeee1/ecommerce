package com.ecommerce.productmanagement.service;

import com.ecommerce.productmanagement.domain.Inventory;
import com.ecommerce.productmanagement.exception.BadRequestException;
import com.ecommerce.productmanagement.mapper.InventoryMapper;
import com.ecommerce.productmanagement.repository.InventoryRepository;
import com.ecommerce.productmanagement.request.InventoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean checkInventory(Long productId,int quantity){
        Inventory byProductId = getByProductId(productId);
        return byProductId.getProductQuantity()>=quantity;
    }

    public Inventory getByProductId(Long productId){
        Optional<Inventory> byId = inventoryRepository.findByProductId(productId);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new BadRequestException("There is no inventory with this id "+productId);
    }

    public ResponseEntity<?> addInventory(InventoryRequest inventoryRequest){
        Inventory inventory ;
        Optional<Inventory> byProductId = inventoryRepository.findByProductId(inventoryRequest.getProductId());
        if(byProductId.isPresent()){
            inventory = byProductId.get();
            inventory.setProductQuantity(inventory.getProductQuantity()+inventoryRequest.getProductQuantity());
        }
        else{
            inventory = InventoryMapper.MAPPER.requestToInventory(inventoryRequest);
        }
        inventoryRepository.save(inventory);
        return ResponseEntity.ok("Inventory is updated");

    }

}
