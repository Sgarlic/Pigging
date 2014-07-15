package com.boding.app;

import java.util.List;

import com.boding.R;
import com.boding.constants.IntentRequestCode;
import com.boding.model.LowPriceSubscribe;
import com.boding.util.Util;

import android.app.Activity;
import android.content.Context;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutboding);
		initView();
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
	}
    
    private class LowPriceSubAdapter extends BaseAdapter{
    	private LayoutInflater inflater;  
    	private List<LowPriceSubscribe> subsList;
        private Context context;
        
    	public LowPriceSubAdapter(Context context,  List<LowPriceSubscribe> subsList) {
    		this.context = context;
    		this.inflater = LayoutInflater.from(context);
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
		
		public void addSubscribe(LowPriceSubscribe subscribe){
			subsList.add(subscribe);
			notifyDataSetChanged();
		}
		
		public void deleteSubscribe(int position){
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
			holder.flyFromToTextView.setText(subscribe.getLeaveName()+" - "+subscribe.getArriveName());
            holder.fromToDateTextView.setText(subscribe.getFlightBeginDate()+"жа"+subscribe.getFlightEndDate());
            holder.originPriceTextView.setText(subscribe.getPrice()+"");
//            holder.subscribeDiscountTextView.setText(people.getCardNumber());
//            holder.dateTextView.setText(text);
            holder.priceTextView.setText(subscribe.getPrice()*subscribe.getDisCount()/100+"");
            holder.discountTextView.setText(subscribe.getDisCount()+"");
            holder.deleteSubscribeLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					subsList.remove(position);
//					setInsusrance();
//					notifyDataSetChanged();
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
}
