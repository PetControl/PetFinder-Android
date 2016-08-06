package com.adammcneilly.petfinder.core;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.adammcneilly.petfinder.interfaces.OnFragmentDismissedListener;
import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.fragments.ScanFragment;

/**
 * Core activity used throughout the app.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public abstract class CoreActivity extends AppCompatActivity implements OnFragmentDismissedListener {

    protected void setupToolbar(String title, boolean displayHome) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
        }
    }

    protected void showFragment(String tag) {
        CoreFragment fragment;

        switch(tag) {
            case ScanFragment.FRAGMENT_NAME:
                fragment = ScanFragment.newInstance();
                break;
            default:
                //TODO: Use a default fragment other than ScanFragment.
                fragment = ScanFragment.newInstance();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.fragment, fragment, tag)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStackImmediate();
                onFragmentDismissed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
