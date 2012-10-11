package no.uib.info331.geoconcert;

import java.util.ArrayList;
import java.util.Collection;

import no.uib.info331.geoconcert.utils.FetchSimilarArtistsAsyncTask;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.umass.lastfm.Artist;

public class SimilarArtistsActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_artists);
        
        FetchSimilarArtistsAsyncTask similarArtistsFetcher = 
        		new FetchSimilarArtistsAsyncTask(this);
        
        String artistName = "Datarock";
        similarArtistsFetcher.execute(artistName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_similar_artists, menu);
        return true;
    }
    
    public void showSimilarArtists(Collection<Artist> artists) {
    	ArrayList<String> artistStrings = new ArrayList<String>();
    	
    	for(Artist artist : artists) {
    		artistStrings.add(artist.getName());
    	}
    	
    	setListAdapter(new ArrayAdapter<String>(
    			this, 
    			android.R.layout.simple_list_item_1, 
    			artistStrings
    			)
    		);
    }
    
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Log.d("infoList", "click on item"+id);
    	
    	ListView artistList = (ListView)getListView();
    	String artistName = (String) artistList.getItemAtPosition(position).toString();
        Intent intent = new Intent(this, ArtistInfoActivity.class);
        intent.putExtra("ArtistName", artistName);
        startActivity(intent);
    	
    }
}
