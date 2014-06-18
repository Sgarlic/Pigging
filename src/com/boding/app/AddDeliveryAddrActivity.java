package com.boding.app;


import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddDeliveryAddrActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private EditText recipientNameEditText;
	private LinearLayout chooseAreaLinearLayout;
	private TextView choosedAreaTextView;
	private EditText detailedAddrEditText;
	private EditText zipcodeEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_deliveryaddress);
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
				Util.returnToPreviousPage(AddDeliveryAddrActivity.this, IntentRequestCode.START_ADD_DELIVERYADDR);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_complete_linearLayout);
		recipientNameEditText = (EditText) findViewById(R.id.adddeliveryaddr_input_recipientName_editText);
		chooseAreaLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_chooseArea_linearLayout);
		choosedAreaTextView = (TextView) findViewById(R.id.adddeliveryaddr_choosedArea_textView);
		detailedAddrEditText = (EditText) findViewById(R.id.adddeliveryaddr_input_detailedAddr_editText);
		zipcodeEditText = (EditText) findViewById(R.id.adddeliveryaddr_input_zipcode_editText);
		addListeners();
	}
	
	private void addListeners(){
	}
}
