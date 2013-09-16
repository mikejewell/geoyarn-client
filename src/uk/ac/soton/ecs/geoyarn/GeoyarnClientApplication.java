package uk.ac.soton.ecs.geoyarn;

import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.Page;
import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Application class to provide the global context.
 */
public class GeoyarnClientApplication extends Application
{
	
	Story currentStory;
	Chapter currentChapter;
	Page currentPage;
	
	private double currentLat;
	private double currentLong;
	
	private static GeoyarnClientApplication instance;

	public GeoyarnClientApplication()
	{
		instance = this;
	}

	public static Context getContext()
	{
		return instance;
	}
	
	public void setStory(Story s){
		currentStory = s;
	}
	
	public Story getStory(){
		return currentStory;
	}
	
	public void setChapter(Chapter c){
		//Log.i("GeoYarn: ","SET CHAPTER "+c.getId());
		currentChapter = c;
	}
	
	public Chapter getChapter(){
		return currentChapter;
	}
	
	public void setPage(Page p){
		currentPage = p;
	}
	
	public Page getPage(){
		return currentPage;
	}

	public void setCurrentLat(double currentLat) {
		this.currentLat = currentLat;
	}

	public double getCurrentLat() {
		return currentLat;
	}

	public void setCurrentLong(double currentLong) {
		this.currentLong = currentLong;
	}

	public double getCurrentLong() {
		return currentLong;
	}
	
	
	
	
}