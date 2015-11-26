package com.partyfinder.tabfragment;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.partyfinder.R;
import com.partyfinder.model.EventItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class PartyFindermapFragment extends Fragment {
	private ArrayList<EventItem> mEventItemList;
	private View rootView;
	private ImageButton gmenu;
	private GoogleMap googleMap;
	private android.support.v4.app.FragmentManager mFragmentManager;
	private com.google.android.gms.maps.SupportMapFragment mapFragment;
	private RelativeLayout googlemapheader;
	public PartyFindermapFragment(){

	}
	public PartyFindermapFragment(ArrayList<EventItem> eventList){
		mEventItemList=eventList;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mFragmentManager=getActivity().getSupportFragmentManager();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView=inflater.inflate(R.layout.googlemap,null);
		gmenu=(ImageButton) rootView.findViewById(R.id.gmenu);
		googlemapheader=(RelativeLayout) rootView.findViewById(R.id.googlemapheader);
		gmenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
			}
		});
		
		initilizeMap(); 

		// Changing map type
		googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

		// Showing / hiding your current location
		googleMap.setMyLocationEnabled(true);

		// Enable / Disable zooming controls
		googleMap.getUiSettings().setZoomControlsEnabled(true);

		// Enable / Disable my location button
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);

		// Enable / Disable Compass icon
		googleMap.getUiSettings().setCompassEnabled(true);

		// Enable / Disable Rotate gesture
		googleMap.getUiSettings().setRotateGesturesEnabled(true);

		// Enable / Disable zooming functionality
		googleMap.getUiSettings().setZoomGesturesEnabled(true);
		
		googleMap.getUiSettings().setTiltGesturesEnabled(true);
		
		googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);

		googleMap.getUiSettings().setAllGesturesEnabled(true);
		
		EventItem meventItem=mEventItemList.get(0);
		double latitude =Double.parseDouble(meventItem.getVcLatitude());
		double longitude =Double.parseDouble(meventItem.getVcLongitude());

		Log.i("", "LatLong"+latitude+"   //   "+longitude);
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(latitude, longitude))
				.title(meventItem.getVcEventName());

		marker.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		googleMap.addMarker(marker);
		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(new LatLng(latitude,
				longitude)).zoom(15).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
		return rootView;
	}

	private void initilizeMap() {
		if (googleMap == null) {
			mapFragment=SupportMapFragment.newInstance();
//			googlemapheader.addView(mapFragment);
		Log.i("","map Fragment :"+mapFragment);
			googleMap =mapFragment.getMap();//((SupportMapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap(); //((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			Log.i("","map Fragment :"+googleMap);
			// check if map is created successfully or not
			if (googleMap == null) {
				/*Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();*/
			}
		}
	}

}
