package com.boding.app;

import java.util.List;

import com.boding.R;
import com.boding.constants.ActivityNumber;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.FlightQuery;
import com.boding.model.LowPriceSubscribe;
import com.boding.task.LowPriceSubscribeTask;
import com.boding.util.CityUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LowPriceSubscribeActivity extends Activity{
	private ListView lowPriceListView;
	private LinearLayout addSubsribeLinearLayout;
	private LowPriceSubAdapter adapter;
	private int deletedPos;
	
	private WarningDialog warningDialog;
	private ProgressBarDialog progressBarDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lowprice_subscribe);
		warningDialog = new WarningDialog(this);
		progressBarDialog = new ProgressBarDialog(this);
		initView();
		
		setViewContent();
	}
	
	public void setLowPriceResultList(List<LowPriceSubscribe> subsList){
		adapter = new LowPriceSubAdapter(this, subsList);
		lowPriceListView.setAdapter(adapter);
		progressBarDialog.dismiss();
	}
	
	private void setViewContent(){
		progressBarDialog = new ProgressBarDialog(this);
		progressBarDialog.show();
		
		(new LowPriceSubscribeTask(this, HTTPAction.GET_LOWPRICESUBS_LIST)).execute();
	}
	
    private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(LowPriceSubscribeActivity.this, IntentRequestCode.LOWPRICE_SUBSCRIBE);
			}
			
		});
		
		lowPriceListView = (ListView) findViewById(R.id.lowpricesubscribe_listView);
		addSubsribeLinearLayout = (LinearLayout) findViewById(R.id.lowpricesubscribe_addsubscribe_linearLayout);
		
		addSubsribeLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(adapter.getCount() == 2){
					warningDialog.setContent("最多只能订阅两条航线");
					warningDialog.show();
					return;
				}
				Intent intent = new Intent();
				intent.setClass(LowPriceSubscribeActivity.this, AddLowpriceSubsActivity.class);
				startActivityForResult(intent, IntentRequestCode.ADD_LOWPRICESUBS.getRequestCode());
			}
		});
	}
    
    private class LowPriceSubAdapter extends BaseAdapter{
    	private List<LowPriceSubscribe> subsList;
        private Context context;
        
    	public LowPriceSubAdapter(Context context,  List<LowPriceSubscribe> subsList) {
    		this.context = context;
    		this.subsList = subsList;
    	}
		@Override
		public int getCount() {
			return subsList.size();
		}
		@Override
		public LowPriceSubscribe getItem(int position) {
			return subsList.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void deleteSubscribe(){
			subsList.remove(deletedPos);
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_lowprice_subscribe, null);
	            holder = new ViewHolder();  
	            
	            holder.flyFromToTextView = (TextView) convertView.findViewById(R.id.lowpricesubscribe_flyfromto_textView);
	            holder.fromToDateTextView = (TextView) convertView.findViewById(R.id.lowpricesubscribe_subscribefromtodate_textView);
	            holder.originPriceTextView = (TextView) convertView.findViewById(R.id.lowpricesubscribe_originprice_textView);
	            holder.subscribeDiscountTextView = (TextView) convertView.findViewById(R.id.lowpricesubscribe_subscribediscount_textView);
	            holder.deleteSubscribeLinearLayout = (LinearLayout) convertView.findViewById(R.id.lowpricesubscribe_deletesubscribe_linearLayout);
	            holder.dateTextView = (TextView) convertView.findViewById(R.id.lowpricesubscribe_date_textView);
	            holder.priceTextView = (TextView) convertView.findViewById(R.id.lowpricesubscribe_price_textView);
	            holder.viewDetailLinearLayout = (LinearLayout) convertView.findViewById(R.id.lowpricesubscribe_viewdetail_linearLayout);
	            holder.discountTextView = (TextView) convertView.findViewById(R.id.lowpricesubscribe_discount_textView);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			final LowPriceSubscribe subscribe = getItem(position);
			final String fromCity = CityUtil.getCityNameByCode(subscribe.getLeaveCode());
			final String toCity = CityUtil.getCityNameByCode(subscribe.getArriveCode()); 
					
			holder.flyFromToTextView.setText(fromCity+" - "+toCity);
			if(subscribe.getFlightEndDate().equals(""))
				holder.fromToDateTextView.setText(subscribe.getFlightBeginDate());
			else
				holder.fromToDateTextView.setText(subscribe.getFlightBeginDate()+"至"+subscribe.getFlightEndDate());
//            holder.originPriceTextView.setText(subscribe.getPrice()+"");
//            holder.subscribeDiscountTextView.setText(people.getCardNumber());
//            holder.dateTextView.setText(text);
            holder.priceTextView.setText("￥"+subscribe.getPrice());
            holder.discountTextView.setText(subscribe.getDisCount()+"折");
            holder.deleteSubscribeLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					deletedPos = position;
					(new LowPriceSubscribeTask(LowPriceSubscribeActivity.this, HTTPAction.DELETE_LOWPRICESUB)).execute(subscribe.getId());
				}
			});
            
            holder.viewDetailLinearLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent();
					intent.setClass(LowPriceSubscribeActivity.this, TicketSearchResultActivity.class);
					FlightQuery fq = new FlightQuery();
					String fromcity =fromCity;
					String tocity = toCity;
					fq.setFromcity(fromcity);
					fq.setTocity(tocity);
					fq.setStartdate(subscribe.getFlightBeginDate());
					boolean isSingleWay = subscribe.getTripType() == 1; 
					if(!isSingleWay){
						fq.setReturndate(subscribe.getFlightEndDate());
					}
					GlobalVariables.From_City = fromcity;
					GlobalVariables.To_City = tocity;
					Bundle bundle = new Bundle();
					bundle.putParcelable(IntentExtraAttribute.FLIGHT_QUERY, fq);
					bundle.putBoolean(Constants.IS_SINGLE_WAY, isSingleWay);
					bundle.putInt(IntentExtraAttribute.PREVIOUS_ACTIVITY, ActivityNumber.MAIN.getActivityNum());
					bundle.putString(IntentExtraAttribute.CLASS_INFO, Constants.COUCH_CLASS);
					intent.putExtras(bundle);
					startActivityForResult(intent,IntentRequestCode.TICKET_SEARCH.getRequestCode());
				}
			});
            
	        return convertView;  
		}

		private class ViewHolder {
			TextView flyFromToTextView;
			TextView fromToDateTextView;
			TextView originPriceTextView;
			TextView subscribeDiscountTextView;
			LinearLayout deleteSubscribeLinearLayout;
			TextView dateTextView;
			TextView priceTextView;
			LinearLayout viewDetailLinearLayout;
			TextView discountTextView;
		}
    }
    
    public void setDeleteResult(boolean isSuccess){
    	progressBarDialog.dismiss();
    	if(isSuccess){
    		adapter.deleteSubscribe();
    	}else{
    		warningDialog.setContent("删除订阅失败");
    		warningDialog.show();
    	}
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		if(requestCode==IntentRequestCode.ADD_LOWPRICESUBS.getRequestCode()){
			if(data.getExtras() == null)
				return;
			if(data.getExtras().containsKey(IntentExtraAttribute.ADD_LOWPRICESUB)){
				setViewContent();
			}
		}
	}
}
