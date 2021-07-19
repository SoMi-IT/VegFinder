package com.somi.vegfinder.discover;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.somi.vegfinder.R;
import com.somi.vegfinder.main.MainActivity;


public class DiscoverFragment extends Fragment {


    private com.somi.vegfinder.main.MainActivity activity;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = (com.somi.vegfinder.main.MainActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        return rootView;

    }//onCreateView


}//DiscoverFragment
