package uk.ac.soton.ecs.geoyarn.model;

public class Trigger{
	
	private int id;
	private String chapteruri;
	
	public void setId (int i){
		id=i;
	}
	
	public int getId(){
		return id;
	}
	
	public void setChapterURI (String c){
		chapteruri = c;
	}
	
	public String getChapterURI(){
		return chapteruri;
	}
	
}