package com.boding.view.layout;

import java.util.List;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.model.OrderFlight;
import com.boding.model.domestic.Flight;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.SeatChangeBackDialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderDetailFlightInfoLayout extends LinearLayout{
	private Context context;
	
	private TextView flyFromDateTextView;
	private TextView flyFromToCityTextView;
	private TextView flyCompanyTextView;
	private TextView planeTypeTextView;
	private TextView classTypeTextView;
	private TextView planeSizeTextView;
	private TextView seatInfoTextView;
	private TextView flyFromTimeTextView;
	private TextView flyFromTerminalTextView;
	private TextView flyToTimeTextView;
	private TextView flyToTerminalTextView;
	private LinearLayout changeRefundConditionLinearLayout;
	
	private OrderFlight flight;
	private String leaveCity;
	private String arriveCity;
	
	// if init complete
	private boolean flag = false;
	public OrderDetailFlightInfoLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public OrderDetailFlightInfoLayout(Context context, OrderFlight flightLine,
			String leaveCity, String arriveCity) {
		super(context);
		this.context = context;
		this.flight = flightLine;
		this.leaveCity = leaveCity;
		this.arriveCity = arriveCity;
	}
	
	public OrderDetailFlightInfoLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	private void setViewContent(){
		flyFromDateTextView.setText(flight.getFlight_date());
		flyFromToCityTextView.setText(leaveCity+" - "+arriveCity);
		flyCompanyTextView.setText(flight.getCarrier_name());
		planeTypeTextView.setText(flight.getCarrier()+flight.getFlight_num());
		classTypeTextView.setText(flight.getDiscount());
		seatInfoTextView.setText(flight.getPlanetype());
		flyFromTimeTextView.setText(flight.getLeave_time());
		flyFromTerminalTextView.setText(flight.getLeave_ariport()+flight.getLeaterminal());
		flyToTimeTextView.setText(flight.getArrive_time());
		flyToTerminalTextView.setText(flight.getArrive_ariport()+flight.getArrterminal());
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
	
	private void initView(){
		this.setOrientation(VERTICAL); //水平布局  
		this.setMinimumWidth(GlobalVariables.Screen_Width);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_orderdetail_flightinfo, null);
		LinearLayout orderFlightInfoLinearLayout = (LinearLayout) view.findViewById(R.id.orderdetail_flightinfo_linearLayout);
		orderFlightInfoLinearLayout.setMinimumWidth(GlobalVariables.Screen_Width);
		
		
		flyFromDateTextView = (TextView)view.findViewById(R.id.orderdetail_flyFromDate_textView);
		flyFromToCityTextView = (TextView)view.findViewById(R.id.orderdetail_flyFromToCity_textView);
		flyCompanyTextView = (TextView)view.findViewById(R.id.orderdetail_flyCompany_textView);
		planeTypeTextView = (TextView)view.findViewById(R.id.orderdetail_planeType_textView);
		classTypeTextView = (TextView)view.findViewById(R.id.orderdetail_classType_textView);
		planeSizeTextView = (TextView)view.findViewById(R.id.orderdetail_planeSize_textView);
		seatInfoTextView = (TextView)view.findViewById(R.id.orderdetail_seatInfo_textView);
		flyFromTimeTextView = (TextView)view.findViewById(R.id.orderdetail_flyFromTime_textView);
		flyFromTerminalTextView = (TextView)view.findViewById(R.id.orderdetail_flyFromTerminal_textView);
		flyToTimeTextView = (TextView)view.findViewById(R.id.orderdetail_flyToTime_textView);
		flyToTerminalTextView = (TextView)view.findViewById(R.id.orderdetail_flyToTerminal_textView);
		changeRefundConditionLinearLayout = (LinearLayout)view.findViewById(R.id.orderdetail_changeRefundCondition_linearLayout);
		
		this.addView(view);
		
		addListeners();
	}
	
	private void addListeners(){
		
	}
	
}
