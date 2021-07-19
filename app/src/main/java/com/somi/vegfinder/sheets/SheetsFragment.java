package com.somi.vegfinder.sheets;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somi.vegfinder.R;
import com.somi.vegfinder.account.accountManager.AccountManager;
import com.somi.vegfinder.main.MainActivity;

import java.util.ArrayList;


public class SheetsFragment extends Fragment{


    private MainActivity activity;

    private RecyclerView rv_sheets;
    private TextView tv_notLoggedLabel, tv_notLoggedDescription;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_sheets, container, false);

        rv_sheets = rootView.findViewById(R.id.rv_sheets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rv_sheets.setLayoutManager(layoutManager);

        tv_notLoggedLabel = rootView.findViewById(R.id.tv_sheets_notlogged_label);
        tv_notLoggedDescription = rootView.findViewById(R.id.tv_sheets_notlogged_label_description);

        return rootView;

    }//onCreateView


}//SheetsFragment
