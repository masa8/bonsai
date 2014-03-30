package aruite.tanoshiku.bousaikunren;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

	GoogleMap googleMap;
	
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        if ( fm != null ){
            googleMap = fm.getMap();
            googleMap.setMyLocationEnabled(true);        	
        }
    }
}
