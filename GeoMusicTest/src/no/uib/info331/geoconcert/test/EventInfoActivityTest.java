package no.uib.info331.geoconcert.test;

import no.uib.info331.geoconcert.EventInfoActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import de.umass.lastfm.Event;

public class EventInfoActivityTest extends
		ActivityInstrumentationTestCase2<EventInfoActivity> {
	
	private EventInfoActivity mEventInfoActivity;
	private TextView mEventName;
	private TextView mEventLocation;
	private TextView mEventDateAndTime;
	private TextView mArtistsTitle;
	private Intent mIntent;
	
	public EventInfoActivityTest(Class<EventInfoActivity> activityClass) {
		super(activityClass);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		mEventInfoActivity = getActivity();
		mEventName = (TextView) mEventInfoActivity.findViewById(no.uib.info331.geoconcert.R.id.eventName);
		mEventLocation = (TextView) mEventInfoActivity.findViewById(no.uib.info331.geoconcert.R.id.eventLocation);
		mEventDateAndTime = (TextView) mEventInfoActivity.findViewById(no.uib.info331.geoconcert.R.id.eventDateAndTime);
		mArtistsTitle = (TextView) mEventInfoActivity.findViewById(no.uib.info331.geoconcert.R.id.artistsTitle);
		
		//we make a new intent for the view as a mock
		Event mEvent = Event.getInfo("3344421", ((String)mEventInfoActivity.getText(no.uib.info331.geoconcert.R.string.lastfm_api_key)));
		
		mIntent = new Intent(Intent.ACTION_VIEW);
	}	


	
	public void testPreCondition() {
	}

}
