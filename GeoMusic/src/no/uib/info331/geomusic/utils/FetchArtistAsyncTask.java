package no.uib.info331.geomusic.utils;

import no.uib.info331.geomusic.ArtistInfoActivity;
import no.uib.info331.geomusic.GeoConcertApplication;
import no.uib.info331.geomusic.R;
import no.uib.info331.geomusic.WelcomeActivity;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class FetchArtistAsyncTask extends AsyncTask<String, Integer, Artist> {

	Activity activity;
	
	public FetchArtistAsyncTask(Activity activity) {
		this.activity = activity;
	}
	
	
	
	@Override
	protected Artist doInBackground(String... params) {
		
		Artist artist = Artist.getInfo(params[0], activity.getString(R.string.lastfm_api_key));
		
		return artist;
	}
	
	@Override
	protected void onPostExecute(Artist result) {
		super.onPostExecute(result);
		
		if(activity instanceof ArtistInfoActivity) {
			
			((ArtistInfoActivity) activity).showArtistInfo(result);
		}
		
	}

}
