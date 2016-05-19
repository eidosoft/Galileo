package com.eido.galileo.types;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Stefano on 13/11/15.
 */

public class Place {

    @Element
    private Beacon Beacon;

    @Element
    private Content Content;

    @Attribute
    private String Image;

    public String getImage() {
        return Image;
    }

    public Beacon getBeacon(){ return Beacon;}

    public Content getContent(){return Content;}

}
