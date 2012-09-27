package no.uib.info331.geomusic;

import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.umass.lastfm.Event;

public class EventInfoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        
        GeoConcertApplication application = (GeoConcertApplication) getApplication();

        /* Catching intent for read the parameters */
        Intent i = getIntent();
        
        /* Reading the title of the event from the intent */
        int id;
        id = i.getIntExtra("id", -1);
        
        if(id == -1)
        {
        	/* error during the read of the parameters */
        }
        else
        {
            /* Finding the event from the list */
            Event e = application.findEvent(id);
            
            /* Setting the activity field */
            TextView eventTitle = (TextView) findViewById(R.id.eventName);
            eventTitle.setText(e.getTitle());
            
            TextView eventTimeStamp = (TextView) findViewById(R.id.eventDateAndTime);
            eventTimeStamp.setText(e.getStartDate().toString());
            
            TextView eventVenue = (TextView) findViewById(R.id.eventLocation);
            eventVenue.setText(e.getVenue().toString());
            
            TextView eventDescription = (TextView) findViewById(R.id.eventDescription);
            eventDescription.setText(e.getDescription());
            
            ListView listView = (ListView) findViewById(R.id.artistList);
            String[] values = (String[]) e.getArtists().toArray();

            // First paramenter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, android.R.id.text1, values);

            // Assign adapter to ListView
            listView.setAdapter(adapter); 
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_info, menu);
        return true;
    }
}
