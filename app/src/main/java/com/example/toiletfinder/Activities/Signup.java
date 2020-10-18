package com.example.toiletfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.toiletfinder.Api.mySinlge;
import com.example.toiletfinder.R;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private EditText first_name, last_name, password, confirmpassowrd, email;
    private Button conti,btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });




        first_name = findViewById(R.id.firstname1);
        last_name = findViewById(R.id.lastname1);
        password = findViewById(R.id.typepass);
        confirmpassowrd = findViewById(R.id.confirm_pass);
        email = findViewById(R.id.user_email);

        conti = findViewById(R.id.btn_conti);


        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtFirstName = first_name.getText().toString();
                String txtLastName = last_name.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                String txtConfirmPassword = confirmpassowrd.getText().toString();
                if (TextUtils.isEmpty(txtFirstName) || TextUtils.isEmpty(txtLastName) || TextUtils.isEmpty(txtEmail) ||
                    TextUtils.isEmpty(txtPassword)) {
                    Toast.makeText(Signup.this, "this is it", Toast.LENGTH_LONG);

                } else {
                    registerNewAccounts(txtFirstName, txtLastName, txtEmail, txtPassword);
                }


            }
        });
        }

        public void registerNewAccounts ( final String first_name, final String last_name,
        final String email, final String password){


            String uRl = "http://192.168.100.8/Login/register.php";
            StringRequest request = new StringRequest(Request.Method.POST, uRl,
                new Response.Listener < String >() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Successfully signed up.")) {
                        startActivity(new Intent(Signup.this, MainActivity.class));
                    }

                }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                }
            })
            {
                @Override
                protected Map < String, String > getParams() throws AuthFailureError {
                    HashMap < String, String > param = new HashMap <>();
                    param.put("first_name", first_name);
                    param.put("last_name", last_name);
                    param.put("email", email);
                    param.put("password ", password);
                    return param;

                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mySinlge.getInstance(Signup.this).addToRequestQueue(request);
        }


    }

