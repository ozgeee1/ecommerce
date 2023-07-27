package com.ecommerce.usermanagement.repository;

import com.ecommerce.usermanagement.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address,Long> {

    List<Address> findByUserId(Long userId);
}
