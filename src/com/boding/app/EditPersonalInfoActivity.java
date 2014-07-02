package com.boding.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.boding.R;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Passenger;
import com.boding.util.DateUtil;
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

public class EditPersonalInfoActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private TextView userNameTextView;
	private EditText userNameEditText;
	private LinearLayout chooseGenderLinearLayout;
	private TextView choosedGenderTextView;
	private LinearLayout chooseBirthdayLinearLayout;
	private TextView choosedBirthdayTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_personalinfo);
		
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
				Util.returnToPreviousPage(EditPersonalInfoActivity.this, IntentRequestCode.EDIT_PERSONALINFO);
			}
			
		});
		completeLinearLayout = (LinearLayout) findViewById(R.id.editpersonalinfo_complete_linearLayout);

		userNameTextView = (TextView) findViewById(R.id.editpersonalinfo_userName_textView);
		userNameEditText = (EditText) findViewById(R.id.editpersonalinfo_input_userName_editText);
		chooseGenderLinearLayout = (LinearLayout) findViewById(R.id.editpersonalinfo_chooseGenderlinearLayout);
		choosedGenderTextView = (TextView) findViewById(R.id.editpersonalinfo_choosedGender_textView);
		chooseBirthdayLinearLayout = (LinearLayout) findViewById(R.id.editpersonalinfo_chooseBirthday_linearLayout);
		choosedBirthdayTextView = (TextView) findViewById(R.id.editpersonalinfo_choosedBirthday_textView);

		addListeners();
	}
	
	DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			String choosedDate = DateUtil.getFormatedDate(year, monthOfYear, dayOfMonth);
			choosedBirthdayTextView.setText(choosedDate);
		}
	};
	
	private void showDatePickerDialog(){
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datepickerDialog = new DatePickerDialog(EditPersonalInfoActivity.this,dateSetListener,
				calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		datepickerDialog.setTitle("选择出生日期");
		datepickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
		datepickerDialog.show();
	}
	
	private void addListeners(){
		chooseBirthdayLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});
		
		chooseGenderLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				List<String> genderList = new ArrayList<String>();
				genderList.add("男");
				genderList.add("女");
				
				SelectionDialog chooseGenderDialog = new SelectionDialog(EditPersonalInfoActivity.this,
						R.style.Custom_Dialog_Theme, "选择性别",genderList);
				chooseGenderDialog.show();
			}
		});
		
	}
}
