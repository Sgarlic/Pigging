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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ChooseDeliveryAddressActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout addNewAddrLinearLayout;
	private ListView deliveryAddrListView;
	
	private DeliveryAddress selectedAddr;
	
	private ProgressBarDialog progressBarDialog;
	
	
	private Bundle bundle;	
	private DeliveryAddressAdapter addressAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_deliveryaddress);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	if(arguments.containsKey(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA)){
        		selectedAddr = arguments.getParcelable(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA);
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
				Util.returnToPreviousPage(ChooseDeliveryAddressActivity.this, IntentRequestCode.CHOOSE_DELIVERYADDR);
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
				intent.setClass(ChooseDeliveryAddressActivity.this, AddDeliveryAddrActivity.class);
				startActivityForResult(intent,IntentRequestCode.ADD_DELIVERYADDR.getRequestCode());
			}
		});
		completeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(selectedAddr == null){
            		WarningDialog dialog = new WarningDialog(ChooseDeliveryAddressActivity.this,
            				Constants.DIALOG_STYLE);
            		dialog.setContent("请选择一个配送地址");
            		dialog.show();
            		return;
            	}
				Intent intent=new Intent();
				intent.putExtra(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA, selectedAddr);
				setResult(IntentRequestCode.CHOOSE_DELIVERYADDR.getRequestCode(), intent);
				finish();
			}
		});
	}
	
	
	private class DeliveryAddressAdapter extends BaseAdapter {
		private List<DeliveryAddress> addressList;
		private Context context;
		private List<CheckBox> checkBoxList;
		public DeliveryAddressAdapter(Context context, List<DeliveryAddress> addressList) {
			this.context = context;
			this.addressList = addressList;
			checkBoxList = new ArrayList<CheckBox>();
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
			if(selectedAddr!=null && selectedAddr.getAddrID().equals(deliverAddr.getAddrID())){
				selectedAddr = null;
			}
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_choosedeliveryaddress, null);
	            
            LinearLayout chooseAddrLinearLayout = (LinearLayout) convertView.findViewById(R.id.choosedeliveryaddress_choose_linearLayout);
            final CheckBox chooseAddrCheckBox = (CheckBox) convertView.findViewById(R.id.choosedeliveryaddress_choose_checkBox);
            TextView recipientNameTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_recipientName_textView);
            TextView addrTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_address_textView);
            TextView zipcodeTextView = (TextView) convertView.findViewById(R.id.choosedeliveryaddress_zipcode_textView);
            LinearLayout editLinearLayout = (LinearLayout) convertView.findViewById(R.id.choosedeliveryaddress_edit_linearLayout);
            
			final DeliveryAddress addr = getItem(position);
			
			checkBoxList.add(chooseAddrCheckBox);
			if(selectedAddr!=null){
				if(selectedAddr.getAddrID().equals(addr.getAddrID())){
					chooseAddrCheckBox.setChecked(true);
				}
			}
			
            recipientNameTextView.setText(addr.getRecipientName());
            addrTextView.setText(addr.getDisplayAddr());
            zipcodeTextView.setText(addr.getZipcode());
            chooseAddrLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (chooseAddrCheckBox.isChecked())
						chooseAddrCheckBox.setChecked(false);
					else
						chooseAddrCheckBox.setChecked(true);
					
				}
			});
            
            editLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					bundle.putBoolean(IntentExtraAttribute.IS_EDIT_DELIVERYADDR, true);
					bundle.putParcelable(IntentExtraAttribute.IS_EDIT_DELIVERYADDR_ADDRINFO, addr);
					intent.putExtras(bundle);
					intent.setClass(ChooseDeliveryAddressActivity.this, AddDeliveryAddrActivity.class);
					startActivityForResult(intent, IntentRequestCode.ADD_DELIVERYADDR.getRequestCode());
				}
			});
//            holder.chooseAddrCheckBox.setOnCheckedChangeListener(new OncheckchangeListner(holder));
            chooseAddrCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
					if(isChecked){
						for(CheckBox checkBox: checkBoxList){
							if(!checkBox.equals(chooseAddrCheckBox)){
								if(checkBox.isChecked()){
									checkBox.setChecked(false);
								}
							}
							selectedAddr = addr;
						}
					}
				}
			});
	        return convertView;  
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
				DeliveryAddress deliveryAddr = (DeliveryAddress) data.getExtras().get(IntentExtraAttribute.EDIT_DELIVERYADDR_EXTRA);
				addressAdapter.resetAddr(deliveryAddr);
			}
			if(data.getExtras().containsKey(IntentExtraAttribute.DELETE_DELIVERADDR_EXTRA)){
				DeliveryAddress deliveryAddr = (DeliveryAddress) data.getExtras().get(IntentExtraAttribute.DELETE_DELIVERADDR_EXTRA);
				addressAdapter.removeAddr(deliveryAddr);
			}
			selectedAddr = null;
		}
	}
}
