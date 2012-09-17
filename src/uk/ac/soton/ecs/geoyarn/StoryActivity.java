package uk.ac.soton.ecs.geoyarn;

import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StoryActivity extends Activity {
    
	Story story;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story);
        
        TextView storyTitle = (TextView)findViewById(R.id.StoryTitle);
        storyTitle.setText(((GeoyarnClientApplication)getApplication()).currentStory.getTitle());
        
        
    }
    
    
}