package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.IntentExtraAttribute;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class InsuranceSelectionActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout needInsuranceInfoLinearLayout;
	private ImageView needInsuranceInfoImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insurance_selection);
		initView();
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	if(arguments.containsKey(IntentExtraAttribute.CHOOSED_INSURANCE)){
        		needInsuranceInfoImageView.setSelected(true);
        	}
        }
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
		needInsuranceInfoImageView = (ImageView) findViewById(R.id.insuranceselection_needInsuranceInfo_imageView);
		addListeners();
	}
	
	private void addListeners(){
		needInsuranceInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(needInsuranceInfoImageView.isSelected())
					needInsuranceInfoImageView.setSelected(false);
				else
					needInsuranceInfoImageView.setSelected(true);
			}
		});
		completeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.putExtra(IntentExtraAttribute.CHOOSED_INSURANCE, needInsuranceInfoImageView.isSelected());
				setResult(IntentRequestCode.INSURANCE_SELECTION.getRequestCode(), intent);
				finish();
			}
		});
		
	}
}
