package no.uib.info331.geomusic;

import java.util.ArrayList;

import de.umass.lastfm.Event;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event> implements Filterable{

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
		String dateAndTime = "" + events[position].getStartDate();
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
	
	
	@Override
	/**
	 * We override the getFilter method for filtering the text on the search box
	 */
	public Filter getFilter() {
		//create a new filter
		return new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				String searchFor = constraint.toString().toLowerCase();

                Event[] list = events.clone();
            	Event[] newlist = new Event[events.length];
            	FilterResults results = new FilterResults();            	
            	
            	
	            if (searchFor == null || searchFor.length() == 0)
	            {
	                results.values = list;
	                results.count = list.length;
	            }
	            
	            else
	            {
	                int count = list.length;
	                int added = 0;
	                for (int i=0; i<count; i++)
	                {
	                    Event eventTreated = list[i];
	                    newlist[added] = eventTreated;
	                    added++;
	                    //TODO add all info we want to filter on. for now only title.
	                    String eventTitleAndArtists = eventTreated.getTitle().toLowerCase();
	                    if(eventTitleAndArtists.indexOf(searchFor) != -1) {
	                    	
	                    }
	                }
	                results.values = newlist;
	                results.count = newlist.length;
	            }
				
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				Event[] newlist = (Event[]) results.values;
				//TODO completely rewrite EventAdapter to use ArrayList
				//http://stackoverflow.com/questions/10504353/adapter-clear-crashes-android-app
				clear();
				for(Event e : newlist) {
					add(e);
				}
				notifyDataSetChanged();
				
				
				
			}
			
		};
		
	}
	
}
