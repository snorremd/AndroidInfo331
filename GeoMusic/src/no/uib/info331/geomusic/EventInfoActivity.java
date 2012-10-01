package no.uib.info331.geomusic;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.umass.lastfm.Event;

public class EventInfoActivity extends Activity {
	
	private ListView artistList;
	private ArrayList<String> artists;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_info);

		GeoConcertApplication application = (GeoConcertApplication) getApplication();

		//Catching intent for read the parameters
		Intent intent = getIntent();

		// Reading the id of the event from the intent
		int id = intent.getIntExtra("id", -1);

		if(id == -1) {
			Log.d("ERROR", "negative id");
		} else {
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

			artistList = (ListView) findViewById(R.id.artistList);
			
			artists = new ArrayList<String>(e.getArtists());
			

			artistList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, artists));
			artistList.setOnItemClickListener(new OnItemClickListener()  {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					String artist = (String) artistList.getItemAtPosition(position);
					Intent intent = new Intent(EventInfoActivity.this, ArtistInfoActivity.class);
					intent.putExtra("ArtistName", artist);
					startActivity(intent);
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_event_info, menu);
		return true;
	}
}
