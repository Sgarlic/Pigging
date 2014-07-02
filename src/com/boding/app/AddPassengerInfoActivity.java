package com.boding.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.http.HttpConnector;
import com.boding.model.BodingUser;
import com.boding.model.Passenger;
import com.boding.task.PassengerTask;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.WarningDialog;
import com.boding.view.layout.OrderFlightInfoLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
	private TextView titleTextView;
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
	private LinearLayout deletePassengerLinearLayout;
	
	private boolean isChoosingBirthday = false;
	private boolean isEditing = false;
	private boolean isMangingPassenger = false;
	
	private Passenger passenger;
	
	private ProgressBarDialog progressBarDialog;
	private WarningDialog warningDialog;
	
	List<String> idTypeList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_passengerinfo);
		
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	isMangingPassenger = arguments.getBoolean(IntentExtraAttribute.IS_MANAGE_PASSENGER);
        	isEditing = arguments.getBoolean(IntentExtraAttribute.IS_EDIT_PASSENGER);
        	if(isEditing){
        		passenger = (Passenger) arguments.getSerializable(IntentExtraAttribute.IS_EDIT_PASSENGER_PASSENGERINFO);
        	}else
        		passenger = new Passenger();
        }
		progressBarDialog = new ProgressBarDialog(this, Constants.DIALOG_STYLE);
		warningDialog = new WarningDialog(this, Constants.DIALOG_STYLE);
		
		initView();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(AddPassengerInfoActivity.this, IntentRequestCode.ADD_PASSENGERINFO);
			}
			
		});
		
		titleTextView = (TextView) findViewById(R.id.addpassenger_title_textView);
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
		deletePassengerLinearLayout = (LinearLayout) findViewById(R.id.addpassenger_deletePassenger_linearLayout);
		
		setViewFromPassengerInfo();
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
			if(isChoosingBirthday){
				choosedBirthdayTextView.setText(choosedDate);
				passenger.setBirthday(choosedDate);
			}
			else{
				choosedValidDateTextView.setText(choosedDate);
				passenger.setValidDate(choosedDate);
			}
			setViewFromPassengerInfo();
		}
	};
	
	private void showDatePickerDialog(){
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datepickerDialog = new DatePickerDialog(AddPassengerInfoActivity.this,dateSetListener,
				calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));;
		if(isChoosingBirthday){
			if(passenger.getBirthday() != null){
				String[] dateArray = passenger.getBirthday().split("-");
				
				datepickerDialog = new DatePickerDialog(AddPassengerInfoActivity.this,dateSetListener,
						Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]) -1 ,Integer.parseInt(dateArray[2]));
			}
			datepickerDialog.setTitle("选择出生日期");
			datepickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
		}
		else{
			if(passenger.getValidDate() != null){
				String[] dateArray = passenger.getValidDate().split("-");
				datepickerDialog = new DatePickerDialog(AddPassengerInfoActivity.this,dateSetListener,
						Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]) -1 ,Integer.parseInt(dateArray[2]));
			}
			datepickerDialog.setTitle("选择有效日期");
			datepickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
		}
		datepickerDialog.show();
	}
	
	public void setAddPassengerResult(boolean result){
		handleResult(result, IntentExtraAttribute.ADD_PASSENGER_EXTRA);
	}
	
	public void setEditPassengerResult(boolean result){
		handleResult(result, IntentExtraAttribute.EDIT_PASSENGER_EXTRA);
	}
	
	public void setDeletePassengerResult(boolean result){
		handleResult(result, IntentExtraAttribute.DELETE_PASSENGER_EXTRA);
	}
	
	private void handleResult(boolean result, String extraKey){
		progressBarDialog.dismiss();
		if(result){
			Intent intent=new Intent();
			intent.putExtra(extraKey, passenger);
			setResult(IntentRequestCode.ADD_PASSENGERINFO.getRequestCode(), intent);
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
				String passengerName = passengerNameEditText.getText().toString();
				String passengerIDNumber = IDNumberEditText.getText().toString();
				// required name and paper number
				if(passenger.getIdentityType() == null){
					warningDialog.setContent("请选择证件类型");
					warningDialog.show();
					return;
				}
				if(passengerIDNumber.length() == 0){
					warningDialog.setContent("证件号不能为空");
					warningDialog.show();
					return;
				}
				
				
				if(passenger.getIdentityType() == IdentityType.NI){
					if(passengerName.length() < 2 || !Util.checkIfChinese(passengerName)){
						warningDialog.setContent("请填写正确的名字");
						warningDialog.show();
						return;
					}
				}
				else{
					if(passengerName.length() < 2 || Util.checkIfChinese(passengerName) || !passengerName.contains("/")){
						warningDialog.setContent("请填写正确的名字");
						warningDialog.show();
						return;
					}
					if(passenger.getNationality().equals("")){
						warningDialog.setContent("请选择国籍");
						warningDialog.show();
						return;
					}
					if(passenger.getGender() == null){
						warningDialog.setContent("请选择性别");
						warningDialog.show();
						return;
					}
					if(passenger.getBirthday().equals("")){
						warningDialog.setContent("请选择出生日期");
						warningDialog.show();
						return;
					}
					if(passenger.getValidDate().equals("")){
						warningDialog.setContent("请选择有效日期");
						warningDialog.show();
						return;
					}
//					passenger.setNationality("美国-US");
//					passenger.setBirthday("1991-03-22");
//					passenger.setValidDate("2017-03-22");
				}
				
				
				passenger.setName(passengerName);
				passenger.seteName(passengerName);
				passenger.setCardNumber(passengerIDNumber);
				
//				passenger.setName("饶礼仁");
//				passenger.seteName("lili/Li");
//				passenger.setCardNumber("35079011156570");
				progressBarDialog.show();
				if(isEditing)
					(new PassengerTask(AddPassengerInfoActivity.this, HTTPAction.EDIT_PASSENGER)).execute(passenger);
				else
					(new PassengerTask(AddPassengerInfoActivity.this, HTTPAction.ADD_PASSENGER)).execute(passenger);
			}
		});
		
		completeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				progressBarDialog.show();
				(new PassengerTask(AddPassengerInfoActivity.this, HTTPAction.DELETE_PASSENGER))
				.execute(passenger.getAuto_id());
			}
		});
		
		chooseIDTypeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SelectionDialog chooseIDTypeDialog = new SelectionDialog(AddPassengerInfoActivity.this,
						R.style.Custom_Dialog_Theme, "选择证件类型",idTypeList);
				chooseIDTypeDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						passenger.setIdentityType(IdentityType.values()[position]);
						setViewFromPassengerInfo();
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
				final List<String> genderList = new ArrayList<String>();
				genderList.add("男");
				genderList.add("女");
				
				SelectionDialog chooseGenderDialog = new SelectionDialog(AddPassengerInfoActivity.this,
						R.style.Custom_Dialog_Theme, "选择性别",genderList);
				chooseGenderDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						passenger.setGenderFromGName(genderList.get(position));
						setViewFromPassengerInfo();
					}
				});
				chooseGenderDialog.show();
			}
		});
		
		chooseNationalityLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AddPassengerInfoActivity.this, NationalitySelectActivity.class);
				startActivityForResult(intent, IntentRequestCode.NATIONALITY_SELECTION.getRequestCode());
			}
		});
	}
	
	private void setViewFromPassengerInfo(){
		if(isEditing){
			if(isMangingPassenger)
				titleTextView.setText("编辑常用旅客");
			else
				titleTextView.setText("编辑登机人");
		}else{
			if(isMangingPassenger)
				titleTextView.setText("新增常用旅客");
			else
				titleTextView.setText("新增登机人");
			deletePassengerLinearLayout.setVisibility(View.GONE);
		}
		
		if(passenger.getIdentityType() == null){
			otherIDTypeLinearLayout.setVisibility(View.INVISIBLE);
			choosedIDTypeTextView.setSelected(false);
			return;
		}
		if(passenger.getIdentityType().isDomestic()){
			passengerNameTextView.setText("姓名");
			passengerNameEditText.setHint("所选证件姓名");
			otherIDTypeLinearLayout.setVisibility(View.INVISIBLE);
			if(passenger.getName() != null)
				passengerNameEditText.setText(passenger.getName());;
		}else{
			passengerNameTextView.setText("英文姓名");
			passengerNameEditText.setHint("Last(姓)/First(名)");
			otherIDTypeLinearLayout.setVisibility(View.VISIBLE);
			if(passenger.geteName() != null)
				passengerNameEditText.setText(passenger.geteName());;
		}
		
		if(passenger.getCardNumber() != null)
			IDNumberEditText.setText(passenger.getCardNumber());
		
		choosedIDTypeTextView.setText(passenger.getIdentityType().getIdentityName());
		choosedIDTypeTextView.setSelected(true);
		
		if(passenger.getNationality() == null){
			choosedNationalityTextView.setSelected(false);
		}else{
			choosedNationalityTextView.setSelected(true);
			choosedNationalityTextView.setText(getNationalityName());
		}
	
		if(passenger.getGender() == null){
			choosedGenderTextView.setSelected(false);
		}else{
			choosedGenderTextView.setSelected(true);
			choosedGenderTextView.setText(passenger.getGender().toString());
		}
		
		if(passenger.getBirthday() == null){
			choosedBirthdayTextView.setSelected(false);
		}else{
			choosedBirthdayTextView.setSelected(true);
			choosedBirthdayTextView.setText(passenger.getBirthday());
		}
		
		if(passenger.getValidDate() == null){
			choosedValidDateTextView.setSelected(false);
		}else{
			choosedValidDateTextView.setSelected(true);
			choosedValidDateTextView.setText(passenger.getValidDate());
		}
	}
	
	private String getNationalityName(){
		return passenger.getNationality().split("-")[0];
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		if(requestCode==IntentRequestCode.NATIONALITY_SELECTION.getRequestCode()){
			if(data.getExtras() == null)
				return;
			if(data.getExtras().containsKey(IntentExtraAttribute.SELECTED_NATIONAL)){
				passenger.setNationality((String) data.getExtras().get(IntentExtraAttribute.SELECTED_NATIONAL));
				setViewFromPassengerInfo();
			}
		}
	}
}
