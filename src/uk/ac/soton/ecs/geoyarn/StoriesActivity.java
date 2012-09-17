package uk.ac.soton.ecs.geoyarn;

import java.util.ArrayList;

import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class StoriesActivity extends Activity {
    
	ArrayList<Story> stories;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stories);
        
        stories=getStoriesList();
        
        LinearLayout storiesList = (LinearLayout)findViewById(R.id.StoriesList);
        
        for(Story s:stories){
        	Button storyButton = new Button(this);
        	storyButton.setText(s.title);
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
    
    
    public ArrayList<Story> getStoriesList(){
    	
    	ArrayList<Story> tempStories = new ArrayList<Story>();
    	
    	tempStories.add(new Story("Story 1",1));
    	tempStories.add(new Story("Story 2",2));
    	tempStories.add(new Story("Story 3",3));
    	
    	return tempStories;    	
    }
    	
    
    public void startStory(Story s){
    	
    }
    
    
}