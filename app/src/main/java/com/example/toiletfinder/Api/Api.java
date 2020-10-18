package com.example.toiletfinder.Api;

import com.example.toiletfinder.models.LoginResponse;
import com.example.toiletfinder.models.Toilets;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("api/add_toilet/")
    Call< ResponseBody > createToilets(
        @Field("lat") String lat,
        @Field("lng")String lng,
        @Field("description") String description,
        @Field("price") String price,
        @Field("place_type")String place_type,
        @Field("disabled_access") String disable_access


    );

    @FormUrlEncoded
    @POST("login.php")
    Call < LoginResponse > userLogin(
        @Field("email")String email,
        @Field("password") String password
    );


    @GET("url.php" )
    Call  < List <Toilets> >  gotToilet();




}




