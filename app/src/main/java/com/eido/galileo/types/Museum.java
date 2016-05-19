package com.eido.galileo.types;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Stefano on 28/12/15.
 */
@Root
public class Museum {

    @ElementList(entry="Floor", inline=true)
    List<Floor> Floors;

    public List<Floor> getFloors()
    {
        return Floors;
    }

}
