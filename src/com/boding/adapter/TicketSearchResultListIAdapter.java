package com.boding.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boding.R;

public class TicketSearchResultListIAdapter extends BaseAdapter {
	private LayoutInflater inflater;  
    private List<ContentValues> list;
	
	public TicketSearchResultListIAdapter(Context context, List<ContentValues> historyCityList) {
		this.inflater = LayoutInflater.from(context);
		this.list = new ArrayList<ContentValues>();
		int sectionSize = 0;
		
		list.addAll(historyCityList);
//		list.addAll(hotCityList);
//		list.addAll(cityList);
//		
//		sectionSize+=(historyCityList.size()+hotCityList.size()+list.size());
//		
		int sectionPointer = 0;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public ContentValues getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {  
            convertView = inflater.inflate(R.layout.list_item_ticket_search_result_i, null);
            holder = new ViewHolder();  
            holder.flightStartTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightstarttime_i_textView);
            holder.flightEndTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightendtime_i_textView);
            holder.flightPriceTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightprice_i_textView);
            holder.flyingTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightflyingtime_i_textView);
            holder.ticketLeftTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightticketleft_i_textView);
            holder.airlineCompanyTextView = (TextView) convertView.findViewById(R.id.ticket_search_airlinecompany_i_textView);
            holder.airlineCodeTextView = (TextView) convertView.findViewById(R.id.ticket_search_airlinecode_i_textView);
            holder.flightClassTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightclass_i_textView);
            holder.startAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightstartairport_i_textView);
            holder.endAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightstopairport_i_textView);
            holder.needTransitImageView = (ImageView) convertView.findViewById(R.id.ticket_search_i_need_transit_imageView);
            holder.moreClassInfoImageView = (ImageView) convertView.findViewById(R.id.ticket_search_moreflightclassinfo_i_linearLayout); 
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        ContentValues cv = list.get(position);  
//        holder.name.setText(cv.getAsString(CITY_NAME));
//        String currentStr = getContentValuesTitle(cv);
//        String previewStr = (position - 1) >= 0 ? getContentValuesTitle(list.get(position-1)) : " ";
//        if (!previewStr.equals(currentStr)) {  
//            holder.alpha.setVisibility(View.VISIBLE);
//            holder.alpha.setText(currentStr);
//        } else {  
//            holder.alpha.setVisibility(View.GONE);
//        }  
        return convertView;  
	}
	
	private class ViewHolder {
		TextView flightStartTimeTextView;
		TextView flightEndTimeTextView;
		TextView flightPriceTextView;
		TextView flyingTimeTextView;
		TextView ticketLeftTextView;
		TextView airlineCompanyTextView;
		TextView airlineCodeTextView;
		TextView flightClassTextView;
		TextView startAirportTextView;
		TextView endAirportTextView;
		ImageView needTransitImageView;
		ImageView moreClassInfoImageView; 
	}
	
}