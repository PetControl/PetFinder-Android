package com.adammcneilly.petfinder.activities;

import android.os.Bundle;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.core.CoreActivity;
import com.adammcneilly.petfinder.fragments.HomeFragment;

public class MainActivity extends CoreActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar(getString(R.string.app_name), false);

        showFragment(HomeFragment.FRAGMENT_NAME);
    }
}
