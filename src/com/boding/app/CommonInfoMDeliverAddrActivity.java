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

public class CommonInfoMDeliverAddrActivity extends Activity {
	private LinearLayout addAddrLinearLayout;
	private TextView noDeliverAddrTextView;
	private ListView deliveryAddrListView;
	
	
	private DeliveryAddressAdapter addressAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoninfomanagement_deliveryaddress);
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
				Util.returnToPreviousPage(CommonInfoMDeliverAddrActivity.this, IntentRequestCode.COMMON_INFO_M_DELIVERADDR);
			}
			
		});
		
		addAddrLinearLayout = (LinearLayout) findViewById(R.id.commoninfomanagement_deliveryAddr_addAddr_linearLayout);
		noDeliverAddrTextView = (TextView) findViewById(R.id.commoninfomanagement_deliveryAddr_noDeliverAddr_textView);
		deliveryAddrListView = (ListView) findViewById(R.id.choose_deliveryaddress_deliveryAddresses_listView);
		
		List<DeliveryAddress> addressList = new ArrayList<DeliveryAddress>();
//		addressList.add(new DeliveryAddress("李大嘴","上海市","宝山区七宝","200000"));
//		addressList.add(new DeliveryAddress("李大嘴1","上海市1","宝山区七宝1","202110"));
//		addressList.add(new DeliveryAddress("李大嘴2","上海市2","宝山区七宝2","203300"));
//		addressAdapter = new DeliveryAddressAdapter(this, addressList);
//		deliveryAddrListView.setAdapter(addressAdapter);
		
		addListeners();
	}
	
	private void viewContentSetting(){
	}

	private void addListeners(){
		addAddrLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CommonInfoMDeliverAddrActivity.this, AddDeliveryAddrActivity.class);
				startActivityForResult(intent,IntentRequestCode.ADD_DELIVERYADDR.getRequestCode());
			}
		});
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
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_commoninfom_deliveraddr, null);
	            holder = new ViewHolder();  
	            
	            holder.recipientNameTextView = (TextView) convertView.findViewById(R.id.commonInfoMDeliverAddr_recipientName_textView);
	            holder.addrTextView = (TextView) convertView.findViewById(R.id.commonInfoMDeliverAddr_address_textView);
	            holder.zipcodeTextView = (TextView) convertView.findViewById(R.id.commonInfoMDeliverAddr_zipcode_textView);
	            holder.editLinearLayout = (LinearLayout) convertView.findViewById(R.id.commonInfoMDeliverAddr_edit_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			DeliveryAddress addr = getItem(position);
            holder.recipientNameTextView.setText(addr.getRecipientName());
            holder.addrTextView.setText(addr.getDisplayAddr());
            holder.zipcodeTextView.setText(addr.getZipcode());
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView recipientNameTextView;
			TextView addrTextView;
			TextView zipcodeTextView;
			LinearLayout editLinearLayout;
		}
	}
}
