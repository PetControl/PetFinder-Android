package com.adammcneilly.petfinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adammcneilly.petfinder.R;
import com.adammcneilly.petfinder.core.CoreFragment;

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

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupToolbar(getString(R.string.app_name), false);
    }
}
