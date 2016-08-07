package com.adammcneilly.petfinder.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
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
import com.adammcneilly.petfinder.core.CoreFragment;
import com.adammcneilly.petfinder.service.PetService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Fragment used for registering.
 *
 * Created by adam.mcneilly on 8/7/16.
 */
public class RegistrationFragment extends CoreFragment {
    public static final String FRAGMENT_NAME = "Registration";

    private EditText firstName;
    private EditText lastName;
    private EditText address;
    private EditText phone;


    public static RegistrationFragment newInstance() {
        Bundle args = new Bundle();

        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (CoordinatorLayout) inflater.inflate(R.layout.fragment_registration, container, false);

        setupToolbar(getString(R.string.registration), false);

        firstName = (EditText) root.findViewById(R.id.first_name);
        lastName = (EditText) root.findViewById(R.id.last_name);
        address = (EditText) root.findViewById(R.id.address);
        phone = (EditText) root.findViewById(R.id.phone);

        Button submit = (Button) root.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isInputValid()) {
                    return;
                }

                PetService service = PetService.retrofit.create(PetService.class);
                service.addOwner(
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        address.getText().toString(),
                        phone.getText().toString()
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(root, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(String id) {
                        Log.v("ADAM_MCNEILLY", id);
                        SharedPreferences sp = getActivity().getSharedPreferences("prefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("owner_id", Integer.parseInt(id));
                        editor.apply();
                        showFragment(HomeFragment.FRAGMENT_NAME);
                    }
                });
            }
        });

        return root;
    }

    private boolean isInputValid() {
        return !(firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || address.getText().toString().isEmpty() || phone.getText().toString().isEmpty());
    }
}
