package no.uib.info331.geomusic;

import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
        Intent intent = getIntent();
        
        /* Reading the id of the event from the intent */
        int id;
        id = intent.getIntExtra("id", -1);
        
        if(id == -1)
        {
        	Log.d("ERROR", "negative id");
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
            eventVenue.setText(e.getVenue().getName() + " , " + e.getVenue().getStreet() + " @ " +e.getVenue().getCity());
            
            TextView eventDescription = (TextView) findViewById(R.id.eventDescription);
            eventDescription.setText(Html.fromHtml(e.getDescription()).toString());
            Log.d("EventInfoActivity eventDescription", "" + e.getDescription());

            
            ListView listView = (ListView) findViewById(R.id.artistList);
            /* Copy of the artists in the array "values" for fill the listview */
            Iterator i = e.getArtists().iterator();
            String[] values =new String[e.getArtists().size()];
            int j = 0;
            while(i.hasNext())
            {
            	values[j] = (String) i.next();
            	j++;
            }
            
            // First paramenter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, android.R.id.text1, values);

            /* Assign adapter to ListView */
            listView.setAdapter(adapter); 
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_info, menu);
        return true;
    }
}
