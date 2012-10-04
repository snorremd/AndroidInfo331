package no.uib.info331.geomusic.utils;

import java.util.ArrayList;
import java.util.Collection;

import no.uib.info331.geomusic.R;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;
import android.app.Activity;
import android.os.AsyncTask;

public class FetchSimilarArtistsAsyncTask extends
		AsyncTask<String, Integer, Collection<Artist>> {
	
	private Activity activity;

	public FetchSimilarArtistsAsyncTask(Activity activity) {
		super();
		this.activity = activity;
	}
	
	@Override
	protected Collection<Artist> doInBackground(String... params) {
		
		String artist = params[0];
		
		String apiKey = activity.getString(R.string.lastfm_api_key);
		ArrayList<Artist> artists = new ArrayList<Artist>(Artist.getSimilar(artist, apiKey));
		
		return artists;
	}
	
	@Override
	protected void onPostExecute(Collection<Artist> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
	}
		
	
		

}
