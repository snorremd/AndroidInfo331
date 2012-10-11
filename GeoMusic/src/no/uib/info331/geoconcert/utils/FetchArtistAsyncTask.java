package no.uib.info331.geoconcert.utils;

import no.uib.info331.geoconcert.ArtistInfoActivity;
import no.uib.info331.geoconcert.R;
import android.app.Activity;
import android.os.AsyncTask;
import de.umass.lastfm.Artist;

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
