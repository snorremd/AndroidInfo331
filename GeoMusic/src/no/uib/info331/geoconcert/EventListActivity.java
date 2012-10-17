package no.uib.info331.geoconcert;

import java.util.ArrayList;
import java.util.Collections;

import no.uib.info331.geoconcert.utils.FetchEventFavoriteArtistsAsyncTask;
import no.uib.info331.geoconcert.utils.FetchEventsForGeoAsyncTask;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import de.umass.lastfm.Event;

/**
 * This activity is used to show a list of concert events.
 * 
 * @author Snorre, Per and company
 *
 */
public class EventListActivity extends ListActivity implements LocationListener {
	
	private TextWatcher searchTextWatcher;
	private LocationManager locManager;
	private GeoConcertApplication application;

	ArrayList <Event> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d("EventListActivity", "Creating the activity");
        setContentView(R.layout.activity_event_list);
        application = ((GeoConcertApplication) getApplication());
        locManager = application.getLocationManager();
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        if(application.getEvents() != null) {
        	events = application.getEvents();
        	Log.d("EventListActivity", "" + events.size());
        }
        
        //ListView eventListView = (ListView) findViewById(R.id.eventListView);
        setListAdapter(new EventAdapter(this,R.layout.eventlistview_item_row, events));
        
        
        //we create a textWatcher for searching in the list
        searchTextWatcher = new TextWatcher() {
    	    @Override
    	        public void onTextChanged(CharSequence s, int start, int before, int count) {
    	            // ignore
    	        }

    	        @Override
    	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    	            // ignore
    	        }

    	        @Override
    	        public void afterTextChanged(Editable s) {
    	            Log.d("TextWatcher", "*** Search value changed: " + s.toString());
    	            EventAdapter adapter = (EventAdapter) getListAdapter();
    	            adapter.getFilter().filter(s);
    	        }
    	};
    	
        EditText searchBox = (EditText) findViewById(R.id.search_box);
        searchBox.addTextChangedListener(searchTextWatcher);
        
        Button nearbyEventButton = (Button) findViewById(R.id.near_events_button);
        nearbyEventButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	        	fetchEventList(application.getLocation());
			}
		});
        
        Button favoriteArtistsEventButton = (Button) findViewById(R.id.favorite_artist_events_button);
        favoriteArtistsEventButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	        	fetchEventFavoriteArtistsList();
			}
		});
        
        CheckBox sortByProximityCheckBox = (CheckBox) findViewById(R.id.sortByGeoCheckBox);
        sortByProximityCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				EventAdapter adapter = (EventAdapter) getListAdapter();	
				
				if(isChecked) {
					//sort by nearest first
					ArrayList <Event> geoSortedEvents = (ArrayList<Event>) events.clone();
					
					Collections.sort(geoSortedEvents, EventListActivity.this.application);
					
					adapter.newList(geoSortedEvents);
					adapter.notifyDataSetChanged();
				}
				else {
					adapter.newList(events);
					adapter.notifyDataSetChanged();
				}
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_list, menu);
        return true;
    }
    
    /**
     * Function for associate a specific behavior to the "click on an item in the list" action 
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Log.d("infoList", "click on item"+id);
    	
    	Event e = events.get(position);
    	
        Intent intent = new Intent(this, EventInfoActivity.class);
        intent.putExtra("id", e.getId()); //for the passage of the parameters, in this case only the id of the event
        startActivity(intent);    	
    	
    }
    
	/* new code for save the data */
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("log", "onPause");

		GeoConcertApplication application = (GeoConcertApplication)getApplication();
		if(application.getEvents() != null)
			application.writeObject();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("log", "onRestart");

		GeoConcertApplication application = (GeoConcertApplication)getApplication();
		if(application.getEvents() == null)
			application.readObject();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("log", "onResume");

		GeoConcertApplication application = (GeoConcertApplication)getApplication();
		if(application.getEvents() == null)
			application.readObject();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("log", "onStop");
		
		GeoConcertApplication application = (GeoConcertApplication)getApplication();
		if(application.getEvents() == null)
			application.writeObject();
	}
	
	 /**
     * This method is called by a LocationListener to initiate
     * fetching of events based on a Location object that specifies
     * the location of the phone.
     * @param location The current location of the phone
     */
    public void fetchEventList(Location location) {
    	Log.d("EventListActivity", "Fetch events based on location: " + location);
    	FetchEventsForGeoAsyncTask fetchEventListAT = new FetchEventsForGeoAsyncTask(this);
        fetchEventListAT.execute(location);
    	//Log.d("WelcomeActivity", "location = " + location.getLatitude() + "-" + location.getLongitude());
    }
    
    /**
     * This method is called by a button press to initiate fetching of
     * events based on a users top/favorite artists list on Last.fm. The executor
     * (AsyncTask) makes a callback to the respective activity.
     */
    public void fetchEventFavoriteArtistsList() {
    	Log.d("EventListActivity", "Fetch events based on favorite artists: ");
    	if(application.getUsername() == null || application.getUsername().equals("")) {
    		showUserNameDialog();
    	}
    	FetchEventFavoriteArtistsAsyncTask fetchEventListAT = new FetchEventFavoriteArtistsAsyncTask(this);
        fetchEventListAT.execute(application.getUsername());
    }
    
    @Override
	public void onLocationChanged(Location location) {
		Log.d("WelcomeActivity", "Received new location, fetch events.");
		
		/* Saving the location for source location to take the direction */
		application.setLocation(location);
//		fetchEventList(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("GeoLocationListener", "GPS provider disabled");
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.d("GeoLocationListener", "GPS provider enabled");
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.d("GeoLocationListener", "GPS status changed.");

		// Do a switch case and debug the new state of the GPS
		switch (status) {
		case LocationProvider.AVAILABLE:
			Log.d("GeoLocationListener", "GPS LocationProvider changed to available");
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Log.d("GeoLocationListener", "GPS LocationProvider changed to temporarily unavilable");
			break;
		case LocationProvider.OUT_OF_SERVICE:
			Log.d("GeoLocationListener", "GPS LocationProvider changed to out of service");

		}
		
	}

	public void showFavoriteArtists() {
		
		
		if(application.getEvents() != null) {
			events = application.getEvents();
		}
		setListAdapter(new EventAdapter(this,R.layout.eventlistview_item_row, events));
		
		
	}

	public void showNearbyEvents() {
		// TODO Auto-generated method stub
		if(application.getEvents() != null) {
			events = application.getEvents();
		}
		setListAdapter(new EventAdapter(this,R.layout.eventlistview_item_row, events));
	}
	

	/**
	 * Event Handling for Individual menu item selected
	 * Identify single menu item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
		case R.id.menu_preferences:
			showUserNameDialog();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void showUserNameDialog() {
		
		Log.d("EventListActivity","Show username dialog");
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Preferences");
		alert.setMessage("Insert username");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Editable value = input.getText();
				Log.d("button value", value.toString());
				application.setUsername(value.toString());
				// Do something with value!
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();
		
	}

}
