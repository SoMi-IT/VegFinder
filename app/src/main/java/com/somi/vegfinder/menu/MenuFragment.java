package com.somi.vegfinder.menu;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.somi.vegfinder.R;
import com.somi.vegfinder.main.MainActivity;


public class MenuFragment extends Fragment implements ImageView.OnClickListener, com.somi.vegfinder.main.MainActivityListener {


    private MainActivity activity;

    private MenuFragmentListener listener;

    private ImageView iv_account;
    private ImageView iv_discover;
    private ImageView iv_sheets;
    private ImageView iv_map;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        activity = (MainActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        iv_account = rootView.findViewById(R.id.iv_menu_account);
        iv_account.setOnClickListener(this);

        iv_discover = rootView.findViewById(R.id.iv_menu_discover);
        iv_discover.setOnClickListener(this);

        iv_sheets = rootView.findViewById(R.id.iv_menu_sheets);
        iv_sheets.setOnClickListener(this);

        iv_map = rootView.findViewById(R.id.iv_menu_map);
        iv_map.setOnClickListener(this);

        activity.setMenuListener(this);

        iv_account.setBackgroundResource(R.drawable.round_frame_pressed);

        return rootView;

    }//onCreateView


    private void changeSelectedTab(int position){

        if(iv_account == null) return;

        iv_account.setBackgroundResource(R.drawable.round_frame_selector);
        iv_discover.setBackgroundResource(R.drawable.round_frame_selector);
        iv_sheets.setBackgroundResource(R.drawable.round_frame_selector);
        iv_map.setBackgroundResource(R.drawable.round_frame_selector);

        if(position == 0) iv_account.setBackgroundResource(R.drawable.round_frame_pressed);
        if(position == 1) iv_discover.setBackgroundResource(R.drawable.round_frame_pressed);
        if(position == 2) iv_sheets.setBackgroundResource(R.drawable.round_frame_pressed);
        if(position == 3) iv_map.setBackgroundResource(R.drawable.round_frame_pressed);

    }//changeSelectedTab


    public void onClick(View v) {

        if (v == iv_account && listener!= null) listener.OnAccountSelected();
        if (v == iv_discover && listener!= null) listener.OnDiscoverSelected();
        if (v == iv_sheets && listener!= null) listener.OnChatSelected();
        if (v == iv_map && listener!= null) listener.OnMapSelected();

    }//onClick


    public void setListener(MenuFragmentListener _listener) {

        listener = _listener;

    }//setListener


    public void OnPageChanged(int page) {

        changeSelectedTab(page);

    }//OnPageChanged

    public void OnSheetsDateSelected() { }


}//MenuFragment
