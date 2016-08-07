package com.adammcneilly.petfinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.core.CoreFragment;

/**
 * Fragment that displays pet info.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public class PetInfoFragment extends CoreFragment {
    private String petId;
    private static final String PET_ID = "petId";
    public static final String FRAGMENT_NAME = "PetInfo";

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

        return root;
    }
}
