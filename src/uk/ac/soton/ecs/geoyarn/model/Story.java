package uk.ac.soton.ecs.geoyarn.model;

import java.util.Date;

public class Story {
	private int id;

	private String title;
	private String description;
	private Date publishedDate;
	private int startChapter;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getStartChapter() {
		return startChapter;
	}

	public void setStartChapter(int startChapter) {
		this.startChapter = startChapter;
	}
}
