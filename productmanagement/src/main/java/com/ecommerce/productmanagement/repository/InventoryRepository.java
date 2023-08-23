package com.ecommerce.productmanagement.repository;

import com.ecommerce.productmanagement.domain.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory,Long> {

    Optional<Inventory> findByProductId(Long productId);
}
