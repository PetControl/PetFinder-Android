package com.adammcneilly.petfinder.core;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

/**
 * Core class for all fragments in this app.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public class CoreFragment extends Fragment {
    protected CoordinatorLayout root;
    protected Snackbar snackbar;

    protected void setupToolbar(String title, boolean displayHome) {
        ((CoreActivity)getActivity()).setupToolbar(title, displayHome);
    }

    protected void showFragment(String tag, Object... args) {
        ((CoreActivity)getActivity()).showFragment(tag, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(snackbar != null) {
            snackbar.dismiss();
        }
    }
}
