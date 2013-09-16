package uk.ac.soton.ecs.geoyarn.model;

import java.util.HashMap;
import java.util.Set;

import nsidc.spheres.SphericalPolygon;

public class LocationQueryResultLocation{
	
	SphericalPolygon location;
	HashMap<String, String> metadata;
	
	public LocationQueryResultLocation(){
		metadata = new HashMap<String, String>();
	}
	
	public String getMetaData(String key){
		return metadata.get(key);		
	}
	
	public SphericalPolygon getLocation(){
		return location;
	}
	
	public void setMetaData(HashMap<String, String> md){
		metadata=md;
	}
	
	public void setLocation(SphericalPolygon loc){
		location=loc;
	}
	
	public void addMetaData(String key, String value){
		metadata.put(key, value);
	}
		
}