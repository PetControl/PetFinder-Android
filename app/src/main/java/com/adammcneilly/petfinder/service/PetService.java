package com.adammcneilly.petfinder.service;

import com.adammcneilly.petfinder.model.Pet;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Service to retrieve pet information.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public interface PetService {
    @GET("getDoggoInfo")
    Observable<Pet> getPetForId(@Query("dogID") String id);

    @FormUrlEncoded
    @POST("addOwner")
    Observable<String> addOwner(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("address") String address,
            @Field("phone") String phone
    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://dfb6c099.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build();
}
