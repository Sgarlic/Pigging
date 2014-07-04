package com.boding.app;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentRequestCode;
import com.boding.model.DeliveryAddress;
import com.boding.model.Province;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AddDeliveryAddrActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private EditText recipientNameEditText;
	private LinearLayout chooseProvinceLinearLayout;
	private TextView choosedProvinceTextView;
	private LinearLayout chooseCityLinearLayout;
	private TextView choosedCityTextView;
	private LinearLayout chooseDistrictLinearLayout;
	private TextView choosedDistrictTextView;
	private EditText detailedAddrEditText;
	private EditText zipcodeEditText;
	
	List<String> provinceList;
	List<String> cityList;
	List<String> districtList;
	
	private DeliveryAddress deliveryAddr;
	
	private WarningDialog warningDialog;
	private ProgressBarDialog progressBarDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_deliveryaddress);
		deliveryAddr = new DeliveryAddress(); 
		provinceList = new ArrayList<String>();
		cityList = new ArrayList<String>();
		districtList = new ArrayList<String>();
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
		initProvinceList();
		initView();
	}
	
	private void initProvinceList(){
		for(Province province : GlobalVariables.allProvincesList){
			provinceList.add(province.getProvinceName());
		}
	}
	
	private void initCityList(int provincePos){
		Province currentProvince = deliveryAddr.getProvince();
		if(currentProvince == null)
			return;
		Iterator<String> cityIterator = currentProvince.getCityIterator();
		while(cityIterator.hasNext()){
			cityList.add(cityIterator.next());
		}
	}
	
	private void initDistrictList(int cityPos){
		Province currentProvince = deliveryAddr.getProvince();
		if(currentProvince == null)
			return;
		districtList = currentProvince.getDistrictList(cityList.get(cityPos));
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(AddDeliveryAddrActivity.this, IntentRequestCode.ADD_DELIVERYADDR);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_complete_linearLayout);
		recipientNameEditText = (EditText) findViewById(R.id.adddeliveryaddr_input_recipientName_editText);
		chooseProvinceLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_chooseProvince_linearLayout);
		choosedProvinceTextView = (TextView) findViewById(R.id.adddeliveryaddr_choosedProvince_textView);
		chooseCityLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_chooseCity_linearLayout);
		choosedCityTextView = (TextView) findViewById(R.id.adddeliveryaddr_choosedCity_textView);
		chooseDistrictLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_chooseDistrict_linearLayout);
		choosedDistrictTextView = (TextView) findViewById(R.id.adddeliveryaddr_choosedDistrict_textView);
		detailedAddrEditText = (EditText) findViewById(R.id.adddeliveryaddr_input_detailedAddr_editText);
		zipcodeEditText = (EditText) findViewById(R.id.adddeliveryaddr_input_zipcode_editText);
		
		setViewFromDeliveryAddr();
		addListeners();
	}
	
	private void setViewFromDeliveryAddr(){
		if(deliveryAddr.getRecipientName() != null)
			recipientNameEditText.setText(deliveryAddr.getRecipientName());
		
		if(deliveryAddr.getProvince()==null){
			choosedProvinceTextView.setSelected(false);
		}else{
			choosedProvinceTextView.setSelected(true);
			choosedProvinceTextView.setText(deliveryAddr.getProvinceName());
		}
		
		if(deliveryAddr.getCity()==null){
			choosedCityTextView.setSelected(false);
		}else{
			choosedCityTextView.setSelected(true);
			choosedCityTextView.setText(deliveryAddr.getCity());
		}
		
		if(deliveryAddr.getDistrict()==null){
			choosedDistrictTextView.setSelected(false);
		}else{
			choosedDistrictTextView.setSelected(true);
			choosedDistrictTextView.setText(deliveryAddr.getDistrict());
		}
		
		if(deliveryAddr.getDetailedAddr()!=null)
			detailedAddrEditText.setText(deliveryAddr.getDetailedAddr());
		
		if(deliveryAddr.getZipcode()!=null)
			zipcodeEditText.setText(deliveryAddr.getZipcode());
	}
	
	private void addListeners(){
		chooseProvinceLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SelectionDialog chooseProvinceDialog = new SelectionDialog(AddDeliveryAddrActivity.this,
						R.style.Custom_Dialog_Theme, "选择省份",provinceList);
				chooseProvinceDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
//						passenger.setIdentityType(IdentityType.values()[position]);
//						setViewFromPassengerInfo();
					}
				});
				chooseProvinceDialog.show();
			}
		});
		chooseCityLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SelectionDialog chooseCityDialog = new SelectionDialog(AddDeliveryAddrActivity.this,
						R.style.Custom_Dialog_Theme, "选择城市",cityList);
				chooseCityDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
//						passenger.setIdentityType(IdentityType.values()[position]);
//						setViewFromPassengerInfo();
					}
				});
				chooseCityDialog.show();
			}
		});
		chooseDistrictLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SelectionDialog chooseDistrictDialog = new SelectionDialog(AddDeliveryAddrActivity.this,
						R.style.Custom_Dialog_Theme, "选择地区",districtList);
				chooseDistrictDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
//						passenger.setIdentityType(IdentityType.values()[position]);
//						setViewFromPassengerInfo();
					}
				});
				chooseDistrictDialog.show();
			}
		});
	}
}
