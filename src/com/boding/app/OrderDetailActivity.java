package com.boding.app;


import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OrderDetailActivity extends Activity {
	private TextView orderStatusTextView;
	private TextView orderIDTextView;
	private TextView orderCreatedDateTextView;
	private TextView orderPaymentMethodTextView;
	private TextView orderReturnBodingCashTextView;
	private LinearLayout notPaidWarningLinearLayout;
	private TextView flyFromDateTextView;
	private TextView flyFromToCityTextView;
	private TextView flyCompanyTextView;
	private TextView planeTypeTextView;
	private TextView classTypeTextView;
	private TextView planeSizeTextView;
	private TextView seatInfoTextView;
	private TextView flyFromTimeTextView;
	private TextView flyFromTerminalTextView;
	private TextView flyToTimeTextView;
	private TextView flyToTerminalTextView;
	private TextView ticketPriceTextView;
	private TextView buildingPriceTextView;
	private TextView fuelPriceTextView;
	private LinearLayout changeRefundConditionLinearLayout;
	private ListView passengerListView;
	private TextView contactPhoneNumTextView;
	private TextView insuranceAmountTextView;
	private TextView insuranceTotalTextView;
	private TextView deliveryMethodTextView;
	private TextView contactCustomServiceTextView;
	private TextView totalPriceTextView;
	private LinearLayout confirmPayLinearLayout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
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
				Util.returnToPreviousPage(OrderDetailActivity.this, IntentRequestCode.ORDER_DETAIL);
			}
			
		});
		
		orderStatusTextView = (TextView)findViewById(R.id.orderdetail_orderStatus_textView);
		orderIDTextView = (TextView)findViewById(R.id.orderdetail_orderID_textView);
		orderCreatedDateTextView = (TextView)findViewById(R.id.orderdetail_orderCreatedDate_textView);
		orderPaymentMethodTextView = (TextView)findViewById(R.id.orderdetail_orderPaymentMethod_textView);
		orderReturnBodingCashTextView = (TextView)findViewById(R.id.orderdetail_orderReturnBodingCash_textView);
		notPaidWarningLinearLayout = (LinearLayout)findViewById(R.id.orderdetail_notPaidWarning_linearLayout);
		flyFromDateTextView = (TextView)findViewById(R.id.orderdetail_flyFromDate_textView);
		flyFromToCityTextView = (TextView)findViewById(R.id.orderdetail_flyFromToCity_textView);
		flyCompanyTextView = (TextView)findViewById(R.id.orderdetail_flyCompany_textView);
		planeTypeTextView = (TextView)findViewById(R.id.orderdetail_planeType_textView);
		classTypeTextView = (TextView)findViewById(R.id.orderdetail_classType_textView);
		planeSizeTextView = (TextView)findViewById(R.id.orderdetail_planeSize_textView);
		seatInfoTextView = (TextView)findViewById(R.id.orderdetail_seatInfo_textView);
		flyFromTimeTextView = (TextView)findViewById(R.id.orderdetail_flyFromTime_textView);
		flyFromTerminalTextView = (TextView)findViewById(R.id.orderdetail_flyFromTerminal_textView);
		flyToTimeTextView = (TextView)findViewById(R.id.orderdetail_flyToTime_textView);
		flyToTerminalTextView = (TextView)findViewById(R.id.orderdetail_flyToTerminal_textView);
		ticketPriceTextView = (TextView)findViewById(R.id.orderdetail_ticketPrice_textView);
		buildingPriceTextView = (TextView)findViewById(R.id.orderdetail_buildingPrice_textView);
		fuelPriceTextView = (TextView)findViewById(R.id.orderdetail_fuelPrice_textView);
		changeRefundConditionLinearLayout = (LinearLayout)findViewById(R.id.orderdetail_changeRefundCondition_linearLayout);
		passengerListView = (ListView)findViewById(R.id.orderdetail_passenger_listView);
		contactPhoneNumTextView = (TextView)findViewById(R.id.orderdetail_contactPhoneNum_textView);
		insuranceAmountTextView = (TextView)findViewById(R.id.orderdetail_insuranceAmount_textView);
		insuranceTotalTextView = (TextView)findViewById(R.id.orderdetail_insuranceTotal_textView);
		deliveryMethodTextView = (TextView)findViewById(R.id.orderdetail_deliveryMethod_textView);
		contactCustomServiceTextView = (TextView)findViewById(R.id.orderdetail_contactCustomService_textView);
		totalPriceTextView = (TextView)findViewById(R.id.orderdetail_totalPrice_textView);
		confirmPayLinearLayout = (LinearLayout)findViewById(R.id.orderdetail_confirmPay_linearLayout);
		
		addListeners();
	}
	
	private void addListeners(){
		confirmPayLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(OrderDetailActivity.this, OrderPaymentActivity.class);
				startActivityForResult(intent, IntentRequestCode.ORDER_PAYEMNT.getRequestCode());
			}
		});
	}
}
