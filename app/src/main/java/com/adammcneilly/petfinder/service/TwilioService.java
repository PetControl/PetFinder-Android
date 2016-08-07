package com.adammcneilly.petfinder.service;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Service to access the Twilio API.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public interface TwilioService {

    @POST("/{id}")
    Call<Void> alertOwner(@Path("id") String id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://4b05d87c.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
