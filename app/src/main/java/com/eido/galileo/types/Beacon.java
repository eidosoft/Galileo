package com.eido.galileo.types;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Stefano on 28/12/15.
 */
@Root
public class Beacon {

    @Attribute
    protected String BeaconId;



    @Attribute
    protected int Minor;

    public int getMinor(){return this.Minor;}

    public String getBeaconId() {
        return BeaconId;
    }

}
