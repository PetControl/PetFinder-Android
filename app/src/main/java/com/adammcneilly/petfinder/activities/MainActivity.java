package com.adammcneilly.petfinder.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.core.CoreActivity;
import com.adammcneilly.petfinder.fragments.HomeFragment;
import com.adammcneilly.petfinder.fragments.RegistrationFragment;

public class MainActivity extends CoreActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar(getString(R.string.app_name), false);

        // Setup NavigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().performIdentifierAction(R.id.nav_announcements, 0);

        int ownerId = getOwnerId();

        Log.v("ADAM_MCNEILLY", "Owner received: " + ownerId);
        if(ownerId == -1) {
            showFragment(RegistrationFragment.FRAGMENT_NAME);
        } else {
            showFragment(HomeFragment.FRAGMENT_NAME);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.home:
                showFragment(HomeFragment.FRAGMENT_NAME);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
