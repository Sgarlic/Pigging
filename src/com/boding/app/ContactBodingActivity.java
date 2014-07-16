package com.boding.app;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactBodingActivity extends Activity {
	private TextView phoneNumTextView;
	private LinearLayout contactPhoneLinearLayout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactboding);
		initView();
		setViewContent();
	}
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(ContactBodingActivity.this, IntentRequestCode.CONTACT_BODING);
			}
			
		});
		
		phoneNumTextView = (TextView) findViewById(R.id.contactboding_contactphone_textView);
		contactPhoneLinearLayout = (LinearLayout) findViewById(R.id.contactboding_contactphone_linearLayout);
		
		addListeners();
	}
    
    private void setViewContent(){
    	phoneNumTextView.setText(GlobalVariables.contact_boding_phonenum);
    }
    
    private void addListeners(){
    	contactPhoneLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String number = GlobalVariables.contact_boding_phonenum;
			    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" +number));
			    startActivity(intent);
			}
		});
    }
}
