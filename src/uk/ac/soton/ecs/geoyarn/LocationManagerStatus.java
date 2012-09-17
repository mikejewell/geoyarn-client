package uk.ac.soton.ecs.geoyarn;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.TextView;

public class LocationManagerStatus extends Activity {
    private LocationManager locationManager;
    private TextView textView;

    private final LocationListener gpsLocationListener = new LocationListener() {

        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
            case LocationProvider.AVAILABLE:
                textView.setText(textView.getText().toString() + "GPS available again\n");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                textView.setText(textView.getText().toString() + "GPS out of service\n");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                textView.setText(textView.getText().toString() + "GPS temporarily unavailable\n");
                break;
            }
        }

        public void onProviderEnabled(String provider) {
            textView.setText(textView.getText().toString() + "GPS Provider Enabled\n");
        }

        public void onProviderDisabled(String provider) {
            textView.setText(textView.getText().toString() + "GPS Provider Disabled\n");
        }

        public void onLocationChanged(Location location) {
            locationManager.removeUpdates(networkLocationListener);

            textView.setText(textView.getText().toString() + "New GPS location: "
                    + String.format("%9.6f", location.getLatitude()) + ", "
                    + String.format("%9.6f", location.getLongitude()) + "\n");

        }
    };

    private final LocationListener networkLocationListener = new LocationListener() {

        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
            case LocationProvider.AVAILABLE:
                textView.setText(textView.getText().toString() + "Network location available again\n");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                textView.setText(textView.getText().toString() + "Network location out of service\n");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                textView.setText(textView.getText().toString() + "Network location temporarily unavailable\n");
                break;
            }
        }

        public void onProviderEnabled(String provider) {
            textView.setText(textView.getText().toString() + "Network Provider Enabled\n");

        }

        public void onProviderDisabled(String provider) {
            textView.setText(textView.getText().toString() + "Network Provider Disabled\n");
        }

        public void onLocationChanged(Location location) {
            textView.setText(textView.getText().toString() + "New network location: "
                    + String.format("%9.6f", location.getLatitude()) + ", "
                    + String.format("%9.6f", location.getLongitude()) + "\n");

        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loctest);

        textView = (TextView) findViewById(R.id.LocTestText);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, networkLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, gpsLocationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(networkLocationListener);
        locationManager.removeUpdates(gpsLocationListener);
    }
}