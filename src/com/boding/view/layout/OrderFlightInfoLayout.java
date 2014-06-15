package com.boding.view.layout;

import com.boding.R;
import com.boding.constants.GlobalVariables;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderFlightInfoLayout extends LinearLayout{
	private Context context;
	
	private TextView flyFromTextView;
	private TextView flyToTextView;
	private TextView dateTextView;
	private ImageView companyLogoImageView;
	private TextView companyTextView;
	private TextView planeTypeTextView;
	private TextView fromDateDayTextView;
	private TextView fromDateTimeTextView;
	private TextView fromTerminalTextView;
	private TextView estimateTimeTextView;
	private TextView toDateDayTextView;
	private TextView toDateTimeTextView;
	private TextView toTerminalTextView;
	private LinearLayout seatBackChangeLinearLayout;
	private TextView changeSeatTextView;
	
	// if init complete
	private boolean flag = false;
	public OrderFlightInfoLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public OrderFlightInfoLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
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
		this.setOrientation(VERTICAL); //ˮƽ����  
		View view = LayoutInflater.from(context).inflate(R.layout.layout_order_flightinfo, null);
		Log.d("poding",this.getWidth()+"childwidthooo");
		flyFromTextView = (TextView)view.findViewById(R.id.flightinfo_from_textView);
		flyToTextView = (TextView)view.findViewById(R.id.flightinfo_to_textView);
		dateTextView = (TextView)view.findViewById(R.id.flightinfo_date_textView);
		companyLogoImageView = (ImageView)view.findViewById(R.id.flightinfo_companylogo_imageView);
		companyTextView = (TextView)view.findViewById(R.id.flightinfo_company_textView);
		planeTypeTextView = (TextView)view.findViewById(R.id.flightinfo_planetype_textView);
		fromDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_fromDateDay_textView);
		fromDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_fromDateTime_textView);
		fromTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_fromTerminal_textView);
		estimateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_estimateTime_textView);
		toDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_toDateDay_textView);
		toDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_toDateTime_textView);
		toTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_toTerminal_textView);
		seatBackChangeLinearLayout = (LinearLayout)view.findViewById(R.id.flightinfo_seatBackChangeInfo_linearLayout);
		changeSeatTextView = (TextView)view.findViewById(R.id.flightinfo_changeClass_textView);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
		this.addView(view,param);
	}
}
 