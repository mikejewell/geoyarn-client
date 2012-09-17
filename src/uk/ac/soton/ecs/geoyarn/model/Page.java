package uk.ac.soton.ecs.geoyarn.model;

public class Page {
	private int id;
	private String content;
	private String description;
	private int nextChapter;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNextChapter() {
		return nextChapter;
	}

	public void setNextChapter(int nextChapter) {
		this.nextChapter = nextChapter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
