package uk.ac.soton.ecs.geoyarn.controller;

import uk.ac.soton.ecs.geoyarn.GeoyarnClientApplication;
import uk.ac.soton.ecs.geoyarn.ILocationActivity;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationController{

	private static final String TAG = "LocationController";
	GeoyarnClientApplication app;
	Activity activity;
	LocationManager locationManager;
	
	public LocationController(Activity a){
		activity =a;
		app=(GeoyarnClientApplication)a.getApplication();
		
		locationManager=(LocationManager)a.getSystemService(Context.LOCATION_SERVICE);
		GPSLocationListener gpsll = new GPSLocationListener();
		//locationManager.removeUpdates(gpsll);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000L, 0, gpsll);
		
		if(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!=null){
			app.setCurrentLat(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude());
			app.setCurrentLong(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
		}
		else{
			app.setCurrentLat(0.0);
			app.setCurrentLong(0.0);
		}
			
	}
	
	public class GPSLocationListener implements LocationListener{

		public void onLocationChanged(Location location) {
			app.setCurrentLat(location.getLatitude());
			app.setCurrentLong(location.getLongitude());
			
			if(activity instanceof ILocationActivity){
				((ILocationActivity) activity).onLocationChanged(location);				
			}
			
			//locationManager.removeUpdates(this);
			//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50000L, 0, this);
			
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void removeUpdates(){
		GPSLocationListener gpsll = new GPSLocationListener();
		locationManager.removeUpdates(gpsll);		
	}
	
	public void resumeUpdates(){
		GPSLocationListener gpsll = new GPSLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000L, 0, gpsll);		
	}
	
}
