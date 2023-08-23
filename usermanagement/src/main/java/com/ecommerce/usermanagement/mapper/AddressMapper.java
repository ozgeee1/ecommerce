package com.ecommerce.usermanagement.mapper;

import com.ecommerce.usermanagement.domain.Address;
import com.ecommerce.usermanagement.request.AddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressMapper MAPPER = Mappers.getMapper(AddressMapper.class);

    Address requestToAddress(AddressRequest request);

    Address updateAddress(AddressRequest request,@MappingTarget Address address);

}
