package com.boding.app;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AirportTransportActivity extends Activity {
	private LinearLayout airportBusLinearLayout;
	private LinearLayout taxiLinearLayout;
	private LinearLayout metroLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_airport_transport);
		initView();
		
		addListeners();
	}
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(AirportTransportActivity.this, IntentRequestCode.AIRPORT_TRANSPORT);
			}
			
		});
		airportBusLinearLayout = (LinearLayout) findViewById(R.id.airporttransport_airportbus_linearLayout);
		taxiLinearLayout = (LinearLayout) findViewById(R.id.airporttransport_taxi_linearLayout);
		metroLinearLayout = (LinearLayout) findViewById(R.id.airporttransport_metro_linearLayout);
	}
    
    private void addListeners(){
    	airportBusLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AirportTransportActivity.this, AirportBusListActivity.class);
				startActivityForResult(intent, IntentRequestCode.AIRPORTBUS_LIST.getRequestCode());
			}
		});
    	taxiLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AirportTransportActivity.this, TaxiInfoActivity.class);
				startActivityForResult(intent, IntentRequestCode.TAXI_INFO.getRequestCode());
			}
		});
    	metroLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AirportTransportActivity.this, MetroActivity.class);
				startActivityForResult(intent, IntentRequestCode.METRO.getRequestCode());
			}
		});
    }
}
