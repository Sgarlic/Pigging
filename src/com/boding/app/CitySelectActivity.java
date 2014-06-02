/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.boding.app;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.model.City;
import com.boding.util.Util;
import com.boding.view.CitySelectLetterListView;
import com.boding.view.WarningDialog;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI
 * that switches between tabs and also allows the user to perform horizontal
 * flicks to move between the tabs.
 */
public class CitySelectActivity extends FragmentActivity {
	private boolean isFlyToCitySelection = false;
	private static final String NATIONAL_CITY = "国内城市";
	private static final String INTERNATIONAL_CITY = "国际城市";
    TabHost mTabHost;
    ViewPager  mViewPager;
    TabsAdapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle arguments = getIntent().getExtras();
        if(arguments != null)
        	isFlyToCitySelection = arguments.getBoolean(Constants.IS_FLY_TO_CITY_SELECTION);
        
        setContentView(R.layout.activity_city_select);
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager)findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
        
        View nationalCityView = (View) LayoutInflater.from(this).inflate(R.layout.tab_layout_city_select_tab, null);  
        TextView nationalCityTextView = (TextView) nationalCityView.findViewById(R.id.tab_label);  
        nationalCityTextView.setText(NATIONAL_CITY);
        
        View internationalCityView = (View) LayoutInflater.from(this).inflate(R.layout.tab_layout_city_select_tab, null);  
        TextView internationalCityTextView = (TextView) internationalCityView.findViewById(R.id.tab_label);  
        internationalCityTextView.setText(INTERNATIONAL_CITY);

        Bundle nationalCityBundle = new Bundle();
        nationalCityBundle.putBoolean(Constants.IS_FLY_TO_CITY_SELECTION, isFlyToCitySelection);
        nationalCityBundle.putBoolean(Constants.IS_INTERNATIONAL_CITY, false);
        
        mTabsAdapter.addTab(mTabHost.newTabSpec(NATIONAL_CITY).setIndicator(nationalCityView),
                CitySelectFragment.class, nationalCityBundle);
        
        Bundle internationalCityBundle = new Bundle();
        internationalCityBundle.putBoolean(Constants.IS_FLY_TO_CITY_SELECTION, isFlyToCitySelection);
        internationalCityBundle.putBoolean(Constants.IS_INTERNATIONAL_CITY, true);
        mTabsAdapter.addTab(mTabHost.newTabSpec(INTERNATIONAL_CITY).setIndicator(internationalCityView),
                CitySelectFragment.class, internationalCityBundle);
       
        initView();
    }

    private void initView(){
    	setTitle();
        
        LinearLayout returnLogoLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
        returnLogoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(CitySelectActivity.this, IntentRequestCode.START_CITY_SELECTION);
			}
        });
    }
    
    private void setTitle(){
    	TextView citySelectTitleTextView = (TextView)findViewById(R.id.city_select_title_textView);
    	if(isFlyToCitySelection)
    		citySelectTitleTextView.setText("到达城市");
    	else
    		citySelectTitleTextView.setText("出发城市");
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

    /**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentPagerAdapter
            implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            
            View previousTabView = mTabHost.getCurrentTabView();
            TextView previousTabTextView = (TextView) previousTabView.findViewById(R.id.tab_label);
            previousTabTextView.setTextColor(R.color.black);
            
            mTabHost.setCurrentTab(position);
            
            View currentTabView = mTabHost.getCurrentTabView();
            TextView currentTabTextView = (TextView) currentTabView.findViewById(R.id.tab_label);
            currentTabTextView.setTextColor(R.color.orange);
            
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
