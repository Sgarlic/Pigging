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

public class JourneySheetDeliveryActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout needJourneySheetInfoLinearLayout;
	private CheckBox needJourneySheetCheckBox;
	private LinearLayout selectDeliveryMethodsLinearLayout;
	private TextView deliveryMethodTextView;
	private LinearLayout selectDeliveryAddrLinearLayout;
	
	private LinearLayout showJourneySheetInfoLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journeysheet_delivery);
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
				Util.returnToPreviousPage(JourneySheetDeliveryActivity.this, IntentRequestCode.START_CHOOSE_PASSENGER);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_complete_linearLayout);
		needJourneySheetInfoLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_needJourneySheetInfo_linearLayout);
		needJourneySheetCheckBox = (CheckBox) findViewById(R.id.journeysheet_needJourneySheetInfo_checkBox);
		selectDeliveryMethodsLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_selectDeliveryMethods_linearLayout);
		deliveryMethodTextView = (TextView) findViewById(R.id.journeysheet_deliveryMethods_textView);
		selectDeliveryAddrLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_selectDeliveryAddress_linearLayout);
		
		showJourneySheetInfoLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_showJourneySheetInfo_linearLayout);
		addListeners();
	}
	
	private void addListeners(){
		needJourneySheetCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				if(checked)
					showJourneySheetInfoLinearLayout.setVisibility(View.VISIBLE);
				else
					showJourneySheetInfoLinearLayout.setVisibility(View.INVISIBLE);
			}
		});
		
		selectDeliveryMethodsLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				List<String> deliverMethodList = new ArrayList<String>();
				deliverMethodList.add("江浙沪（￥10）");
				deliverMethodList.add("其他地区（￥20）");
				
				SelectionDialog deliveryMethodDialog = new SelectionDialog(JourneySheetDeliveryActivity.this,
						R.style.Custom_Dialog_Theme, "选择配送方式",deliverMethodList);
				deliveryMethodDialog.show();
			}
		});
		
		needJourneySheetInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(needJourneySheetCheckBox.isChecked())
					needJourneySheetCheckBox.setChecked(false);
				else
					needJourneySheetCheckBox.setChecked(true);
			}
		});
		
		selectDeliveryAddrLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(JourneySheetDeliveryActivity.this, ChooseDeliveryAddressActivity.class);
				startActivityForResult(intent,IntentRequestCode.START_CHOOSE_DELIVERYADDR.getRequestCode());
			}
		});
	}
}
