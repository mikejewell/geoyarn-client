package uk.ac.soton.ecs.geoyarn;

import uk.ac.soton.ecs.geoyarn.controller.LocationController;
import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

public class GeoyarnClientActivity extends Activity {
    
	LocationController locCont;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        locCont = new LocationController(this);
        
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        locCont.removeUpdates();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        locCont.resumeUpdates();
    }
    
    public void goStories(View v){
    	Intent storiesIntent = new Intent(getBaseContext(), StoriesActivity.class);
    	startActivity(storiesIntent);
    }
    
    public void goSettings(View v){
    	Intent settingsActivity = new Intent(getBaseContext(), GeoyarnPreferences.class);
    	startActivity(settingsActivity);
    }
    
    
    
    
}