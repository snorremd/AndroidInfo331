package no.uib.info331.geomusic.utils;

import java.util.ArrayList;
import java.util.List;

import no.uib.info331.geomusic.GeoConcertApplication;
import no.uib.info331.geomusic.WelcomeActivity;

import de.umass.lastfm.Event;
import de.umass.lastfm.Geo;
import de.umass.lastfm.PaginatedResult;


import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;

public class FetchEventListAsyncTask extends
		AsyncTask<Location, Integer, List<Event>> {
	

	
	//LocationManager locManager;

	Activity activity;
	
	public FetchEventListAsyncTask(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	@Override
	protected List<Event> doInBackground(Location... locations) {
		// TODO Auto-generated method stub
		Location loc=locations[0];
		double latitude=loc.getLatitude();
		double longitude=loc.getLongitude();
	
		PaginatedResult<Event> event=Geo.getEvents(latitude,longitude,1,"5456gfgh");
		
		
		
		return new ArrayList<Event>();
	}
	
	@Override
	protected void onPostExecute(List<Event> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		GeoConcertApplication app = (GeoConcertApplication) activity.getApplication();
		app.setEvents(result);
		if(activity instanceof WelcomeActivity) {
			
			((WelcomeActivity) activity).showEventListActivity();
		}
		
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
