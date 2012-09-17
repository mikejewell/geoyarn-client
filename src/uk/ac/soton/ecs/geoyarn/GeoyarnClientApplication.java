package uk.ac.soton.ecs.geoyarn;

import uk.ac.soton.ecs.geoyarn.model.Story;
import android.app.Application;
import android.content.Context;

/**
 * Application class to provide the global context.
 */
public class GeoyarnClientApplication extends Application
{
	
	Story currentStory;
	
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
}