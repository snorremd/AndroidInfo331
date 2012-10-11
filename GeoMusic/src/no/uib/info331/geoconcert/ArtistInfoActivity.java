package no.uib.info331.geoconcert;

import java.io.InputStream;
import java.net.URL;

import no.uib.info331.geoconcert.utils.FetchArtistAsyncTask;
import no.uib.info331.geoconcert.utils.FetchEventsForArtistAsyncTask;
import no.uib.info331.geoconcert.utils.FetchPopularTrackTask;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import de.umass.lastfm.Artist;
import de.umass.lastfm.ImageSize;

public class ArtistInfoActivity extends Activity {
	
	String artist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_info);
        
        Intent intent = getIntent();
        artist = intent.getStringExtra("ArtistName");
        new FetchArtistAsyncTask(this).execute(artist);
        
        Button trackButton=(Button)findViewById(R.id.popularTracks);
        trackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FetchPopularTrackTask fetchPopularTrack = new FetchPopularTrackTask(ArtistInfoActivity.this);
				fetchPopularTrack.execute(artist);
				
			}
		});
        
        Button artistButton=(Button)findViewById(R.id.artistsOnSpotify);
        artistButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				
			intent.setAction(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
				intent.setComponent(new ComponentName("com.spotify.mobile.android.ui", "com.spotify.mobile.android.ui.Launcher"));
				intent.putExtra(SearchManager.QUERY, artist);
				
				try {
				  startActivity(intent);
					//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
				}catch (ActivityNotFoundException e) {
					//Toast.makeText(this, "You must first install Spotify", Toast.LENGTH_LONG);
				 Toast.makeText(ArtistInfoActivity.this, "You must first install Spotify", Toast.LENGTH_LONG).show();  
				 // Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.spotify.mobile.android.ui"));
				 Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.spotify.mobile.android.ui"));
				  
				 // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
				  startActivity(i);
				}
				
			}
		});
        
        Button similarArtistsButton = (Button)findViewById(R.id.findSimilarArtists);
        similarArtistsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(ArtistInfoActivity.this, SimilarArtistsActivity.class);
				intent.putExtra("ArtistName", artist);
				startActivity(intent);
			}
		});
        
        Button findEventsButton = (Button)findViewById(R.id.findEvents);
        findEventsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FetchEventsForArtistAsyncTask fetchEvents = 
						new FetchEventsForArtistAsyncTask(ArtistInfoActivity.this);
				
				fetchEvents.execute(artist);
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_artist_info, menu);
        return true;
    }

	public void showArtistInfo(Artist result) {

		TextView artistName = (TextView)findViewById(R.id.artist_name);
		artistName.setText(result.getName());
		
		ImageView artistImage = (ImageView)findViewById(R.id.artist_image);
		
		try {
	        InputStream is = (InputStream) new URL(result.getImageURL(ImageSize.LARGE)).getContent();
	        Drawable d = Drawable.createFromStream(is, "Last.fm");
	        artistImage.setImageDrawable(d);
	    } catch (Exception e) {
	        Log.d("ArtistInfoActivity", "Could not get artist image from Last.fm");
	    }
		
		TextView artistSummary = (TextView)findViewById(R.id.artist_summary_text);
		artistSummary.setText(result.getWikiSummary());
	}
	public void playPopularTrack(String trackname){
		
		Intent intent = new Intent(Intent.ACTION_MAIN);
		//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		//startActivity(intent);
	intent.setAction(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
		intent.setComponent(new ComponentName("com.spotify.mobile.android.ui", "com.spotify.mobile.android.ui.Launcher"));
		intent.putExtra(SearchManager.QUERY, trackname);
		
		try {
		  startActivity(intent);
			//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
		}catch (ActivityNotFoundException e) {
		  Toast.makeText(this, "You must first install Spotify", Toast.LENGTH_LONG).show();  
		 // Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.spotify.mobile.android.ui"));
		 Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.spotify.mobile.android.ui"));
		  
		 // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
		  startActivity(i);
		}
		
		
	
	}

	public void showEventListActivity() {
		Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
	}
}
