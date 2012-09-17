package uk.ac.soton.ecs.geoyarn.model;

import java.util.HashSet;
import java.util.Set;

public class Chapter {
	private int id;
	private Set<Page> pages;
	
	public Chapter() {
		pages = new HashSet<Page>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void addPage(Page page) {
		pages.add(page);
	}
	public Set<Page> getPages() {
		return pages;
	}
	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}
}
