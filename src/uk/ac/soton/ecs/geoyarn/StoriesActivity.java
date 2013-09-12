package uk.ac.soton.ecs.geoyarn;

import java.util.ArrayList;

import uk.ac.soton.ecs.geoyarn.controller.LocationController;
import uk.ac.soton.ecs.geoyarn.controller.StoryEngine;
import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class StoriesActivity extends Activity {
    
	ArrayList<Story> stories;
	StoryEngine storyController;
	
	LocationController locCont;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.stories);
        
        locCont = new LocationController(this);
        
        storyController = new StoryEngine();
        
        //stories=getStoriesList();
        stories = storyController.getStories();
        
        
        LinearLayout storiesList = (LinearLayout)findViewById(R.id.StoriesList);
        
        for(Story s:stories){
        	Button storyButton = new Button(this);
        	storyButton.setText(s.getTitle());
        	storyButton.setTag(s);
        	storyButton.setOnClickListener(new View.OnClickListener(){
    			public void onClick(View v) {
    				Story story = (Story)v.getTag();
    				startStory(story);				
    			}
            });
            
        	storiesList.addView(storyButton);
        }
        
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
    
    
      public void startStory(Story s){
    	
    	((GeoyarnClientApplication)getApplication()).setStory(s);
    	((GeoyarnClientApplication) getApplication()).setChapter(null);
    	((GeoyarnClientApplication) getApplication()).setPage(null);
    	  
    	Intent storyIntent = new Intent(getBaseContext(), StoryActivity.class);
    	startActivity(storyIntent);
    }
    
    
}