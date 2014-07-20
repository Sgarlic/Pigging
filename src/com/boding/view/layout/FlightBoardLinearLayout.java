package com.boding.view.layout;


import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.model.FlightDynamics;
import com.boding.model.OrderFlight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlightBoardLinearLayout extends LinearLayout{
	private Context context;
	
	private ImageView topLineImageView;
	private LinearLayout searchLinearLayout;
	private TextView flightCompanyNumTextView;
	private TextView fromCityTextView;
	private TextView fromCityWeatherTextView;
	private ImageView fromCityWeatherImageView;
	private TextView fromTerminalTextView;
	private TextView planeFromTimeTextView;
	private TextView actualFromTimeTextView;
	private TextView onTimeRateTextView;
	private TextView toCityTextView;
	private TextView toCityWeatherTextView;
	private ImageView toCityWeatherImageView;
	private TextView toTerminalTextView;
	private TextView planToTimeTextView;
	private TextView actualToTimeTextView;
	private ImageView flightStatusImageView;
	private TextView fromAirportInfoTextView;
	private LinearLayout fromAirportInfoLinearLayout;
	private TextView toAirportInfoTextView;
	private LinearLayout toAirportInfoLinearLayout;
	private LinearLayout followLinearLayout;
	private TextView followTextView;
	private ImageView pageIndicatorImageView;
	
	
	private FlightDynamics flightDynamics;
	
	// if init complete
	private boolean flag = false;
	
	public FlightBoardLinearLayout(Context context, FlightDynamics flightDynamics) {
		super(context);
		this.context = context;
		this.flightDynamics = flightDynamics;
	}
	
	private void setViewContent(){
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
		View view = LayoutInflater.from(context).inflate(R.layout.layout_flightboard, null);
		LinearLayout flightBoardLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_container_linearLayout);
		flightBoardLinearLayout.setMinimumWidth(GlobalVariables.Screen_Width);
		
		
		
		this.addView(view);
		
		addListeners();
	}
	
	private void addListeners(){
	}
	
}
