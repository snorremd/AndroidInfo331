package no.uib.info331.geoconcert.utils;

import no.uib.info331.geoconcert.ArtistInfoActivity;
import no.uib.info331.geoconcert.GeoConcertApplication;
import no.uib.info331.geoconcert.R;
import android.app.Activity;
import android.os.AsyncTask;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;

public class FetchEventsForArtistAsyncTask extends
		AsyncTask<String, Integer, PaginatedResult<Event>> {
	
	Activity activity;

	public FetchEventsForArtistAsyncTask(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	protected PaginatedResult<Event> doInBackground(String... params) {
		
		String apiKey = activity.getString(R.string.lastfm_api_key);
		PaginatedResult<Event> events = Artist.getEvents(params[0], apiKey);
		
		return events;
	}
	
	@Override
	protected void onPostExecute(PaginatedResult<Event> result) {
		super.onPostExecute(result);
		GeoConcertApplication app = (GeoConcertApplication) activity.getApplication();
		app.createEvents(result);
		if(activity instanceof ArtistInfoActivity) {

			((ArtistInfoActivity) activity).showEventListActivity();
		}
	}

}
