package uk.ac.soton.ecs.geoyarn.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nsidc.spheres.SphericalPolygon;

public class LocationQueryResult{

	private Set<SphericalPolygon> locations;
	HashMap<String, String> metadata;
	
	public LocationQueryResult(String query){
		locations = new HashSet<SphericalPolygon>();
		metadata = new HashMap<String,String>();
		
		//Make and parse Query here
	}	
	
	public String getMetaData(String key){
		return metadata.get(key);		
	}
	
	public Set<SphericalPolygon> getLocations(){
		return locations;
	}
}