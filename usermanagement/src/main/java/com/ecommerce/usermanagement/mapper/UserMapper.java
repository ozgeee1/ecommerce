package com.ecommerce.usermanagement.mapper;

import com.ecommerce.usermanagement.domain.User;
import com.ecommerce.usermanagement.request.UpdateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    User updateRequestToUser(UpdateUserRequest updateUserRequest,@MappingTarget User user);
}
