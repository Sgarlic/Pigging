package com.boding.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.boding.R;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Passenger;
import com.boding.util.Util;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.layout.OrderFlightInfoLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ChangePhonenumActivity extends Activity {
	private EditText currentPhonenumEditText;
	private EditText newPhonenumEditText;
	private EditText verificationNumEditText;
	private LinearLayout sendVerificationNumLinearLayout;
	private LinearLayout confirmLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phonenum);
		
//		selectedIDType = IdentityType.values()[0];
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
				Util.returnToPreviousPage(ChangePhonenumActivity.this, IntentRequestCode.CHANGE_PHONENUM);
			}
			
		});

		currentPhonenumEditText = (EditText) findViewById(R.id.changephonenum_input_currentPhonenum_editText);
		newPhonenumEditText = (EditText) findViewById(R.id.changephonenum_input_newPhoneNum_editText);
		verificationNumEditText = (EditText) findViewById(R.id.changephonenum_input_verificationNum_editText);
		
		sendVerificationNumLinearLayout = (LinearLayout) findViewById(R.id.changephonenum_sendVerificationNum_linearLayout);
		confirmLinearLayout = (LinearLayout) findViewById(R.id.changephonenum_confirm_linearLayout);

		addListeners();
	}
	private void addListeners(){
	}
}
