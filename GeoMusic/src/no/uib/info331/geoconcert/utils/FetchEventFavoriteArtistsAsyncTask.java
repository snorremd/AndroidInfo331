package no.uib.info331.geoconcert.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import no.uib.info331.geoconcert.GeoConcertApplication;
import no.uib.info331.geoconcert.R;
import no.uib.info331.geoconcert.WelcomeActivity;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.User;

public class FetchEventFavoriteArtistsAsyncTask extends
AsyncTask<String, Integer, ArrayList<Event>> {

	Activity activity;

	public FetchEventFavoriteArtistsAsyncTask(Activity activity) {
		super();
		this.activity = activity;
		Log.d("debug","start async task");

	}

	@Override
	protected ArrayList<Event> doInBackground(String... strings) {
	
//		Log.d("FetchEventList", "Get events with location: " + loc.getLatitude() + " : " + loc.getLongitude());

		ArrayList<Event> events = new ArrayList<Event>();
		
		Collection<Artist> artists = User.getTopArtists(activity.getString(R.string.user), activity.getString(R.string.lastfm_api_key));
		Log.d("DEBUG", "artist size = " + artists.size());
		
		int num = 0;
		
		Iterator i = artists.iterator();
		while(i.hasNext() && num < 4)
		{
			num++;
			Artist a = (Artist) i.next();
			PaginatedResult<Event> pe = Artist.getEvents(a.getName(), activity.getString(R.string.lastfm_api_key));
			Iterator ie = pe.iterator();
			while (ie.hasNext())
			{
				
				Event e = (Event) ie.next();
				Log.d("event",e.getTitle());
				events.add(e);
			}
		}
		
		return events;

	}

	@Override
	protected void onPostExecute(ArrayList<Event> result) {
		super.onPostExecute(result);
		GeoConcertApplication app = (GeoConcertApplication) activity.getApplication();
		app.setEvents(result);
		if(activity instanceof WelcomeActivity) {

			((WelcomeActivity) activity).showEventListActivity();
		}
		Log.d("popular log","list size " + result.size());
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
