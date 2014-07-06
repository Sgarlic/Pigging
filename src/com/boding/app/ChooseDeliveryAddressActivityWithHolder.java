package com.boding.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.DeliveryAddress;
import com.boding.model.Passenger;
import com.boding.task.DeliveryAddrTask;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ChooseDeliveryAddressActivityWithHolder extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout addNewAddrLinearLayout;
	private ListView deliveryAddrListView;
	
	private Set<String> selectedAddrIds;
	
	private ProgressBarDialog progressBarDialog;
	
	
	private Bundle bundle;	
	private DeliveryAddressAdapter addressAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_deliveryaddress);
		selectedAddrIds = new HashSet<String>();
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	if(arguments.containsKey(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA)){
	        	ArrayList<DeliveryAddress> selectedAddrs = arguments.getParcelableArrayList(
	        			IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA);
	        	for(DeliveryAddress addr : selectedAddrs)
	        		selectedAddrIds.add(addr.getAddrID());
        	}
        }
		initView();
		viewContentSetting();
	}
	
	private void initView(){
		bundle = new Bundle();
		bundle.putBoolean(IntentExtraAttribute.IS_MANAGE_DELIVERYADDR, false);
		
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(ChooseDeliveryAddressActivityWithHolder.this, IntentRequestCode.CHOOSE_DELIVERYADDR);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.choose_deliveryaddress_complete_linearLayout);
		addNewAddrLinearLayout = (LinearLayout) findViewById(R.id.choose_deliveryaddress_addNewAddress_linearLayout);
		deliveryAddrListView = (ListView) findViewById(R.id.choose_deliveryaddress_deliveryAddresses_listView);
		
		addListeners();
	}

	private void viewContentSetting(){
		progressBarDialog = new ProgressBarDialog(this, Constants.DIALOG_STYLE);
		progressBarDialog.show();
		
		DeliveryAddrTask deliveryAddrTask = new DeliveryAddrTask(this, HTTPAction.GET_DELIVERYADDRLIST);
		deliveryAddrTask.execute();
	}
	
	public void setAddrList(List<DeliveryAddress> addrList){
		addressAdapter = new DeliveryAddressAdapter(this, addrList);
		deliveryAddrListView.setAdapter(addressAdapter);
		progressBarDialog.dismiss();
	}
	private void addListeners(){
		addNewAddrLinearLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				bundle.putBoolean(IntentExtraAttribute.IS_EDIT_DELIVERYADDR, false);
				intent.putExtras(bundle);
				intent.setClass(ChooseDeliveryAddressActivityWithHolder.this, AddDeliveryAddrActivity.class);
				startActivityForResult(intent,IntentRequestCode.ADD_DELIVERYADDR.getRequestCode());
			}
		});
		completeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(selectedAddrIds.size() > 1){
            		WarningDialog dialog = new WarningDialog(ChooseDeliveryAddressActivityWithHolder.this,
            				Constants.DIALOG_STYLE);
            		dialog.setContent("最多只能选择9位乘客");
            		dialog.show();
            		return;
            	}
				Intent intent=new Intent();
				ArrayList<DeliveryAddress> selectedAddrList = new ArrayList<DeliveryAddress>();
				for(int i=0;i<addressAdapter.getCount();i++){
					DeliveryAddress addr = addressAdapter.getItem(i);
					if(selectedAddrIds.contains(addr.getAddrID())){
						selectedAddrList.add(addr);
					}
				}
				intent.putParcelableArrayListExtra(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA, selectedAddrList);
				setResult(IntentRequestCode.CHOOSE_DELIVERYADDR.getRequestCode(), intent);
				finish();
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
		
		public void addNewAddr(DeliveryAddress addr){
			addressList.add(addr);
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
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_choosedeliveryaddress, null);
	            holder = new ViewHolder();  
	            
	            holder.chooseAddrLinearLayout = (LinearLayout) convertView.findViewById(R.id.choosedeliveryaddress_choose_linearLayout);
//	            holder.chooseAddrCheckBox = (CheckBox) convertView.findViewById(R.id.choosedeliveryaddress_choose_checkBox);
	            holder.recipientNameTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_recipientName_textView);
	            holder.addrTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_address_textView);
	            holder.zipcodeTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_zipcode_textView);
	            holder.editLinearLayout = (LinearLayout) convertView.findViewById(R.id.choosedeliveryaddress_edit_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			final DeliveryAddress addr = getItem(position);
			holder.addrId = addr.getAddrID();
			if(selectedAddrIds.contains(holder.addrId))
				holder.chooseAddrCheckBox.setChecked(true);
			else
				holder.chooseAddrCheckBox.setChecked(false);
			
            holder.recipientNameTextView.setText(addr.getRecipientName());
            holder.addrTextView.setText(addr.getDisplayAddr());
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
            
            holder.editLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					bundle.putBoolean(IntentExtraAttribute.IS_EDIT_DELIVERYADDR, true);
					bundle.putParcelable(IntentExtraAttribute.IS_EDIT_DELIVERYADDR_ADDRINFO, addr);
					intent.putExtras(bundle);
					intent.setClass(ChooseDeliveryAddressActivityWithHolder.this, AddDeliveryAddrActivity.class);
					startActivityForResult(intent, IntentRequestCode.ADD_DELIVERYADDR.getRequestCode());
				}
			});
            holder.chooseAddrCheckBox.setOnCheckedChangeListener(new OncheckchangeListner(holder));
	        return convertView;  
		}
		
		class OncheckchangeListner implements OnCheckedChangeListener{

            ViewHolder viewHolder = null; 
            public OncheckchangeListner(ViewHolder viHolder)
            {
                viewHolder =  viHolder;  
            }
            @Override 
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {

                if(viewHolder.chooseAddrCheckBox.equals(buttonView))
                {       
	                if(!isChecked)
	                {
	                	selectedAddrIds.remove(viewHolder.addrId);
	                }
	                else{
	                	selectedAddrIds.add(viewHolder.addrId);
	                }
            	}
            }

        }
		
		private class ViewHolder {
			LinearLayout chooseAddrLinearLayout;
			CheckBox chooseAddrCheckBox;
			TextView recipientNameTextView;
			TextView addrTextView;
			TextView zipcodeTextView;
			LinearLayout editLinearLayout;
			String addrId;
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
				DeliveryAddress passenger = (DeliveryAddress) data.getExtras().get(IntentExtraAttribute.EDIT_PASSENGER_EXTRA);
				addressAdapter.resetAddr(passenger);
			}
			if(data.getExtras().containsKey(IntentExtraAttribute.DELETE_DELIVERADDR_EXTRA)){
				DeliveryAddress passenger = (DeliveryAddress) data.getExtras().get(IntentExtraAttribute.DELETE_PASSENGER_EXTRA);
				addressAdapter.removeAddr(passenger);
			}
		}
	}
}
