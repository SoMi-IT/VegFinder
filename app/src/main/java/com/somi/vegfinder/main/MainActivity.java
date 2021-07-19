package com.somi.vegfinder.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.somi.vegfinder.R;
import com.somi.vegfinder.account.AccountFragment;
import com.somi.vegfinder.contacts.ContactsFragment;
import com.somi.vegfinder.discover.DiscoverFragment;
import com.somi.vegfinder.menu.MenuFragment;
import com.somi.vegfinder.menu.MenuFragmentListener;
import com.somi.vegfinder.sheets.SheetsFragment;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements MenuFragmentListener, ViewPager.OnPageChangeListener{



    private FragmentManager mainFragmentManager;
    private MainActivityListener menuListener, stateListener, tasksListener;
    private List<Fragment> fragments = new Vector<Fragment>();
    private ViewPager vp_main;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        vp_main = findViewById(R.id.vp_main_container);

        MenuFragment menuFragment = new MenuFragment();

        initMenuFragment(menuFragment);

        fragments.add(new AccountFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new SheetsFragment());
        fragments.add(new ContactsFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        vp_main.setAdapter(pagerAdapter);
        vp_main.addOnPageChangeListener(this);

    }//onCreate


    public void setMenuListener(MainActivityListener _listener) {

        menuListener = _listener;

    }//setMenuListener


    public void initMenuFragment(com.somi.vegfinder.menu.MenuFragment _fragment) {

        mainFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mainFragmentManager.beginTransaction();


        fragmentTransaction.replace(R.id.fl_main_menu, _fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        fragmentTransaction.commit();
        _fragment.setListener(this);

    }//initMenuFragment


    public void OnAccountSelected() { vp_main.setCurrentItem(0); }//OnAccountSelected


    public void OnDiscoverSelected() { vp_main.setCurrentItem(1); }//OnDiscoverSelected


    public void OnChatSelected() { vp_main.setCurrentItem(2); }//OnChatSelected


    public void OnMapSelected() { vp_main.setCurrentItem(3); }//OnMapSelected


    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }//onPageScrolled


    public void onPageSelected(int position) {

        if (menuListener != null)menuListener.OnPageChanged(position);

    }//onPageScrolled


    public void onPageScrollStateChanged(int state) { }//onPageScrollStateChanged


    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                .getWindowToken(), 0);

    }//hideSoftKeyboard

}//MainActivity
