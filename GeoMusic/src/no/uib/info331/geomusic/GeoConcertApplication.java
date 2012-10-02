package no.uib.info331.geomusic;

import java.util.Iterator;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;

public class GeoConcertApplication extends Application {
//	private static GeoConcertApplication singleton;

	/* list of the events */
	private PaginatedResult<Event> events;
	private LocationManager locationManager;
	private Location location;
	
	/**
	 * Private constructor. Is not to be called.
	 */
	public GeoConcertApplication() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	}
	
	/**
	 * 
	 * @return
	 */
	public PaginatedResult<Event> getEvents() {
		return events;
	}

	/**
	 * 
	 * @param result
	 */
	public void setEvents(PaginatedResult<Event> result) {
		this.events = result;
	}

	/**
	 * @return the locationManager
	 */
	public LocationManager getLocationManager() {
		return locationManager;
	}

	/**
	 * @param locationManager the locationManager to set
	 */
	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}

	/**
	 * Function for get a specific event from the list
	 * @param id
	 * @return the searched event
	 */
	public Event findEvent(int id) {
		/* Get iterator for search inside the list of the events */
		Iterator i = this.events.iterator();
		while(i.hasNext())
		{
			Event e = (Event) i.next();
			if (e.getId() == id)
				return e;
		}
		return null;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
