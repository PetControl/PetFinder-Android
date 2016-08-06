package com.adammcneilly.petfinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.core.CoreFragment;
import com.twilio.client.TwilioClientService;

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

        AppCompatButton scanTagButton = (AppCompatButton) root.findViewById(R.id.scan_button);
        scanTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(ScanFragment.FRAGMENT_NAME);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupToolbar(getString(R.string.app_name), false);
    }

    private void twilio() {
        String tokenServiceUrl = "https://4b05d87c.ngrok.io";


    }
}
