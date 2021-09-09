package br.com.icorrespondencia.api.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;

/**
 * Interface to stipulate mapper between domain and DTO layer of Townhouse
 * entity
 *
 * @author Luís Paulino
 * @since 0.1
 * @version 0.1
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TownhouseMapper {

    TownhouseMapper INSTANCE = Mappers.getMapper(TownhouseMapper.class);

    TownhouseDTO toDTO(Townhouse townhouse);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "publicId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Townhouse toDomain(TownhouseDTO townhouseDTO);
}
