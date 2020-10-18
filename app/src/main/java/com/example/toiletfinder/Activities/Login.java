package com.example.toiletfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toiletfinder.Api.RetrofitClient;
import com.example.toiletfinder.R;
import com.example.toiletfinder.models.LoginResponse;
import com.example.toiletfinder.storage.SharedPref;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        email= findViewById(R.id.login_email);
        password=findViewById(R.id.Login_password);

        login= findViewById(R.id.login_btn);







        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    userlogin();
                    Log.d("tag","yo aaucha hola ne aba ssssssssssssssssssssssssssssssssssssssssssssssssm");
                    password.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPref.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(Login.this, MapProfile.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Log.d("tag","Ram Ram Ram Ram Ram Ram Ram Ram Ram Ram Ram Ram Ram Ram");
        }
    }

    public void userlogin(){
        String txtEmail= email.getText().toString();
        String txtPassword= password.getText().toString();


        if(txtEmail.isEmpty()){
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
            email.setError("Enter valid email");
            email.requestFocus();
            return;
        }


        if(txtPassword.isEmpty()){
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if(txtPassword.length()< 8){
            password.setError("Password short");
            password.requestFocus();
            return;
        }

        Call < LoginResponse > call = RetrofitClient
            .getInstance().getApi().userLogin(txtEmail,txtPassword);
        call.enqueue(new Callback < LoginResponse >() {
            @Override
            public void onResponse(Call < LoginResponse > call, Response < LoginResponse > response) {

                if(response.isSuccessful()) {
                    LoginResponse loginResponse=response.body();

                    if (!loginResponse.isError()) {
                        Log.d("tag","yess. ");
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();

                        SharedPref.getInstance(Login.this)
                            .saveUser(loginResponse.getUser());



                        Intent intent=new Intent(Login.this, MapProfile.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);


                    } else {
                        Log.d("tag","response faliure check");
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }else {
                    ResponseBody errorBody = response.errorBody();
                    Log.d("tag", "error check.");
                   }

            }

            @Override
            public void onFailure(Call < LoginResponse > call, Throwable t) {

            }
        });

    }


}
