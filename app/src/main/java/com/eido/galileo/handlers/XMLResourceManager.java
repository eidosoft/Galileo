package com.eido.galileo.handlers;

import com.eido.galileo.exceptions.FloorNotFoundException;
import com.eido.galileo.exceptions.PlaceNotFoundException;
import com.eido.galileo.types.Floor;
import com.eido.galileo.types.Museum;
import com.eido.galileo.types.Place;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;


/**
 * Created by Stefano on 07/04/16.
 */
public class XMLResourceManager {

    private Museum Museum;

    public XMLResourceManager(InputStream xmlInputStream) throws Exception
    {
        Serializer serializer = new Persister();
        Museum = serializer.read(Museum.class, xmlInputStream);

    }

    /**
     *Metodo che dato un Major restituisce un piano
     *
     * @param major intero che rappresenta il Major del Beacon
     * @return Piano in cui è posizionato il beacon
     * @throws FloorNotFoundException Eccezione che viene lanciata quando non viene trovato
     *         il piano
     */
    public Floor GetFloorByMayor(int major) throws FloorNotFoundException {

        for (Floor floor: Museum.getFloors()) {
            if(floor.getMajor() == major)
                return floor;
        }

        throw new FloorNotFoundException(major);
    }

    /**
     *
     * @param floor Piano in cui ricercare la stnza
     * @param minor Minor del beacon rilevato
     * @return Place rilevato in cui è contenuto il beacon
     * @throws PlaceNotFoundException Eccezione che viene lanciata quando non viene trovato
     *         il beacon all' intenro della stanza
     */
    public Place GetPlaceByFloorAndMinor(Floor floor, int minor) throws PlaceNotFoundException {

        for (Place place : floor.getPlaces()) {
            if(place.getBeacon().getMinor() == minor)
                return place;
        }

        throw new PlaceNotFoundException(floor, minor);
    }
}
