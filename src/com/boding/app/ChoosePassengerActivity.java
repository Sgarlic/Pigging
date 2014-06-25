package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Passenger;
import com.boding.util.Util;

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

public class ChoosePassengerActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout addPassengerLinearLayout;
	private ListView passengerListView;
	
	
	private PassengerAdapter peopleAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_passenger);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
	}
	
	private void initView(){
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
		
		List<Passenger> peopleList = new ArrayList<Passenger>();
		peopleList.add(new Passenger("李大嘴", "325478569852314569"));
		peopleList.add(new Passenger("李大232嘴", "256542d14589631452"));
		peopleList.add(new Passenger("李大嘴wew", "1225478965325468774"));
		peopleAdapter = new PassengerAdapter(this, peopleList);
		passengerListView.setAdapter(peopleAdapter);
		
		addListeenrs();
	}
	
	private void addListeenrs(){
		addPassengerLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ChoosePassengerActivity.this, AddPassengerInfoActivity.class);
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
			
			Passenger people = getItem(position);
            holder.nameTextView.setText(people.getName());
            holder.idNumberTextView.setText(people.getCardNumber());
            holder.choosePassengerLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (holder.choosePassengerCheckBox.isChecked())
						holder.choosePassengerCheckBox.setChecked(false);
					else
						holder.choosePassengerCheckBox.setChecked(true);
					
				}
			});
	        return convertView;  
		}
		
		private class ViewHolder {
			LinearLayout choosePassengerLinearLayout;
			CheckBox choosePassengerCheckBox;
			TextView nameTextView;
			TextView idNumberTextView;
			TextView idTypeTextView;
			LinearLayout editLinearLayout;
		}
	}
}
