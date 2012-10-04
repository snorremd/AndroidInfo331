package no.uib.info331.geomusic.utils;
import java.util.ArrayList;
import java.util.Collection;

import no.uib.info331.geomusic.GeoConcertApplication;

import no.uib.info331.geomusic.ArtistInfoActivity;
import no.uib.info331.geomusic.R;
import no.uib.info331.geomusic.WelcomeActivity;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
public class FetchPopularTrackTask extends
AsyncTask<String, Integer, Collection<Track>> {
	
	Activity activity;
	
	public FetchPopularTrackTask(Activity activity) {
		super();
		this.activity = activity;
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Collection<Track> doInBackground(String... artists) {
		// TODO Auto-generated method stub
		
		String artist=artists[0];
			ArrayList<Track> tracks =new ArrayList<Track>( Artist.getTopTracks(artist, activity.getString(R.string.lastfm_api_key)));

			Log.d("DEBUG", "" + tracks.isEmpty());
			return tracks;
		
	}
	
	@Override
	protected void onPostExecute(Collection<Track> result){
		
		super.onPostExecute(result);
		
		ArrayList<Track> tracks= new ArrayList<Track>(result);
		String trackurl = tracks.get(0).getUrl();
		String trackname=tracks.get(0).getName();
		Log.d("FetchPopularTrackTask", "track uri"+trackurl);
		Log.d("FetchPopularTrackTask","Track name"+trackname);
		if(activity instanceof ArtistInfoActivity)
		{
			((ArtistInfoActivity) activity).playPopularTrack(trackname);
		}
		
		
	
	}

}
