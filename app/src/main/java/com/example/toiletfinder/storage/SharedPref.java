package com.example.toiletfinder.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.toiletfinder.models.User;

public class SharedPref {
    private static final String SHARED_PREF_NAME ="my_shared_preff";
    private  static SharedPref mInstance;
    private Context mCtx;

    private  SharedPref(Context mCtx){
        this.mCtx= mCtx;
    }
    public static synchronized  SharedPref getInstance(Context mctx){
        if(mInstance==null){
            mInstance= new SharedPref(mctx);

        }return  mInstance;
    }
    public void saveUser(User user){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("id",user.getId());
        editor.putString("email",user.getEmail());
        editor.putString("first_name",user.getFirst_name());
        editor.putString("last_name",user.getLast_name());

        editor.apply();


    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        /*if(sharedPreferences.getInt("id",-1)!= -1)
            return true;
        return false;*/

        return sharedPreferences.getInt("id",-1)!= -1;
    }
    public User getUser(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        User user=new User(
            sharedPreferences.getInt("id",-1),
            sharedPreferences.getString("email",null),
            sharedPreferences.getString("first_name",null),
            sharedPreferences.getString("last_name",null)
        );
        return user;
    }
    public void clear(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
