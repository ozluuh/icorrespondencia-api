package br.com.icorrespondencia.api.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import br.com.icorrespondencia.api.domain.User;
import br.com.icorrespondencia.api.dto.UserDTO;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "publicId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    User toDomain(UserDTO userDTO);
}
