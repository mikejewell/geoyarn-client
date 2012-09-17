package uk.ac.soton.ecs.geoyarn.controller;

import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.geoyarn.model.Story;

public class StoryController {
	public ArrayList<Story> getStories() {
		ArrayList<Story> stories = new ArrayList<Story>();
		
		
		Story story1 = new Story();
    	story1.setTitle("Story 1");
    	story1.setId(1);
    	
    	Story story2 = new Story();
    	story2.setTitle("Story 2");
    	story2.setId(2);
    	
    	Story story3 = new Story();
    	story3.setTitle("Story 3");
    	story3.setId(3);
    	
    	stories.add(story1);
    	stories.add(story2);
    	stories.add(story3);
		
		
		return stories;
	}
}
