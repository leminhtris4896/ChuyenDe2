package com.example.trile.foodlocation;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trile.foodlocation.Adapter.PlaceAutocompleteAdapter;
import com.example.trile.foodlocation.Models.mdBusiness;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AreaFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {

    private ImageView imgSetGPSLocation;
    // EDT search map
    private AutoCompleteTextView edtSearchMap;
    // MAP : Add Manifest
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COUASE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    // Camera zoom 15f
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    // API : user compile "com.google.android.gms:play-services-location:12.0.1"
    private FusedLocationProviderClient mFusedLocationProviderClient;
    // ADAPTER autocomplete search location
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    //

    // DISPLAY LOCATION
    private ChildEventListener mChildEventListener;
    private DatabaseReference mLocations;
    Marker marker;
    List<mdBusiness> venueList;
    ArrayList<mdBusiness> arrBusiness ;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    private DatabaseReference mData;
    //
    public AreaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //getActivity().getActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        mData = FirebaseDatabase.getInstance().getReference();
        edtSearchMap = (AutoCompleteTextView) view.findViewById(R.id.edt_search_map);
        imgSetGPSLocation = (ImageView) view.findViewById(R.id.img_set_gps);
        arrBusiness = new ArrayList<>();
        edtSearchMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtSearchMap.getRight() - edtSearchMap.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        edtSearchMap.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        getLocationPermisssion();
        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(),0,this)
                .build();
        // Adapter autocomplete search location
        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(getContext(),mGoogleApiClient,LAT_LNG_BOUNDS,null);
        initMap();
        edtSearchMap.setAdapter(placeAutocompleteAdapter);
        mLocations = FirebaseDatabase.getInstance().getReference();
        mLocations.push().setValue(marker);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Get location device , Show where we are
    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        try {
            if (mLocationPermissionGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "onComplete : found location !");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "Điện thoại trí ở đây nhé");
                        } else {
                            Log.e(TAG, "onComplete : no found location !");
                            Toast.makeText(getContext(), "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getLocation Device : SecurityException" + e.getMessage());
        }
    }

    // Display marker , camera , zom , title location
    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        // Other location
        if (!title.equals("Điện thoại trí ở đây nhé")) {
            // Display marker in map
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(markerOptions);
        }
        // HIDE KEY
        hideKeySoftWindow();

    }

    private void init() {

        // Edit search map , return location
        edtSearchMap.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == keyEvent.ACTION_DOWN
                        || keyEvent.getAction() == keyEvent.KEYCODE_ENTER) {
                    geoLocation();
                }
                return false;
            }
        });

        // Set location with image gps
        imgSetGPSLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });
        // HIDE KEY
        hideKeySoftWindow();
    }



    // Search location
    private void geoLocation() {
        // Get location
        String searchString = edtSearchMap.getText().toString();
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (Exception e) {
            Log.e(TAG, "Not found location" + e.getMessage());
        }
        // List >0 character
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.e(TAG, "Found a location : " + address.toString());
            // After search succesful , return location
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }

    // Get map , Map successfull , Map Ready display
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(getContext(), "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            // Function edtSearch return location search
            init();
            // LOAD multi marker from Firebase
            googleMap.setOnMarkerClickListener(this);

            mLocations.child("Business").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot s : dataSnapshot.getChildren()){
                        final mdBusiness venue = s.getValue(mdBusiness.class);
                        venueList  = new ArrayList<mdBusiness>();
                        venueList.add(venue);

                        for (int i = 0; i < venueList.size(); i++)
                        {
                            mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                            LatLng latLng = new LatLng(venue.getDbLatitude(),venue.getDbLongitude());
                            if (mMap != null) {
                                if (venue.getStrBusinessType().equalsIgnoreCase("Quán ăn")) {
                                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(venue.getStrName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                }else if (venue.getStrBusinessType().equalsIgnoreCase("Quán nước")){
                                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(venue.getStrName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_drink)));
                                }else {
                                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(venue.getStrName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)));
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            arrBusiness = new ArrayList<mdBusiness>();
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(final Marker marker) {
                    mData.child("Business").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            mdBusiness mdBusiness = dataSnapshot.getValue(com.example.trile.foodlocation.Models.mdBusiness.class);
                            Intent myIntent = new Intent(getContext(), BusinessDetailActivity.class);
                            if (marker.getTitle().equalsIgnoreCase(mdBusiness.getStrName())) {
                                Bundle bundle = new Bundle();
                                bundle.putString("detailBusiness", mdBusiness.getStrID());
                                myIntent.putExtra("bundle", bundle);
                                getContext().startActivity(myIntent);
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

        }
    }

    private void getLocationPermisssion() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getContext(), COUASE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), permission, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                            mLocationPermissionGranted = false;
                        return;
                    }
                    mLocationPermissionGranted = true;
                    //Initalize our map
                    initMap();
                }
            }
        }
    }

    private void hideKeySoftWindow() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
        return false;
    }
}
