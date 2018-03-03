package com.rexdarel.redline.provider.fragments;

/**
 * Created by Admin on 2/28/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rexdarel.redline.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Profile");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
