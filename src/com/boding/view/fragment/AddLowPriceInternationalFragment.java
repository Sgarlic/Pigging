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

public class AddLowPriceInternationalFragment extends Fragment {
    private View currentView;

    private LinearLayout tripTypeLinearLayout;
    private TextView tripTypeTextView;
    private LinearLayout fromCityLinearLayout;
    private TextView fromCityTextView;
    private LinearLayout toCityLinearLayout;
    private TextView toCityTextView;
    private LinearLayout fromDateLinearLayout;
    private TextView fromDateTextView;
    private LinearLayout returnDateLinearLayout;
    private TextView returnDateTextView;
    private LinearLayout earlyDelayLinearLayout;
    private TextView earlyDelayTextView;
    private LinearLayout subsMethodLayout;
    private TextView subsMethodTextView;
    private LinearLayout discountLayout;
    private TextView discountTextView;
    private LinearLayout priceLayout;
    private EditText priceEditText;
    private EditText mobileEditText;
    
    private Context context;
    
    List<String> tripTypeList = new ArrayList<String>();
    List<String> subsWayList = new ArrayList<String>();
    List<String> discountList = new ArrayList<String>();
    List<String> beforeAfterDayList = new ArrayList<String>();

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = AddLowPriceInternationalFragment.this.getActivity();
        tripTypeList.add("单程");
        tripTypeList.add("往返");
        
        subsWayList.add("折扣");
		subsWayList.add("价格");
		
		for(int i = 7; i > 2; i--){
			discountList.add(i + "折及其以下");
		}
		
		beforeAfterDayList.add("否");
		beforeAfterDayList.add("是");
//		
//		firstHotInternationalCity = GlobalVariables.interHotCitiesList.get(0);
//		if(firstHotInternationalCity == null)
//			firstHotInternationalCity = GlobalVariables.interCitiesList.get(0);
//		secondHotInternationalCity = GlobalVariables.interHotCitiesList.get(1);
//		if(secondHotInternationalCity == null)
//			secondHotInternationalCity = GlobalVariables.interCitiesList.get(1);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	currentView = inflater.inflate(R.layout.fragment_addlowpricesubs_international, container, false);
//
//    	if(!GlobalVariables.currentSubscribe.isInternational()){
//    		GlobalVariables.currentSubscribe.setLeaveCode(firstHotInternationalCity.getCityCode());
//    		GlobalVariables.currentSubscribe.setLeaveName(firstHotInternationalCity.getCityName());
//    		GlobalVariables.currentSubscribe.setArriveCode(secondHotInternationalCity.getCityCode());
//    		GlobalVariables.currentSubscribe.setArriveName(secondHotInternationalCity.getCityName());
//    	}
    	
        initView();
        return currentView;
    }
    
    private void initView(){
    	tripTypeLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_chooseTripType_linearLayout);
    	tripTypeTextView = (TextView) currentView.findViewById(R.id.addlowpricesubsinternational_choosedTripType_textView);
    	fromCityLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_chooseFromCity_linearLayout);
        fromCityTextView = (TextView) currentView.findViewById(R.id.addlowpricesubsinternational_choosedFromCity_textView);
        toCityLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_chooseToCity_linearLayout);
        toCityTextView = (TextView) currentView.findViewById(R.id.addlowpricesubsinternational_choosedToCity_textView);
        fromDateLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_chooseFromDate_linearLayout);
        fromDateTextView = (TextView) currentView.findViewById(R.id.addlowpricesubsinternational_choosedFromDate_textView);
        returnDateLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_chooseReturnDate_linearLayout);
        returnDateTextView = (TextView) currentView.findViewById(R.id.addlowpricesubsinternational_choosedReturnDate_textView);
        earlyDelayLinearLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_chooseEarlyDelay_linearLayout);
        earlyDelayTextView = (TextView) currentView.findViewById(R.id.addlowpricesubsinternational_chooseEarlyDelay_textView);
        subsMethodLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_chooseSubscribeMethod_linearLayout);
        subsMethodTextView = (TextView) currentView.findViewById(R.id.addlowpricesubsinternational_choosedSubscribeMethod_textView);
        discountLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_chooseDiscount_linearLayout);
        discountTextView = (TextView) currentView.findViewById(R.id.addlowpricesubsinternational_choosedDiscount_textView);
        priceLayout = (LinearLayout) currentView.findViewById(R.id.addlowpricesubsinternational_inputPrice_linearLayout);
        priceEditText = (EditText) currentView.findViewById(R.id.addlowpricesubsinternational_inputPrice_editText);
        mobileEditText = (EditText) currentView.findViewById(R.id.addlowpricesubsinternational_input_mobile_editText);
        
        setViewContent();
        addListeners();
    }
    
    OnClickListener openCitySelectOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			int viewId = arg0.getId();
			boolean isFlyToCitySelection = false;
			if(viewId==R.id.addlowpricesubsinternational_chooseToCity_linearLayout)
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
    	tripTypeLinearLayout.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
				SelectionDialog chooseSubsWayDialog = new SelectionDialog(context, "选择形成类型",tripTypeList);
				chooseSubsWayDialog.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						GlobalVariables.currentSubscribe.setTripType(position+1);
						setTripType();
					}
				});
				chooseSubsWayDialog.show();
    		}
    	});
    	
    	fromCityLinearLayout.setOnClickListener(openCitySelectOnClickListener);
    	toCityLinearLayout.setOnClickListener(openCitySelectOnClickListener);
    	fromDateLinearLayout.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
				String[] dateArray = GlobalVariables.currentSubscribe.getFlightBeginDate().split("-");
				DatePickerDialog datepickerDialog = new DatePickerDialog(context,dateSetListener,
					Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]) -1 ,Integer.parseInt(dateArray[2]));
    			datepickerDialog.setTitle("选择出发日期");
    			datepickerDialog.show();
    		}
    	});
    	returnDateLinearLayout.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			String[] dateArray = GlobalVariables.currentSubscribe.getFlightEndDate().split("-");
    			DatePickerDialog datepickerDialog = new DatePickerDialog(context,dateSetListener,
    					Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]) -1 ,Integer.parseInt(dateArray[2]));
    			datepickerDialog.setTitle("选择返回日期");
    			String todayString = DateUtil.getFormatedDate(Calendar.getInstance());
    			datepickerDialog.getDatePicker().setMinDate(DateUtil.getMillIsFromDate(todayString));
    			datepickerDialog.show();
    		}
    	});
    	earlyDelayLinearLayout.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			SelectionDialog chooseSubsWayDialog = new SelectionDialog(context, "可提前或延后一天",beforeAfterDayList);
    			chooseSubsWayDialog.setOnItemSelectedListener(new OnItemSelectedListener() {
    				@Override
    				public void OnItemSelected(int position) {
    					GlobalVariables.currentSubscribe.setBeforeAfterDay(position);
    					earlyDelayTextView.setText(beforeAfterDayList.get(position));
    				}
    			});
    			chooseSubsWayDialog.show();
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
    	setTripType();
    	fromCityTextView.setText(GlobalVariables.currentSubscribe.getLeaveName());
    	toCityTextView.setText(GlobalVariables.currentSubscribe.getArriveName());
    	fromDateTextView.setText(GlobalVariables.currentSubscribe.getFlightBeginDate());
    	setSubsMethod();
    	mobileEditText.setText(GlobalVariables.currentSubscribe.getMobile());
    	
    	if(GlobalVariables.currentSubscribe.getBeforeAfterDay() == 0)
    		earlyDelayTextView.setText("否");
    	else
    		earlyDelayTextView.setText("是");
    }

    private void setTripType(){
    	if(GlobalVariables.currentSubscribe.getTripType() == 1){
    		returnDateLinearLayout.setVisibility(View.GONE);
    		tripTypeTextView.setText("单程");
    		GlobalVariables.currentSubscribe.setFlightEndDate("");
    	}else{
    		if(GlobalVariables.currentSubscribe.getFlightEndDate().equals("") 
    		|| DateUtil.compareDateString(GlobalVariables.currentSubscribe.getFlightBeginDate(), 
    				GlobalVariables.currentSubscribe.getFlightEndDate())>0){
    			GlobalVariables.currentSubscribe.setFlightEndDate(DateUtil.getNextDay(
    				GlobalVariables.currentSubscribe.getFlightBeginDate()));
    		}
    		returnDateTextView.setText(GlobalVariables.currentSubscribe.getFlightEndDate());
    		returnDateLinearLayout.setVisibility(View.VISIBLE);
    	}
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
