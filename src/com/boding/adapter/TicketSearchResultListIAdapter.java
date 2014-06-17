package com.boding.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.app.MainActivity;
import com.boding.app.OrderFormActivity;
import com.boding.app.TicketSearchResultIActivity;
import com.boding.app.VoiceSearchActivity;
import com.boding.constants.IntentRequestCode;
import com.boding.model.AirlineView;
import com.boding.model.FlightLine;
import com.boding.util.Util;

public class TicketSearchResultListIAdapter extends BaseAdapter {
	private LayoutInflater inflater;  
	private AirlineView airlineView;
    private List<FlightLine> flightLineList;
    private Context context;
    private FlightLineFilter flightlineFilter;
	
	public TicketSearchResultListIAdapter(Context context, AirlineView airlineView) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
//		this.airlineViewList = new ArrayList<AirlineView>();
		this.airlineView = airlineView;
		this.flightLineList = airlineView.getLines();
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
            holder.moreClassInfoLinearLayout = (LinearLayout) convertView.findViewById(R.id.ticket_search_i_moreflightclassinfo_linearLayout);
            holder.moreClassListView = (ExpandableListView) convertView.findViewById(R.id.ticket_search_i_moreflightclassinfo_expandListView);
            holder.toOrderLinearLayout = (LinearLayout) convertView.findViewById(R.id.ticket_search_i_toOrder_linearLayout);
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
		holder.flyingTimeTextView.setText(flyingtime);	
		holder.ticketLeftTextView.setText(currentFlightLine.getSeat()); //To edit by class type passed by invoker.
		holder.airlineCompanyTextView.setText(currentFlightLine.getAirCompany());
		holder.airlineCodeTextView.setText(currentFlightLine.getCarrier()+currentFlightLine.getNum());
		//此处设置舱位信息，要根据传入的参数决定。
		holder.startAirportTextView.setText(currentFlightLine.getLeaveAirport());
		holder.endAirportTextView.setText(currentFlightLine.getArriveAirport());
		//image view
		if(currentFlightLine.getSegmentSize() > 1){
			//有中转
			holder.needTransitImageView.setImageResource(R.drawable.line2_2x);
		}else{
			holder.needTransitImageView.setImageResource(R.drawable.line1_2x);
		}
		
		if(currentFlightLine.getFlightClassNum() == 1){
			holder.moreClassInfoLinearLayout.setVisibility(View.GONE);		
		}
		
		holder.moreClassListView.setGroupIndicator(null); 
		holder.moreClassListView.setAdapter(new MoreClassInfoAdapter(context));
		
		holder.toOrderLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, OrderFormActivity.class);
				((TicketSearchResultIActivity)context).startActivityForResult(intent,IntentRequestCode.START_VOICE_SEARCH.getRequestCode());
			}
		});
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
		LinearLayout moreClassInfoLinearLayout; 
		ExpandableListView moreClassListView;
		LinearLayout toOrderLinearLayout;
	}
	
	public Filter getFilter(){
		if(flightlineFilter != null)
			return flightlineFilter;
		else
			return new FlightLineFilter();
	}
	
	public class FlightLineFilter extends Filter{
		private List<String> timeConstraint;
		private List<String> classConstraint;
		private List<String> compConstraint;

		public void setConstraint(List<String> timeConst, List<String> classConst, List<String> compConst){
			this.timeConstraint = timeConst;
			this.classConstraint = classConst;
			this.compConstraint = compConst;
		}
		
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			List<FlightLine> lines = new ArrayList<FlightLine>();
			FlightLine flightline = null;
			boolean tag = false;
			for (Iterator<FlightLine> iterator = flightLineList.iterator(); iterator.hasNext();) {
		        flightline = iterator.next();
		        tag = false;
		        //System.out.println("---> name=" + name);
		        if(timeConstraint.size() >0)
		        	for(String timeSeg : timeConstraint){
		        	//System.out.println(flightline.getLeaveTime() +"   " +timeSeg);
		        		if (Util.IsInTimeSegment(flightline.getLeaveTime(), (String)timeSeg)) {
				          //lines.add(flightline);
				          tag = true;
				          break;
		        		}
		        	}
		        else
		        	tag = true;
		        if(tag) tag = false;
		        else
		        	break;
		        if(classConstraint.size() > 0)
		        	for(String flightclass : classConstraint){
		        	//待添加舱位判断
		        		if (true) {
				          tag = true;
				          break;
		        		}
		        	}
		        else
		        	tag = true;
		        if(tag) tag = false;
		        else
		        	break;
		        if(compConstraint.size() > 0)
		        	for(String company : compConstraint){
		        		System.out.println(flightline.getAirCompany());
		        		if (flightline.getAirCompany().equals(company)) {
				          //lines.add(flightline);
				          tag = true;
				          break;
		        		}
		        	}
		        else
		        	tag = true;
		        if(tag) lines.add(flightline);
		    }
			
		    filterResults.values = lines;
		    return filterResults;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			flightLineList = (List<FlightLine>)results.values;
		    if (results.count > 0) {
		        notifyDataSetChanged();
		    } else {
		        notifyDataSetInvalidated();
		    }
			
		}
		
	}
	
}