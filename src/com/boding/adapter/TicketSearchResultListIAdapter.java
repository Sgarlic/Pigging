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
import com.boding.model.AirlineView;
import com.boding.model.FlightLine;

public class TicketSearchResultListIAdapter extends BaseAdapter {
	private LayoutInflater inflater;  
    private List<FlightLine> flightLineList;
	
	public TicketSearchResultListIAdapter(Context context, List<FlightLine> flightLineList) {
		this.inflater = LayoutInflater.from(context);
//		this.airlineViewList = new ArrayList<AirlineView>();
		
		this.flightLineList = flightLineList;
//		int sectionPointer = 0;
	}
	
	@Override
	public int getCount() {
		return flightLineList.size();
	}

	@Override
	public FlightLine getItem(int position) {
		return flightLineList.get(position);
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
            holder.flightStartTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightstarttime_textView);
            holder.flightEndTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightendtime_textView);
            holder.flightPriceTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightprice_textView);
            holder.flyingTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightflyingtime_textView);
            holder.ticketLeftTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightticketleft_textView);
            holder.airlineCompanyTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_airlinecompany_textView);
            holder.airlineCodeTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_airlinecode_textView);
            holder.flightClassTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightclass_textView);
            holder.startAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightstartairport_textView);
            holder.endAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightstopairport_textView);
            holder.needTransitImageView = (ImageView) convertView.findViewById(R.id.ticket_search_i_need_transit_imageView);
            holder.moreClassInfoImageView = (ImageView) convertView.findViewById(R.id.ticket_search_i_moreflightclassinfo_linearLayout); 
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
		
		FlightLine currentFlightLine = flightLineList.get(position);
		holder.flightStartTimeTextView.setText(currentFlightLine.getDepartureTime());
		holder.flightEndTimeTextView.setText(currentFlightLine.getArriveTime());
		holder.flightPriceTextView.setText(currentFlightLine.getFlightPrice());
		
//        ContentValues cv = airlineViewList.get(position);  
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