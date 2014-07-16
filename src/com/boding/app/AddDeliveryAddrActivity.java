package com.boding.app;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.DeliveryAddress;
import com.boding.model.Province;
import com.boding.task.DeliveryAddrTask;
import com.boding.task.PassengerTask;
import com.boding.util.RegularExpressionsUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddDeliveryAddrActivity extends Activity {
	private TextView titleTextView;
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
	private EditText mobileEditText;
	private EditText phoneEditText;
	private LinearLayout deleteDeliverAddrLinearLayout;
	
	private boolean isEditing = false;
	
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
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	isEditing = arguments.getBoolean(IntentExtraAttribute.IS_EDIT_DELIVERYADDR);
        	if(isEditing){
        		deliveryAddr = (DeliveryAddress) arguments.getParcelable(IntentExtraAttribute.IS_EDIT_DELIVERYADDR_ADDRINFO);
        	}else
        		deliveryAddr = new DeliveryAddress();
        }
		provinceList = new ArrayList<String>();
		cityList = new ArrayList<String>();
		districtList = new ArrayList<String>();
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
		warningDialog = new WarningDialog(this);
		progressBarDialog = new ProgressBarDialog(this);
		initProvinceList();
		initView();
	}
	
	private void initProvinceList(){
		for(Province province : GlobalVariables.allProvincesList){
			provinceList.add(province.getProvinceName());
		}
	}
	
	private void initCityList(){
		Province currentProvince = deliveryAddr.getProvince();
		if(currentProvince == null)
			return;
		cityList.clear();
		Iterator<String> cityIterator = currentProvince.getCityIterator();
		while(cityIterator.hasNext()){
			cityList.add(cityIterator.next());
		}
	}
	
	private void initDistrictList(String cityName){
		Province currentProvince = deliveryAddr.getProvince();
		if(currentProvince == null)
			return;
		districtList = currentProvince.getDistrictList(cityName);
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(AddDeliveryAddrActivity.this, IntentRequestCode.ADD_DELIVERYADDR);
			}
			
		});
		
		titleTextView = (TextView) findViewById(R.id.adddeliveryaddr_title_textView);
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
		mobileEditText = (EditText) findViewById(R.id.adddeliveryaddr_input_mobile_editText);
		phoneEditText = (EditText) findViewById(R.id.adddeliveryaddr_input_phone_editText);
		deleteDeliverAddrLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_deleteAddr_linearLayout);
		
		setViewFromDeliveryAddr();
		addListeners();
	}
	
	private void setViewFromDeliveryAddr(){
		if(isEditing){
			titleTextView.setText("编辑常用地址");
		}else{
			titleTextView.setText("新增常用地址");
			deleteDeliverAddrLinearLayout.setVisibility(View.GONE);
		}
		
		recipientNameEditText.setText(deliveryAddr.getRecipientName());
		
		if(deliveryAddr.getProvince()==null){
			choosedProvinceTextView.setSelected(false);
		}else{
			choosedProvinceTextView.setSelected(true);
			choosedProvinceTextView.setText(deliveryAddr.getProvinceName());
		}
		
		if(deliveryAddr.getCity().length() == 0){
			choosedCityTextView.setSelected(false);
		}else{
			choosedCityTextView.setSelected(true);
			choosedCityTextView.setText(deliveryAddr.getCity());
		}
		
		if(deliveryAddr.getDistrict().length() == 0){
			choosedDistrictTextView.setSelected(false);
		}else{
			choosedDistrictTextView.setSelected(true);
			choosedDistrictTextView.setText(deliveryAddr.getDistrict());
		}
		
		detailedAddrEditText.setText(deliveryAddr.getDetailedAddr());
		
		zipcodeEditText.setText(deliveryAddr.getZipcode());
		
		mobileEditText.setText(deliveryAddr.getMobile());
		
		phoneEditText.setText(deliveryAddr.getPhone());
	}

	public void setAddDeliveryAddrResult(boolean result){
		handleResult(result, IntentExtraAttribute.ADD_DELIVERYADDR_EXTRA);
	}
	
	public void setEditDeliveryAddrResult(boolean result){
		handleResult(result, IntentExtraAttribute.EDIT_DELIVERYADDR_EXTRA);
	}
	
	public void setDeleteDeliveryAddrResult(boolean result){
		handleResult(result, IntentExtraAttribute.DELETE_DELIVERADDR_EXTRA);
	}
	
	private void handleResult(boolean result, String extraKey){
		progressBarDialog.dismiss();
		if(result){
			Intent intent=new Intent();
			intent.putExtra(extraKey, deliveryAddr);
			setResult(IntentRequestCode.ADD_DELIVERYADDR.getRequestCode(), intent);
			finish();
		}else{
			warningDialog.setContent("请填写正确的信息");
			warningDialog.show();
		}
	}
	
	private void addListeners(){
		completeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(deliveryAddr.getRecipientName().length() == 0){
					warningDialog.setContent("请填写收件人姓名");
					warningDialog.show();
					return;
				}
				if(deliveryAddr.getProvince() == null){
					warningDialog.setContent("请选择省份");
					warningDialog.show();
					return;
				}
				if(deliveryAddr.getCity().length() == 0){
					warningDialog.setContent("请选择城市");
					warningDialog.show();
					return;
				}
				if(deliveryAddr.getDistrict().length() == 0){
					warningDialog.setContent("请选择地区");
					warningDialog.show();
					return;
				}
				if(deliveryAddr.getDetailedAddr().length() == 0){
					warningDialog.setContent("请填写详细地址");
					warningDialog.show();
					return;
				}
				if(deliveryAddr.getZipcode().length() == 0){
					warningDialog.setContent("请填写邮编");
					warningDialog.show();
					return;
				}else if(!RegularExpressionsUtil.checkZipCode(deliveryAddr.getZipcode())){
					warningDialog.setContent("请填写正确的邮编");
					warningDialog.show();
					return;
				}
				if(deliveryAddr.getMobile().length() == 0){
					warningDialog.setContent("请填写手机号码");
					warningDialog.show();
					return;
				}else if(!RegularExpressionsUtil.checkMobile(deliveryAddr.getMobile())){
					warningDialog.setContent("请填写正确的手机号码");
					warningDialog.show();
					return;
				}
				if(deliveryAddr.getPhone().length() != 0&&
						!RegularExpressionsUtil.checkPhone(deliveryAddr.getPhone())){
					warningDialog.setContent("请填写正确的电话号码");
					warningDialog.show();
					return;
				}
				progressBarDialog.show();
				if(isEditing)
					(new DeliveryAddrTask(AddDeliveryAddrActivity.this, HTTPAction.EDIT_DELIVERYADDR)).execute(deliveryAddr);
				else
					(new DeliveryAddrTask(AddDeliveryAddrActivity.this, HTTPAction.ADD_DELIVERYADDR)).execute(deliveryAddr);
			}
		});
		chooseProvinceLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SelectionDialog chooseProvinceDialog = new SelectionDialog(AddDeliveryAddrActivity.this, 
						"选择省份",provinceList);
				chooseProvinceDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						deliveryAddr.setProvince(GlobalVariables.allProvincesList.get(position));
						initCityList();
						setViewFromDeliveryAddr();
					}
				});
				chooseProvinceDialog.show();
			}
		});
		chooseCityLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(deliveryAddr.getProvince() == null){
					warningDialog.setContent("请先选择省份");
					warningDialog.show();
					return;
				}
				SelectionDialog chooseCityDialog = new SelectionDialog(AddDeliveryAddrActivity.this,
						"选择城市",cityList);
				chooseCityDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						String cityName = cityList.get(position);
						deliveryAddr.setCity(cityName);
						initDistrictList(cityName);
						setViewFromDeliveryAddr();
					}
				});
				chooseCityDialog.show();
			}
		});
		chooseDistrictLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(deliveryAddr.getCity() == null){
					warningDialog.setContent("请先选择城市");
					warningDialog.show();
					return;
				}
				SelectionDialog chooseDistrictDialog = new SelectionDialog(AddDeliveryAddrActivity.this,
						"选择地区",districtList);
				chooseDistrictDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						deliveryAddr.setDistrict(districtList.get(position));
						setViewFromDeliveryAddr();
					}
				});
				chooseDistrictDialog.show();
			}
		});
		
		recipientNameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				deliveryAddr.setRecipientName(s.toString());
			}
		});
		
		detailedAddrEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				deliveryAddr.setDetailedAddr(s.toString());
			}
		});
		
		zipcodeEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				deliveryAddr.setZipcode(s.toString());
			}
		});
		
		mobileEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				deliveryAddr.setMobile(s.toString());
			}
		});
		
		phoneEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				deliveryAddr.setPhone(s.toString());
			}
		});
		deleteDeliverAddrLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				progressBarDialog.show();
				(new DeliveryAddrTask(AddDeliveryAddrActivity.this, HTTPAction.DELETE_DELIVERYADDR))
				.execute(deliveryAddr.getAddrID());
			}
		});
	}
}
