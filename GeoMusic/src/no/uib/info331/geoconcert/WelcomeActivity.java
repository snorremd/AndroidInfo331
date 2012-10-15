package no.uib.info331.geoconcert;

import no.uib.info331.geoconcert.utils.FetchEventFavoriteArtistsAsyncTask;
import no.uib.info331.geoconcert.utils.FetchEventsForGeoAsyncTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeActivity extends Activity implements LocationListener {

	private LocationManager locManager;
	private GeoConcertApplication application;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("WelcomeActivity", "In on create welcome activity");

		Log.d("WelcomeActivity", "Sat content view");
		setContentView(R.layout.activity_welcome);

		Log.d("WelcomeActivity", "Get application instance");
		application = ((GeoConcertApplication) getApplication());
		locManager = application.getLocationManager();
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		/* the follow line works only on a phone!! */
		//        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		//the following lines are needed for the welcomeactivity not to wait for updates and run immediately
		Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location != null) {
			application.setLocation(location);
		}

		fetchEventList(application.getLocation());
	}

	/**
	 * This method is called by a LocationListener to initiate
	 * fetching of events based on a Location object that specifies
	 * the location of the phone.
	 * @param location The current location of the phone
	 */
	public void fetchEventList(Location location) {
		Log.d("WelcomeActivity", "Fetch events based on location: " + location);
		FetchEventsForGeoAsyncTask fetchEventListGeoTask = new FetchEventsForGeoAsyncTask(this);
		fetchEventListGeoTask.execute(location);
		//Log.d("WelcomeActivity", "location = " + location.getLatitude() + "-" + location.getLongitude());
	}

	/**
	 * An object implementing the AsyncTask interface can call
	 * this method and pass a list of events to show the
	 * event list activity and pass it's events to the next
	 * activity.
	 */
	public void showEventListActivity() {
		// Make intent to change to event list activity
		Intent intent = new Intent(this, EventListActivity.class);
		startActivity(intent);

	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("WelcomeActivity", "Received new location, fetch events.");

		/* Saving the location for source location to take the direction */
		application.setLocation(location);
		//		fetchEventList(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("GeoLocationListener", "GPS provider disabled");

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("GeoLocationListener", "GPS provider enabled");

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

	/* MENU FUNCTION */

	// Initiating Menu XML file (menu.xml)
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected
	 * Identify single menu item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
		case R.id.menu_preferences:
			Log.d("menu","pressed preferences button ");
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Preferences");
			alert.setMessage("Insert username");

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Editable value = input.getText();
					Log.d("button value", value.toString());
					application.setUsername(value.toString());
					// Do something with value!
				}
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});

			alert.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}    
}
