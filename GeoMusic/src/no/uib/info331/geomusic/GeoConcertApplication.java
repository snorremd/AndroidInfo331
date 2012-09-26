package no.uib.info331.geomusic;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;

public class GeoConcertApplication extends Application {
//	private static GeoConcertApplication singleton;

	/* list of the events */
	private PaginatedResult<Event> events;
	private LocationManager locationManager;
	
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
}
