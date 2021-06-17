package br.com.icorrespondencia.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;

@Mapper(componentModel = "spring")
public abstract class TownhouseMapper {

    public static final TownhouseMapper INSTANCE = Mappers.getMapper(TownhouseMapper.class);

    @Mapping(target = "active", ignore = true)
    public abstract Townhouse toTownhouse(TownhouseDTO townhouseDTO);

    @Mapping(dateFormat = "yyyy-MM-dd HH:mm:ss", target = "createdAt")
    public abstract TownhouseDTO toTownhouseDTO(Townhouse townhouse);
}
