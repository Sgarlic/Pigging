package com.boding.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Passenger;
import com.boding.util.Util;
import com.boding.view.calendar.DateSelectCalendarView;
import com.boding.view.calendar.DateSelectCalendarView.OnItemClickListener;
import com.boding.view.layout.CalendarLayout;
import com.boding.view.layout.OrderFlightInfoILayout;
import com.boding.view.layout.OrderFlightInfoLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class OrderFormActivity extends Activity {
	private boolean isReturnDateSelection = false;
	
	private boolean isInternalJourney = false;
	private boolean isRoundTrip = false;
	
	private LinearLayout flightInfoLinearLayout;
	private TextView ticketPriceTextView;
	private TextView ticketPricePriceTextView;
	private LinearLayout planeBuildingLinearLayout; 
	private TextView planeBuildingPriceTextView;
	private LinearLayout fuelOilLinearLayout; 
	private TextView fuelOilPriceTextView;
	private LinearLayout ticketTaxLinearLayout; 
	private TextView ticketTaxPriceTextView;
	private TextView passengerAmountTextView;
	private ListView passengerListView;
	private LinearLayout addPassengerLinearLayout;
	private TextView phoneNumberTextView;
	private LinearLayout insuranceLinearLayout;
	private TextView insurancePriceTextView;
	private TextView insuranceAmountTextView;
	private LinearLayout journeySheetLinearLayout; 
	private TextView journeySheetTextView;
	private TextView totalPriceTextView;
	private LinearLayout nextStepLinearLayout; 
	
	private List<Passenger> peopleList;
	private PassengerAdapter peopleAdapter;
	
//	private PopupWindow insuranceAmountSelector;
//	private ListView insuranceSelectorListView;
//	private int insurancePopupParentWidth;

	// if init complete
	private boolean flag = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_form);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
//        
//		initView();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		while(!flag){
			initView();
			flag = true;
		}
	}
	
	private void initView(){
		peopleList = new ArrayList<Passenger>();
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(OrderFormActivity.this, IntentRequestCode.DATE_SELECTION);
			}
			
		});
		flightInfoLinearLayout = (LinearLayout) findViewById(R.id.orderform_flightInfo_linearLayout);
		
		OrderFlightInfoLayout orderFlightInfoLinearLayout = new OrderFlightInfoLayout(this);
		flightInfoLinearLayout.addView(orderFlightInfoLinearLayout);
//		OrderFlightInfoLayout orderFlightInfoReturnLinearLayout = new OrderFlightInfoLayout(this);
//		flightInfoLinearLayout.addView(orderFlightInfoReturnLinearLayout);
//		
//		OrderFlightInfoILayout orderFlightInfoILinearLayout = new OrderFlightInfoILayout(this);
//		OrderFlightInfoILayout orderFlightInfoIReturnLinearLayout = new OrderFlightInfoILayout(this);
//		flightInfoLinearLayout.addView(orderFlightInfoILinearLayout);
//		flightInfoLinearLayout.addView(orderFlightInfoIReturnLinearLayout);
		
		ticketPriceTextView = (TextView) findViewById(R.id.orderform_ticketPrice_textView);
		ticketPricePriceTextView = (TextView) findViewById(R.id.orderform_ticketPrice_price_textView);
		planeBuildingLinearLayout = (LinearLayout) findViewById(R.id.orderform_planeBuilding_linearLayout); 
		planeBuildingPriceTextView = (TextView) findViewById(R.id.orderform_planeBuilding_price_textView);
		fuelOilLinearLayout = (LinearLayout) findViewById(R.id.orderform_fuelOil_linearLayout); 
		fuelOilPriceTextView = (TextView) findViewById(R.id.orderform_fuelOil_price_textView);
		ticketTaxLinearLayout = (LinearLayout) findViewById(R.id.orderform_ticketTax_linearLayout); 
		ticketTaxPriceTextView = (TextView) findViewById(R.id.orderform_ticketTax_price_textView);
		passengerAmountTextView = (TextView) findViewById(R.id.orderform_passengerAmount_textView);
		passengerListView = (ListView) findViewById(R.id.orderform_passenger_listView);
		addPassengerLinearLayout = (LinearLayout) findViewById(R.id.orderform_addPassenger_linearLayout);
		phoneNumberTextView = (TextView) findViewById(R.id.orderform_phoneNumber_textView);
		insuranceLinearLayout = (LinearLayout) findViewById(R.id.orderform_insurance_linearLayout);
		insurancePriceTextView = (TextView) findViewById(R.id.orderform_insurance_price_textView);
		insuranceAmountTextView = (TextView) findViewById(R.id.orderform_insurance_amount_textView);
		journeySheetLinearLayout = (LinearLayout) findViewById(R.id.orderform_journeySheet_linearLayout); 
		journeySheetTextView = (TextView) findViewById(R.id.orderform_journeySheet_textView);
		totalPriceTextView = (TextView) findViewById(R.id.orderform_totalPrice_textView);
		nextStepLinearLayout = (LinearLayout) findViewById(R.id.orderform_nextStep_linearLayout); 
		
		if(isInternalJourney){
			planeBuildingLinearLayout.setVisibility(View.GONE);
			fuelOilLinearLayout.setVisibility(View.GONE);
		}else{
			ticketTaxLinearLayout.setVisibility(View.GONE);
		}
		
		if(isRoundTrip)
			ticketPriceTextView.setText("往返总价");
		
		peopleAdapter = new PassengerAdapter(this, peopleList);
		passengerListView.setAdapter(peopleAdapter);
		setPassengerView();
		
//		insurancePopupParentWidth = insuranceLinearLayout.getWidth();
		addListeners();
    }
	
	private static int count = 0;
	
	private void setPassengerView(){
		Util.setListViewHeightBasedOnChildren(passengerListView);
        passengerAmountTextView.setText(String.valueOf(peopleList.size()));
	}
	
	private void addListeners(){
		DataSetObserver observer=new DataSetObserver(){  
	        public void onChanged() {  
	        	setPassengerView();
	        }  
	    };
	    peopleAdapter.registerDataSetObserver(observer);
		
		addPassengerLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Passenger people = new Passenger("李大嘴"+(count++), "35247412569853265"+(count++),IdentityType.GA); 
//				peopleAdapter.addPassenger(people);
				
				Intent intent = new Intent();
				intent.setClass(OrderFormActivity.this, ChoosePassengerActivity.class);
				startActivityForResult(intent,IntentRequestCode.CHOOSE_PASSENGER.getRequestCode());
			}
		});
		
		
		journeySheetLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OrderFormActivity.this, JourneySheetDeliveryActivity.class);
				startActivityForResult(intent,IntentRequestCode.JOURNEYSHEET_DELIVERY.getRequestCode());
			}
		});
		
		nextStepLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OrderFormActivity.this, OrderDetailActivity.class);
				startActivityForResult(intent,IntentRequestCode.ORDER_DETAIL.getRequestCode());
			}
		});
		
		insuranceLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OrderFormActivity.this, InsuranceSelectionActivity.class);
				startActivityForResult(intent,IntentRequestCode.INSURANCE_SELECTION.getRequestCode());
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
			ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_passenger, null);
	            holder = new ViewHolder();  
	            
	            holder.nameTextView = (TextView) convertView.findViewById(R.id.passenger_name_textView);
	            holder.idNumberTextView = (TextView) convertView.findViewById(R.id.passenger_idnumber_textView);
	            holder.idTypeTextView = (TextView) convertView.findViewById(R.id.passenger_idtype_textView);
	            holder.deleteLinearLayout = (LinearLayout) convertView.findViewById(R.id.passenger_delete_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			Passenger people = getItem(position);
            holder.nameTextView.setText(people.getName());
            holder.idNumberTextView.setText(people.getCardNumber());
			holder.deleteLinearLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						passengerList.remove(position); 
						Log.d("poding",position+"");
						notifyDataSetChanged();
					}
				});
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView nameTextView;
			TextView idNumberTextView;
			TextView idTypeTextView;
			LinearLayout deleteLinearLayout;
		}
	}
//	
//	private void initPopupWindow(){
//		View popupWindow =  LayoutInflater.from(this).inflate(R.layout.popup_insurance_selector, null);
//		insuranceSelectorListView = (ListView)popupWindow.findViewById(R.id.insurance_select_list);
//		insuranceAmountSelector = new PopupWindow(popupWindow,insurancePopupParentWidth,LayoutParams.WRAP_CONTENT,true);
//		insuranceAmountSelector.setOutsideTouchable(true);
//		insuranceAmountSelector.setBackgroundDrawable(new BitmapDrawable());
//	}
//	
//	private void popupWindowShowing(){
//		insuranceAmountSelector.showAsDropDown(insuranceLinearLayout, 0, -3);
//	}
//	
//	private void popupWindowDismiss(){
//		insuranceAmountSelector.dismiss();
//	}
}
