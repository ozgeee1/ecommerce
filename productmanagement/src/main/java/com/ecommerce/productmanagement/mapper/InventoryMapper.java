package com.ecommerce.productmanagement.mapper;

import com.ecommerce.productmanagement.domain.Category;
import com.ecommerce.productmanagement.domain.Inventory;
import com.ecommerce.productmanagement.request.CategoryRequest;
import com.ecommerce.productmanagement.request.InventoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InventoryMapper {

    InventoryMapper MAPPER = Mappers.getMapper(InventoryMapper.class);

    Inventory requestToInventory(InventoryRequest inventoryRequest);

}
