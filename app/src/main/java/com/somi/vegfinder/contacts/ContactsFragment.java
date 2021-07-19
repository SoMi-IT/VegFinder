package com.somi.vegfinder.contacts;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somi.vegfinder.R;
import com.somi.vegfinder.account.CustomAccount;
import com.somi.vegfinder.main.MainActivity;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {


    private MainActivity activity;


    private RecyclerView rv_admins, rv_users;
    private TextView tv_notLoggedLabel, tv_notLoggedDescription;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(activity);
        rv_admins = rootView.findViewById(R.id.rv_contacts_admins);
        rv_admins.setLayoutManager(layoutManager1);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(activity);
        rv_users = rootView.findViewById(R.id.rv_contacts_users);
        rv_users.setLayoutManager(layoutManager2);

        tv_notLoggedLabel = rootView.findViewById(R.id.tv_contacts_notlogged_label);
        tv_notLoggedDescription = rootView.findViewById(R.id.tv_contacts_notlogged_label_description);

        return rootView;

    }//onCreateView


}//ContactsFragment
