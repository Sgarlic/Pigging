package com.boding.app;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AirportBusListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_airportbus_list);
		initView();
	}
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(AirportBusListActivity.this, IntentRequestCode.AIRPORTBUS_LIST);
			}
			
		});
		
	}
}
