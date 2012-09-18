package no.uib.info331.geomusic.utils;

import no.uib.info331.geomusic.WelcomeActivity;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

public class GeoLocationListener implements LocationListener {

	private LocationManager locManager;
	private Activity activity;

	public GeoLocationListener(LocationManager locManager, Activity activity) {
		super();
		this.locManager = locManager;
		this.activity = activity;
	}

	@Override
	public void onLocationChanged(Location location) {
		// Make sure we have a connection
		if(location != null) {
			// Stop listening for gps location to save battery
			locManager.removeUpdates(this);
			
			/*
			 * Check which type of activity is requesting a gps location and
			 * send a callback to one of that activity functions with a gps
			 * 
			 */
			if(activity instanceof WelcomeActivity) {
				((WelcomeActivity) activity).fetchEventList(location);
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
