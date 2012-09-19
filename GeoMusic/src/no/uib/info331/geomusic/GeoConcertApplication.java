package no.uib.info331.geomusic;

import java.util.ArrayList;
import java.util.List;

import de.umass.lastfm.Event;

import android.app.Application;

public class GeoConcertApplication extends Application {
	
	private List<Event> events;
	
	public GeoConcertApplication() {
		super();
		events = new ArrayList(); 
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	

}
