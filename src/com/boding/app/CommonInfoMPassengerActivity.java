package com.boding.app;

import java.util.List;

import com.boding.R;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Passenger;
import com.boding.task.PassengerTask;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.NetworkUnavaiableDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CommonInfoMPassengerActivity extends BodingBaseActivity {
	private LinearLayout addPassengerLinearLayout;
	private TextView noPassengerTextView;
	private ListView passengerListView;
	
	private PassengerAdapter peopleAdapter;
	
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoninfomanagement_passenger);
		progressBarDialog = new ProgressBarDialog(this);
		networkUnavaiableDialog = new NetworkUnavaiableDialog(this);
		initView();
		viewContentSetting();
	}
	
	private void initView(){
		bundle = new Bundle();
		bundle.putBoolean(IntentExtraAttribute.IS_MANAGE_PASSENGER, true);
		
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
		if(!Util.isNetworkAvailable(CommonInfoMPassengerActivity.this)){
			networkUnavaiableDialog.show();
			return;
		}
		progressBarDialog.show();
		
		PassengerTask passengerTask = new PassengerTask(this, HTTPAction.GET_PASSENGERLIST_MANAGEMENT);
		passengerTask.execute();
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
				bundle.putBoolean(IntentExtraAttribute.IS_EDIT_PASSENGER, false);
				intent.putExtras(bundle);
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
		
		public void resetPassenger(Passenger passenger){
			for(int i = 0 ;i < passengerList.size(); i++){
				Passenger pass = passengerList.get(i);
				if (pass.getAuto_id().equals(passenger.getAuto_id())){
					passengerList.set(i, passenger);
				}
			}
			notifyDataSetChanged();
		}
		
		public void removePassenger(Passenger passenger){
			int passengerPos = -1;
			for(int i = 0;i<passengerList.size();i++){
				if(passengerList.get(i).getAuto_id().equals(passenger.getAuto_id())){
					passengerPos = i;
					break;
				}
			}
			if(passengerPos != -1)
				passengerList.remove(passengerPos);
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
			
			final Passenger people = getItem(position);
            holder.nameTextView.setText(people.getDiaplayName());
            holder.idTypeTextView.setText(people.getIdentityType().getIdentityName());
            holder.idNumberTextView.setText(people.getCardNumber());
            holder.editLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					bundle.putBoolean(IntentExtraAttribute.IS_EDIT_PASSENGER, true);
					bundle.putParcelable(IntentExtraAttribute.IS_EDIT_PASSENGER_PASSENGERINFO, people);
					intent.putExtras(bundle);
					intent.setClass(CommonInfoMPassengerActivity.this, AddPassengerInfoActivity.class);
					startActivityForResult(intent, IntentRequestCode.ADD_PASSENGERINFO.getRequestCode());
				}
			});
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView nameTextView;
			TextView idNumberTextView;
			TextView idTypeTextView;
			LinearLayout editLinearLayout;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		if(requestCode==IntentRequestCode.ADD_PASSENGERINFO.getRequestCode()){
			if(data.getExtras() == null)
				return;
			if(data.getExtras().containsKey(IntentExtraAttribute.ADD_PASSENGER_EXTRA)){
//				Passenger passenger = (Passenger) data.getExtras().get(IntentExtraAttribute.ADD_PASSENGER_EXTRA);
//				peopleAdapter.addPassenger(passenger);
				viewContentSetting();
			}
			if(data.getExtras().containsKey(IntentExtraAttribute.EDIT_PASSENGER_EXTRA)){
				Passenger passenger = (Passenger) data.getExtras().get(IntentExtraAttribute.EDIT_PASSENGER_EXTRA);
				peopleAdapter.resetPassenger(passenger);
			}
			if(data.getExtras().containsKey(IntentExtraAttribute.DELETE_PASSENGER_EXTRA)){
				Passenger passenger = (Passenger) data.getExtras().get(IntentExtraAttribute.DELETE_PASSENGER_EXTRA);
				peopleAdapter.removePassenger(passenger);
			}
		}
	}
}
