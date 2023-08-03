package com.xoxo.webfluxsecutiry.mapper;

import com.xoxo.webfluxsecutiry.dto.UserDto;
import com.xoxo.webfluxsecutiry.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity map(UserDto userDto);
}
