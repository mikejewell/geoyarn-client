package uk.ac.soton.ecs.geoyarn.model;

import java.util.Date;

public class Story {
	private int id;
	private String URI;

	private String title;
	private String description;
	private Date publishedDate;
	private String startChapterURI;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getURI() {
		return URI;
	}

	public void setURI(String u) {
		this.URI = u;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getStartChapterURI() {
		return startChapterURI;
	}

	public void setStartChapter(String startChapter) {
		this.startChapterURI = startChapter;
	}	
	
}
