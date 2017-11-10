package com.forzametrix.forzametrix.velocity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.forzametrix.forzametrix.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VelocityFragment extends Fragment {

    public static VelocityFragment newInstance(){
        return new VelocityFragment();
    }

    public VelocityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.velocity_fragment, container, false);
    }

}
