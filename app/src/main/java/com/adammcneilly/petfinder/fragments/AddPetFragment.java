package com.adammcneilly.petfinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.core.CoreActivity;
import com.adammcneilly.petfinder.core.CoreFragment;
import com.adammcneilly.petfinder.service.PetService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Fragment for adding a pet.
 *
 * Created by adam.mcneilly on 8/7/16.
 */
public class AddPetFragment extends CoreFragment{
    public static final String FRAGMENT_NAME = "AddPet";

    private EditText petName;
    private EditText petBreed;
    private EditText petNotes;

    public static AddPetFragment newInstance() {
        Bundle args = new Bundle();

        AddPetFragment fragment = new AddPetFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (CoordinatorLayout) inflater.inflate(R.layout.fragment_add_pet, container, false);

        setupToolbar(getString(R.string.add_pet), true);

        petName = (EditText) root.findViewById(R.id.pet_name);
        petBreed = (EditText) root.findViewById(R.id.pet_breed);
        petNotes = (EditText) root.findViewById(R.id.pet_notes);

        Button submitButton = (Button) root.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidInput()) {
                    return;
                }

                int ownerId = ((CoreActivity)getActivity()).getOwnerId();
                if(ownerId == -1) {
                    return;
                }

                PetService service = PetService.retrofit.create(PetService.class);
                service.addPet(
                        petName.getText().toString(),
                        petBreed.getText().toString(),
                        petNotes.getText().toString(),
                        String.valueOf(ownerId)
                ).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Snackbar.make(root, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(String s) {
                                Log.v("ADAM_MCNEILLY", s);
                                showFragment(HomeFragment.FRAGMENT_NAME);
                            }
                        });
            }
        });

        return root;
    }

    private boolean isValidInput() {
        return !(petName.getText().toString().isEmpty() || petBreed.getText().toString().isEmpty());
    }
}
