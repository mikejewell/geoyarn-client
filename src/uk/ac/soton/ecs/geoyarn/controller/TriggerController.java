package uk.ac.soton.ecs.geoyarn.controller;

import java.util.ArrayList;
import java.util.Date;

import uk.ac.soton.ecs.geoyarn.GeoyarnClientApplication;
import uk.ac.soton.ecs.geoyarn.StoryActivity;
import uk.ac.soton.ecs.geoyarn.model.AlarmTrigger;
import uk.ac.soton.ecs.geoyarn.model.Chapter;
import uk.ac.soton.ecs.geoyarn.model.TimerTrigger;
import uk.ac.soton.ecs.geoyarn.model.Trigger;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TriggerController{
	
	Activity context;
	ArrayList<Trigger> triggers;
	StoryEngine storyController;
	
	AlarmManager alarmManager;
	
	GeoyarnClientApplication app;
	
	public TriggerController(Activity a){
		
		context = a;
		storyController = new StoryEngine();
		alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
		app = (GeoyarnClientApplication)context.getApplication();
		Log.i("GeoYarn: ", "TriggerController");
		
		triggers=app.getChapter().getTriggers();
		//triggers = new ArrayList<Trigger>();
		
		Log.i("GeoYarn: ", "TriggerController trigs "+triggers.size());
		
		for(Trigger t:triggers){
			
			if(t instanceof AlarmTrigger){
				
				setAlarm(t.getChapter(),((AlarmTrigger)t).getAlarmTime().getTime());
			}
			else if(t instanceof TimerTrigger){
				Log.i("GeoYarn: ", "TimerTrigger "+((TimerTrigger)t).getTimer());
				long timerMilli = ((TimerTrigger)t).getTimer()*1000;
				setAlarm(t.getChapter(),new Date().getTime()+timerMilli);
			}
			
		}	
		
	}
	
	public void setAlarm(int c, long time){
		
		app.setChapter(storyController.getChapter(app.getStory().getId(),c, app.getCurrentLat(), app.getCurrentLong()));
		Intent myIntent = new Intent(context , StoryActivity.class);     
	    PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);
		
	    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
	    
	}
		
}