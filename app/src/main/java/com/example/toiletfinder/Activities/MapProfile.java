package com.example.toiletfinder.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.toiletfinder.Api.RetrofitClient;
import com.example.toiletfinder.Api.mySinlge;
import com.example.toiletfinder.R;
import com.example.toiletfinder.models.LoginResponse;
import com.example.toiletfinder.models.Toilets;
import com.example.toiletfinder.storage.SharedPref;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapProfile extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ImageView tottle;
    Toolbar toolbar;
    private GoogleMap mMap;
    private boolean mLocationPermission = false;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    public static final String TAG=" MainActivity";
    public Button button;
    Polyline currentPolyline;

    Marker mMarker;


    ArrayList markerPoints = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_profile);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        toiletData();
        getDeviceLocation();

        getLocationPermission();
        initMap();



        FloatingActionButton floatingActionButton = findViewById(R.id.fab1);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

       /* button=findViewById(R.id.button_mapu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapProfile.this, PinMarker.class);
                startActivity(intent);
            }
        });*/

        FloatingActionButton floatingActionButton1=findViewById(R.id.fab2);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapProfile.this, PinMarker.class);
                startActivity(intent);
            }
        });



        toolbar=findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);


        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,
            R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

   }



    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }




    public void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);


        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try {
                    if(mMarker.isInfoWindowShown()){
                        mMarker.isInfoWindowShown();
                    }else{
                        mMarker.showInfoWindow();
                    }


                }catch (NullPointerException e){

                }


                return false;
            }
        });


       }
    public  double latitude;
    public  double longitude;

    public  void getDeviceLocation()      {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(this, new OnSuccessListener < Location >() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
                        mMap.animateCamera(cameraUpdate);




                    }
                }
            });

    }
    private void moveCamera(LatLng latLng, float zoom){

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }



    private static final float DEFAULT_ZOOM =15f;
    private LocationManager locationManager;
    private boolean mLocationPermissionsGranted=false;
    private static final  int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOACTION = Manifest.permission.ACCESS_COARSE_LOCATION;


    public void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
            FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                COURSE_LOACTION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);

            }
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                LOCATION_PERMISSION_REQUEST_CODE);

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted=false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i=0;i<grantResults.length;i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted=false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted=true;
                    initMap();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedPref.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
        switch (Item.getItemId()){
            case R.id.nav_home:
                Intent intents = new Intent(this, MapProfile.class);
                startActivity(intents);

            case R.id.nav_lists:
                getSupportFragmentManager().beginTransaction().replace(R.id.map,
                    new MessageFragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.map ,
                    new ChatFragment()).commit();
                break;
            case R.id.nav_Logout:
                SharedPreferences preferences =getSharedPreferences("my_shared_preff",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);



        }
        return true;


    }




    public void toiletData() {
        

        Call < List<Toilets> > call = RetrofitClient
            .getInstance().getApi().gotToilet();
        call.enqueue(new Callback < List < Toilets> >() {
            @Override
            public void onResponse(Call < List < Toilets > > call, Response < List < Toilets> > response) {

                if(response.isSuccessful()){
                    List<Toilets> toilets=response.body();

                    for(Toilets t:toilets){
                        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapProfile.this));


                        Log.d("kuch","0 0 0 0 00 0 0 0 0 0 0 00 0 0 00 0 0 0 0 00 0 0 0 0 0 00 0 0 "+t.getDescription());
                        String snippet= "Price: "+t.getPrice()+" \n"+
                                "Place Type: "+t.getPlaceType()+"\n"+
                                "Disable access: "+t.getDisabledAccess();

                        String title=t.getDescription();

                          /* .snippet(" Price : "+"\n"+t.getPrice() +"\n"+" Place type: "+t.getPlaceType()+"\n"+"  Disable access   "+t.getDisabledAccess()*/


                        LatLng latLng = new LatLng(Float.valueOf(t.getLat()),Float.valueOf(t.getLng()));
                        MarkerOptions options=new MarkerOptions().position(latLng)
                            .title(title)
                            .snippet(snippet);
                        mMarker=mMap.addMarker(options);




                    }




                }else {
                    Log.d("kehi","hola ke bhanne aaaaaa 0 0 0 0 00 0 0 0 0 0 0 00 0 0 00 0 0 0 0 00 0 0 0 0 0 00 0 0  hola");
                }


            }

            @Override
            public void onFailure(Call < List < Toilets > > call, Throwable t) {
                Log.d("kehi","hola ke bhanne aaaaaa 0 0 0 0 00 0 0 0 0 0 0 00 0 0 00 0 0 0 0 00 0 0 0 0 0 00 0 0  hola aaaaa ");
            }
        });








    }







}
