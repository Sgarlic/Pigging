package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.model.DeliveryAddress;
import com.boding.model.Passenger;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Context;
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

public class ChooseDeliveryAddressActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout addNewAddrLinearLayout;
	private ListView deliveryAddrListView;
	
	
	private DeliveryAddressAdapter addressAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_deliveryaddress);
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
				Util.returnToPreviousPage(ChooseDeliveryAddressActivity.this, IntentRequestCode.START_CHOOSE_DELIVERYADDR);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.choose_deliveryaddress_complete_linearLayout);
		addNewAddrLinearLayout = (LinearLayout) findViewById(R.id.choose_deliveryaddress_addNewAddress_linearLayout);
		deliveryAddrListView = (ListView) findViewById(R.id.choose_deliveryaddress_deliveryAddresses_listView);
		
		List<DeliveryAddress> addressList = new ArrayList<DeliveryAddress>();
		addressList.add(new DeliveryAddress("李大嘴","上海市","宝山区七宝","200000"));
		addressList.add(new DeliveryAddress("李大嘴1","上海市1","宝山区七宝1","202110"));
		addressList.add(new DeliveryAddress("李大嘴2","上海市2","宝山区七宝2","203300"));
		addressAdapter = new DeliveryAddressAdapter(this, addressList);
		deliveryAddrListView.setAdapter(addressAdapter);
	}
	
	private class DeliveryAddressAdapter extends BaseAdapter {
		private List<DeliveryAddress> addressList;
		private Context context;
		public DeliveryAddressAdapter(Context context, List<DeliveryAddress> addressList) {
			this.context = context;
			this.addressList = addressList;
		}
		@Override
		public int getCount() {
			return addressList.size();
		}

		@Override
		public DeliveryAddress getItem(int position) {
			return addressList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void addNewAddr(DeliveryAddress addr){
			addressList.add(addr);
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_choosedeliveryaddress, null);
	            holder = new ViewHolder();  
	            
	            holder.chooseAddrLinearLayout = (LinearLayout) convertView.findViewById(R.id.choosedeliveryaddress_choose_linearLayout);
	            holder.chooseAddrCheckBox = (CheckBox) convertView.findViewById(R.id.choosedeliveryaddress_choose_checkBox);
	            holder.recipientNameTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_recipientName_textView);
	            holder.addrTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_address_textView);
	            holder.zipcodeTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_zipcode_textView);
	            holder.editLinearLayout = (LinearLayout) convertView.findViewById(R.id.choosedeliveryaddress_edit_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			DeliveryAddress addr = getItem(position);
            holder.recipientNameTextView.setText(addr.getRecipientName());
            holder.addrTextView.setText(addr.getArea()+addr.getDetailedAddr());
            holder.zipcodeTextView.setText(addr.getZipcode());
            holder.chooseAddrLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (holder.chooseAddrCheckBox.isChecked())
						holder.chooseAddrCheckBox.setChecked(false);
					else
						holder.chooseAddrCheckBox.setChecked(true);
					
				}
			});
	        return convertView;  
		}
		
		private class ViewHolder {
			LinearLayout chooseAddrLinearLayout;
			CheckBox chooseAddrCheckBox;
			TextView recipientNameTextView;
			TextView addrTextView;
			TextView zipcodeTextView;
			LinearLayout editLinearLayout;
		}
	}
}
