package com.testtask.applicationa.ui.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.testtask.applicationa.ui.adapters.MainPagerAdapter;
import com.testtask.applicationa.R;
import com.testtask.applicationa.ui.fragments.HistoryFragment;
import com.testtask.applicationa.ui.fragments.TestFragment;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    MainPagerAdapter mainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set Toolbar as supportActionBar, because we have Theme.AppCompat.Light.NoActionBar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //init views
        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.main_tab_layout);

        //Create pagerAdapter, add Test and History tabs
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPagerAdapter.addFragment(new TestFragment(), getString(R.string.tab_name_test));
        mainPagerAdapter.addFragment(new HistoryFragment(), getString(R.string.tab_name_history));

        //Connect tabLayout with viewPager and finally setAdapter
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(mainPagerAdapter);
    }

}
