package uk.ac.soton.ecs.geoyarn;

import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.Page;
import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Application;
import android.content.Context;

/**
 * Application class to provide the global context.
 */
public class GeoyarnClientApplication extends Application
{
	
	Story currentStory;
	Chapter currentChapter;
	Page currentPage;
	
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
}