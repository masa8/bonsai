package aruite.tanoshiku.bousaikunren;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import aruite.tanoshiku.bousaikunren.GeoHex.Zone;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapFragment extends SupportMapFragment implements
		OnMapClickListener, android.location.LocationListener {
	Random rnd = new Random();
	
	private LocationManager mLocationManager;
	private GoogleMap mMap;

	private HashMap<String,Polygon> mPolyMap;
	private HashMap<String,Data> mDataMap;
	
	public static MapFragment newInstance() {
		return new MapFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPolyMap = new HashMap<String,Polygon>();
		mDataMap = new HashMap<String,Data>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		mMap = getMap();
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.450821,
				139.631497), 14.0f));
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				return false;
			}
		});

		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker arg0) {

			}

		});

		mMap.setMyLocationEnabled(true);

		mMap.setOnMapClickListener(this);
		return view;

	}

	@Override
	public void onLocationChanged(Location location) {

		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());
		update(latLng);
		
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

	}

	@Override
	public void onResume() {
		super.onResume();
		
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getApplicationContext());
		if (result == ConnectionResult.SUCCESS){
			mLocationManager =
			        (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
			if ( mLocationManager != null ){
				mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
						10000,          // 10-second interval.
						10,             // 10 meters.
						this);
			}

			
		}else{
		}

	}

	private void itemProvider() {

		HashMap<String, Data> d = DataLab.get(getActivity()).getData();
		if (d != null) {
			mMap.clear();
			for (Map.Entry<String, Data> item : d.entrySet()) {

				if (item.getValue().title.equals(Data.KYOTEN) ) {

					mMap.addMarker(new MarkerOptions()
							.position(new LatLng(Double.valueOf(item.getValue().lat),
												Double.valueOf(item.getValue().lng)))
							.title(item.getValue().description)
							.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
							);
					
				} else if (item.getValue().title.equals(Data.LIQUEFY) ) {
					Zone z = GeoHex.getZoneByLocation(
							Double.valueOf(item.getValue().lat),
							Double.valueOf(item.getValue().lng), 8);
					GeoHex.Loc[] l = z.getHexCoords();
					Polygon polygon  = mMap.addPolygon(new PolygonOptions()
							.add(new LatLng(l[0].lat, l[0].lon),
									new LatLng(l[1].lat, l[1].lon),
									new LatLng(l[2].lat, l[2].lon),
									new LatLng(l[3].lat, l[3].lon),
									new LatLng(l[4].lat, l[4].lon),
									new LatLng(l[5].lat, l[5].lon))
							.strokeColor(Color.RED)
							.strokeWidth(2)
							.fillColor(0x0f0000ff)
							.strokeColor(0xff000088));
					if (mPolyMap.containsKey(z.code)){
						Polygon p = mPolyMap.get(z.code);
						p.remove();
					}
					mPolyMap.put(z.code, polygon);
					mDataMap.put(z.code, item.getValue());

				}

			}

		}
	}

	private void update(LatLng place){

		new JsonTask(getActivity()).execute(Data.URL);
		itemProvider();

		Zone current = GeoHex.getZoneByLocation(
				place.latitude,
				place.longitude, 8);
		
		if ( mPolyMap.containsKey(current.code) ){
			Toast.makeText(getActivity(), mDataMap.get(current.code).description,
					Toast.LENGTH_SHORT).show();
			
		}
		
	}
	
	@Override
	public void onMapClick(LatLng arg0) {
		update(arg0);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		
	}

}
