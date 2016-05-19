package com.eido.galileo.types;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Stefano on 18/11/15.
 */
@Root
public class Floor {

    @ElementList(entry="Place", inline=true)
    private List<Place> Places;

    @Attribute
    protected String Name;

    @Attribute
    protected String Route;

    @Attribute
    protected String Map;

    @Attribute
    protected int Major;

    public List<Place> getPlaces() {
        return Places;
    }

    public String getMap(){return Map;}

    public String getName(){return this.Name;}

    public String getRoute(){return this.Route;}

    public int getMajor(){return this.Major;}
}
