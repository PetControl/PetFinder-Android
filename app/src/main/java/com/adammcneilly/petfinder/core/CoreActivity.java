package com.adammcneilly.petfinder.core;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.fragments.AddPetFragment;
import com.adammcneilly.petfinder.fragments.HomeFragment;
import com.adammcneilly.petfinder.fragments.PetInfoFragment;
import com.adammcneilly.petfinder.fragments.RegistrationFragment;
import com.adammcneilly.petfinder.fragments.ScanFragment;

/**
 * Core activity used throughout the app.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public class CoreActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected String currentFragment;

    protected void setupToolbar(String title, boolean displayHome) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
        }
    }

    protected void showFragment(String tag, Object... args) {
        CoreFragment fragment;

        switch(tag) {
            case AddPetFragment.FRAGMENT_NAME:
                fragment = AddPetFragment.newInstance();
                break;
            case RegistrationFragment.FRAGMENT_NAME:
                fragment = RegistrationFragment.newInstance();
                break;
            case PetInfoFragment.FRAGMENT_NAME:
                if(args.length != 1 && !(args[0] instanceof String)) {
                    throw new IllegalArgumentException("Fragment requires pet identifier.");
                }
                fragment = PetInfoFragment.newInstance(args[0].toString());
                break;
            case ScanFragment.FRAGMENT_NAME:
                fragment = ScanFragment.newInstance();
                break;
            case HomeFragment.FRAGMENT_NAME:
            default:
                fragment = HomeFragment.newInstance();
                break;
        }

        currentFragment = tag;

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                        R.anim.fade_in, R.anim.fade_out)
                .addToBackStack(tag)
                .replace(R.id.fragment, fragment, tag)
                .commit();
    }

    public int getOwnerId() {
        SharedPreferences sp = getSharedPreferences("prefs", Activity.MODE_PRIVATE);
        return sp.getInt("owner_id", -1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                // Never pop home
                if(!currentFragment.equals(HomeFragment.FRAGMENT_NAME)) {
                    getSupportFragmentManager().popBackStackImmediate();
                }

                // If we only have one fragment, hide back button.
                if(getSupportFragmentManager().getBackStackEntryCount() == 1 && getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
