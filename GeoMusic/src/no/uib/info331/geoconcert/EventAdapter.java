package no.uib.info331.geoconcert;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import de.umass.lastfm.Event;

public class EventAdapter extends ArrayAdapter<Event> implements Filterable{

	Context context; 
	int layoutResourceId;
	ArrayList<Event> initialEvents;
	ArrayList<Event> events;

	public EventAdapter(Context context, int layoutResourceId, ArrayList<Event> events) {
		super(context, layoutResourceId, events);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.events = events;
		this.initialEvents = (ArrayList<Event>) events.clone();

	}
	
	public void newList(ArrayList<Event> col)  {
		this.events = col;
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
		TextView shortLocationTextView = (TextView) eventListViewItemRow.findViewById(R.id.eventShortLocation);
		TextView eventArtistsTextView = (TextView) eventListViewItemRow.findViewById(R.id.eventArtistsTextView);

		String eventTitle = events.get(position).getTitle();
		String dateAndTime = "" + events.get(position).getStartDate();
		String shortLocation = events.get(position).getVenue().getName() + " " + events.get(position).getVenue().getCity();
		String artists = "";
		ArrayList<String> artistList = new ArrayList<String>(events.get(position).getArtists());
		int listSize = artistList.size();
		for(int i = 0; i < listSize; i++) {

			artists += artistList.get(i);
			if(i < listSize-1) {
				artists += ", ";
			}
		}

		titleTextView.setText(eventTitle);
		dateAndTimeTextView.setText(dateAndTime);
		shortLocationTextView.setText(shortLocation);
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
				ArrayList<Event> list = initialEvents;
				ArrayList<Event> newlist = new ArrayList<Event>();
				FilterResults results = new FilterResults();            	


				if (searchFor == null || searchFor.length() == 0)
				{
					results.values = list;
					results.count = list.size();
				}

				else
				{
					for(Event e : list) {
						String textToMatch = e.getTitle() + " " + e.getVenue().getName() + " ";
						ArrayList<String> artists = new ArrayList<String>(e.getArtists());
						StringBuilder sb = new StringBuilder(textToMatch);

						for(String a : artists) {
							sb.append(a);
							sb.append(" ");
						}
						textToMatch = sb.toString().toLowerCase();
						//	            			Log.d("EventAdapter", "" + textToMatch);
						if(textToMatch.indexOf(searchFor) != -1) {
							newlist.add(e);
						}
					}
					results.values = newlist;
					results.count = newlist.size();
				}

				return results;
			}



			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				@SuppressWarnings("unchecked")
				ArrayList<Event> newlist = (ArrayList<Event>) results.values;
				clear();
				for(Event e : newlist) {
					add(e);
				}
				notifyDataSetChanged();



			}

		};

	}

}
