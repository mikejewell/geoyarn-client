package uk.ac.soton.ecs.geoyarn.model;

import java.util.HashSet;
import java.util.Set;

import nsidc.spheres.SphericalPolygon;

public class Page {
	private int id;
	private String content;
	private String description;
	private String nextChapterURI;
	private Set<SphericalPolygon> locations;

	
	public Page() {
		locations = new HashSet<SphericalPolygon>();
	}
	
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

	public String getNextChapterURI() {
		return nextChapterURI;
	}

	public void setNextChapterURI(String nextChapter) {
		this.nextChapterURI = nextChapter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<SphericalPolygon> getLocations() {
		return locations;
	}

	public void setLocations(Set<SphericalPolygon> locations) {
		this.locations = locations;
	}
	
	public void addLocation(SphericalPolygon location) {
		this.locations.add(location);
	}

}
