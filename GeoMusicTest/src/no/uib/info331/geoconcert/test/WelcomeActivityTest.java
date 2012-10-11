package no.uib.info331.geoconcert.test;

import no.uib.info331.geoconcert.WelcomeActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class WelcomeActivityTest extends
		ActivityInstrumentationTestCase2<WelcomeActivity> {
	
	private WelcomeActivity mWelcomeActivity;
	private TextView mTextView;

	public WelcomeActivityTest() {
		super(WelcomeActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		mWelcomeActivity = getActivity();
		mTextView = (TextView) mWelcomeActivity.findViewById(no.uib.info331.geoconcert.R.id.welcomeText);
	}
	
	public void testPreCondition() {
		
		String appName =  mWelcomeActivity.getString(no.uib.info331.geoconcert.R.string.app_name);
		assertEquals(appName, mTextView.getText());
	}

}
