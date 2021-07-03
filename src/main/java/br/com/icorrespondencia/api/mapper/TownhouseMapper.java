package br.com.icorrespondencia.api.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(target = "createdAt", expression = "java( br.com.icorrespondencia.api.util.DateUtil.formatDateTimeToSQL( obj.getCreatedAt() ) )")
    @Mapping(target = "excludedAt", expression = "java( br.com.icorrespondencia.api.util.DateUtil.formatDateTimeToSQL( obj.getExcludedAt() ) )")
    @Mapping(target = "cnpj", source = "nin")
    TownhouseDTO toDTO(Townhouse obj);

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "excludedAt", expression = "java( br.com.icorrespondencia.api.util.DateUtil.formatSQLDateStringToLocalDateTime( obj.getExcludedAt() ) )")
    Townhouse toDomain(TownhouseDTO obj);
}
