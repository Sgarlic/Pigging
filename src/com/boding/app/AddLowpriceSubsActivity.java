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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.LowPriceSubscribe;
import com.boding.task.LowPriceSubscribeTask;
import com.boding.util.RegularExpressionsUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.NetworkUnavaiableDialog;
import com.boding.view.dialog.WarningDialog;
import com.boding.view.fragment.AddLowPriceDomesticFragment;
import com.boding.view.fragment.AddLowPriceInternationalFragment;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI
 * that switches between tabs and also allows the user to perform horizontal
 * flicks to move between the tabs.
 */
public class AddLowpriceSubsActivity extends BodingBaseActivity {
	private LinearLayout completeLinearLayout;
	
	private static final String LOWPRICE_DOMESTIC = "国内";
	private static final String lOWPRICE_INTERNATIONAL = "国际";
	FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarDialog = new ProgressBarDialog(this);
        warningDialog = new WarningDialog(this);
        networkUnavaiableDialog = new NetworkUnavaiableDialog(this);
        setContentView(R.layout.activity_add_lowpricesubscribe);
        

        if(GlobalVariables.currentSubscribe == null){
        	GlobalVariables.currentSubscribe = new LowPriceSubscribe();
        	GlobalVariables.currentSubscribe.setCardNo(GlobalVariables.bodingUser.getCardno());
        	GlobalVariables.currentSubscribe.setTicketType(1);
        	GlobalVariables.currentSubscribe.setTripType(1);
        	GlobalVariables.currentSubscribe.setNoticeWay(2);
        	GlobalVariables.currentSubscribe.setBeforeAfterDay(0);
            
        	GlobalVariables.currentSubscribe.setLeaveCode(GlobalVariables.Fly_From_City.getCityCode());
        	GlobalVariables.currentSubscribe.setLeaveName(GlobalVariables.Fly_From_City.getCityName());
        	GlobalVariables.currentSubscribe.setArriveCode(GlobalVariables.Fly_To_City.getCityCode());
        	GlobalVariables.currentSubscribe.setArriveName(GlobalVariables.Fly_To_City.getCityName());
        	GlobalVariables.currentSubscribe.setFlightBeginDate(GlobalVariables.Fly_From_Date);
        	GlobalVariables.currentSubscribe.setSubscribeWay(1);
        	GlobalVariables.currentSubscribe.setDisCount(7);
        	GlobalVariables.currentSubscribe.setMobile(GlobalVariables.bodingUser.getMobile());
        }
        
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        
        View domesticLowPrice = (View) LayoutInflater.from(this).inflate(R.layout.tab_layout_city_select, null);  
        TextView domesticTextView = (TextView) domesticLowPrice.findViewById(R.id.tab_label);  
        domesticTextView.setText(LOWPRICE_DOMESTIC);
        
        View internationalLowPrice = (View) LayoutInflater.from(this).inflate(R.layout.tab_layout_city_select, null);  
        TextView internationalTextView = (TextView) internationalLowPrice.findViewById(R.id.tab_label);  
        internationalTextView.setText(lOWPRICE_INTERNATIONAL);

        mTabHost.addTab(mTabHost.newTabSpec(LOWPRICE_DOMESTIC).setIndicator(domesticLowPrice),
                AddLowPriceDomesticFragment.class, null);
        
        mTabHost.addTab(mTabHost.newTabSpec(lOWPRICE_INTERNATIONAL).setIndicator(internationalLowPrice),
        		AddLowPriceInternationalFragment.class, null);
 
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String arg0) {
				if(arg0.equals(LOWPRICE_DOMESTIC)){
					if(GlobalVariables.currentSubscribe.isInternational()){
						mTabHost.setCurrentTab(1);
					}
				}else{
					if(!GlobalVariables.currentSubscribe.isInternational()){
						mTabHost.setCurrentTab(0);
					}
				}
			}
		});
        
        initView();
        setCurrentTab();
    }
    
    private void setCurrentTab(){
    	if(GlobalVariables.currentSubscribe.isInternational()){
    		GlobalVariables.currentSubscribe.setTicketType(2);
    		mTabHost.setCurrentTab(0);
    		mTabHost.setCurrentTab(1);
    	}
    	else{
    		GlobalVariables.currentSubscribe.setTicketType(1);
    		mTabHost.setCurrentTab(1);
    		mTabHost.setCurrentTab(0);
    	}
    }
    

    private void initView(){
        LinearLayout returnLogoLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
        returnLogoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(AddLowpriceSubsActivity.this, IntentRequestCode.ADD_LOWPRICESUBS);
			}
        });
        
        completeLinearLayout = (LinearLayout)findViewById(R.id.addlowpricesubs_complete_linearLayout);
        completeLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(GlobalVariables.currentSubscribe.getSubscribeWay() == 2 &&
					GlobalVariables.currentSubscribe.getPrice() == 0){
					warningDialog.setContent("请输入价格");
					warningDialog.show();
					return;
				}
				if(GlobalVariables.currentSubscribe.getMobile().equals("") ||
					!RegularExpressionsUtil.checkMobile(GlobalVariables.currentSubscribe.getMobile())){
					warningDialog.setContent("请输入正确的手机号码");
					warningDialog.show();
					return;
				}
				if(!Util.isNetworkAvailable(AddLowpriceSubsActivity.this)){
					networkUnavaiableDialog.show();
					return;
				}
				
				progressBarDialog.show();
				(new LowPriceSubscribeTask(AddLowpriceSubsActivity.this, HTTPAction.ADD_LOWPRICESUB)).execute(GlobalVariables.currentSubscribe);
			}
        });
    }
    
    public void setAddLowPriceSubsResult(boolean isSuccess){
    	progressBarDialog.dismiss();
    	if(isSuccess){
    		Intent intent=new Intent();
    		intent.putExtra(IntentExtraAttribute.ADD_LOWPRICESUB, true);
			setResult(IntentRequestCode.ADD_LOWPRICESUBS.getRequestCode(), intent);
			finish();
    	}else{
    		warningDialog.setContent("新增订阅失败");
    		warningDialog.show();
    	}
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  GlobalVariables.currentSubscribe.setLeaveCode(GlobalVariables.Fly_From_City.getCityCode());
    	  GlobalVariables.currentSubscribe.setLeaveName(GlobalVariables.Fly_From_City.getCityName());
    	  GlobalVariables.currentSubscribe.setArriveCode(GlobalVariables.Fly_To_City.getCityCode());
    	  GlobalVariables.currentSubscribe.setArriveName(GlobalVariables.Fly_To_City.getCityName());
    	  System.out.println(GlobalVariables.Fly_From_City.getCityCode());
    	  System.out.println(GlobalVariables.Fly_From_City.getCityName());
    	  System.out.println(GlobalVariables.Fly_To_City.getCityCode());
    	  System.out.println(GlobalVariables.Fly_To_City.getCityName());
          setCurrentTab();
	 }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }
}
