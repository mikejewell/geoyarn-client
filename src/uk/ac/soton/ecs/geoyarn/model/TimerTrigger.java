package uk.ac.soton.ecs.geoyarn.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerTrigger extends TimeTrigger{
	
private int timer;
	
	public TimerTrigger(int i, int c, int t){
				
		setId(i);
		setChapter(c);
		timer=t;	    
	}
	
	public int getTimer(){
		
		return timer;
		
	}
	
}