package com.ecommerce.productmanagement.mapper;

import com.ecommerce.productmanagement.domain.Product;
import com.ecommerce.productmanagement.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product requestToProduct(ProductRequest productRequest);

    Product updateProduct(ProductRequest request,@MappingTarget Product product);

}
