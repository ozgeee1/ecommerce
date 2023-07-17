package com.ecommerce.usermanagement.repository;

import com.ecommerce.usermanagement.domain.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority,Long> {

    @Query(value = "SELECT * FROM authorities a WHERE a.user_id=?1",nativeQuery = true)
    List<Authority> findAuthoritiesByUserId(Long userId);
}
