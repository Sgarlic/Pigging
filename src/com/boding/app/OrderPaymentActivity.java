package com.boding.app;


import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderPaymentActivity extends Activity {
	private TextView titleTextView;
	private LinearLayout completeLinearLayout;
	private TextView fromToTextView;
	private TextView singleorRoundWayTextView;
	private TextView orderTotalTextView;
	private LinearLayout bodingDeductionLinearLayout;
	private TextView bodingDeductionTextView;
	private CheckBox useBodingDeductionCheckBox;
	private LinearLayout paybyCreditCardLinearLayout;
	private LinearLayout paybySavingCardLinearLayout;
	private LinearLayout paybyAlipayLinearLayout;
	private LinearLayout paybyTenpayLinearLayout;
	
	private boolean isPaying = false;// if is order created
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderpayment);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
		setTitle();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(OrderPaymentActivity.this, IntentRequestCode.ORDER_PAYEMNT);
			}
			
		});
		
		titleTextView = (TextView) findViewById(R.id.orderpayment_title_textView);
		completeLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_complete_linearLayout);
		fromToTextView = (TextView) findViewById(R.id.orderpayment_fromto_textView);
		singleorRoundWayTextView = (TextView) findViewById(R.id.orderpayment_singleorroundway_textView);
		orderTotalTextView = (TextView) findViewById(R.id.orderpayment_orderTotal_textView);
		bodingDeductionLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_bodingDeduction_linearLayout);
		bodingDeductionTextView = (TextView) findViewById(R.id.orderpayment_bodingDeduction_textView);
		useBodingDeductionCheckBox = (CheckBox) findViewById(R.id.orderpayment_usePodingDeduction__checkBox);
		paybyCreditCardLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_paybyCreditCard_linearLayout);
		paybySavingCardLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_paybySavingCard_linearLayout);
		paybyAlipayLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_paybyAlipay_linearLayout);
		paybyTenpayLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_paybyTenpay_linearLayout);
		addListeners();
	}
	
	private void setTitle(){
		if(isPaying){
			titleTextView.setText("订单支付");
		}else{
			titleTextView.setText("选择支付方式");
		}
	}
	
	private void addListeners(){
		paybyCreditCardLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openBankcardSelectionActivity(true);
			}
		});
		paybySavingCardLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openBankcardSelectionActivity(false);
			}
		});
		bodingDeductionLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(useBodingDeductionCheckBox.isChecked())
					useBodingDeductionCheckBox.setChecked(false);
				else
					useBodingDeductionCheckBox.setChecked(true);
			}
		});
	}
	
	private void openBankcardSelectionActivity(boolean isCreditcardSelection){
		Bundle bundle  = new Bundle();
		bundle.putBoolean(Constants.IS_CREDITCARD_SELECTION, isCreditcardSelection);
		Intent intent = new Intent();
		intent.setClass(OrderPaymentActivity.this, BankCardSelectActivity.class);
//		GlobalVariables.isFlyToCitySelection = isFlyToCitySelection;
		intent.putExtras(bundle);
		startActivityForResult(intent,IntentRequestCode.BANKCARD_SELECTION.getRequestCode());
	}
}
