package uk.ac.soton.ecs.geoyarn.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nsidc.spheres.SphericalPolygon;

public class LocationQueryResult{

	ArrayList<LocationQueryResultLocation> locations;
	
	public LocationQueryResult(){
		locations = new ArrayList<LocationQueryResultLocation>();
	}
	
	public ArrayList<LocationQueryResultLocation> getLocations(){
		return locations;
	}
	
	public void setLocations(ArrayList<LocationQueryResultLocation> locs){
		locations=locs;
	}
	
	public void addLocation(LocationQueryResultLocation loc){
		locations.add(loc);
	}
	
}