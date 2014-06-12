package com.boding.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.model.AirlineView;
import com.boding.model.FlightLine;
import com.boding.util.Util;

public class TicketSearchResultListAdapter extends BaseAdapter {
	private LayoutInflater inflater;  
	private AirlineView airlineView;
    private List<FlightLine> flightLineList;
    private Context context;
	
	public TicketSearchResultListAdapter(Context context, AirlineView airlineView) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.airlineView = airlineView;
		this.flightLineList = airlineView.getLines();
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
            holder.ontimeRateTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightflyingtime_textView);
            holder.flightPriceTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightprice_textView);
            holder.fromAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_fromAirport_textView);
            holder.toAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_toAirport_textView);
            holder.ticketLeftTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_ontimeRate_textView);
            holder.airlineCompanyTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_airlinecompany_textView);
            holder.airlineCodeTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_airlinecode_textView);
            holder.planTypeTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_planeType_textView);
            holder.flightClassTextView = (TextView) convertView.findViewById(R.id.ticket_search_i_flightclass_textView);
            holder.moreClassInfoLinearLayout = (LinearLayout) convertView.findViewById(R.id.ticket_search_i_moreflightclassinfo_linearLayout);
            // holder.moreClassListView = (ExpandableListView) convertView.findViewById(R.id.ticket_search_i_moreflightclassinfo_expandListView);
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
		
		FlightLine currentFlightLine = flightLineList.get(position);
		String leavetime = currentFlightLine.getLeaveTime();
		String arrivetime = currentFlightLine.getArriveTime();
		String flyingtime = Util.calculateFlyingtime(currentFlightLine.getLeaveDate(), currentFlightLine.getArriveDate(), leavetime, arrivetime);
		
		holder.flightStartTimeTextView.setText(Util.formatTime(leavetime)+" -");
		holder.flightEndTimeTextView.setText(Util.formatTime(arrivetime));
		holder.flightPriceTextView.setText(currentFlightLine.getFlightPrice());
		//holder.flyingTimeTextView.setText(flyingtime);	
		holder.ticketLeftTextView.setText(currentFlightLine.getSeat()); //To edit by class type passed by invoker.
		holder.airlineCompanyTextView.setText(currentFlightLine.getAirCompany());
		holder.airlineCodeTextView.setText(currentFlightLine.getCarrier()+currentFlightLine.getNum());
		//此处设置舱位信息，要根据传入的参数决定。
		//holder.startAirportTextView.setText(currentFlightLine.getLeaveAirport());
		//holder.endAirportTextView.setText(currentFlightLine.getArriveAirport());
		//image view
		if(currentFlightLine.getFlightClassNum() == 1){
			holder.moreClassInfoLinearLayout.setVisibility(View.GONE);		
		}
		
//		holder.moreClassListView.setGroupIndicator(null); 
//		holder.moreClassListView.setAdapter(new MoreClassInfoAdapter(context));
		
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
		TextView ontimeRateTextView;
		TextView flightPriceTextView;
		TextView fromAirportTextView;
		TextView toAirportTextView;
		TextView ticketLeftTextView;
		TextView airlineCompanyTextView;
		TextView airlineCodeTextView;
		TextView planTypeTextView;
		TextView flightClassTextView;
		LinearLayout moreClassInfoLinearLayout; 
		//ExpandableListView moreClassListView;
	}
	
}