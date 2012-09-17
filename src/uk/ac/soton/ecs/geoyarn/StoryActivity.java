package uk.ac.soton.ecs.geoyarn;

import java.util.ArrayList;

import uk.ac.soton.ecs.geoyarn.controller.LocationController;
import uk.ac.soton.ecs.geoyarn.controller.StoryController;
import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.Page;
import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StoryActivity extends Activity implements ILocationActivity{
    
	Story story;
	Chapter chapter;
	Page page;
	
	ArrayList<Button> linkButtons;
	StoryController storyController;
	
	LocationController locCont;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.story);
       
        linkButtons=new ArrayList<Button>();
        storyController=new StoryController();
        locCont = new LocationController(this);
        
        story = ((GeoyarnClientApplication)getApplication()).getStory();
        
        chapter = ((GeoyarnClientApplication)getApplication()).getChapter();
        if(chapter==null){
        	chapter = storyController.getChapter(story.getId(), story.getStartChapter());
        	((GeoyarnClientApplication)getApplication()).setChapter(chapter);
        }
        
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
        
        LinearLayout storyLinkList = (LinearLayout)findViewById(R.id.StoryLinksList);
        for(Page p:chapter.getPages()){
        	if(p!=page){
        		Button linkButton = new Button(this);
        		linkButton.setText(p.getDescription());
        		linkButton.setTag(p);
        		linkButton.setBackgroundColor(Color.RED);
        		linkButton.setOnClickListener(new View.OnClickListener(){
        			public void onClick(View v) {
        				Page page = (Page)v.getTag();
        				followLink(page);				
        			}
                });
                
        		linkButtons.add(linkButton);
        		storyLinkList.addView(linkButton);
        		
        		
        	}
        }
        
        
    }
    
    
    
    public void followLink(Page p){
    	((GeoyarnClientApplication)getApplication()).setPage(p);
    	p.getNextChapter();
    	Intent storyIntent = new Intent(getBaseContext(), StoryActivity.class);
    	startActivity(storyIntent);
    }



	public void onLocationChanged() {
		
		
	}



	public void onLocationChanged(Location location) {
		
		
	}
    
    
    
    
}