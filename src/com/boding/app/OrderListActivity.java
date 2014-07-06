package com.boding.app;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.boding.R;
import com.boding.constants.AirlineType;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.constants.OrderStatus;
import com.boding.model.DeliveryAddress;
import com.boding.model.Order;
import com.boding.model.Passenger;
import com.boding.task.OrderTask;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OrderListActivity extends Activity {
	private LinearLayout orderFilterLinearLayout;
	private TextView orderFilterTypeTextView;
	private ListView ordersListView;
	
	private OrderListAdapter orderListAdapter;
	
	private ProgressBarDialog progressBarDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        progressBarDialog = new ProgressBarDialog(this, Constants.DIALOG_STYLE);
		initView();
		setViewContent();
	}
	
	private void setViewContent(){
		progressBarDialog.show();
		(new OrderTask(this, HTTPAction.GET_ORDER_LIST)).execute("20","1");
	}
	
	public void setOrderList(List<Order> orderList){
		orderListAdapter = new OrderListAdapter(this, orderList);
		ordersListView.setAdapter(orderListAdapter);
		
		progressBarDialog.dismiss();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(OrderListActivity.this, IntentRequestCode.ORDERS_LIST);
			}
			
		});
		
		orderFilterLinearLayout = (LinearLayout) findViewById(R.id.allorders_orderfilter_linearLayout);
		orderFilterTypeTextView = (TextView) findViewById(R.id.allorders_orderFilterType_textView);
		ordersListView = (ListView) findViewById(R.id.allorders_listView);
		
		addListeners();
	}
	
	private void addListeners(){
		ordersListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Order selectedOrder = orderListAdapter.getItem(position);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString(IntentExtraAttribute.CHOOSED_ORDER_ID, 
						selectedOrder.getOrderCode());
				intent.putExtras(bundle);
				intent.setClass(OrderListActivity.this, OrderDetailActivity.class);
				startActivityForResult(intent, IntentRequestCode.ORDER_DETAIL.getRequestCode());
			}
		});
	}
	
	
	private class OrderListAdapter extends BaseAdapter {
		private List<Order> orderList;
		private Context context;
		public OrderListAdapter(Context context, List<Order> orderList) {
			this.context = context;
			this.orderList = orderList;
		}
		@Override
		public int getCount() {
			return orderList.size();
		}

		@Override
		public Order getItem(int position) {
			return orderList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void addNewOrder(Order addr){
			orderList.add(addr);
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_order, null);
	            holder = new ViewHolder();  
	            
	            holder.flyFromCityTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyFromCity_textView);
//	            holder.transitCityTextView = (TextView) convertView.findViewById(R.id.listitemorder_transitCity_textView);
	            holder.flyFromTerminalTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyFromTerminal_textView);
	            holder.flyFromDateTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyFromDate_textView);
	            holder.flyFromTimeTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyFromTime_textView);
	            holder.flyToCityTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyToCity_textView);
	            holder.flyToTerminalTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyToTerminal_textView);
	            holder.flyToDateTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyToDate_textView);
	            holder.flyToTimeTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyToTime_textView);
	            holder.flyTypeTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyType_textView);
	            holder.flyPassengerTextView = (TextView) convertView.findViewById(R.id.listitemorder_flyPassenger_textView);
	            holder.orderStatusTextView = (TextView) convertView.findViewById(R.id.listitemorder_orderStatus_textView);
	            holder.airlineTypeLinearLayout = (LinearLayout) convertView.findViewById(R.id.listitemorder_airlineType_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			Order order = getItem(position);
			holder.flyFromCityTextView.setText(order.getLeaveCity());
//			holder.transitCityTextView.setText(order.getTransitCity());
			holder.flyFromTerminalTextView.setText(order.getLeaveAirport()+order.getLeaveTerminal());
			holder.flyFromDateTextView.setText(order.getLeaveDate());
			holder.flyFromTimeTextView.setText(order.getLeaveTime());
			holder.flyToCityTextView.setText(order.getArriveCity());
			holder.flyToTerminalTextView.setText(order.getArriveAirport()+order.getArriveTerminal());
			holder.flyToDateTextView.setText(order.getArriveDate());
			holder.flyToTimeTextView.setText(order.getLeaveTime());
			
			holder.flyTypeTextView.setText(order.getAirlineType().getAirlineTypeName());
			holder.flyPassengerTextView.setText(order.getDisplayPassengerName());
			holder.orderStatusTextView.setText(order.getOrderStatus().getOrderStatusName());
			
			Util.setViewBackground(holder.airlineTypeLinearLayout, getResources().getDrawable(order.getAirlineType().getAirlineTypePicID()));
			
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView flyFromCityTextView;
//			TextView transitCityTextView;
			TextView flyFromTerminalTextView;
			TextView flyFromDateTextView;
			TextView flyFromTimeTextView;
			TextView flyToCityTextView;
			TextView flyToTerminalTextView;
			TextView flyToDateTextView;
			TextView flyToTimeTextView;
			TextView flyTypeTextView;
			TextView flyPassengerTextView;
			TextView orderStatusTextView;
			LinearLayout airlineTypeLinearLayout;
		}
	}
}
