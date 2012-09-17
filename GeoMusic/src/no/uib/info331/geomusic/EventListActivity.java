package no.uib.info331.geomusic;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class EventListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FetchEventListAsyncTask fetcher = new FetchEventListAsyncTask();
        fetcher.execute();
        
        setContentView(R.layout.activity_event_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_list, menu);
        return true;
    }
    
    
    public class FetchEventListAsyncTask extends
    AsyncTask<String, Integer, List<Event>> {

        public double lati = 0.0;
        public double longi = 0.0;

        public LocationManager mLocationManager;
        public VeggsterLocationListener mVeggsterLocationListener;

        @Override
        protected void onPreExecute() {
            mVeggsterLocationListener = new VeggsterLocationListener();
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0,
                    mVeggsterLocationListener);

        }

        protected void onPostExecute(String result) {
           
        	Log.d("Position", "Lattiitude: " + this.lati);
            Toast.makeText(EventListActivity.this,
                    "LATITUDE :" + lati + " LONGITUDE :" + longi,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected List<Event> doInBackground(String... params) {
            // TODO Auto-generated method stub

            while (this.lati == 0.0) {

            }
            return null;
        }

        public class VeggsterLocationListener implements LocationListener {

            @Override
            public void onLocationChanged(Location location) {

                int lat = (int) location.getLatitude(); // * 1E6);
                int log = (int) location.getLongitude(); // * 1E6);
                int acc = (int) (location.getAccuracy());

                String info = location.getProvider();
                try {

                    // LocatorService.myLatitude=location.getLatitude();

                    // LocatorService.myLongitude=location.getLongitude();

                    lati = location.getLatitude();
                    longi = location.getLongitude();

                } catch (Exception e) {
                    // progDailog.dismiss();
                    // Toast.makeText(getApplicationContext(),"Unable to get Location"
                    // , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("OnProviderDisabled", "OnProviderDisabled");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("onProviderEnabled", "onProviderEnabled");
            }

            @Override
            public void onStatusChanged(String provider, int status,
                    Bundle extras) {
                Log.i("onStatusChanged", "onStatusChanged");

            }

        }

    }

}
