package com.adammcneilly.petfinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adammcneilly.CoreDividerItemDecoration;
import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.adapters.PetAdapter;
import com.adammcneilly.petfinder.core.CoreActivity;
import com.adammcneilly.petfinder.core.CoreFragment;
import com.adammcneilly.petfinder.model.Pet;
import com.adammcneilly.petfinder.model.PetList;
import com.adammcneilly.petfinder.service.PetService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Main fragment displayed to the user.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public class HomeFragment extends CoreFragment {
    public static final String FRAGMENT_NAME = "HomeFragment";

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (CoordinatorLayout) inflater.inflate(R.layout.fragment_home, container, false);

        Button scanTagButton = (Button) root.findViewById(R.id.scan_button);
        scanTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(ScanFragment.FRAGMENT_NAME);
            }
        });

        final EditText searchByIdEditText = (EditText) root.findViewById(R.id.search_by_id_edit_text);
        searchByIdEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                showFragment(PetInfoFragment.FRAGMENT_NAME, searchByIdEditText.getText().toString());
                return true;
            }
        });

        Button addPetButton = (Button) root.findViewById(R.id.add_pet_button);
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(AddPetFragment.FRAGMENT_NAME);
            }
        });

        setupRecyclerView();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupToolbar(getString(R.string.app_name), false);
    }

    private void setupRecyclerView() {
        final PetAdapter petAdapter = new PetAdapter(getActivity(), new ArrayList<Pet>());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.pet_recycler_view);
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CoreDividerItemDecoration(getActivity(), CoreDividerItemDecoration.VERTICAL_LIST));

        PetService petService = PetService.retrofit.create(PetService.class);
        petService.getPetsForOwner(String.valueOf(((CoreActivity)getActivity()).getOwnerId()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PetList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(root, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(PetList petList) {
                        Log.v("ADAM_MCNEILLY", "Pets found: " + petList.pets.size());
                        petAdapter.swapItems(petList.pets);
                    }
                });
    }

    private List<Pet> getPets() {
        Pet testPet = new Pet();
        testPet.name = "Daisy";
        Pet testPet1 = new Pet();
        testPet1.name = "Minnie";

        List<Pet> fakePets = new ArrayList<>();

        fakePets.add(testPet);
        fakePets.add(testPet1);

        return fakePets;
    }
}
