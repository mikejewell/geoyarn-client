package uk.ac.soton.ecs.geoyarn.model;

import java.util.Set;

public class Chapter {
	private int id;
	private Set<Page> pages;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<Page> getPages() {
		return pages;
	}
	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}
}
