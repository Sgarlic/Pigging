package com.boding.app;


import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.constants.OrderFilterStatus;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 3，派送中和4，收银中都归为已出票状态
 * PENDING_DELIVERY("3","派送中","待派送",R.color.darkgray),
 * PENDING_COLLECTMONEY("4","收银中","待收银",R.color.darkgray),
 * 在创建Filter的时候，pending_delivery就不放进去了，就放pending_collectmoney
 * @author shiyge
 *
 */
public class OrderListActivity extends Activity{
	private LinearLayout orderFilterLinearLayout;
	private TextView orderFilterTypeTextView;
	private DragListView ordersListView;
	
	private ProgressBarDialog progressBarDialog;
	private int currentPage = 0;
	private List<Order> ordersList = new ArrayList<Order>();
	
	
	private OrderListAdapter adapter;
	
	private PopupWindow monthSelector;
	
	private ListView orderFilterListView;
	
	private OrderFilterAdapter filterAdapter;
	
	private OrderFilterStatus currentFilterStatus = OrderFilterStatus.ALL_ORDERS;
	
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
		
		
		setOrdersView();
	}
	
	private void setOrdersView(){
		currentPage++;
		if(currentPage == 1){
			progressBarDialog.show();
		}
		if(currentFilterStatus == OrderFilterStatus.TICKET_ALREADY_GENERATED){
			(new OrderTask(this, HTTPAction.GET_ORDER_LIST)).execute(3, "10",currentPage+"");
			(new OrderTask(this, HTTPAction.GET_ORDER_LIST)).execute(4, "10",currentPage+"");
		}
		else
			(new OrderTask(this, HTTPAction.GET_ORDER_LIST)).execute(currentFilterStatus.getOrderStatusCode(), "10",currentPage+"");
		popupWindowDismiss();
	}
	
	private void initPopupWindow(){
		View popupWindow =  LayoutInflater.from(this).inflate(R.layout.popup_order_filter, null);
		orderFilterListView = (ListView) popupWindow.findViewById(R.id.orderfilter_filter_listView);
		
		List<OrderFilterStatus> orderStatusList = new ArrayList<OrderFilterStatus>();
		for(OrderFilterStatus filterStatus : OrderFilterStatus.values()){
			orderStatusList.add(filterStatus);
		}
		filterAdapter = new OrderFilterAdapter(this, orderStatusList);
		orderFilterListView.setAdapter(filterAdapter);
		
		monthSelector = new PopupWindow(popupWindow,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,true);
		
		orderFilterListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				currentFilterStatus = filterAdapter.getItem(position);
				orderFilterTypeTextView.setText(currentFilterStatus.getOrderStatusName());
				currentPage = 0;
				setOrdersView();
				ordersList.clear();
				ordersListView.onLoadMoreComplete(true);
			}
		});
	}
	
	private void popupWindowShowing(){
		int parentY = (int) orderFilterLinearLayout.getHeight();
		monthSelector.showAtLocation(orderFilterLinearLayout, Gravity.CENTER_HORIZONTAL | Gravity.TOP, 
				0, GlobalVariables.Screen_Height * 50/ 640);
		System.out.println(parentY);
		
//		monthSelector.showAsDropDown(orderFilterTypeTextView, 0 - width/2, -6);
	}
	
	private void popupWindowDismiss(){
		monthSelector.dismiss();
	}
	
	
	public void setOrderList(List<Order> orderList){
		ordersList.addAll(orderList);
		adapter.notifyDataSetChanged();
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
		adapter = new OrderListAdapter(this, ordersList);
		ordersListView.setAdapter(adapter);
		
		addListeners();
		initPopupWindow();
	}
	
	private void addListeners(){
		ordersListView.setOnRefreshListener(new OnRefreshLoadingMoreListener() {
			@Override
			public void onLoadMore() {
				setOrdersView();
			}
		});
		ordersListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Order selectedOrder = adapter.getItem(position);
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
				popupWindowShowing();
			}
		});
	}
	
	private class OrderFilterAdapter extends BaseAdapter{
		private List<OrderFilterStatus> filters;
		private Context context;
		public OrderFilterAdapter(Context context, List<OrderFilterStatus> orderStatus){
			this.context = context;
			this.filters = orderStatus;
		}
		@Override
		public int getCount() {
			return filters.size();
		}
		
		@Override
		public OrderFilterStatus getItem(int position) {
			return filters.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_order_filter, null);
			TextView filterItemTextView = (TextView) convertView.findViewById(R.id.orderfilter_filter_textView);
			OrderFilterStatus orderStatus = getItem(position);
			
			if(orderStatus == currentFilterStatus)
				filterItemTextView.setSelected(true);
			else
				filterItemTextView.setSelected(false);
			filterItemTextView.setText(orderStatus.getOrderStatusName());
			
	        return convertView;  
		}
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
