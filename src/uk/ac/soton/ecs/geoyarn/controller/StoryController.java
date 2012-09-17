package uk.ac.soton.ecs.geoyarn.controller;

import java.util.ArrayList;

import org.json.JSONArray;

import uk.ac.soton.ecs.geoyarn.model.Story;
import android.util.Log;

public class StoryController extends BaseController {
	
	private static final String TAG = "StoryController";
	
	public ArrayList<Story> getStories() {
		ArrayList<Story> stories = new ArrayList<Story>();
		try {
			String storyText = this.getURL("http://lab.thecollectedmike.com/yarn/stories.json");
			JSONArray storyJSON = new JSONArray(storyText);
			Log.v(TAG, "json=" + storyJSON);
		} catch (Exception e) {

		}
		return stories;
	}

}
