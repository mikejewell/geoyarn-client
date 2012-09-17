package uk.ac.soton.ecs.geoyarn;

import java.util.ArrayList;

import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.Page;
import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class StoryActivity extends Activity {
    
	Story story;
	Chapter chapter;
	Page page;
	
	ArrayList<Button> linkButtons;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story);
       
        linkButtons=new ArrayList<Button>();
        
        story = ((GeoyarnClientApplication)getApplication()).getStory();
        chapter = ((GeoyarnClientApplication)getApplication()).getChapter();
        page = ((GeoyarnClientApplication)getApplication()).getPage();
        
        TextView storyTitleTv = (TextView)findViewById(R.id.StoryTitle);
        storyTitleTv.setText(story.getTitle());
        
        TextView pageContentTv = (TextView)findViewById(R.id.StoryPageContent);
        if(page!=null){
        	pageContentTv.setText(page.getContent());
        }
        else{
        	pageContentTv.setText(getResources().getString(R.string.storyBegin));
        }
        
        for(Page p:chapter.getPages()){
        	if(p!=page){
        		
        	}
        }
        
        
    }
    
    
}