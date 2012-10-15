package no.uib.info331.geoconcert.utils;

import no.uib.info331.geoconcert.EventListActivity;
import no.uib.info331.geoconcert.GeoConcertApplication;
import no.uib.info331.geoconcert.R;
import no.uib.info331.geoconcert.WelcomeActivity;
import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import de.umass.lastfm.Event;
import de.umass.lastfm.Geo;
import de.umass.lastfm.PaginatedResult;

public class FetchEventsForGeoAsyncTask extends
AsyncTask<Location, Integer, PaginatedResult<Event>> {

	Activity activity;

	public FetchEventsForGeoAsyncTask(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	protected PaginatedResult<Event> doInBackground(Location... locations) {
		
		if(locations == null)
			return null;
		
		Location loc=locations[0];
		
		if(loc == null)
			return null;
		double latitude=loc.getLatitude();
		double longitude=loc.getLongitude();

		
		
		Log.d("FetchEventList", "Get events with location: " + loc.getLatitude() + " : " + loc.getLongitude());

		PaginatedResult<Event> events = Geo.getEvents(latitude,longitude,1, activity.getString(R.string.lastfm_api_key));

		Log.d("DEBUG", "" + events.isEmpty());
		return events;

	}

	@Override
	protected void onPostExecute(PaginatedResult<Event> result) {
		
		super.onPostExecute(result);
		GeoConcertApplication app = (GeoConcertApplication) activity.getApplication();
		app.createEvents(result);
		if(activity instanceof WelcomeActivity) {

			((WelcomeActivity) activity).showEventListActivity();
		} else if(activity instanceof EventListActivity) {
			((EventListActivity) activity).showNearbyEvents();
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
