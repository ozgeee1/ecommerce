package com.ecommerce.productmanagement.repository;

import com.ecommerce.productmanagement.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {
}
