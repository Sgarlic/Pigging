package com.boding.app;

import java.util.Calendar;
import java.util.Date;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DateSelectActivity extends Activity {
	private boolean isReturnDateSelection = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Util.setFullScreen(this);
		setContentView(R.layout.activity_date_select);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null)
        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
		setTitle();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(DateSelectActivity.this, IntentRequestCode.START_DATE_SELECTION);
			}
			
		});
		
//		LinearLayout fromDateLinearLayout = (LinearLayout)findViewById(R.id.from_date_linearLayout);
//		fromDateLinearLayout.setOnTouchListener(new OnTouchListener(){
//			@Override
//			public boolean onTouch(View arg0, MotionEvent event) {
//				Log.d("poding","eeeeeeeeeeeeeeeeee");
//				if(event.equals(MotionEvent.ACTION_DOWN)){
//					Log.d("poding","dwon11111111111");
//				}
//				return false;
//			}
//			
//		});
		
		CalendarView fromDateCalendarView = (CalendarView)findViewById(R.id.from_date_calendarView);
//		Calendar minDate = Calendar.getInstance();
//		Log.d("poding","here");
//		fromDateCalendarView.setMinDate(minDate.getTimeInMillis()-100);
//		Log.d("poding","here111111111111");
		Calendar maxDate = Calendar.getInstance();
		maxDate.add(Calendar.MONTH, 6);
		fromDateCalendarView.setMaxDate(maxDate.getTimeInMillis());
		fromDateCalendarView.setDate(Util.getMillIsFromDate(GlobalVariables.Fly_From_Date));
		fromDateCalendarView.setOnDateChangeListener(new OnDateChangeListener(){
			@Override
			public void onSelectedDayChange(CalendarView arg0, int year, int month, int dayOfMonth) {
				String selectedDate = Util.getFormatedDate(year, month, dayOfMonth);
				if(isReturnDateSelection){
					if(GlobalVariables.Fly_To_Date.equals(selectedDate))
						return;
					GlobalVariables.Fly_To_Date = selectedDate;
				}
				else{
					if(GlobalVariables.Fly_From_Date.equals(selectedDate))
						return;
					GlobalVariables.Fly_From_Date = selectedDate;
				}
				Util.returnToPreviousPage(DateSelectActivity.this, IntentRequestCode.START_DATE_SELECTION);
//				Log.d("poding",year+"-"+month+"-"+dayOfMonth);
			}

//			@Override
//			public void onClick(View arg0) {
//				Log.d("poding","clicked!!!!!!!!!!");
//				CalendarView newCalendarView = (CalendarView)arg0;
//				Date selectedDate = new Date(newCalendarView.getDate());
//				returnToPreviousPage(true, selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
//			}
			
		});
//		DatePicker fromDatePicker = (DatePicker)findViewById(R.id.from_date_datePicker);
//		Calendar minCalendar = Calendar.getInstance();
////		minCalendar.add(Calendar.YEAR, -1900);
//		fromDatePicker.setMinDate(minCalendar.getTimeInMillis()-100);
//		
//		Util.fixSetMinDateForDatePickerCalendarView(Calendar.getInstance(), fromDatePicker);
//		Calendar maxCalendar = Calendar.getInstance();
//		maxCalendar.add(Calendar.MONTH, 6);
//		fromDatePicker.setMaxDate(maxCalendar.getTimeInMillis());
//		
//		fromDatePicker.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				Log.d("poding","clicked!!!!!!!!!!");
////				CalendarView newCalendarView = (CalendarView)arg0;
////				Date selectedDate = new Date(newCalendarView.getDate());
////				returnToPreviousPage(true, selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate());
//			}
//			
//		});
	}
	
	private void setTitle(){
    	TextView dateSelectTitleTextView = (TextView)findViewById(R.id.date_select_title_textView);
    	if(isReturnDateSelection)
    		dateSelectTitleTextView.setText("选择返回日期");
    	else
    		dateSelectTitleTextView.setText("选择出发日期");
    }

}
