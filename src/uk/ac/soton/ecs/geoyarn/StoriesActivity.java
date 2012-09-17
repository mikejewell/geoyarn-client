package uk.ac.soton.ecs.geoyarn;

import java.util.ArrayList;

import uk.ac.soton.ecs.geoyarn.controller.StoryController;
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
	StoryController storyController;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.stories);
        
        storyController = new StoryController();
        
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
    
    
      public void startStory(Story s){
    	
    	((GeoyarnClientApplication)getApplication()).setStory(s);
    	  
    	Intent storyIntent = new Intent(getBaseContext(), StoryActivity.class);
    	startActivity(storyIntent);
    }
    
    
}