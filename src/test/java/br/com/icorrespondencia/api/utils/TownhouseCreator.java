package br.com.icorrespondencia.api.utils;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.icorrespondencia.api.domain.Townhouse;

public class TownhouseCreator {

    public static final Townhouse store() {
        Townhouse townhouse = new Townhouse();

        townhouse.setName("Gandalf Town");
        townhouse.setCnpj("27.419.373/0001-58");
        townhouse.setEmail("gandalf@fakemail.com");
        townhouse.setSite("www.towngandalf.com");
        townhouse.setPhone("11988887777");

        return townhouse;
    }

    public static final Townhouse valid() {
        Townhouse townhouse = store();

        townhouse.setId(UUID.randomUUID());
        townhouse.setCreatedAt(LocalDateTime.now());

        return townhouse;
    }
}
