package com.somi.vegfinder.main;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


public class PagerAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> fragments;


    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {

        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        this.fragments = fragments;

    }//constructor


    public Fragment getItem(int position) {

        return this.fragments.get(position);

    }//getItem


    public int getCount() {

        return this.fragments.size();

    }//getCount


}//PagerAdapter
