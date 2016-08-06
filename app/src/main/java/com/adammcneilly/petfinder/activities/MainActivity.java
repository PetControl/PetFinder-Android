package com.adammcneilly.petfinder.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.fragments.ScanFragment;
import com.adammcneilly.petfinder.core.CoreActivity;

public class MainActivity extends CoreActivity {
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar(getString(R.string.app_name), false);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.hide();
                showFragment(ScanFragment.FRAGMENT_NAME);
            }
        });
    }

    @Override
    public void onFragmentDismissed() {
        setupToolbar(getString(R.string.app_name), false);
        fab.show();
    }
}
