package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Passenger;
import com.boding.util.Util;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.layout.OrderFlightInfoLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class InsuranceSelectionActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout needInsuranceInfoLinearLayout;
	private CheckBox needInsuranceInfoCheckBox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insurance_selection);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(InsuranceSelectionActivity.this, IntentRequestCode.INSURANCE_SELECTION);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.insuranceselection_complete_linearLayout);
		needInsuranceInfoLinearLayout = (LinearLayout) findViewById(R.id.insuranceselection_needInsuranceInfo_linearLayout);
		needInsuranceInfoCheckBox = (CheckBox) findViewById(R.id.insuranceselection_needInsuranceInfo_checkBox);
		addListeners();
	}
	
	private void addListeners(){
		
		needInsuranceInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(needInsuranceInfoCheckBox.isChecked())
					needInsuranceInfoCheckBox.setChecked(false);
				else
					needInsuranceInfoCheckBox.setChecked(true);
			}
		});
		
	}
}
