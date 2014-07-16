package com.boding.app;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlightBoardActivity extends Activity {
	private TextView planeInfoTextView;
	private TextView dateTextView;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flightboard);
		initView();
	}
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(FlightBoardActivity.this, IntentRequestCode.ABOUT_BODING);
			}
			
		});
	
		planeInfoTextView = (TextView) findViewById(R.id.flightboard_planeInfo_textView);
		dateTextView = (TextView) findViewById(R.id.flightboard_date_textView);
		fromCityTextView = (TextView) findViewById(R.id.flightboard_fromCity_textView);
		fromCityWeatherTextView = (TextView) findViewById(R.id.flightboard_fromCityWeather_textView);
		fromCityWeatherImageView = (ImageView) findViewById(R.id.flightboard_fromCityWeather_imageView);
		fromTerminalTextView = (TextView) findViewById(R.id.flightboard_fromTerminal_textView);
		planeFromTimeTextView = (TextView) findViewById(R.id.flightboard_planFromTime_textView);
		actualFromTimeTextView = (TextView) findViewById(R.id.flightboard_actualFromTime_textView);
		onTimeRateTextView = (TextView) findViewById(R.id.flightboard_ontimeRate_textView);
		toCityTextView = (TextView) findViewById(R.id.flightboard_toCity_textView);
		toCityWeatherTextView = (TextView) findViewById(R.id.flightboard_toCityWeather_textView);
		toCityWeatherImageView = (ImageView) findViewById(R.id.flightboard_toCityWeather_imageView);
		toTerminalTextView = (TextView) findViewById(R.id.flightboard_toTerminal_textView);
		planToTimeTextView = (TextView) findViewById(R.id.flightboard_planToTime_textView);
		actualToTimeTextView = (TextView) findViewById(R.id.flightboard_actualToTime_textView);
		flightStatusImageView = (ImageView) findViewById(R.id.flightboard_flightStatus_imageView);
		fromAirportInfoTextView = (TextView) findViewById(R.id.flightboard_fromAirportInfo_textView);
		fromAirportInfoLinearLayout = (LinearLayout) findViewById(R.id.flightboard_fromAirportInfo_linearLayout);
		toAirportInfoTextView = (TextView) findViewById(R.id.flightboard_toAirportInfo_textView);
		toAirportInfoLinearLayout = (LinearLayout) findViewById(R.id.flightboard_toAirportInfo_linearLayout);
		followLinearLayout = (LinearLayout) findViewById(R.id.flightboard_follow_linearLayout);
		followTextView = (TextView) findViewById(R.id.flightboard_follow_textView);
	}
}
