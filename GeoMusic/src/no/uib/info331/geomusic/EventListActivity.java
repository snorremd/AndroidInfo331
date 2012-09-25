package no.uib.info331.geomusic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class EventListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d("EventListActivity", "Creating the activity");
        setContentView(R.layout.activity_event_list);
        GeoConcertApplication application = (GeoConcertApplication) getApplication();
        PaginatedResult<Event> events = application.getEvents();
        Event[] eventArray = new Event[0];
        if(events != null) {
        	ArrayList <Event> a = new ArrayList<Event>(events.getPageResults());
        	eventArray = (Event[]) a.toArray(new Event[a.size()]);
        	Log.d("EventListActivity", "" + a.size());
        }
        
        //ListView eventListView = (ListView) findViewById(R.id.eventListView);
        setListAdapter(new EventAdapter(this,R.layout.eventlistview_item_row, eventArray));
        
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_list, menu);
        return true;
    }
}
