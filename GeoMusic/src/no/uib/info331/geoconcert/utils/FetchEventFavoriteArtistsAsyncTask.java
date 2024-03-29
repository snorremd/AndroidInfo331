package no.uib.info331.geoconcert.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import no.uib.info331.geoconcert.EventListActivity;
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
	
		Log.d("FetchEventFavoriteArtistsAsyncTask", "Getting events from Last.fm api");

		ArrayList<Event> events = new ArrayList<Event>();
		
		String username = strings[0];
		GeoConcertApplication application = (GeoConcertApplication) activity.getApplication();
		if(username == null || username.equals(""))
			return events;
		
		Collection<Artist> artists = User.getTopArtists(username, activity.getString(R.string.lastfm_api_key));
		Log.d("FetchEventFavoriteArtistsAsyncTask", "Artist size = " + artists.size());
		
		int num = 0;
		
		Iterator<Artist> i = artists.iterator();
		while(i.hasNext() && num < 4)
		{
			num++;
			Artist a = (Artist) i.next();
			PaginatedResult<Event> pe = Artist.getEvents(a.getName(), activity.getString(R.string.lastfm_api_key));
			Iterator<Event> ie = pe.iterator();
			while (ie.hasNext())
			{
				
				Event e = ie.next();
				Log.d("event",e.getTitle());
				events.add(e);
			}
		}
		
		/* ordering list by data */
		ArrayList<Event> returnList = new ArrayList<Event>();
		while(events.size() > 0)
		{
			Date data = events.get(0).getStartDate();
			int index = 0;
			
			for(int j = 0; j < events.size(); j++)
			{
				if(data.after(events.get(j).getStartDate()))
				{
					data = events.get(j).getStartDate();
					index = j;
				}
			}
			returnList.add(events.get(index));
			events.remove(index);
		}
		return returnList;

	}

	@Override
	protected void onPostExecute(ArrayList<Event> result) {
		super.onPostExecute(result);
		GeoConcertApplication app = (GeoConcertApplication) activity.getApplication();
		app.setEvents(result);
		if(activity instanceof WelcomeActivity) {
			((WelcomeActivity) activity).showEventListActivity();
		} else if(activity instanceof EventListActivity) {
			((EventListActivity) activity).showFavoriteArtists();
		}
		Log.d("popular log","list size " + result.size());
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
