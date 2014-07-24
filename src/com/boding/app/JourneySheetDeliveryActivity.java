package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.DeliveryAddress;
import com.boding.util.Util;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.SelectionDialog.OnItemSelectedListener;
import com.boding.view.dialog.WarningDialog;

public class JourneySheetDeliveryActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private LinearLayout needJourneySheetInfoLinearLayout;
	private ImageView needJourneySheetImageView;
	private LinearLayout selectDeliveryMethodsLinearLayout;
	private TextView deliveryMethodTextView;
	private LinearLayout selectDeliveryAddrLinearLayout;
	private LinearLayout showJourneySheetInfoLinearLayout;
	private TextView deliveryAddrTextView; 
	
	private DeliveryAddress selectedAddr;
	
	private int deliveryPrice = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journeysheet_delivery);
		
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	if(arguments.containsKey(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA)){
        		selectedAddr = arguments.getParcelable(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA);
        	}
        }
        
		initView();
		setViewContent();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(JourneySheetDeliveryActivity.this, IntentRequestCode.CHOOSE_PASSENGER);
			}
			
		});
		
		completeLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_complete_linearLayout);
		needJourneySheetInfoLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_needJourneySheetInfo_linearLayout);
		needJourneySheetImageView = (ImageView) findViewById(R.id.journeysheet_needJourneySheetInfo_imageView);
		selectDeliveryMethodsLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_selectDeliveryMethods_linearLayout);
		deliveryMethodTextView = (TextView) findViewById(R.id.journeysheet_deliveryMethods_textView);
		selectDeliveryAddrLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_selectDeliveryAddress_linearLayout);
		showJourneySheetInfoLinearLayout = (LinearLayout) findViewById(R.id.journeysheet_showJourneySheetInfo_linearLayout);
		deliveryAddrTextView = (TextView) findViewById(R.id.journeysheet_deliveryAddr_textView);
		
		addListeners();
	}
	
	private void setViewContent(){
		if(selectedAddr == null){
			deliveryAddrTextView.setText("");
			showJourneySheetInfoLinearLayout.setVisibility(View.INVISIBLE);
			needJourneySheetImageView.setSelected(false);
		}else{
			deliveryAddrTextView.setText(selectedAddr.getRecipientName());
			showJourneySheetInfoLinearLayout.setVisibility(View.VISIBLE);
			needJourneySheetImageView.setSelected(true);
		}
	}
	
	
	private void addListeners(){
		selectDeliveryMethodsLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final List<String> deliverMethodList = new ArrayList<String>();
				deliverMethodList.add("江浙沪（￥10）");
				deliverMethodList.add("其他地区（￥20）");
				
				SelectionDialog deliveryMethodDialog = new SelectionDialog(JourneySheetDeliveryActivity.this,
						"选择配送方式",deliverMethodList);
				deliveryMethodDialog.show();
				deliveryMethodDialog.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						if(position == 0)
							deliveryPrice = 10;
						else
							deliveryPrice = 20;
						deliveryMethodTextView.setText(deliverMethodList.get(position));
					}
				});
			}
		});
		
		needJourneySheetInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(needJourneySheetImageView.isSelected()){
					needJourneySheetImageView.setSelected(false);
					showJourneySheetInfoLinearLayout.setVisibility(View.INVISIBLE);
				}else{
					needJourneySheetImageView.setSelected(true);
					showJourneySheetInfoLinearLayout.setVisibility(View.VISIBLE);
				}
			}
		});
		
		selectDeliveryAddrLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if(selectedAddr!=null){
					Bundle bundle  = new Bundle();
					bundle.putParcelable(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA,
							selectedAddr);
					intent.putExtras(bundle);
				}
				intent.setClass(JourneySheetDeliveryActivity.this, ChooseDeliveryAddressActivity.class);
				startActivityForResult(intent,IntentRequestCode.CHOOSE_DELIVERYADDR.getRequestCode());
			}
		});
		completeLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				if(needJourneySheetImageView.isSelected()){
					if(selectedAddr == null){
						WarningDialog warningDialog = new WarningDialog(
							JourneySheetDeliveryActivity.this);
						warningDialog.setContent("请选择配送地址");
						warningDialog.show();
						return;
	            	}else{
	            		intent.putExtra(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA, selectedAddr);
	            		intent.putExtra(IntentExtraAttribute.CHOOSED_DELIVERPRICE_EXTRA, deliveryPrice);
	            	}
				}
				setResult(IntentRequestCode.JOURNEYSHEET_DELIVERY.getRequestCode(), intent);
				finish();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		if(requestCode==IntentRequestCode.CHOOSE_DELIVERYADDR.getRequestCode()){
			if(data.getExtras() == null)
				return;
			if(data.getExtras().containsKey(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA)){
				selectedAddr = data.getExtras().getParcelable(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA);
			}else
				selectedAddr = null;
		}
		setViewContent();
	}
}
