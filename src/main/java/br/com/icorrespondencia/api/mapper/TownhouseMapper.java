package br.com.icorrespondencia.api.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TownhouseMapper {

    TownhouseMapper INSTANCE = Mappers.getMapper(TownhouseMapper.class);

    @Mapping(target = "createdAt", expression = "java( br.com.icorrespondencia.api.util.DateUtil.formatDateTimeToSQL( townhouse.getCreatedAt() ) )")
    @Mapping(target = "excludedAt", expression = "java( br.com.icorrespondencia.api.util.DateUtil.formatDateTimeToSQL( townhouse.getExcludedAt() ) )")
    @Mapping(target = "cnpj", source = "nin")
    TownhouseDTO toDTO(Townhouse townhouse);

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", ignore = true)
    Townhouse toDomain(TownhouseDTO townhouseDTO);
}
