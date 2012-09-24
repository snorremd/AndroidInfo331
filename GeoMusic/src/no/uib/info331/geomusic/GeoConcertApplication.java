package no.uib.info331.geomusic;

import java.util.ArrayList;
import java.util.List;

import no.uib.info331.geomusic.utils.GeoLocationListener;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import de.umass.lastfm.Event;

public class GeoConcertApplication extends Application {
//	private static GeoConcertApplication singleton;

	/* list of the events */
	private List<Event> events;
	/* last saved location */
	private Location location;
	
	
	LocationManager locationManager;
	GeoLocationListener locListener;
	
	/* code for implement singleton 
	 * but i don't know if it's useful
	 * */
	
//	public GeoConcertApplication getInstance(){
//		return singleton;
//	}
//	
//	@Override
//	public void onCreate() {
//		super.onCreate();
//		singleton = this;
//	}
	
	
	public GeoConcertApplication() {
		super();
		setEvents(new ArrayList<Event>()); 
	}

	@Override
	public void onCreate() {
		super.onCreate();  
	}
	
	public void updateLocation(Activity activity)
	{
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locListener = new GeoLocationListener(locationManager, this, activity);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
		        
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (lastKnownLocation != null){
			this.setLocation(lastKnownLocation);
		} 
	}
	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
