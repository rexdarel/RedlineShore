package com.rexdarel.redline.provider.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rexdarel.redline.R;

/**
 * Created by Admin on 3/4/2018.
 */

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Settings");
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}