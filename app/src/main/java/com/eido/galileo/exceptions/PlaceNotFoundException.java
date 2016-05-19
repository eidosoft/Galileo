package com.eido.galileo.exceptions;

import com.eido.galileo.types.Floor;

/**
 * Created by Stefano on 08/04/16.
 */
public class PlaceNotFoundException extends MapElementNotFoundException {
    public PlaceNotFoundException(Floor floor, int minor) {

        super(new StringBuilder()
                .append("Nessuna stanza trovata nel piano: ")
                .append(floor.getName())
                .append(" con Major: ")
                .append(floor.getMajor())
                .append(" e Minor: ")
                .append(minor).toString());
    }
}
