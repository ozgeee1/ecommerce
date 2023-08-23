package com.ecommerce.productmanagement.service;

import com.ecommerce.productmanagement.domain.Category;
import com.ecommerce.productmanagement.exception.BadRequestException;
import com.ecommerce.productmanagement.mapper.CategoryMapper;
import com.ecommerce.productmanagement.repository.CategoryRepository;
import com.ecommerce.productmanagement.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public boolean checkIfCatIdValid(Long categoryId){
       return categoryRepository.existsById(categoryId);
    }

    public ResponseEntity<?> addCategory(CategoryRequest categoryRequest){
        Category category = CategoryMapper.MAPPER.requestToCategory(categoryRequest);
        categoryRepository.save(category);
        return ResponseEntity.ok("Category is saved to the DB");
    }

    public ResponseEntity<?> updateCategory(Long categoryId,CategoryRequest request){
        Category beforeUpdate = getCategoryById(categoryId);
        Category afterUpdate = CategoryMapper.MAPPER.updateCategory(request, beforeUpdate);
        categoryRepository.save(afterUpdate);
        return ResponseEntity.ok("Category is updated");
    }

    public Category getCategoryById(Long id){
        Optional<Category> byId = categoryRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new BadRequestException("There is no category with this id "+id);
    }
}
