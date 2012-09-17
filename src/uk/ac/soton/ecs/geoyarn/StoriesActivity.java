package uk.ac.soton.ecs.geoyarn;

import java.util.ArrayList;

import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


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
    
    
    public ArrayList<Story> getStoriesList(){
    	
    	ArrayList<Story> tempStories = new ArrayList<Story>();
    	
    	Story story1 = new Story();
    	story1.setTitle("Story 1");
    	story1.setId(1);
    	
    	Story story2 = new Story();
    	story2.setTitle("Story 2");
    	story2.setId(2);
    	
    	Story story3 = new Story();
    	story3.setTitle("Story 3");
    	story3.setId(3);
    	
    	tempStories.add(story1);
    	tempStories.add(story2);
    	tempStories.add(story3);
    	
    	return tempStories;    	
    }
    	
    
    public void startStory(Story s){
    	Toast.makeText(this, "Starting story: "+s.getTitle(), Toast.LENGTH_SHORT).show();
    }
    
    
}