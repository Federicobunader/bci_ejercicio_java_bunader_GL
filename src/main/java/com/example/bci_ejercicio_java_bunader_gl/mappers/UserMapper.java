package com.example.bci_ejercicio_java_bunader_gl.mappers;

import com.example.bci_ejercicio_java_bunader_gl.dtos.UserDTO;
import com.example.bci_ejercicio_java_bunader_gl.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);



    @Mapping(target = "phones", expression = "java(com.example.bci_ejercicio_java_bunader_gl.mappers.PhoneMapper.INSTANCE.phoneEntitiesToDTOs(user.getPhones()))")
    UserDTO userEntityToDto(User user);

    @Mapping(target = "phones", expression = "java(com.example.bci_ejercicio_java_bunader_gl.mappers.PhoneMapper.INSTANCE.phonesDTOToEntities(userDTO.getPhones()))")
    User userDtoToEntity(UserDTO userDTO);
}

