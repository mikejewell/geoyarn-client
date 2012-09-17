package uk.ac.soton.ecs.geoyarn;

import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StoryActivity extends Activity {
    
	Story story;
	Chapter chapter;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story);
        
        story = ((GeoyarnClientApplication)getApplication()).getStory();
        
        TextView storyTitle = (TextView)findViewById(R.id.StoryTitle);
        storyTitle.setText(story.getTitle());
        
        
    }
    
    
}