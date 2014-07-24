package com.boding.view.fragment;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.app.CitySelectActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.DateUtil;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.SelectionDialog.OnItemSelectedListener;

public class AddLowPriceDomesticFragment extends Fragment {
    private View currentView;

    private LinearLayout fromCityLinearLayout;
    private TextView fromCityTextView;
    private LinearLayout toCityLinearLayout;
    private TextView toCityTextView;
    private LinearLayout fromDateLinearLayout;
    private TextView fromDateTextView;
    private LinearLayout subsMethodLayout;
    private TextView subsMethodTextView;
    private LinearLayout discountLayout;
    private TextView discountTextView;
    private LinearLayout priceLayout;
    private EditText priceEditText;
    private EditText mobileEditText;
    
    private Context context;
    
    List<String> subsWayList = new ArrayList<String>();
    List<String> discountList = new ArrayList<String>();
    
    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = AddLowPriceDomesticFragment.this.getActivity();
        
        subsWayList.add("折扣");
		subsWayList.add("价格");
		
		for(int i = 7; i > 2; i--){
			discountList.add(i + "折及其以下");
		}
		
//		firstHotDomesticCity = GlobalVariables.domHotCitiesList.get(0);
//		if(firstHotDomesticCity == null)
//			firstHotDomesticCity = GlobalVariables.domesticCitiesList.get(0);
//		secondHotDomesticCity = GlobalVariables.domHotCitiesList.get(1);
//		if(secondHotDomesticCity == null)
//			secondHotDomesticCity = GlobalVariables.domesticCitiesList.get(1);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		currentView = inflater.inflate(R.layout.fragment_addlowpricesubs_domestic, container, false);
    	
//		if(GlobalVariables.currentSubscribe.isInternational()){
//			GlobalVariables.Fly_From_City = firstHotDomesticCity;
//    		GlobalVariables.currentSubscribe.setLeaveCode(firstHotDomesticCity.getCityCode());
//    		GlobalVariables.currentSubscribe.setLeaveName(firstHotDomesticCity.getCityName());
//    		GlobalVariables.Fly_To_City = secondHotDomesticCity;
//    		GlobalVariables.currentSubscribe.setArriveCode(secondHotDomesticCity.getCityCode());
//    		GlobalVariables.currentSubscribe.setArriveName(secondHotDomesticCity.getCityName());
//		}
		
        initView();
        return currentView;
    }
    
    private void initView(){
    	fromCityLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubs_chooseFromCity_linearLayout);
        fromCityTextView = (TextView) currentView.findViewById(R.id.addlowpricesubs_choosedFromCity_textView);
        toCityLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubs_chooseToCity_linearLayout);
        toCityTextView = (TextView) currentView.findViewById(R.id.addlowpricesubs_choosedToCity_textView);
        fromDateLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubs_chooseFromDate_linearLayout);
        fromDateTextView = (TextView) currentView.findViewById(R.id.addlowpricesubs_choosedFromDate_textView);
        subsMethodLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubs_chooseSubscribeMethod_linearLayout);
        subsMethodTextView = (TextView) currentView.findViewById(R.id.addlowpricesubs_choosedSubscribeMethod_textView);
        discountLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubs_chooseDiscount_linearLayout);
        discountTextView = (TextView) currentView.findViewById(R.id.addlowpricesubs_choosedDiscount_textView);
        priceLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubs_inputPrice_linearLayout);
        priceEditText = (EditText) currentView.findViewById(R.id.addlowpricesubs_inputPrice_editText);
        mobileEditText = (EditText) currentView.findViewById(R.id.addlowpricesubs_input_mobile_editText);
        
        setViewContent();
        addListeners();
    } 
    
    OnClickListener openCitySelectOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			int viewId = arg0.getId();
			boolean isFlyToCitySelection = false;
			if(viewId==R.id.addlowpricesubs_chooseToCity_linearLayout)
				isFlyToCitySelection = true;
			
			Bundle bundle  = new Bundle();
			bundle.putBoolean(Constants.IS_FLY_TO_CITY_SELECTION, isFlyToCitySelection);
			Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			intent.setClass(context, CitySelectActivity.class);
//			GlobalVariables.isFlyToCitySelection = isFlyToCitySelection;
			intent.putExtras(bundle);
			startActivityForResult(intent,IntentRequestCode.CITY_SELECTION.getRequestCode());
		}
		
	};
	
	DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			String choosedDate = DateUtil.getFormatedDate(year, monthOfYear, dayOfMonth);
			fromDateTextView.setText(choosedDate);
			GlobalVariables.currentSubscribe.setFlightBeginDate(choosedDate);
		}
	};
    
    @SuppressLint("NewApi")
	private void addListeners(){
    	fromCityLinearLayout.setOnClickListener(openCitySelectOnClickListener);
    	toCityLinearLayout.setOnClickListener(openCitySelectOnClickListener);
    	fromDateLinearLayout.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
				String[] dateArray = GlobalVariables.currentSubscribe.getFlightBeginDate().split("-");
				DatePickerDialog datepickerDialog = new DatePickerDialog(context,dateSetListener,
					Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]) -1 ,Integer.parseInt(dateArray[2]));
    			datepickerDialog.setTitle("选择出发日期");
    			String todayString = DateUtil.getFormatedDate(Calendar.getInstance());
    			datepickerDialog.getDatePicker().setMinDate(DateUtil.getMillIsFromDate(todayString));
    			datepickerDialog.show();
    		}
    	});
    	subsMethodLayout.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
				SelectionDialog chooseSubsWayDialog = new SelectionDialog(context, "选择订阅方式",subsWayList);
				chooseSubsWayDialog.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						GlobalVariables.currentSubscribe.setSubscribeWay(position+1);
						setSubsMethod();
					}
				});
				chooseSubsWayDialog.show();
    		}
    	});
    	discountLayout.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			SelectionDialog chooseDiscountDialog = new SelectionDialog(context, "选择订阅方式",discountList);
				chooseDiscountDialog.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						String choosedDiscount = discountList.get(position);
						GlobalVariables.currentSubscribe.setDisCount(Integer.parseInt(choosedDiscount.substring(0,1)));
						discountTextView.setText(choosedDiscount);
					}
				});
				chooseDiscountDialog.show();
    		}
    	});
    	priceEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			@Override
			public void afterTextChanged(Editable arg0) {
				if(!arg0.toString().equals(""))
					GlobalVariables.currentSubscribe.setPrice(Double.parseDouble(arg0.toString()));
			}
		});
    	mobileEditText.addTextChangedListener(new TextWatcher() {
    		@Override
    		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    		}
    		@Override
    		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
    				int arg3) {
    		}
    		@Override
    		public void afterTextChanged(Editable arg0) {
    			GlobalVariables.currentSubscribe.setMobile(arg0.toString());
    		}
    	});
    }
    
    private void setViewContent(){
    	fromCityTextView.setText(GlobalVariables.currentSubscribe.getLeaveName());
    	toCityTextView.setText(GlobalVariables.currentSubscribe.getArriveName());
    	fromDateTextView.setText(GlobalVariables.currentSubscribe.getFlightBeginDate());
    	setSubsMethod();
    	mobileEditText.setText(GlobalVariables.currentSubscribe.getMobile());
    }
    
    private void setSubsMethod(){
    	if(GlobalVariables.currentSubscribe.getSubscribeWay() == 1){
    		subsMethodTextView.setText("折扣");
    		priceLayout.setVisibility(View.GONE);
    		discountLayout.setVisibility(View.VISIBLE);
    		discountTextView.setText(GlobalVariables.currentSubscribe.getDisCount()+"折及其以下");
    	}
    	else{
    		subsMethodTextView.setText("价格");
    		discountLayout.setVisibility(View.GONE);
    		priceLayout.setVisibility(View.VISIBLE);
    		priceEditText.setText(GlobalVariables.currentSubscribe.getPrice()+"");
    	}
    }
}
