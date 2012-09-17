package uk.ac.soton.ecs.geoyarn.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.soton.ecs.geoyarn.model.Story;
import android.util.Log;

public class StoryController extends BaseController {
	
	private static final String TAG = "StoryController";
	
	public ArrayList<Story> getStories() {
		ArrayList<Story> stories = new ArrayList<Story>();
		try {
			String storyText = this.getURL("http://lab.thecollectedmike.com/yarn/stories.json");
			JSONArray storiesJSON = new JSONArray(storyText);
			
			for(int i=0; i<storiesJSON.length(); i++)
			{
				JSONObject storyJSON = storiesJSON.getJSONObject(i);
				Story story = new Story();
				story.setTitle(storyJSON.getString("title"));
				story.setId(storyJSON.getInt("id"));
				stories.add(story);
			}
			
		} catch (Exception e) {

			Log.e(TAG, e.getMessage());
		}
		return stories;
	}

}
