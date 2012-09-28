package no.uib.info331.geomusic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ArtistInfoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_artist_info, menu);
        return true;
    }
}
