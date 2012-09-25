package no.uib.info331.geomusic;

import java.util.ArrayList;

import de.umass.lastfm.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event> {

	Context context; 
    int layoutResourceId;
    Event[] events;
    
	public EventAdapter(Context context, int layoutResourceId, Event[] events) {
		super(context, layoutResourceId, events);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.events = events;
	}
	
	@Override
	public View getView(
			int position, 
			View convertView, 
			ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View eventListViewItemRow = inflater.inflate(R.layout.eventlistview_item_row, parent, false);
		
		// Get instances for text views in the row view
		TextView titleTextView = (TextView) eventListViewItemRow.findViewById(R.id.eventTitleTextView);
		TextView dateAndTimeTextView = (TextView) eventListViewItemRow.findViewById(R.id.eventDateAndTimeTextView);
		TextView eventArtistsTextView = (TextView) eventListViewItemRow.findViewById(R.id.eventArtistsTextView);
		
		String eventTitle = events[position].getTitle();
		String dateAndTime = events[position].getStartDate() + " - " + events[position].getEndDate();
		String artists = "";
		ArrayList<String> artistList = new ArrayList<String>(events[position].getArtists());
		int listSize = artistList.size();
		for(int i = 0; i < listSize; i++) {
			
			artists += artistList.get(i);
			if(i < listSize-1) {
				artists += ", ";
			}
		}
		
		titleTextView.setText(eventTitle);
		dateAndTimeTextView.setText(dateAndTime);
		eventArtistsTextView.setText(artists);
		
		return eventListViewItemRow;
		
	}

}
