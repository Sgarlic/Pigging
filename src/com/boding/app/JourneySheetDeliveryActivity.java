package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.model.BoardingPeople;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class JourneySheetDeliveryActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout needJourneySheetInfoLinearLayout;
	private CheckBox needJourneySheetCheckBox;
	private LinearLayout selectDeliveryMethodsLinearLayout;
	private TextView deliveryMethodTextView;
	private LinearLayout selectDeliveryAddrLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journeysheet_distribution);
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
				Util.returnToPreviousPage(JourneySheetDeliveryActivity.this, IntentRequestCode.START_CHOOSE_BOARDINGPEOPLE);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_complete_linearLayout);
		needJourneySheetInfoLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_needJourneySheetInfo_linearLayout);
		needJourneySheetCheckBox = (CheckBox) findViewById(R.id.journeysheet_needJourneySheetInfo_checkBox);
		selectDeliveryMethodsLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_selectDeliveryMethods_linearLayout);
		deliveryMethodTextView = (TextView) findViewById(R.id.journeysheet_deliveryMethods_textView);
		selectDeliveryAddrLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_selectDeliveryAddress_linearLayout);
		
		addListeners();
	}
	
	private void addListeners(){
		needJourneySheetInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(needJourneySheetCheckBox.isChecked())
					needJourneySheetCheckBox.setChecked(false);
				else
					needJourneySheetCheckBox.setChecked(true);
			}
		});
	}
}
