package uk.ac.soton.ecs.geoyarn.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nsidc.spheres.Point;
import nsidc.spheres.SphericalPolygon;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.soton.ecs.geoyarn.GeoyarnClientApplication;
import uk.ac.soton.ecs.geoyarn.R;
import uk.ac.soton.ecs.geoyarn.model.AlarmTrigger;
import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.LocationQueryResult;
import uk.ac.soton.ecs.geoyarn.model.LocationQueryResultLocation;
import uk.ac.soton.ecs.geoyarn.model.Page;
import uk.ac.soton.ecs.geoyarn.model.Story;
import uk.ac.soton.ecs.geoyarn.model.TimerTrigger;
import uk.ac.soton.ecs.geoyarn.model.Trigger;
import android.location.Location;
import android.util.Log;

public class StoryEngine {

	private static final String TAG = "StoryController";
	//private static final String BASE = "http://lab.thecollectedmike.com/yarn/";
	//private static final String BASE = "http://wais-demo.ecs.soton.ac.uk/geoyarn/";
	//private static final String NEW_BASE = "http://www.yarnspinner.ecs.soton.ac.uk/";
	private static final String BASE = "http://www.yarnspinner.ecs.soton.ac.uk/data/";
	
	private static final String LOC_BASE = "http://tools.southampton.ac.uk/places/";
	
	String[] boundingBox={"-1.4164621","50.9270515","-1.3992239","50.9372517"};

	
	public ArrayList<Story> getStories(){
				
		ArrayList<Story> stories = new ArrayList<Story>();
		try {
			//String storyText = this.getURL(BASE +"story/");
			//String storyText = this.getURL(BASE +"yarns?nocache="+System.currentTimeMillis());
			String storyText = this.getURL(BASE +"yarns");
			//String storyText = this.getURL(NEW_BASE +"yarns");
			Log.i("GeoYarn: ", "StoryList: "+storyText);
			
			JSONArray storiesJSON = new JSONArray(storyText);
			
			for (int i = 0; i < storiesJSON.length(); i++) {
				JSONObject storyJSON = storiesJSON.getJSONObject(i);
				Story story = new Story();
				story.setTitle(storyJSON.getString("title"));
				story.setId(storyJSON.getInt("id"));
				story.setStartChapter(storyJSON.getInt("start_chapter_id"));
				stories.add(story);
			}

		} catch (Exception e) {

			Log.e(TAG, e.getMessage());
		}
		return stories;
	}

	public Chapter getChapter(int storyid, int chapterid, double latitude, double longitude) {
		
		try {
			locationQuery("green", boundingBox);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Chapter chapter = new Chapter();
		try {
			String chapterText = this.getURL(BASE+"chapter/"+chapterid+"?lat="+latitude+"&long="+longitude);
			//String chapterText = this.getURL(NEW_BASE+"testfiles/"+chapterid+".json?nocache="+System.currentTimeMillis());
			//String chapterText = this.getURL("http://www.yarnspinner.ecs.soton.ac.uk/data/chapter/"+chapterid);
			
			chapterText=chapterText.replace("\n", "");
			chapterText=chapterText.replace("\r", "");
			
			Log.i("GeoYarn: ", "ChapText "+BASE+"testfiles/"+chapterid+".json"+" "+chapterText);
			//Log.i("GeoYarn: ", "ChapText "+NEW_BASE+"testfiles/"+chapterid+".json"+" "+chapterText);
			//Log.i("GeoYarn: ", "ChapText "+"http://www.yarnspinner.ecs.soton.ac.uk/data/chapter/"+chapterid+" "+chapterText);
			
			
			JSONObject chapterJSON = new JSONObject(chapterText);
			chapter.setId(chapterJSON.getInt("id"));
			
			// Build pages
			JSONArray pagesJSON=null;
			if(!chapterJSON.isNull("pages")){
				pagesJSON = chapterJSON.getJSONArray("pages");
			}
			else if(!chapterJSON.isNull("ownNode")){
				pagesJSON = chapterJSON.getJSONArray("ownNode");
			}
			for (int i = 0; i < pagesJSON.length(); i++) {
				Page page = new Page();
				JSONObject pageJSON = pagesJSON.getJSONObject(i);
				page.setId(pageJSON.getInt("id"));
				
				if(!pageJSON.isNull("content")){
					page.setContent(pageJSON.getString("content"));
				}
				else{
					page.setContent("");					
				}
				
				if(!pageJSON.isNull("title")){
					page.setDescription(pageJSON.getString("title"));
				}
				else if(!pageJSON.isNull("description")){
					page.setDescription(pageJSON.getString("description"));
				}
				else{
					page.setDescription("");
				}
				
				if(!pageJSON.isNull("next_chapter")){
					page.setNextChapter(pageJSON.getInt("next_chapter"));
					Log.i("GeoYarn: ",chapter.getId()+" NEXT CHAP "+ pageJSON.getInt("next_chapter"));
				}
				else{
					page.setNextChapter(chapter.getId());
					Log.i("GeoYarn: ",chapter.getId()+" BAD NEXT CHAP "+ pageJSON.getInt("next_chapter"));
				}

				loadLocations(page, pageJSON);
								
				//chapter.getPages().add(page);
				processPage(page);
				chapter.addPage(page);
			}
			
			//Build Triggers
			JSONArray triggersJSON = chapterJSON.getJSONArray("triggers");
			for (int i = 0; i < triggersJSON.length(); i++) {
				
				Trigger trigger=new Trigger();
				
				JSONObject triggerJSON = triggersJSON.getJSONObject(i);
				int triggerType = triggerJSON.getInt("type");
				
				switch(triggerType){
					//Timer
					case 0:
						trigger = new TimerTrigger(triggerJSON.getInt("id"), triggerJSON.getInt("chapter"), triggerJSON.getInt("timer"));						
						break;
					//Alarm
					case 1: 
						trigger = new AlarmTrigger(triggerJSON.getInt("id"), triggerJSON.getInt("chapter"), triggerJSON.getString("alarm"));						
						break;
					//Other
					case 2: 
						break;
				}				
			
				chapter.addTrigger(trigger);
			}

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return chapter;
	}
	
	
	public Page processPage(Page page){
		
		String content = page.getContent();

		while(content.contains("<")&&content.contains(">")){
			String query = content.substring(content.indexOf("<")+1,content.indexOf(">"));
			try{
				LocationQueryResult queryResult = locationQuery(query, boundingBox);
				
				String nameResult="";
				int locIt = 0;
				while(nameResult.equals("")){
					LocationQueryResultLocation locres = queryResult.getLocations().get(locIt);
					String firstResult = locres.getMetaData("name");
					locIt++;
					if(locIt==queryResult.getLocations().size()){
						nameResult=query;
					}
				}
				
				content=content.replace("<"+query+">", nameResult);
			}
			catch(Exception e){
				Log.e("GeoYarn: ","Adapt Error!");
				e.printStackTrace();
				content=content.replace("<"+query+">",query);
			}
		}
		
		return page;
		
	}
	
	public Boolean canViewPage(Page page, Location location) {
		double lon = location.getLongitude();
		double lat = location.getLatitude();
		Point centre = new Point(lat, lon);
		
		double R = 6371; // earth radius in km
		double radius = 0.005; // km
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
	
	public String getURL(String url) throws Exception {
		Log.e(TAG, "Load "+url);
		String page = "";
		BufferedReader in = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
						
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				//Log.i("GeoYarn: ","RESPONSE "+line);
				sb.append(line + NL);
			}
			in.close();
			page = sb.toString();
		}
		catch(Exception e) {
			Log.e(TAG, "Errr:" +e.toString());
		}
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return page;
	}
	
	
	public SphericalPolygon loadLocation(JSONObject locationJSON) throws JSONException{
		SphericalPolygon location = null;
		
		JSONArray polygonJSON = locationJSON.getJSONArray("polygon");
					
		// Get each point array
		ArrayList<Point> points = new ArrayList<Point>();
		for (int k = 0; k < polygonJSON.length(); k++) {
			JSONObject locJSON = polygonJSON.getJSONObject(k);
			Point point = new Point(Double.parseDouble(locJSON
					.getString("lat")), Double.parseDouble(locJSON
					.getString("lon")));
			points.add(point);
		}
		
		location = new SphericalPolygon(points.toArray(new Point[] {}));
		
		return location;
	}
	
	public void loadLocations(Page page, JSONObject pageJSON) throws JSONException, UnsupportedEncodingException{
		// Build locations
		
		if(!pageJSON.isNull("locations")){
		
			JSONArray locationsJSON = pageJSON.getJSONArray("locations");
			// Get each location
			for (int j = 0; j < locationsJSON.length(); j++) {
				JSONObject locationJSON = locationsJSON.getJSONObject(j);
				SphericalPolygon location = loadLocation(locationJSON);
								
				page.addLocation(location);
			}
		}
		else if(!pageJSON.isNull("query")){
			//do a query, load the locations into the page
			LocationQueryResult queryResult = locationQuery(pageJSON.getString("query"),boundingBox);
			ArrayList<LocationQueryResultLocation> locations = queryResult.getLocations();
			for(LocationQueryResultLocation location:locations){
				page.addLocation(location.getLocation());
			}
		}
	}
		
	public LocationQueryResult locationQuery(String query, String[] boundingBox) throws JSONException, UnsupportedEncodingException{
		LocationQueryResult queryResult = new LocationQueryResult();
		
		
		HttpClient httpclient = new DefaultHttpClient();
        
    	//HttpPost request = new HttpPost(LOC_BASE +"areas/"+query+".json");
		HttpGet request = new HttpGet(LOC_BASE +"areas/"+query+".json?bounding=-1.4164621,50.9270515,-1.3992239,50.9372517");
        request.addHeader("deviceId", "xxxxx"); 
        request.addHeader("Content-Type", "text/xml");
        //request.setEntity(new StringEntity("-1.4164621,50.9270515,-1.3992239,50.9372517"));
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        
        nameValuePairs.add(new BasicNameValuePair("bounding", "-1.4164621,50.9270515,-1.3992239,50.9372517"));
        
        //request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                
        ResponseHandler<String> handler = new BasicResponseHandler(); 
        HttpResponse response = null;
		
        String result="DEFAULT POST RESULT";
        Log.i("GeoYarn: ", "HttpClient Created");
        try {  
        	//Log.i("!GeoYarn!: ", "POST Request at "+LOC_BASE +"areas/"+query+".json");
        		response = httpclient.execute(request);
        		result = handler.handleResponse(response);
        		//Log.i("GeoYarn: ", "POST Request at "+LOC_BASE +"areas/"+query+".json"+" response "+result);
       
        } catch (ClientProtocolException e) {  
        	result = e.toString();
        	e.printStackTrace();
        	Log.e("GeoYarn: ", "POST Fail "+ e);
        } catch (IOException e) {
        	result = e.toString();
            e.printStackTrace();
            Log.e("GeoYarn: ", "POST Fail "+ e);
        }  
        httpclient.getConnectionManager().shutdown();
        
        JSONObject resultJSON = new JSONObject(result);
        JSONArray featuresJSON=resultJSON.getJSONArray("features");
        //JSONArray featuresJSON = new JSONArray(result);
        for(int i = 0; i<featuresJSON.length(); i++){
        	LocationQueryResultLocation resultLoc = new LocationQueryResultLocation();
        	
        	JSONObject resultFeatureJSON = featuresJSON.getJSONObject(i);
        	JSONArray locationPolyJSON=resultFeatureJSON.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0);
        	JSONObject metaPolyJSON=resultFeatureJSON.getJSONObject("properties");
        	
        	
        	ArrayList<Point> points = new ArrayList<Point>();
        	
        	for(int l = 0; l<locationPolyJSON.length();l++){
        		JSONArray pointJSON = locationPolyJSON.getJSONArray(l);
        		Point point = new Point(pointJSON.getDouble(0), pointJSON.getDouble(1));
    			points.add(point);
        	}
        	SphericalPolygon location = new SphericalPolygon(points.toArray(new Point[] {}));
        	
        	resultLoc.setLocation(location);
        	
        	//queryResult.addLocations(location);
        	
        	
        	Iterator<String> metadataKeys = metaPolyJSON.keys();
        	while(metadataKeys.hasNext()){
        		String key = metadataKeys.next();
        		resultLoc.addMetaData(key, metaPolyJSON.getString(key));
        	}
        	
        	queryResult.addLocation(resultLoc);
        }
        
		
		return queryResult;
	}

}
