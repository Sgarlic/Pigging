package com.boding.view.layout;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.model.FlightLine;
import com.boding.model.Segment;
import com.boding.util.CityUtil;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.dialog.SeatChangeBackDialog;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderFlightInfoISegmentLayout extends LinearLayout{
	private Context context;
	
	private ImageView fromCompanyLogoImageView;
	private TextView fromCompanyTextView;
	private TextView fromPlaneCodeTextView;
	private TextView fromPlaneSizeTextView;
	private TextView fromFlightClassTextView;
	private TextView fromPlaneTypeTextView;
	private TextView fromDateDayTextView;
	private TextView fromDateTimeTextView;
	private TextView fromTerminalTextView;
	private TextView arrivalDateDayTextView;
	private TextView arrivalDateTimeTextView;
	private TextView arrivalTerminalTextView;
	private TextView fromEstimateTimeTextView;
	private TextView transitCityTextView;
	private TextView transitEstimateTimeTextView;
	
	private Segment segment;
	
	private String transitEstimateTime;
	
	// if init complete
	private boolean flag = false;
	public OrderFlightInfoISegmentLayout(Context context, Segment segment,
			String transitEstimateTime) {
		super(context);
		this.context = context;
		this.segment = segment;
		this.transitEstimateTime = transitEstimateTime;
	}
	
	public OrderFlightInfoISegmentLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		while(!flag){
			initView();
			setViewContent();
			flag = true;
		}
	}
	
	
	private void setViewContent(){
		fromCompanyLogoImageView.setImageBitmap(Util.getFlightCompanyLogo(context, segment.getCarrier()));
		fromCompanyTextView.setText(segment.getCarname());
		fromPlaneCodeTextView.setText(segment.getCarrier() + segment.getNum());
		fromPlaneSizeTextView.setText(segment.getPlane());
//		fromFlightClassTextView.setText(segment.getFclasslist().g);
		fromPlaneTypeTextView.setText(segment.getPlane());
		fromDateDayTextView.setText(segment.getLeadate());
		fromDateTimeTextView.setText(DateUtil.getFormatedTime(segment.getLeatime()));
		fromTerminalTextView.setText(segment.getLeaTerminal());
		arrivalDateDayTextView.setText(segment.getArrdate());
		arrivalDateTimeTextView.setText(DateUtil.getFormatedTime(segment.getArrtime()));
		arrivalTerminalTextView.setText(segment.getArrTerminal());
		fromEstimateTimeTextView.setText(segment.getEstimatedTime());
		transitCityTextView.setText(CityUtil.getCityNameByCode(segment.getArrcode()));
		transitEstimateTimeTextView.setText(transitEstimateTime);
	}
	
	private void initView(){
		this.setOrientation(VERTICAL); //水平布局  
		this.setMinimumWidth(GlobalVariables.Screen_Width);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_order_flightinfo_i_segment, null);
		LinearLayout orderFlightInfoILinearLayout = (LinearLayout) view.findViewById(R.id.order_flightinfo_i_segment_linearLayout);
		orderFlightInfoILinearLayout.setMinimumWidth(GlobalVariables.Screen_Width);
		
		
		fromCompanyLogoImageView = (ImageView)view.findViewById(R.id.flightinfo_i_segment_fromCompanyLogo_imageView);
		fromCompanyTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromCompany_textView);
		fromPlaneCodeTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromPlanecode_textView);
		fromPlaneSizeTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromPlanesize_textView);
		fromFlightClassTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromFlightclass_textView);
		fromPlaneTypeTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromPlaneType_textView);
		fromDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromDateDay_textView);
		fromDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromDateTime_textView);
		fromTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromTerminal_textView);
		arrivalDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_arrivalDateDay_textView);
		arrivalDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_arrivalDateTime_textView);
		arrivalTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_arrivalTerminal_textView);
		fromEstimateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_fromEstimateTime_textView);
		transitCityTextView = (TextView)view.findViewById(R.id.flightinfo_i_segment_tansitCity_textView);
		transitEstimateTimeTextView = (TextView) view.findViewById(R.id.flightinfo_i_segment_tansitEstimateStayTime_textView);
		
		this.addView(view);
	}
}
 