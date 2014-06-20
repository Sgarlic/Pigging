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

public class AddPassengerInfoActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private TextView passengerNameTextView;
	private EditText passengerNameEditText;
	private LinearLayout chooseIDTypeLinearLayout;
	private TextView choosedIDTypeTextView;
	private EditText IDNumberEditText;
	private LinearLayout otherIDTypeLinearLayout;
	private LinearLayout chooseValidDateLinearLayout;
	private TextView choosedValidDateTextView;
	private LinearLayout chooseNationalityLinearLayout;
	private TextView choosedNationalityTextView;
	private LinearLayout chooseGenderLinearLayout;
	private TextView choosedGenderTextView;
	private LinearLayout chooseBirthdayLinearLayout;
	private TextView choosedBirthdayTextView;
	
	private IdentityType selectedIDType = null;
	private boolean isChoosingBirthday = false;
	
	List<String> idTypeList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_passengerinfo);
		
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
				Util.returnToPreviousPage(AddPassengerInfoActivity.this, IntentRequestCode.START_ADD_PASSENGERINFO);
			}
			
		});
		completeLinearLayout = (LinearLayout) findViewById(R.id.addpassenger_complete_linearLayout);

		passengerNameTextView = (TextView) findViewById(R.id.addpassenger_passengerName_textView);
		passengerNameEditText = (EditText) findViewById(R.id.addpassenger_input_passengerName_editText);
		chooseIDTypeLinearLayout = (LinearLayout) findViewById(R.id.addpassenger_chooseIDType_linearLayout);
		choosedIDTypeTextView = (TextView) findViewById(R.id.addpassenger_choosedIDType_textView);
		IDNumberEditText = (EditText) findViewById(R.id.addpassenger_input_IDNumber_editText);
		otherIDTypeLinearLayout = (LinearLayout) findViewById(R.id.addpassenger_otherIDType_linearLayout);
		chooseValidDateLinearLayout = (LinearLayout) findViewById(R.id.addpassenger_chooseValidDate_linearLayout);
		choosedValidDateTextView = (TextView) findViewById(R.id.addpassenger_choosedValidDate_textView);
		chooseNationalityLinearLayout = (LinearLayout) findViewById(R.id.addpassenger_chooseNationality_linearLayout);
		choosedNationalityTextView = (TextView) findViewById(R.id.addpassenger_choosedNationality_textView);
		chooseGenderLinearLayout = (LinearLayout) findViewById(R.id.addpassenger_chooseGenderlinearLayout);
		choosedGenderTextView = (TextView) findViewById(R.id.addpassenger_choosedGender_textView);
		chooseBirthdayLinearLayout = (LinearLayout) findViewById(R.id.addpassenger_chooseBirthday_linearLayout);
		choosedBirthdayTextView = (TextView) findViewById(R.id.addpassenger_choosedBirthday_textView);

		setViewAccordingtoIDType();
		addListeners();
		
		idTypeList = new ArrayList<String>();
		for(IdentityType identityType : IdentityType.values()){
			idTypeList.add(identityType.getIdentityName());
		}
	}
	
	DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			String choosedDate = Util.getFormatedDate(year, monthOfYear, dayOfMonth);
			if(isChoosingBirthday)
				choosedBirthdayTextView.setText(choosedDate);
			else
				choosedValidDateTextView.setText(choosedDate);
		}
	};
	
	private void showDatePickerDialog(){
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datepickerDialog = new DatePickerDialog(AddPassengerInfoActivity.this,dateSetListener,
				calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		if(isChoosingBirthday){
			datepickerDialog.setTitle("选择出生日期");
			datepickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
		}
		else{
			datepickerDialog.setTitle("选择有效日期");
			datepickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
		}
		datepickerDialog.show();
	}
	
	private void addListeners(){
		chooseIDTypeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SelectionDialog chooseIDTypeDialog = new SelectionDialog(AddPassengerInfoActivity.this,
						R.style.Custom_Dialog_Theme, "选择证件类型",idTypeList);
				chooseIDTypeDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						selectedIDType = IdentityType.values()[position];
						setViewAccordingtoIDType();
					}
				});
				chooseIDTypeDialog.show();
			}
		});
		
		chooseValidDateLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isChoosingBirthday = false;
				showDatePickerDialog();
			}
		});
		
		chooseBirthdayLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isChoosingBirthday = true;
				showDatePickerDialog();
			}
		});
		
		chooseGenderLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				List<String> genderList = new ArrayList<String>();
				genderList.add("男");
				genderList.add("女");
				
				SelectionDialog chooseGenderDialog = new SelectionDialog(AddPassengerInfoActivity.this,
						R.style.Custom_Dialog_Theme, "选择性别",genderList);
				chooseGenderDialog.show();
			}
		});
		
		chooseNationalityLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AddPassengerInfoActivity.this, NationalitySelectActivity.class);
				startActivityForResult(intent, IntentRequestCode.START_NATIONALITY_SELECTION.getRequestCode());
			}
		});
	}
	
	private void setViewAccordingtoIDType(){
		if(selectedIDType == null)
			return;
		if(selectedIDType.isDomestic()){
			passengerNameTextView.setText("姓名");
			passengerNameEditText.setHint("所选证件姓名");
			otherIDTypeLinearLayout.setVisibility(View.INVISIBLE);
		}else{
			passengerNameTextView.setText("英文姓名");
			passengerNameEditText.setHint("Last(姓)/First(名)");
			otherIDTypeLinearLayout.setVisibility(View.VISIBLE);
		}
		choosedIDTypeTextView.setText(selectedIDType.getIdentityName());
	}
}
