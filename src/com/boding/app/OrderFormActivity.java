package com.boding.app;

import java.util.Date;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;
import com.boding.view.calendar.DateSelectCalendarView;
import com.boding.view.calendar.DateSelectCalendarView.OnItemClickListener;
import com.boding.view.layout.CalendarLayout;
import com.boding.view.layout.OrderFlightInfoLayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderFormActivity extends Activity {
	private boolean isReturnDateSelection = false;
	private LinearLayout flightInfoLinearLayout;

	// if init complete
	private boolean flag = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_form);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
//        
//		initView();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		while(!flag){
			initView();
			flag = true;
		}
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(OrderFormActivity.this, IntentRequestCode.START_DATE_SELECTION);
			}
			
		});
		flightInfoLinearLayout = (LinearLayout) findViewById(R.id.flightInfo_linearLayout);
		OrderFlightInfoLayout orderFlightInfoLinearLayout = new OrderFlightInfoLayout(this);
//		orderFlightInfoLinearLayout.setOrientation(LinearLayout.VERTICAL);
		Log.d("poding",flightInfoLinearLayout.getWidth()+"width");
		Log.d("poding",orderFlightInfoLinearLayout.getWidth()+"childwidth");
//		orderFlightInfoLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,flightInfoLinearLayout.getWidth()));
//		Log.d("poding",orderFlightInfoLinearLayout.getWidth()+"childwidth");
//		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,flightInfoLinearLayout.getWidth());
//		flightInfoLinearLayout.addView(orderFlightInfoLinearLayout,param);
		flightInfoLinearLayout.addView(orderFlightInfoLinearLayout);
		Log.d("poding",orderFlightInfoLinearLayout.getWidth()+"childwidth");
    }
}
