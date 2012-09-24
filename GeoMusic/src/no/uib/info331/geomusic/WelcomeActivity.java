package no.uib.info331.geomusic;

import java.util.List;

import de.umass.lastfm.Event;

import no.uib.info331.geomusic.utils.FetchEventListAsyncTask;
import no.uib.info331.geomusic.utils.GeoLocationListener;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class WelcomeActivity extends Activity {
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        ((GeoConcertApplication)this.getApplication()).updateLocation(this); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_welcome, menu);
        return true;
    }
    
    /**
     * This method is called by a LocationListener to initiate
     * fetching of events based on a Location object that specifies
     * the location of the phone.
     * @param location The current location of the phone
     */
    public void fetchEventList(Location location) {
    	//FetchEventListAsyncTask fetchEventListAT = new FetchEventListAsyncTask(this);
        //fetchEventListAT.execute(location);
    	Log.d("Info", "inside fetcheventlist");
    	
    }
    
    /**
     * An object implementing the AsyncTask interface can call
     * this method and pass a list of events to show the
     * event list activity and pass it's events to the next
     * activity.
     */
    public void showEventListActivity() {
    	// Make intent to change to event list activity
        Intent intent = new Intent(this, EventInfoActivity.class);
        startActivity(intent);

    }
    
}
