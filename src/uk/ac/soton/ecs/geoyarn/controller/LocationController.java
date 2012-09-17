package uk.ac.soton.ecs.geoyarn.controller;

import uk.ac.soton.ecs.geoyarn.GeoyarnClientApplication;
import uk.ac.soton.ecs.geoyarn.ILocationActivity;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationController{
	
	GeoyarnClientApplication app;
	Activity activity;
	LocationManager locationManager;
	
	public LocationController(Activity a){
		activity =a;
		app=(GeoyarnClientApplication)a.getApplication();
		
		locationManager=(LocationManager)a.getSystemService(Context.LOCATION_SERVICE);
		GPSLocationListener gpsll = new GPSLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000L, 1, gpsll);
		
		app.setCurrentLat(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude());
		app.setCurrentLong(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
	}
	
	public class GPSLocationListener implements LocationListener{

		public void onLocationChanged(Location location) {
			app.setCurrentLat(location.getLatitude());
			app.setCurrentLong(location.getLatitude());
			if(activity instanceof ILocationActivity){
				((ILocationActivity) activity).onLocationChanged(location);				
			}
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
	
}