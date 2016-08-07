package com.adammcneilly.petfinder.service;

import com.adammcneilly.petfinder.model.Pet;
import com.adammcneilly.petfinder.model.PetList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Service to retrieve pet information.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public interface PetService {
    @POST("{id}")
    Observable<Pet> getPetForId(@Path("id") String id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("placeholder_url")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build();
}
