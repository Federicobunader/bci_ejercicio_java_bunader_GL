package com.example.bci_ejercicio_java_bunader_gl.mappers;

import com.example.bci_ejercicio_java_bunader_gl.dtos.PhoneDTO;
import com.example.bci_ejercicio_java_bunader_gl.entities.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhoneMapper {

    PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    List<PhoneDTO> phoneEntitiesToDTOs(List <Phone> phones);

    List <Phone> phonesDTOToEntities(List <PhoneDTO> phonesDTO);
}

