package no.uib.info331.geomusic;

import java.util.ArrayList;
import java.util.List;

import no.uib.info331.geomusic.utils.GeoLocationListener;
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
	/* last saved location */
	private Location location;
	
	LocationManager locationManager;
	GeoLocationListener locListener;
	
	/**
	 * Private constructor. Is not to be called.
	 */
	private GeoConcertApplication() {
		super(); 
	}

	@Override
	public void onCreate() {
		super.onCreate();  
	}
	
	/**
	 * Fetches the current location of the user. The location is sent
	 * to an activity through a call back method on location update from
	 * the GeoLocationListener.
	 * 
	 * @param activity to make the callback to
	 */
	public void updateLocation(Activity activity) {
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locListener = new GeoLocationListener(locationManager, this, activity);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
		        
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (lastKnownLocation != null){
			this.setLocation(lastKnownLocation);
		} 
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
	 * 
	 * @return
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

}
