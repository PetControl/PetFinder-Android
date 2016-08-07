package com.adammcneilly.petfinder.service;

import com.adammcneilly.petfinder.model.Pet;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
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

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://petfinderdb.c1y1gazlb9dv.us-east-1.rds.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build();
}
