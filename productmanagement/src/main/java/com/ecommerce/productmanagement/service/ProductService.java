package com.ecommerce.productmanagement.service;

import com.ecommerce.productmanagement.domain.Category;
import com.ecommerce.productmanagement.domain.Product;
import com.ecommerce.productmanagement.exception.BadRequestException;
import com.ecommerce.productmanagement.mapper.ProductMapper;
import com.ecommerce.productmanagement.repository.ProductRepository;
import com.ecommerce.productmanagement.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    public ResponseEntity<?> addProduct(ProductRequest productRequest){
        if((productRequest.getCategoryId()==null) || !categoryService.checkIfCatIdValid(productRequest.getCategoryId())){
            throw new BadRequestException("This category Id is not valid! "+productRequest.getCategoryId());
        }
        Product product = ProductMapper.MAPPER.requestToProduct(productRequest);
        Category category = categoryService.getCategoryById(productRequest.getCategoryId());
        product.setCategory(category);
        productRepository.save(product);
        return ResponseEntity.ok("Product is saved to the DB");

    }

    public ResponseEntity<?> updateProduct(Long productId,ProductRequest productRequest){
        Product beforeUpdate = getProductById(productId);
        Product afterUpdate = ProductMapper.MAPPER.updateProduct(productRequest, beforeUpdate);
        productRepository.save(afterUpdate);
        return ResponseEntity.ok("Product is updated");
    }

    public Product getProductById(Long id){
        Optional<Product> byId = productRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new BadRequestException("There is no product with this id "+id);
    }
}
