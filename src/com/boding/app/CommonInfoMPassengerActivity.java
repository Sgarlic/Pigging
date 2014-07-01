package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentRequestCode;
import com.boding.http.HttpConnector;
import com.boding.model.Passenger;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CommonInfoMPassengerActivity extends Activity {
	private LinearLayout addPassengerLinearLayout;
	private TextView noPassengerTextView;
	private ListView passengerListView;
	
	private ProgressBarDialog progressBarDialog;
	
	
	private PassengerAdapter peopleAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoninfomanagement_passenger);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
		viewContentSetting();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(CommonInfoMPassengerActivity.this, IntentRequestCode.COMMON_INFO_M_PASSENGER);
			}
			
		});
		
		addPassengerLinearLayout = (LinearLayout) findViewById(R.id.commoninfomanagement_passenger_addPassenger_linearLayout);
		noPassengerTextView = (TextView) findViewById(R.id.commoninfomanagement_passenger_noPassenger_textView);
		passengerListView = (ListView) findViewById(R.id.commoninfomanagement_passenger_passenger_listView);
		
		addListeners();
	}
	
	private void viewContentSetting(){
		progressBarDialog = new ProgressBarDialog(this, Constants.DIALOG_STYLE);
		progressBarDialog.show();
		
		HttpConnector httpConnector = new HttpConnector(this, HTTPAction.GET_PASSENGERLIST_MANAGEMENT);
		httpConnector.execute();
	}
	
	private void refreshView(){
		if(peopleAdapter.getCount() > 0)
			noPassengerTextView.setVisibility(View.GONE);
		else
			noPassengerTextView.setVisibility(View.VISIBLE);
	}
	
	public void setPassengerList(List<Passenger> passengerList){
		peopleAdapter = new PassengerAdapter(this, passengerList);
		passengerListView.setAdapter(peopleAdapter);
		refreshView();
		progressBarDialog.dismiss();
	}
	
	private void addListeners(){
		addPassengerLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CommonInfoMPassengerActivity.this, AddPassengerInfoActivity.class);
				startActivityForResult(intent, IntentRequestCode.ADD_PASSENGERINFO.getRequestCode());
			}
		});
	}
	
	private class PassengerAdapter extends BaseAdapter {
		private List<Passenger> passengerList;
		private Context context;
		public PassengerAdapter(Context context, List<Passenger> passengerList) {
			this.context = context;
			this.passengerList = passengerList;
		}
		@Override
		public int getCount() {
			return passengerList.size();
		}

		@Override
		public Passenger getItem(int position) {
			return passengerList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void addPassenger(Passenger passenger){
			passengerList.add(passenger);
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_commoninfom_passenger, null);
	            holder = new ViewHolder();  
	            
	            holder.nameTextView = (TextView) convertView.findViewById(R.id.commoninfom_passenger_name_textView);
	            holder.idNumberTextView = (TextView) convertView.findViewById(R.id.commoninfom_passenger_idnumber_textView);
	            holder.idTypeTextView = (TextView) convertView.findViewById(R.id.commoninfom_passenger_idType_textView);
	            holder.editLinearLayout = (LinearLayout) convertView.findViewById(R.id.commoninfom_passenger_edit_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			Passenger people = getItem(position);
            holder.nameTextView.setText(people.getName());
            holder.idTypeTextView.setText(people.getIdentityType().getIdentityName());
            holder.idNumberTextView.setText(people.getCardNumber());
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView nameTextView;
			TextView idNumberTextView;
			TextView idTypeTextView;
			LinearLayout editLinearLayout;
		}
	}
}