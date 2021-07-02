package br.com.icorrespondencia.api.utils;

import br.com.icorrespondencia.api.domain.Townhouse;

public class TownhouseCreator {

    public static final Townhouse toBeStored() {
        return Townhouse
                    .builder()
                    .name("Gandalf Town")
                    .nin("27.419.373/0001-58")
                    .site("www.towngandalf.com")
                    .email("gandalf@fakemail.com")
                .build();
    }

    public static final Townhouse valid() {
        Townhouse toBeReturned = toBeStored();

        toBeReturned.setId(1L);

        return toBeReturned;
    }

    public static final Townhouse updated() {
        Townhouse toBeReturned = valid();

        toBeReturned.setName("Frodo Town");
        toBeReturned.setSite("www.townfrodo.com");
        toBeReturned.setEmail("frodo.town@fakemail.com");

        return toBeReturned;
    }
}
