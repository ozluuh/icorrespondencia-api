package br.com.icorrespondencia.api.util;

import br.com.icorrespondencia.api.domain.Townhouse;
import br.com.icorrespondencia.api.dto.TownhouseDTO;
import br.com.icorrespondencia.api.mapper.TownhouseMapper;

public class TownhouseDTOCreator {

    private static final TownhouseDTO townhouseMapper(Townhouse townhouse) {
        return TownhouseMapper.INSTANCE.toDTO(townhouse);
    }

    public static final TownhouseDTO townhouseDTOValid() {
        return townhouseMapper(TownhouseCreator.townhouseValid());
    }

    public static final TownhouseDTO townhouseDTOToBeStored() {
        return townhouseMapper(TownhouseCreator.townhouseToBeStored());
    }

    public static final TownhouseDTO townhouseDTOUpdated() {
        return townhouseMapper(TownhouseCreator.townhouseUpdated());
    }
}
