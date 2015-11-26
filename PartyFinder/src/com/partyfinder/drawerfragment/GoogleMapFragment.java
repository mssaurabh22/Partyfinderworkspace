package com.partyfinder.drawerfragment;


import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.mp;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.partyfinder.EventDetailsActivity;
import com.partyfinder.R;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.model.EventItem;
import com.partyfinder.widget.SlideHolder;
import com.partyfinder.widget.SlideHolder.OnSlideListener;

public class GoogleMapFragment extends android.support.v4.app.Fragment{
	private GoogleMap googleMap;
	private Context mContext;
	private EventItem meventItem;
	private ArrayList<EventItem> eventItemList;
	private final static String TAG="GoogleMapFragment";
	List<Marker> mMarkers = new ArrayList<Marker>();
	private SlideHolder mSlidHolder;
	private FrameLayout mFramelayout;
	private RelativeLayout mHeader; 
	private int i;
	private LayoutInflater inflater;
	private ViewGroup root;
	private ImageButton gmenu;
	private FragmentManager mFragmentManager;
	private SupportMapFragment mSupportMapFragment; 

	public LocationManager locationManager;
	private MapView mapView;
	private android.support.v4.app.Fragment mFragment;





	public GoogleMapFragment() {
		// TODO Auto-generated constructor stub
	}
	public GoogleMapFragment(ArrayList<EventItem> eventItemArray) {
		// TODO Auto-generated constructor stub
		eventItemList=eventItemArray;
		Log.i("",TAG+"Event list : "+eventItemArray.size());
	}


	@Override
	public View onCreateView( final LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub



		mContext=getActivity().getApplicationContext();
		mFragmentManager=getActivity().getSupportFragmentManager();



		//	mFragmentManager=getFragmentManager();

		root=(ViewGroup) inflater.inflate(R.layout.googlemap,container, false);
		root.requestTransparentRegion(root);

		mapView = (MapView) root.findViewById(R.id.mapView);
		mapView.requestTransparentRegion(mapView);
		mapView.onCreate(savedInstanceState);


		//		mapView.onResume();


		mSlidHolder=(SlideHolder) getActivity().findViewById(R.id.slideHolder);
		mFramelayout=(FrameLayout) root.findViewById(R.id.frameContainer);
		mHeader=(RelativeLayout) root.findViewById(R.id.googlemapheader);


		//	initMapView(); 

		/*		 final Handler handler = new Handler();
		    handler.postDelayed(new Runnable() {
		        @Override
		        public void run() {
		              map = supportMapFragment.getMap();
		              if (map != null) {
		                initializeMap();
		              }
		        	createMapView();
		        	addMarker();
		        }
		    }, 20000);*/

		createMapView();
		addMarker();
		googleMap.setIndoorEnabled(true);
		//		googleMap.setMyLocationEnabled(true);

		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// Enable / Disable Rotate gesture
		googleMap.getUiSettings().setRotateGesturesEnabled(true);

		// Enable / Disable zooming functionality
		googleMap.getUiSettings().setZoomGesturesEnabled(true);

		googleMap.getUiSettings().setAllGesturesEnabled(true);

		/*		mSlidHolder.setOnSlideListener(new OnSlideListener() {

			@Override
			public void onSlideCompleted(boolean opened) {
				// TODO Auto-generated method stub

			}
		});*/

		gmenu=(ImageButton) root.findViewById(R.id.gmenu);
		gmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
		});

		mHeader.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mSlidHolder.setEnabled(true);

				return false;
			}
		});

		mSlidHolder.setOnSlideListener(new OnSlideListener() {

			@Override
			public void onSlideCompleted(boolean opened) {
				// TODO Auto-generated method stub

			}
		});

		//setMapTransparent(root);

		return root;
	}



	/**
	 * function to load map If map is not created it will create it for you
	 * */
	/*	private void initilizeMap() {
		if (googleMap != null) {
			googleMap.clear();
			googleMap=null;
			//			getActivity().getSupportFragmentManager().beginTransaction().remove(googleMap);
		}
		if (googleMap == null) {
			mSlidHolder.setEnabled(false);
			Log.i(TAG, "Calling initilizeMap...");
			// Try to obtain the map from the SupportMapFragment.

			//	googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();

			googleMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng arg0) {
					// TODO Auto-generated method stub
					mSlidHolder.setEnabled(false);
				}	
			});

		}	
	}
	 */

	public void addMarkersToMap() {

		Log.i(TAG, "Calling addMarkersToMap()..."+eventItemList.size());
		googleMap.clear();
		Double[] latitude = new Double[eventItemList.size()];
		Double[] longitude = new Double[eventItemList.size()];

		if (eventItemList != null && eventItemList.size() > 0) {
			for ( i = 0; i < eventItemList.size(); i++) {

				latitude[i] =  Double.parseDouble(eventItemList.get(i).getVcLatitude());
				longitude[i] = Double.parseDouble(eventItemList.get(i).getVcLongitude());
				System.out.println("latitude = " + latitude[i] + " longitude = " + longitude[i]);
				googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(latitude[i], longitude[i]))
				.title(eventItemList.get(i).getVcEventName())
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
				//.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						);

				googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

					@Override
					public void onInfoWindowClick(Marker arg0) {
						// TODO Auto-generated method stub

					}
				});
				googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {

					@Override
					public View getInfoWindow(Marker arg0) {
						// TODO Auto-generated method stub
						View view =LayoutInflater.from(mContext).inflate(R.layout.custom_info_window_content, null);

						String markerId=arg0.getId();



						int id=	Integer.parseInt(markerId.substring(1));
						Log.i(TAG," makerId..... :"+id);
						EventItem eventItem=eventItemList.get(id);

						TextView eventName=(TextView) view.findViewById(R.id.eventNameMap);
						TextView clubName=(TextView) view.findViewById(R.id.clubNameMap);
						TextView eventDate=(TextView) view.findViewById(R.id.eventdate_map);

						Log.i(TAG, "Event name: "+eventItem.getVcEventName());
						eventName.setText(eventItem.getVcEventName());
						Log.i(TAG, "Club name: "+eventItem.getVcCompanyName());
						clubName.setText(eventItem.getVcCompanyName());
						Log.i(TAG, "Event name: "+eventItem.getDtEventDate());
						String arr[]=AppUtils.changeDateFormatArray(eventItem.getDtEventDate());
						eventDate.setText(modifyDate(arr[1])+"\n"+arr[0]);

						googleMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {

							@Override
							public void onMapLoaded() {
								// TODO Auto-generated method stub
								Log.i(TAG,"Calling onMapLoaded()..... :");
								mSlidHolder.setEnabled(false);
							}
						});
						googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

							@Override
							public void onInfoWindowClick(Marker arg0) {
								// TODO Auto-generated method stub
								String markerId=arg0.getId();
								int id=	Integer.parseInt(markerId.substring(1));
								Log.i(TAG, "MarkerId: "+markerId.substring(1));
								Intent theIntent=new Intent(mContext,EventDetailsActivity.class);
								theIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								theIntent.putExtra(PartyFinderConstants.EXTRA_EVENTITEM,eventItemList.get(id));
								startActivity(theIntent);
							}
						});


						return view;
					}

					@Override
					public View getInfoContents(final Marker arg0) {
						// TODO Auto-generated method stub


						return null;
					}

				});


			}
		}
	}

	/*
	private void initMapView() {

		MapsInitializer.initialize(getActivity());

		switch (GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity())) {
				case ConnectionResult.SUCCESS:

					// Gets to GoogleMap from the MapView and does initialization
					// stuff
					if (mapView != null) {

						locationManager = ((LocationManager) getActivity()
								.getSystemService(Context.LOCATION_SERVICE));

							Boolean localBoolean = Boolean.valueOf(locationManager
									.isProviderEnabled("network"));


						googleMap = mapView.getMap();

						mSlidHolder.setEnabled(false);
						googleMap.setOnMapClickListener(new OnMapClickListener() {

							@Override
							public void onMapClick(LatLng arg0) {
								// TODO Auto-generated method stub
								mSlidHolder.setEnabled(false);
							}	
						});

						if (googleMap == null) {

							Log.d("", "Map Fragment Not Found or no Map in it!!");

						}


						googleMap.clear();

						//add marker to map....
						addMarkersToMap();


						googleMap.setIndoorEnabled(true);
						googleMap.setMyLocationEnabled(true);

						googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						// Enable / Disable Rotate gesture
						googleMap.getUiSettings().setRotateGesturesEnabled(true);

						// Enable / Disable zooming functionality
						googleMap.getUiSettings().setZoomGesturesEnabled(true);

						googleMap.getUiSettings().setAllGesturesEnabled(true);

					}

					break;
				case ConnectionResult.SERVICE_MISSING:

					break;
				case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:

					break;
				default:

		}
	} */


	private void createMapView(){
		/** 
		 * Catch the null pointer exception that
		 * may be thrown when initialising the map
		 */
		switch (GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity())) {
				case ConnectionResult.SUCCESS:

					//	try {
					if(null == googleMap){
						//	googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(
						//		R.id.mapView)).getMap();
						googleMap=mapView.getMap();
						mSlidHolder.setEnabled(false);
						Log.i(TAG, "Google map:   "+googleMap);

						/**
						 * If the map is still null after attempted initialisation,
						 * show an error to the user
						 */
						if(null == googleMap) {
							/*Toast.makeText(mContext,
							"Error creating map",Toast.LENGTH_SHORT).show();*/
						}
					}
					/*	} catch (NullPointerException exception){
			Log.e("mapApp", exception.toString());
		}*/

					break;
				case ConnectionResult.SERVICE_MISSING:

					break;
				case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:

					break;
				default:

		}
	}


	private void addMarker(){

		/** Make sure that the map has been initialised **/
		MapsInitializer.initialize(getActivity());
		if(null != googleMap){

			addMarkersToMap();




		}
	}



	public static String modifyDate(String date){
		String tempString;
		if(date.length()==1){
			tempString="0"+date;
		}else{
			tempString=date;
		}
		return tempString;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

		if (outState != null) {
			//	  setUserVisibleHint(false);
			super.onSaveInstanceState(outState);
			//setTargetFragment(null, -1);
			Log.i(TAG, "Calling onSaveInstanceState()");

		}
	}


	@Override
	public void onResume() {
		super.onResume();
		if(mapView!=null){
			Log.i(TAG, "Calling onResume()");
			mapView.onResume();
			//	mapView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "Calling onPause()");
		mapView.onPause();

	}



	@Override
	public void onDestroy() {
		Log.i(TAG, "Calling onDestroy()");
		mapView.onDestroy();

		super.onDestroy();
	}

	/*@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub

		super.onLowMemory();
		mapView.onLowMemory();
	}*/



}
