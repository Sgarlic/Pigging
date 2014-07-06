package com.boding.app;


import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.constants.OrderStatus;
import com.boding.model.Order;
import com.boding.model.OrderFlight;
import com.boding.model.Passenger;
import com.boding.task.OrderTask;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.layout.OrderDetailFlightInfoLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OrderDetailActivity extends Activity {
	private TextView orderStatusTextView;
	private TextView orderIDTextView;
	private TextView orderCreatedDateTextView;
	private TextView orderPaymentMethodTextView;
	private TextView orderReturnBodingCashTextView;
	private LinearLayout notPaidWarningLinearLayout;
	
	private LinearLayout flightInfoLinearLayout;
	private TextView ticketPriceTextView;
	private TextView buildingPriceTextView;
	private TextView fuelPriceTextView;
	private LinearLayout changeRefundConditionLinearLayout;
	
	private ListView passengerListView;
	private TextView contactPhoneNumTextView;
	private TextView insuranceAmountTextView;
	private TextView insuranceTotalTextView;
	private TextView deliveryMethodTextView;
	private TextView contactCustomServiceTextView;
	private TextView totalPriceTextView;
	private LinearLayout confirmPayLinearLayout;
	private TextView paymentButtonTextView;
	
	private PassengerAdapter passengerAdapter;
	
	private String orderCode;
//	private Order order;
	
	private ProgressBarDialog progressBarDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		progressBarDialog = new ProgressBarDialog(this, Constants.DIALOG_STYLE);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	if(arguments.containsKey(IntentExtraAttribute.CHOOSED_ORDER_ID))
        		orderCode = arguments.getString(IntentExtraAttribute.CHOOSED_ORDER_ID);
        }
        
		initView();
		setViewContent();
	}
	
	public void setOrderInfo(Order order){
//		this.order = order;
		orderStatusTextView.setText(order.getOrderStatus().getOrderDetailStatusName());
		orderIDTextView.setText(order.getOrderCode());
		orderCreatedDateTextView.setText(order.getBookingDate());
		orderPaymentMethodTextView.setText(order.getPayMode());
		orderReturnBodingCashTextView.setText(order.getPrePayment());
		if(order.getOrderStatus().getOrderStatusCode().equals("4")){
			notPaidWarningLinearLayout.setVisibility(View.VISIBLE);
		}else{
			notPaidWarningLinearLayout.setVisibility(View.GONE);
		}
		
		OrderDetailFlightInfoLayout detailLinearLayout = new OrderDetailFlightInfoLayout(
			this,order.getOrderFlights().get(0), order.getLeaveCity(), order.getArriveCity());
		flightInfoLinearLayout.addView(detailLinearLayout);
		
		if(order.getOrderFlights().size()>1){
			System.out.println("!111111111111111111111111111111111");
			OrderDetailFlightInfoLayout detailRLinearLayout = new OrderDetailFlightInfoLayout(
				this,order.getOrderFlights().get(1), order.getArriveCity(), order.getLeaveCity());
			flightInfoLinearLayout.addView(detailRLinearLayout);
		}
		ticketPriceTextView.setText(order.getTicketPrice());

		passengerAdapter = new PassengerAdapter(this, order.getPassengers());
		passengerListView.setAdapter(passengerAdapter);
		Util.setListViewHeightBasedOnChildren(passengerListView);
		
		contactPhoneNumTextView.setText(order.getContactPhone());
		insuranceAmountTextView.setText(order.getInsuranceNum());
		insuranceTotalTextView.setText(order.getInsurance());
//		deliveryMethodTextView
		totalPriceTextView.setText(order.getPayAmount());
		
		if(order.getOrderStatus() == OrderStatus.PENDING_PAYMENT){
			confirmPayLinearLayout.setVisibility(View.VISIBLE);
		}else{
			confirmPayLinearLayout.setVisibility(View.GONE);
		}
		
		progressBarDialog.dismiss();
	}
	
	private void setViewContent(){
		progressBarDialog.show();
		(new OrderTask(this, HTTPAction.GET_ORDER_DETAIL)).execute(orderCode);
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(OrderDetailActivity.this, IntentRequestCode.ORDER_DETAIL);
			}
			
		});
		
		orderStatusTextView = (TextView)findViewById(R.id.orderdetail_orderStatus_textView);
		orderIDTextView = (TextView)findViewById(R.id.orderdetail_orderID_textView);
		orderCreatedDateTextView = (TextView)findViewById(R.id.orderdetail_orderCreatedDate_textView);
		orderPaymentMethodTextView = (TextView)findViewById(R.id.orderdetail_orderPaymentMethod_textView);
		orderReturnBodingCashTextView = (TextView)findViewById(R.id.orderdetail_orderReturnBodingCash_textView);
		notPaidWarningLinearLayout = (LinearLayout)findViewById(R.id.orderdetail_notPaidWarning_linearLayout);
		
		flightInfoLinearLayout = (LinearLayout) findViewById(R.id.orderdetail_flightInfo_linearLayout);
		
		ticketPriceTextView = (TextView)findViewById(R.id.orderdetail_ticketPrice_textView);
		buildingPriceTextView = (TextView)findViewById(R.id.orderdetail_buildingPrice_textView);
		fuelPriceTextView = (TextView)findViewById(R.id.orderdetail_fuelPrice_textView);
		changeRefundConditionLinearLayout = (LinearLayout)findViewById(R.id.orderdetail_changeRefundCondition_linearLayout);
		
		passengerListView = (ListView)findViewById(R.id.orderdetail_passenger_listView);
		contactPhoneNumTextView = (TextView)findViewById(R.id.orderdetail_contactPhoneNum_textView);
		insuranceAmountTextView = (TextView)findViewById(R.id.orderdetail_insuranceAmount_textView);
		insuranceTotalTextView = (TextView)findViewById(R.id.orderdetail_insuranceTotal_textView);
		deliveryMethodTextView = (TextView)findViewById(R.id.orderdetail_deliveryMethod_textView);
		contactCustomServiceTextView = (TextView)findViewById(R.id.orderdetail_contactCustomService_textView);
		totalPriceTextView = (TextView)findViewById(R.id.orderdetail_totalPrice_textView);
		confirmPayLinearLayout = (LinearLayout)findViewById(R.id.orderdetail_confirmPay_linearLayout);
		paymentButtonTextView = (TextView) findViewById(R.id.orderdetail_paymentButton_textView);
		
		addListeners();
	}
	
	private void addListeners(){
//		DataSetObserver observer=new DataSetObserver(){  
//	        public void onChanged() {  
//	            Util.setListViewHeightBasedOnChildren(passengerListView);
//	        }  
//	    };
//	    passengerAdapter.registerDataSetObserver(observer);
		
		confirmPayLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(OrderDetailActivity.this, OrderPaymentActivity.class);
				startActivityForResult(intent, IntentRequestCode.ORDER_PAYEMNT.getRequestCode());
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
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_orderdetail_passenger, null);
	            holder = new ViewHolder();  
	            
	            holder.nameTextView = (TextView) convertView.findViewById(R.id.orderdetailpassenger_name_textView);
	            holder.idNumberTextView = (TextView) convertView.findViewById(R.id.orderdetailpassenger_idnumber_textView);
	            holder.idTypeTextView = (TextView) convertView.findViewById(R.id.orderdetailpassenger_idType_textView);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			Passenger people = getItem(position);
            holder.nameTextView.setText(people.getDiaplayName());
            holder.idNumberTextView.setText(people.getCardNumber());
            holder.idTypeTextView.setText(people.getIdentityType().getIdentityName());
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView nameTextView;
			TextView idNumberTextView;
			TextView idTypeTextView;
		}
	}
}
