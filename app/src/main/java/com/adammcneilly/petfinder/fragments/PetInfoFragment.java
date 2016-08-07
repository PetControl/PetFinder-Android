package com.adammcneilly.petfinder.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.core.CoreFragment;
import com.adammcneilly.petfinder.model.Pet;
import com.adammcneilly.petfinder.service.PetService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Fragment that displays pet info.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public class PetInfoFragment extends CoreFragment {
    private String petId;
    private Pet pet;
    private static final String PET_ID = "petId";
    public static final String FRAGMENT_NAME = "PetInfo";

    private Button callOwnerButton;
    private Button textOwnerButton;

    public static PetInfoFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(PET_ID, id);

        PetInfoFragment fragment = new PetInfoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            this.petId = getArguments().getString(PET_ID, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (CoordinatorLayout) inflater.inflate(R.layout.fragment_pet_info, container, false);

        setupToolbar(getString(R.string.pet_info), true);

        callOwnerButton = (Button) root.findViewById(R.id.call_owner);
        textOwnerButton = (Button) root.findViewById(R.id.text_owner);

        callOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callOwner();
            }
        });
        textOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textOwner();
            }
        });

        fetchPet();

        return root;
    }

    private void fetchPet() {
        PetService service = PetService.retrofit.create(PetService.class);
        service.getPetForId(petId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pet>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Pet pet) {
                        displayPet(pet);
                    }
                });
    }

    private void displayPet(Pet pet) {
        this.pet = pet;
        ((TextView)root.findViewById(R.id.pet_name)).setText(pet.petName);
        ((TextView)root.findViewById(R.id.pet_owner)).setText(pet.ownerName);
        ((TextView)root.findViewById(R.id.owner_address)).setText(pet.ownerAddress);
        callOwnerButton.setEnabled(true);
        textOwnerButton.setEnabled(true);
    }

    private void callOwner() {
        if(pet != null && pet.ownerPhone.length() > 0) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + pet.ownerPhone));
            startActivity(intent);
        }
    }

    private void textOwner() {
        if(pet != null && pet.ownerPhone.length() > 0) {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:" + pet.ownerPhone));
            sendIntent.putExtra("sms_body", "Hey, your pet is lost! I have " + pet.petName + ".");
            startActivity(sendIntent);
        }
    }
}
