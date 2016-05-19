package com.eido.galileo.exceptions;

/**
 * Created by Stefano on 08/04/16.
 */
public class FloorNotFoundException extends MapElementNotFoundException {
    public FloorNotFoundException(int major) {
        super(new StringBuilder()
                .append("Nessun piano trovato con Major: ")
                .append(major).toString());
    }
}
