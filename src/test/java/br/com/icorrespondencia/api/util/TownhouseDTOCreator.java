package br.com.icorrespondencia.api.util;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.mapper.TownhouseMapper;

public class TownhouseDTOCreator {

    private static final TownhouseDTO townhouseMapper(final Townhouse townhouse) {
        return TownhouseMapper.INSTANCE.toDTO(townhouse);
    }

    public static final TownhouseDTO valid() {
        return townhouseMapper(TownhouseCreator.valid());
    }

    public static final TownhouseDTO toBeStored() {
        Townhouse townhouseToBeStored = TownhouseCreator.toBeStored();

        TownhouseDTO townhouseToBeReturned = TownhouseDTO
            .builder()
                .name(townhouseToBeStored.getName())
                .email(townhouseToBeStored.getEmail())
                .cnpj(townhouseToBeStored.getNin())
                .site(townhouseToBeStored.getSite())
            .build();

        return townhouseToBeReturned;
    }

    public static final TownhouseDTO updated() {
        return townhouseMapper(TownhouseCreator.updated());
    }
}
