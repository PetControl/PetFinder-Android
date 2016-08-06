package com.adammcneilly.petfinder.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Service to access the Twilio API.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public interface TwilioService {

    @POST("/")
    void alertOwner(@Query("id") String id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("apiurl")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
