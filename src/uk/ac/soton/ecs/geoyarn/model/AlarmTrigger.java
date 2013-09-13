package uk.ac.soton.ecs.geoyarn.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmTrigger extends TimeTrigger{
	
	private Date alarmTime;
	
	public AlarmTrigger(int i, int c, String ats){
				
		setId(i);
		setChapter(c);
		
	    DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
	    try {
			Date alarmTime =  df.parse(ats);
		} catch (ParseException e) {
			e.printStackTrace();
		} 		
	}
	
	public Date getAlarmTime(){
		
		return alarmTime;
		
	}
	
}