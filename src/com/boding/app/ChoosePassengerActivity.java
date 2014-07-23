package com.boding.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Passenger;
import com.boding.task.PassengerTask;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ChoosePassengerActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout addPassengerLinearLayout;
	private ListView passengerListView;
	
	private Set<String> selectedPassengerIds;
	
	private ProgressBarDialog progressBarDialog;
	
	private PassengerAdapter peopleAdapter;
	
	private Bundle bundle;
	
	private boolean isInternational = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_passenger);
		selectedPassengerIds = new HashSet<String>();
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	if(arguments.containsKey(IntentExtraAttribute.CHOOSED_PASSENGERS_EXTRA)){
	        	ArrayList<Passenger> selectedPassengers = arguments.getParcelableArrayList(IntentExtraAttribute.CHOOSED_PASSENGERS_EXTRA);
	        	for(Passenger passenger : selectedPassengers)
	        		selectedPassengerIds.add(passenger.getAuto_id());
        	}
        	if(arguments.containsKey(IntentExtraAttribute.IS_INTERNATIONAL_CHOOSEPASSENGER)){
        		isInternational = arguments.getBoolean(IntentExtraAttribute.IS_INTERNATIONAL_CHOOSEPASSENGER);
        	}
        }
		initView();
		viewContentSetting();
		System.out.println(isInternational + "ddddddddddddddddddddddddddddd");
	}
	
	private void initView(){
		bundle = new Bundle();
		bundle.putBoolean(IntentExtraAttribute.IS_MANAGE_PASSENGER, false);
		
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(ChoosePassengerActivity.this, IntentRequestCode.CHOOSE_PASSENGER);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.choose_passenger_complete_linearLayout);
		addPassengerLinearLayout = (LinearLayout) findViewById(R.id.choose_passenger_addPassenger_linearLayout);
		passengerListView = (ListView) findViewById(R.id.choose_passenger_passenger_listView);
		
		addListeenrs();
	}
	
	
	private void viewContentSetting(){
		progressBarDialog = new ProgressBarDialog(this);
		progressBarDialog.show();
		
		PassengerTask passengerTask = new PassengerTask(this, HTTPAction.GET_PASSENGERLIST);
		passengerTask.execute();
	}
	
	public void setPassengerList(List<Passenger> passengerList){
		peopleAdapter = new PassengerAdapter(this, passengerList);
		passengerListView.setAdapter(peopleAdapter);
		progressBarDialog.dismiss();
	}
	
	private void addListeenrs(){
		addPassengerLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				bundle.putBoolean(IntentExtraAttribute.IS_EDIT_PASSENGER, false);
				intent.putExtras(bundle);
				intent.setClass(ChoosePassengerActivity.this, AddPassengerInfoActivity.class);
				startActivityForResult(intent, IntentRequestCode.ADD_PASSENGERINFO.getRequestCode());
			}
		});
		
		completeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(selectedPassengerIds.size() > 9){
            		WarningDialog dialog = new WarningDialog(ChoosePassengerActivity.this);
            		dialog.setContent("最多只能选择9位乘客");
            		dialog.show();
            		return;
            	}
				Intent intent=new Intent();
				ArrayList<Passenger> selectedPassengerList = new ArrayList<Passenger>();
				for(int i=0;i<peopleAdapter.getCount();i++){
					Passenger passenger = peopleAdapter.getItem(i);
					if(selectedPassengerIds.contains(passenger.getAuto_id())){
						selectedPassengerList.add(passenger);
					}
				}
				intent.putParcelableArrayListExtra(IntentExtraAttribute.CHOOSED_PASSENGERS_EXTRA, selectedPassengerList);
				setResult(IntentRequestCode.CHOOSE_PASSENGER.getRequestCode(), intent);
				finish();
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
		
		public void addPassenger(Passenger passenger){
			passengerList.add(passenger);
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
			
			selectedPassengerIds.remove(passenger.getAuto_id());
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_choosepassenger, null);
	            holder = new ViewHolder();  
	            
	            holder.choosePassengerLinearLayout = (LinearLayout) convertView.findViewById(R.id.choosepassenger_choose_linearLayout);
	            holder.choosePassengerCheckBox = (CheckBox) convertView.findViewById(R.id.choosepassenger_choose_checkBox);
	            holder.nameTextView = (TextView) convertView.findViewById(R.id.choosepassenger_name_textView);
	            holder.idNumberTextView = (TextView) convertView.findViewById(R.id.choosepassenger_idnumber_textView);
	            holder.idTypeTextView = (TextView) convertView.findViewById(R.id.choosepassenger_idType_textView);
	            holder.editLinearLayout = (LinearLayout) convertView.findViewById(R.id.choosepassenger_edit_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			final Passenger people = getItem(position);
			holder.passengerId = people.getAuto_id();
			
			if(selectedPassengerIds.contains(holder.passengerId))
				holder.choosePassengerCheckBox.setChecked(true);
			else
				holder.choosePassengerCheckBox.setChecked(false);
			
			
			holder.nameTextView.setText(people.getDiaplayName());
            holder.idTypeTextView.setText(people.getIdentityType().getIdentityName());
            holder.idNumberTextView.setText(people.getCardNumber());
            holder.choosePassengerLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (holder.choosePassengerCheckBox.isChecked()){
						holder.choosePassengerCheckBox.setChecked(false);
					}
					else{
						holder.choosePassengerCheckBox.setChecked(true);
					}
				}
			});
            holder.editLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					bundle.putBoolean(IntentExtraAttribute.IS_EDIT_PASSENGER, true);
					bundle.putParcelable(IntentExtraAttribute.IS_EDIT_PASSENGER_PASSENGERINFO, people);
					intent.putExtras(bundle);
					intent.setClass(ChoosePassengerActivity.this, AddPassengerInfoActivity.class);
					startActivityForResult(intent, IntentRequestCode.ADD_PASSENGERINFO.getRequestCode());
				}
			});
            holder.choosePassengerCheckBox.setOnCheckedChangeListener(new OncheckchangeListner(holder,people));
	        return convertView;  
		}
		
		class OncheckchangeListner implements OnCheckedChangeListener{

            ViewHolder viewHolder = null; 
            Passenger people;
            public OncheckchangeListner(ViewHolder viHolder, Passenger people)
            {
                viewHolder =  viHolder;  
                this.people = people;
            }
            @Override 
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {

                if(viewHolder.choosePassengerCheckBox.equals(buttonView))
                {       
	                if(!isChecked)
	                {
	                	selectedPassengerIds.remove(viewHolder.passengerId);
	                }
	                else{
	                	System.out.println(isInternational + "ddssssssssss");
						System.out.println(people.isDomestic() + "ddssssssssss");
						
						if(isInternational && people.isDomestic()){
							WarningDialog warningDialog = new WarningDialog(ChoosePassengerActivity.this);
							warningDialog.setContent("预定国际机票请使用英文姓名");
							warningDialog.show();
							viewHolder.choosePassengerCheckBox.setChecked(false);
							return;
						}
	                	selectedPassengerIds.add(viewHolder.passengerId);
	                }
            	}
            }

        }
		
		private class ViewHolder {
			LinearLayout choosePassengerLinearLayout;
			CheckBox choosePassengerCheckBox;
			TextView nameTextView;
			TextView idNumberTextView;
			TextView idTypeTextView;
			LinearLayout editLinearLayout;
			String passengerId;
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
//			selectedPassengerIds = new HashSet<String>();
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
