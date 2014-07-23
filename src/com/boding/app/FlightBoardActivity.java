package com.boding.app;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.City;
import com.boding.model.FlightDynamics;
import com.boding.task.FlightDynamicsTask;
import com.boding.util.CityUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlightBoardActivity extends Activity {
	private FlightDynamics dynamics;
	
	private TextView planeInfoTextView;
	private TextView dateTextView;
	private TextView fromCityTextView;
	private TextView fromCityWeatherTextView;
	private ImageView fromCityWeatherImageView;
	private TextView fromTerminalTextView;
	private TextView planeFromTimeTextView;
	private TextView actualFromTimeTextView;
	private TextView onTimeRateTextTextView;
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
	private LinearLayout fromDividerLinearLayout;
	private LinearLayout toDividerLinearLayout;
	
	private WarningDialog warningDialog;
	private ProgressBarDialog progressBarDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flightboard);
		warningDialog = new WarningDialog(this);
		progressBarDialog = new ProgressBarDialog(this);
		
		Bundle arguments = getIntent().getExtras();
		dynamics = arguments.getParcelable(IntentExtraAttribute.FLIGHT_DYNAMIC);
		initView();
		setViewContent();
	}
	
	private void addListeners(){
		followLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if(GlobalVariables.bodingUser == null){
					intent.setClass(FlightBoardActivity.this, LoginActivity.class);
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}else if(!GlobalVariables.bodingUser.isActivated_state()){
					intent.setClass(FlightBoardActivity.this, VerifyPhonenumActivity.class);
					intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "2");
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}
				
				if(GlobalVariables.myFollowedFdList.size() == 5){
					Util.showToast(FlightBoardActivity.this, "最多只能关注5个航班");
					return;
				}
				
				progressBarDialog.show();
				if(dynamics.isFollowed()){
					if(dynamics.getId() == null || dynamics.getId().equals("")){
						Util.showToast(FlightBoardActivity.this, "取消关注失败");
					}
					(new FlightDynamicsTask(FlightBoardActivity.this, HTTPAction.UNFOLLOW_FLIGHTDYNAMICS))
					.execute(dynamics.getId());
				}else{
					(new FlightDynamicsTask(FlightBoardActivity.this, HTTPAction.FOLLOW_FLIGHTDYNAMICS))
					.execute(dynamics);
				}
			}
		});
	}
	
	public void setFollowFlightResult(boolean isSuccess){
		progressBarDialog.dismiss();
		if(isSuccess){
			dynamics.setFollowed(true);
			int index = GlobalVariables.myFollowedFdList.indexOf(dynamics);
			if(index != -1){
				dynamics = GlobalVariables.myFollowedFdList.get(index);
			}
		}else{
			Util.showToast(this, "关注航班失败");
		}
	}
	public void setUnFollowFlightResult(boolean isSuccess){
		progressBarDialog.dismiss();
		if(isSuccess){
			dynamics.setFollowed(false);
			dynamics.setId("");
		}else{
			Util.showToast(this, "取消关注失败");
		}
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
		onTimeRateTextTextView = (TextView) findViewById(R.id.flightboard_ontimeRateText_textView);
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
		fromDividerLinearLayout = (LinearLayout) findViewById(R.id.flightboard_fromDivider_linearLayout);
		toDividerLinearLayout = (LinearLayout) findViewById(R.id.flightboard_toDivider_linearLayout);
		
		addListeners();
	}
    
    private void setViewContent(){
    	planeInfoTextView.setText(dynamics.getCar_name() + dynamics.getCarrier() + dynamics.getNum());
    	dateTextView.setText(dynamics.getDate());
    	fromCityTextView.setText(CityUtil.getCityNameByCode(dynamics.getDep_airport_code()));
    	if (dynamics.getDep_weather() == null){
			fromCityWeatherTextView.setVisibility(View.GONE);
			fromCityWeatherImageView.setVisibility(View.GONE);
		}else{
			fromCityWeatherTextView.setText(dynamics.getDep_weather().getWeatherName());
			fromCityWeatherImageView.setImageResource(dynamics.getDep_weather().getWeatherDrawable());
		}
		int color = this.getResources().getColor(dynamics.getFlightStatus().getFlightStatusColor());
		fromDividerLinearLayout.setBackgroundColor(color);
		toDividerLinearLayout.setBackgroundColor(color);
		
		fromTerminalTextView.setText(dynamics.getDep_airport_name() + dynamics.getDep_terminal());
		planeFromTimeTextView.setText(dynamics.getPlan_dep_time());
		if(!dynamics.getActual_dep_time().equals(""))
			actualFromTimeTextView.setText(dynamics.getActual_dep_time());
		onTimeRateTextView.setText(dynamics.getPunctuality());
		onTimeRateTextTextView.setTextColor(color);
		onTimeRateTextView.setTextColor(color);
		toCityTextView.setText(CityUtil.getCityNameByCode(dynamics.getArr_airport_code()));
		if(dynamics.getArr_weather() == null){
			toCityWeatherTextView.setVisibility(View.GONE);
			toCityWeatherImageView.setVisibility(View.GONE);
		}else{
			toCityWeatherTextView.setText(dynamics.getArr_weather().getWeatherName());
			toCityWeatherImageView.setImageResource(dynamics.getArr_weather().getWeatherDrawable());
		}
		toTerminalTextView.setText(dynamics.getArr_airport_name() + dynamics.getArr_terminal());
		planToTimeTextView.setText(dynamics.getPlan_arr_time());
		if(!dynamics.getActual_arr_time().equals(""))
			actualToTimeTextView.setText(dynamics.getActual_arr_time());
		fromAirportInfoTextView.setText(dynamics.getDep_airport_name());
		toAirportInfoTextView.setText(dynamics.getArr_airport_name());
		flightStatusImageView.setImageResource(dynamics.getFlightStatus().getFlightBoardDrawable());
		
		if(dynamics.isFollowed()){
			followTextView.setText("取消关注");
			followLinearLayout.setBackgroundColor(this.getResources().getColor(R.color.priceGray));
		}else{
			followTextView.setText("关注航班");
			followLinearLayout.setBackgroundColor(this.getResources().getColor(R.color.textBlue));
		}
    }
}
