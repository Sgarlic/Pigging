package com.boding.app;

import java.util.Date;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.calendar.DateSelectCalendarView;
import com.boding.view.calendar.DateSelectCalendarView.OnItemClickListener;
import com.boding.view.layout.CalendarLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DateSelectActivity extends Activity {
	private boolean isReturnDateSelection = false;
	private CalendarLayout calendarLinearLayout;
//	private TextView currentMonthTextView;
//	private DateSelectCalendarView dateSelectCalendarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				Util.returnToPreviousPage(DateSelectActivity.this, IntentRequestCode.DATE_SELECTION);
			}
			
		});
		calendarLinearLayout = (CalendarLayout)findViewById(R.id.date_select_calendar_linearLayout);
//		dateSelectCalendarView = (DateSelectCalendarView)findViewById(R.id.date_select_calendarView);
		if(GlobalVariables.isRoundWaySelection){
			if(isReturnDateSelection){
				calendarLinearLayout.setDate(DateUtil.getDateFromString(GlobalVariables.Fly_Return_Date));
				calendarLinearLayout.setMinClickableDate(DateUtil.getDateFromString(GlobalVariables.Fly_From_Date));
			}
			else{
				calendarLinearLayout.setDate(DateUtil.getDateFromString(GlobalVariables.Fly_From_Date));
//				calendarLinearLayout.setMaxClickableDate(DateUtil.getDateFromString(GlobalVariables.Fly_Return_Date));
			}
		}
		
		calendarLinearLayout.setOnItemClickListener(new CalendarLayout.OnItemClickListener(){
			@Override
			public void OnItemClick(Date date) {
				String selectedDate = DateUtil.getFormatedDate(date);
				if(isReturnDateSelection){
					GlobalVariables.Fly_Return_Date = selectedDate;
				}
				else{
					GlobalVariables.Fly_From_Date = selectedDate;
					DateUtil.setRetunrnWayDate();
				}
				Util.returnToPreviousPage(DateSelectActivity.this, IntentRequestCode.DATE_SELECTION);
			}
		});
		
//		currentMonthTextView = (TextView) findViewById(R.id.current_month_textView);
//		setMonth();
//		lastMonthLinearLayout = (LinearLayout) this.findViewById(R.id.last_month_linearLayout);
//		nextMonthLinearLayout = (LinearLayout) this.findViewById(R.id.next_month_lienarLayout);
//		lastMonthLinearLayout.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View arg0) {
//				dateSelectCalendarView.gotoLastMonth();
//				setMonth();
//			}
//			
//		});	
//		nextMonthLinearLayout.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View arg0) {
//				dateSelectCalendarView.gotoNextMonth();
//				setMonth();
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

	private void setMonth(){
//		String currentMonth = Util.getYearMonthString(Util.getDateFromString(GlobalVariables.Fly_From_Date));
//		currentMonthTextView.setText(dateSelectCalendarView.getYearAndmonth());
	}
}
