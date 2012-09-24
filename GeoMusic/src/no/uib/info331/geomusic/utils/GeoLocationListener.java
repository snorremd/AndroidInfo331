package no.uib.info331.geomusic.utils;

import no.uib.info331.geomusic.GeoConcertApplication;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import no.uib.info331.geomusic.WelcomeActivity;

/**
 * 
 * @author Snorre Magnus Dav¿en
 * @author Per-¯ivin Andersen
 * @author Uma Behera
 * @author Andrea Cresta
 * @author Armindokht
 *
 */
public class GeoLocationListener implements LocationListener {

	private LocationManager locManager;
	private GeoConcertApplication application;
	private Activity activity;
	
	/**
	 * 
	 * @param locManager object to get current location
	 * @param application GeoConcertApplication object to store location info
	 * @param activity to make a callback to
	 */
	public GeoLocationListener(LocationManager locManager, GeoConcertApplication application, Activity activity) {
		super();
		
		this.locManager = locManager;
		this.application = application;
		this.activity=activity;
	}

	@Override
	public void onLocationChanged(Location location) {
		// Make sure we have a connection
		if(location != null) {

			// Setting location
			application.setLocation(location);
			
			// Stop listening for gps location to save battery
			locManager.removeUpdates(this);
			if(this.activity != null)
			{
				// Make a callback according to Activity class
				if(this.activity.getClass().equals(WelcomeActivity.class))
				{
					((WelcomeActivity) this.activity).fetchEventList(location);
				}
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Make a callback to an activity to start gps setting intent
		Log.d("GeoLocationListener", "GPS provider disabled");

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("GeoLocationListener", "GPS provider enabled.");

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.d("GeoLocationListener", "GPS status changed.");

		// Do a switch case and debug the new state of the GPS
		switch (status) {
		case LocationProvider.AVAILABLE:
			Log.d("GeoLocationListener", "GPS LocationProvider changed to available");
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Log.d("GeoLocationListener", "GPS LocationProvider changed to temporarily unavilable");
			break;
		case LocationProvider.OUT_OF_SERVICE:
			Log.d("GeoLocationListener", "GPS LocationProvider changed to out of service");

		}

	}

}
