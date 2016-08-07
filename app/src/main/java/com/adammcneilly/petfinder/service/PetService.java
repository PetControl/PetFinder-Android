package com.adammcneilly.petfinder.service;

import com.adammcneilly.petfinder.model.Pet;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Service to retrieve pet information.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public interface PetService {
    @GET("{id}")
    Observable<Pet> getPetForId(@Path("id") String id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/PetControl/PetFinder-Android/master/pets/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build();
}
