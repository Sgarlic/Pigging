package com.boding.app;


import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.constants.OrderStatus;
import com.boding.model.Order;
import com.boding.task.OrderTask;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.listview.DragListView;
import com.boding.view.listview.DragListView.OnRefreshLoadingMoreListener;

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
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class OrderListActivity extends Activity{
	private LinearLayout orderFilterLinearLayout;
	private TextView orderFilterTypeTextView;
	private DragListView ordersListView;
	
	private OrderListAdapter allOrderListAdapter;
	private OrderListAdapter unpaymentOrdersListAdapter;
	private OrderListAdapter unTravelledOrdersAdapter;
	
	private ProgressBarDialog progressBarDialog;
	private int currentPage = 0;
	private List<Order> allOrdersList = new ArrayList<Order>();
	private List<Order> unpaymentOrdersList;
	private List<Order> unTravelledOrdersList;
	
	
	private PopupWindow monthSelector;
	
	
	private TextView allOrdersTextView;
	private TextView unPaymentOrdersTextView;
	private TextView unTravelledOrdersTextView;
	
	/**
	 * 0 == all orders
	 * 1 == unpayment orders
	 * 2 == untravelled orders
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        progressBarDialog = new ProgressBarDialog(this);
		initView();
		
		
		setViewContent();
	}
	
	private void setOrdersView(int selectedFilter){
		allOrdersTextView.setSelected(false);
		unPaymentOrdersTextView.setSelected(false);
		unTravelledOrdersTextView.setSelected(false);
		switch (selectedFilter) {
			case 1:
				unPaymentOrdersTextView.setSelected(true);
				ordersListView.setAdapter(unpaymentOrdersListAdapter);
				break;
			case 2:
				unTravelledOrdersTextView.setSelected(true);
				ordersListView.setAdapter(unTravelledOrdersAdapter);
				break;
			default:
				allOrdersTextView.setSelected(true);
				ordersListView.setAdapter(allOrderListAdapter);
				break;
		}
		popupWindowDismiss();
	}
	
	private void initPopupWindow(){
		View popupWindow =  LayoutInflater.from(this).inflate(R.layout.popup_order_filter, null);
		allOrdersTextView = (TextView) popupWindow.findViewById(R.id.orderfilter_allOrders_textView);
		allOrdersTextView.setSelected(true);
		unPaymentOrdersTextView = (TextView) popupWindow.findViewById(R.id.orderfilter_unPaymentOrders_textView);
		unTravelledOrdersTextView = (TextView) popupWindow.findViewById(R.id.orderfilter_unTravelledOrders_textView);
		allOrdersTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setOrdersView(0);
			}
		});
		
		unPaymentOrdersTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setOrdersView(1);
			}
		});
		
		unTravelledOrdersTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setOrdersView(2);
			}
		});
		monthSelector = new PopupWindow(popupWindow,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,true);
	}
	
	private void popupWindowShowing(){
		int width = orderFilterTypeTextView.getWidth();
		monthSelector.showAsDropDown(orderFilterTypeTextView, 0 - width/3, -6);
	}
	
	private void popupWindowDismiss(){
		monthSelector.dismiss();
	}
	
	
	private void setViewContent(){
		currentPage++;
		if(currentPage == 1){
			progressBarDialog.show();
		}
		(new OrderTask(this, HTTPAction.GET_ORDER_LIST)).execute("10",currentPage+"");
	}
	
	private void addOtherOrdersFromOrderList(List<Order> orderList){
		if(unpaymentOrdersList == null || unTravelledOrdersList == null){
			unpaymentOrdersList = new ArrayList<Order>();
			unTravelledOrdersList = new ArrayList<Order>();
			
			unpaymentOrdersListAdapter = new OrderListAdapter(this, unpaymentOrdersList);
			unTravelledOrdersAdapter = new OrderListAdapter(this, unTravelledOrdersList);
		}
		for(Order order : orderList){
			OrderStatus orderStatus = order.getOrderStatus();
			if(orderStatus == OrderStatus.PENDING_AUDIT || orderStatus == OrderStatus.PENDING_COLLECTMONEY
			|| orderStatus == OrderStatus.PENDING_DELIVERY|| orderStatus == OrderStatus.PENDING_PAYMENT){
				unpaymentOrdersList.add(order);
			}
			if(orderStatus == OrderStatus.PENDING_GETTICKET || orderStatus == OrderStatus.CENCELED){
				unTravelledOrdersList.add(order);
			}
		}
		unpaymentOrdersListAdapter.notifyDataSetChanged();
		unTravelledOrdersAdapter.notifyDataSetChanged();
	}
	
	public void setOrderList(List<Order> orderList){
		allOrdersList.addAll(orderList);
		allOrderListAdapter.notifyDataSetChanged();
		addOtherOrdersFromOrderList(orderList);
		boolean hasMoreInfo = true;
		if(orderList.size() == 0)
			hasMoreInfo = false;
		ordersListView.onLoadMoreComplete(hasMoreInfo);
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
		ordersListView = (DragListView) findViewById(R.id.allorders_listView);
		allOrderListAdapter = new OrderListAdapter(this, allOrdersList);
		ordersListView.setAdapter(allOrderListAdapter);
		
		addListeners();
		initPopupWindow();
	}
	
	private void addListeners(){
		ordersListView.setOnRefreshListener(new OnRefreshLoadingMoreListener() {
			@Override
			public void onLoadMore() {
				setViewContent();
			}
		});
		ordersListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Order selectedOrder = allOrderListAdapter.getItem(position);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString(IntentExtraAttribute.CHOOSED_ORDER_ID, 
						selectedOrder.getOrderCode());
				intent.putExtras(bundle);
				intent.setClass(OrderListActivity.this, OrderDetailActivity.class);
				startActivityForResult(intent, IntentRequestCode.ORDER_DETAIL.getRequestCode());
			}
		});
		
		orderFilterLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("clicked");
				popupWindowShowing();
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
