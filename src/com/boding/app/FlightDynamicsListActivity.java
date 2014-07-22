package com.boding.app;


import com.boding.R;
import com.boding.adapter.FlightDynamicsAdapter;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FlightDynamicsListActivity extends Activity{
	private ListView flightDynamicsListView;
	private FlightDynamicsAdapter adapter;
	
	private WarningDialog warningDialog;
	private ProgressBarDialog progressBarDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flightdynamicslist);
		warningDialog = new WarningDialog(this);
		progressBarDialog = new ProgressBarDialog(this);
		initView();
	}
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(FlightDynamicsListActivity.this, IntentRequestCode.MY_FOLLOWED_FLIGHTDYNAMICS);
			}
			
		});
		
		flightDynamicsListView = (ListView) findViewById(R.id.myfollowedflightdynamics_listView);
	}
}
