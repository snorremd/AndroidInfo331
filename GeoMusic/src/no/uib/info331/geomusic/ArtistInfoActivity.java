package no.uib.info331.geomusic;

import java.io.InputStream;
import java.net.URL;

import no.uib.info331.geomusic.utils.FetchArtistAsyncTask;
import no.uib.info331.geomusic.utils.FetchEventListAsyncTask;
import no.uib.info331.geomusic.utils.FetchPopularTrackTask;

import de.umass.lastfm.Artist;
import de.umass.lastfm.ImageSize;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
}
