package br.com.icorrespondencia.api.util;

import br.com.icorrespondencia.api.domain.Townhouse;

public class TownhouseCreator {

    public static final Townhouse townhouseToBeStored(){
        return Townhouse
                    .builder()
                    .name("Gandalf Town")
                    .nin("27.419.373/0001-58")
                    .site("www.towngandalf.com")
                    .email("gandalf@fakemail.com")
                .build();
    }

    public static final Townhouse townhouseValid(){
        return Townhouse
                    .builder()
                    .id(1L)
                    .name("Gandalf Town")
                    .nin("27.419.373/0001-58")
                    .site("www.towngandalf.com")
                    .email("gandalf@fakemail.com")
                .build();
    }

    public static final Townhouse townhouseUpdated(){
        return Townhouse
                    .builder()
                    .id(1L)
                    .name("Frodo Town")
                    .nin("27.419.373/0001-58")
                    .site("www.townfrodo.com")
                    .email("frodo.town@fakemail.com")
                .build();
    }
}
