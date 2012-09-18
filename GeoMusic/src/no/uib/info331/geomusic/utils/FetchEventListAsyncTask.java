package no.uib.info331.geomusic.utils;

import java.util.List;

import de.umass.lastfm.Event;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;

public class FetchEventListAsyncTask extends
		AsyncTask<Location, Integer, List<Event>> {

	LocationManager locManager;
	Activity activity;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	@Override
	protected List<Event> doInBackground(Location... locations) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void onPostExecute(List<Event> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
