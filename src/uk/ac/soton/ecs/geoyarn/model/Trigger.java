package uk.ac.soton.ecs.geoyarn.model;

public class Trigger{
	
	private int id;
	private int chapter;
	
	public void setId (int i){
		id=i;
	}
	
	public int getId(){
		return id;
	}
	
	public void setChapter (int c){
		chapter = c;
	}
	
	public int getChapter(){
		return chapter;
	}
	
}