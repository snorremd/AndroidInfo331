package no.uib.info331.geoconcert;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import de.umass.lastfm.Event;

public class EventListActivity extends ListActivity {
	
	private TextWatcher searchTextWatcher;

	

	ArrayList <Event> eventsAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d("EventListActivity", "Creating the activity");
        setContentView(R.layout.activity_event_list);
        GeoConcertApplication application = (GeoConcertApplication) getApplication();
        if(application.getEvents() != null) {
        	eventsAL = application.getEvents();
        	Log.d("EventListActivity", "" + eventsAL.size());
        }
        
        //ListView eventListView = (ListView) findViewById(R.id.eventListView);
        setListAdapter(new EventAdapter(this,R.layout.eventlistview_item_row, eventsAL));
        
        
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
    	            Log.d("Search box says", "*** Search value changed: " + s.toString());
    	            EventAdapter adapter = (EventAdapter) getListAdapter();
    	            adapter.getFilter().filter(s);
    	        }
    	};
    	
        EditText searchBox = (EditText) findViewById(R.id.search_box);
        searchBox.addTextChangedListener(searchTextWatcher);
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
    	
    	Event e = eventsAL.get(position);
    	
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

}
