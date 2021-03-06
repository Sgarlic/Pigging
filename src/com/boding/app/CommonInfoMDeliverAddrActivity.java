package com.boding.app;

import java.util.List;

import com.boding.R;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.DeliveryAddress;
import com.boding.task.DeliveryAddrTask;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CommonInfoMDeliverAddrActivity extends BodingBaseActivity {
	private LinearLayout addAddrLinearLayout;
	private TextView noDeliverAddrTextView;
	private ListView deliveryAddrListView;
	
	private Bundle bundle;
	
	private DeliveryAddressAdapter addressAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoninfomanagement_deliveryaddress);
		progressBarDialog = new ProgressBarDialog(this);
		networkUnavaiableDialog = new NetworkUnavaiableDialog(this);
        
		initView();
		
		viewContentSetting();
	}
	
	private void initView(){
		bundle = new Bundle();
		bundle.putBoolean(IntentExtraAttribute.IS_MANAGE_DELIVERYADDR, true);
		
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(CommonInfoMDeliverAddrActivity.this, IntentRequestCode.COMMON_INFO_M_DELIVERADDR);
			}
			
		});
		
		addAddrLinearLayout = (LinearLayout) findViewById(R.id.commoninfomanagement_deliveryAddr_addAddr_linearLayout);
		noDeliverAddrTextView = (TextView) findViewById(R.id.commoninfomanagement_deliveryAddr_noDeliverAddr_textView);
		deliveryAddrListView = (ListView) findViewById(R.id.commoninfomanagement_deliveryAddr_deliveryAddresses_listView);
		
		addListeners();
	}
	
	private void viewContentSetting(){
		if(!Util.isNetworkAvailable(CommonInfoMDeliverAddrActivity.this)){
			networkUnavaiableDialog.show();
			return;
		}
		progressBarDialog.show();
		
		DeliveryAddrTask deliverAddrTask = new DeliveryAddrTask(this, HTTPAction.GET_DELIVERYADDRLIST_MANAGEMENT);
		deliverAddrTask.execute();
	}
	
	private void refreshView(){
		if(addressAdapter.getCount() > 0)
			noDeliverAddrTextView.setVisibility(View.GONE);
		else
			noDeliverAddrTextView.setVisibility(View.VISIBLE);
	}
	
	public void setDeliveryAddrList(List<DeliveryAddress> deliverAddrList){
		addressAdapter = new DeliveryAddressAdapter(this, deliverAddrList);
		deliveryAddrListView.setAdapter(addressAdapter);
		refreshView();
		progressBarDialog.dismiss();
	}
	private void addListeners(){
		addAddrLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				bundle.putBoolean(IntentExtraAttribute.IS_EDIT_DELIVERYADDR, false);
				intent.putExtras(bundle);
				intent.setClass(CommonInfoMDeliverAddrActivity.this, AddDeliveryAddrActivity.class);
				startActivityForResult(intent,IntentRequestCode.ADD_DELIVERYADDR.getRequestCode());
			}
		});
		
		deliveryAddrListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				bundle.putBoolean(IntentExtraAttribute.IS_EDIT_DELIVERYADDR, true);
				bundle.putParcelable(IntentExtraAttribute.IS_EDIT_DELIVERYADDR_ADDRINFO, addressAdapter.getItem(position));
				intent.putExtras(bundle);
				intent.setClass(CommonInfoMDeliverAddrActivity.this, AddDeliveryAddrActivity.class);
				startActivityForResult(intent, IntentRequestCode.ADD_DELIVERYADDR.getRequestCode());				
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
		
		public void resetAddr(DeliveryAddress deliverAddr){
			for(int i = 0 ;i < addressList.size(); i++){
				DeliveryAddress addr = addressList.get(i);
				if (addr.getAddrID().equals(deliverAddr.getAddrID())){
					addressList.set(i, deliverAddr);
				}
			}
			notifyDataSetChanged();
		}

		public void removeAddr(DeliveryAddress deliverAddr){
			int passengerPos = -1;
			for(int i = 0;i<addressList.size();i++){
				if(addressList.get(i).getAddrID().equals(deliverAddr.getAddrID())){
					passengerPos = i;
					break;
				}
			}
			if(passengerPos != -1)
				addressList.remove(passengerPos);
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
			
			final DeliveryAddress addr = getItem(position);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		if(requestCode==IntentRequestCode.ADD_DELIVERYADDR.getRequestCode()){
			if(data.getExtras() == null)
				return;
			if(data.getExtras().containsKey(IntentExtraAttribute.ADD_DELIVERYADDR_EXTRA)){
				viewContentSetting();
			}
			if(data.getExtras().containsKey(IntentExtraAttribute.EDIT_DELIVERYADDR_EXTRA)){
				DeliveryAddress passenger = (DeliveryAddress) data.getExtras().get(IntentExtraAttribute.EDIT_DELIVERYADDR_EXTRA);
				addressAdapter.resetAddr(passenger);
			}
			if(data.getExtras().containsKey(IntentExtraAttribute.DELETE_DELIVERADDR_EXTRA)){
				DeliveryAddress passenger = (DeliveryAddress) data.getExtras().get(IntentExtraAttribute.DELETE_DELIVERADDR_EXTRA);
				addressAdapter.removeAddr(passenger);
			}
		}
	}
}
