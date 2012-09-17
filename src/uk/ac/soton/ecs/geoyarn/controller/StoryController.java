package uk.ac.soton.ecs.geoyarn.controller;

import java.util.ArrayList;

import nsidc.spheres.Point;
import nsidc.spheres.SphericalPolygon;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.Page;
import uk.ac.soton.ecs.geoyarn.model.Story;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class StoryController extends BaseController {

	private static final String TAG = "StoryController";

	public ArrayList<Story> getStories() {
		ArrayList<Story> stories = new ArrayList<Story>();
		try {
			String storyText = this
					.getURL("http://lab.thecollectedmike.com/yarn/stories.json");
			JSONArray storiesJSON = new JSONArray(storyText);

			
			for (int i = 0; i < storiesJSON.length(); i++) {
				JSONObject storyJSON = storiesJSON.getJSONObject(i);
				Story story = new Story();
				story.setTitle(storyJSON.getString("title"));
				story.setId(storyJSON.getInt("id"));
				story.setStartChapter(storyJSON.getInt("startChapter"));
				stories.add(story);
			}

		} catch (Exception e) {

			Log.e(TAG, e.getMessage());
		}
		return stories;
	}

	public Chapter getChapter(int storyid, int chapterid) {
		Chapter chapter = new Chapter();
		try {
			String chapterText = this
					.getURL("http://lab.thecollectedmike.com/yarn/chapter.json");
			JSONObject chapterJSON = new JSONObject(chapterText);
			chapter.setId(chapterJSON.getInt("id"));

			// Build pages
			JSONArray pagesJSON = chapterJSON.getJSONArray("pages");
			for (int i = 0; i < pagesJSON.length(); i++) {
				Page page = new Page();
				JSONObject pageJSON = pagesJSON.getJSONObject(i);
				page.setId(pageJSON.getInt("id"));
				page.setContent(pageJSON.getString("content"));
				page.setDescription(pageJSON.getString("description"));
				page.setNextChapter(pageJSON.getInt("nextChapter"));

				
				// Build locations
				JSONArray locationsJSON = pageJSON.getJSONArray("locations");
				// Get each location
				for (int j = 0; j < locationsJSON.length(); j++) {
					JSONArray locationPointsJSON = locationsJSON
							.getJSONArray(j);
					
					
					// Get each point array
					ArrayList<Point> points = new ArrayList<Point>();
					for (int k = 0; k < locationPointsJSON.length(); k++) {
						JSONObject locJSON = locationPointsJSON.getJSONObject(k);
						Point point = new Point(Double.parseDouble(locJSON
								.getString("lat")), Double.parseDouble(locJSON
								.getString("lon")));
						points.add(point);
					}

					page.getLocations()
							.add(new SphericalPolygon(points
									.toArray(new Point[] {})));
				}
				chapter.getPages().add(page);

			}

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return chapter;
	}
	
	public Boolean canViewPage(Page page, Location location) {
		double lon = location.getLongitude();
		double lat = location.getLatitude();
		Point centre = new Point(lat, lon);
		
		double R = 6371; // earth radius in km
		double radius = 0.010; // km
		double x1 = lon - Math.toDegrees(radius/R/Math.cos(Math.toRadians(lat)));
		double x2 = lon + Math.toDegrees(radius/R/Math.cos(Math.toRadians(lat)));
		double y1 = lat + Math.toDegrees(radius/R);
		double y2 = lat - Math.toDegrees(radius/R);
		
		Point[] points = new Point[]{
				new Point(y1, x1),
				new Point(y2, x1),
				new Point(y2, x2),
				new Point(y1, x2)};
		SphericalPolygon centreBox = new SphericalPolygon(points);
		
		Log.e(TAG, y1+","+x1);
		Log.e(TAG, y2+","+x1);
		Log.e(TAG, y2+","+x2);
		Log.e(TAG, y1+","+x2);
		
		for(SphericalPolygon poly: page.getLocations()) {
			
			if(poly.contains(centre)) {
				Log.e(TAG, "Centre");
				return true;
			}
			
			if(poly.overlaps(centreBox)) {
				Log.e(TAG, "Overlaps!");
				return true;
			}
			
			for(Point point: points) {
				if(poly.contains(point)) {
					Log.e(TAG, "Contains");
					return true;
				}
			}
		}
		return false;
	}

}
