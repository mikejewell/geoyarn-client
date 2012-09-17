package uk.ac.soton.ecs.geoyarn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GeoyarnClientActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void goStories(View v){
    	Intent storiesIntent = new Intent(getBaseContext(), StoriesActivity.class);
    	startActivity(storiesIntent);
    }
    
    public void goSettings(View v){
    	
    }
}