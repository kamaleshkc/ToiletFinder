package com.example.toiletfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.toiletfinder.Api.Api;
import com.example.toiletfinder.Api.RetrofitClient;
import com.example.toiletfinder.R;
import com.example.toiletfinder.models.Toilets;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Details_toilet extends AppCompatActivity {

    EditText lat,lng,description,photo,price;
    Switch simpleSwitch1;
    RadioGroup place_type;
    Button add,cancel;
    RadioButton radioButton;
    private  final String BASE_URL= "http://192.168.100.8:8000/";
    Retrofit retrofit=null;



    String longi,lati;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_toilet);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



         longi=getIntent().getExtras().getString("key1");
         lati=getIntent().getExtras().getString("key2");

        Log.d("hai","latitude longitude check " + longi+lati);



        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        }





        lat=findViewById(R.id.txt_4);
        lng=findViewById(R.id.txt5);

        lat.setText(lati);
        lng.setText(longi);


        description=findViewById(R.id.description);

        price=findViewById(R.id.price_txt);

        place_type=findViewById(R.id.placeGroup);

        simpleSwitch1=findViewById(R.id.switch_disable);

        Toast.makeText(Details_toilet.this, "this is it", Toast.LENGTH_LONG);

        cancel=findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Details_toilet.this,MapProfile.class);
                startActivity(intent);

            }
        });


        add=findViewById(R.id.buttonAdd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data();

            }
        });
    }


    public void data(){
        String txtLat= lati;
        String txtLng= longi;
        Log.d("hai","Checking value.");
        String txtDescription=description.getText().toString();
        String txtPrice= price.getText().toString();

        if(txtDescription.isEmpty()){
            description.setError("Enter a valid location description.");
            description.requestFocus();
            return;
        }

        if(txtPrice.isEmpty()){
            price.setError("Please enter price or type free if it's free.");
            price.requestFocus();
            return;
        }




        int selectedId=place_type.getCheckedRadioButtonId();

        radioButton= findViewById(selectedId);
        String txtPlace_type= radioButton.getText().toString();


        String status;

        if (simpleSwitch1.isChecked())
            status = simpleSwitch1.getTextOn().toString();
        else
            status = simpleSwitch1.getTextOff().toString();

        String disable_access= status;


        Api api= retrofit.create(Api.class);


        Call < ResponseBody > call = api.createToilets(
            txtLat,txtLng,txtDescription,txtPrice,txtPlace_type,disable_access);
        call.enqueue(new Callback < ResponseBody >() {
            @Override
            public void onResponse(Call < ResponseBody > call, Response < ResponseBody > response) {
                if(response.isSuccessful()) {

                    Intent intent= new Intent(Details_toilet.this,MapProfile.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call < ResponseBody > call, Throwable t) {
                Log.d("hai","This is a faliure. Try again.");
            }
        });


    }




}
