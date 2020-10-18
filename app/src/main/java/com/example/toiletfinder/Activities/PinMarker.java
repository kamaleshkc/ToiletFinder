package com.example.toiletfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.toiletfinder.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PinMarker extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap zMap;
    private MapProfile mapProfile= new MapProfile();
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    public static final String TAG=" MainActivity";
    FloatingActionButton floatingActionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_marker);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar1);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);


        initMap();
        getDeviceLocation();
        floatingActionBtn = findViewById(R.id.floating_action);

        floatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PinMarker.this, Details_toilet.class);
                intent.putExtra("key1",String.valueOf(longitude) );
                intent.putExtra("key2", String.valueOf(latitude));
                Log.d("tag"," recieving  longitude latitude."+longitude+" / "+latitude);
                startActivity(intent);


            }
        });



    }
    double latitude;
    double longitude;


    @Override
    public void onMapReady(GoogleMap googleMap) {
        zMap = googleMap;
        zMap.setMyLocationEnabled(true);

        zMap.getUiSettings().setMyLocationButtonEnabled(false);
        zMap.getUiSettings().setCompassEnabled(false);
        zMap.getUiSettings().setMapToolbarEnabled(false);



        zMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            int height = 100;
            int width = 70;


            @Override
            public void onMapLongClick(LatLng latLng) {
                //creating marker
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.marker);
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);


                MarkerOptions mp = new MarkerOptions()
                    .icon(smallMarkerIcon)
                    .anchor(0.5f, 1);
                //marker position
                mp.position(latLng);
                //setting latitude longitude on marker.
                mp.title(latLng.latitude + " : " + latLng.longitude);
                //clear the previous click of marker



                zMap.clear();
                //zoom the marker
                zMap.animateCamera((CameraUpdateFactory.newLatLngZoom(latLng, 20)));
                //add marker on map
                zMap.addMarker(mp);

                latitude=latLng.latitude;
                longitude=latLng.longitude;


                                }



        });




    }


    public void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public  void getDeviceLocation()      {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(this, new OnSuccessListener < Location >() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 20);
                        zMap.animateCamera(cameraUpdate);


                    }
                }
            });

    }
    private void moveCamera(LatLng latLng, float zoom){

        zMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

}
