package com.ecommerce.productmanagement.mapper;

import com.ecommerce.productmanagement.domain.Category;
import com.ecommerce.productmanagement.request.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category requestToCategory(CategoryRequest categoryRequest);

    Category updateCategory(CategoryRequest request,@MappingTarget Category category);

}
