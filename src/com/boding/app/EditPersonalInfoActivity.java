package com.boding.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentRequestCode;
import com.boding.task.BodingUserTask;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.SelectionDialog.OnItemSelectedListener;
import com.boding.view.dialog.NetworkUnavaiableDialog;
import com.boding.view.dialog.WarningDialog;

@SuppressLint("NewApi")
public class EditPersonalInfoActivity extends BodingBaseActivity {
	private LinearLayout completeLinearLayout;
	private EditText userNameEditText;
	private LinearLayout chooseGenderLinearLayout;
	private TextView choosedGenderTextView;
	private LinearLayout chooseBirthdayLinearLayout;
	private TextView choosedBirthdayTextView;
	
	List<String> genderList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_personalinfo);
		progressBarDialog = new ProgressBarDialog(this);
		networkUnavaiableDialog = new NetworkUnavaiableDialog(this);
		warningDialog = new WarningDialog(this);
//		selectedIDType = IdentityType.values()[0];
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
		genderList.add("男");
		genderList.add("女");
		initView();
		setViewContent();
	}
	
	private void setViewContent(){
		userNameEditText.setText(GlobalVariables.bodingUser.getName());
		
		choosedGenderTextView.setText(GlobalVariables.bodingUser.getGender());
		choosedGenderTextView.setSelected(true);
		
		if(GlobalVariables.bodingUser.getBirthdayInfo().equals("")){
			choosedBirthdayTextView.setSelected(false);
		}else{
			choosedBirthdayTextView.setSelected(true);
			choosedBirthdayTextView.setText(GlobalVariables.bodingUser.getBirthdayInfo());
		}
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
			GlobalVariables.bodingUser.setBirthdayInfo(choosedDate);
		}
	};
	
	private void showDatePickerDialog(){
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datepickerDialog = new DatePickerDialog(EditPersonalInfoActivity.this,dateSetListener,
			calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		if(!GlobalVariables.bodingUser.getBirthdayInfo().equals("")){
			String[] dateArray = GlobalVariables.bodingUser.getBirthdayInfo().split("-");
			datepickerDialog = new DatePickerDialog(EditPersonalInfoActivity.this,dateSetListener,
				Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]) -1 ,Integer.parseInt(dateArray[2]));
		}
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
				SelectionDialog chooseGenderDialog = new SelectionDialog(EditPersonalInfoActivity.this, "选择性别",genderList);
				chooseGenderDialog.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						GlobalVariables.bodingUser.setGenderFromGName(genderList.get(position));
						setViewContent();
					}
				});
				chooseGenderDialog.show();
			}
		});
		
		userNameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			@Override
			public void afterTextChanged(Editable arg0) {
				GlobalVariables.bodingUser.setName(arg0.toString());
			}
		});
		
		completeLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!Util.isNetworkAvailable(EditPersonalInfoActivity.this)){
					networkUnavaiableDialog.show();
					return;
				}
				progressBarDialog.show();
				(new BodingUserTask(EditPersonalInfoActivity.this, HTTPAction.EDIT_PERSONAL_INFO)).execute();
			}
		});
	}
	
	public void editPersonalInfoResult(boolean isSuccess){
		progressBarDialog.dismiss();
		if(isSuccess){
			Intent intent=new Intent();
			setResult(IntentRequestCode.EDIT_PERSONALINFO.getRequestCode(), intent);
			finish();
		}
		else{
			warningDialog.setContent("请填写正确的信息");
		}
	}
}
