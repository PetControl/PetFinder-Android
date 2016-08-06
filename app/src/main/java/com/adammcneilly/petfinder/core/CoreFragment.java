package com.adammcneilly.petfinder.core;

import android.support.v4.app.Fragment;

/**
 * Core class for all fragments in this app.
 *
 * Created by adam.mcneilly on 8/6/16.
 */
public class CoreFragment extends Fragment {
    protected void setupToolbar(String title, boolean displayHome) {
        ((CoreActivity)getActivity()).setupToolbar(title, displayHome);
    }
}
