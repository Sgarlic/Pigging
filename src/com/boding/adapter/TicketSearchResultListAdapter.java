package com.boding.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.app.TicketSearchResultActivity;
import com.boding.model.domestic.Airlines;
import com.boding.model.domestic.Cabin;
import com.boding.model.domestic.Flight;
import com.boding.util.DateUtil;
import com.boding.util.Util;

public class TicketSearchResultListAdapter extends TicketSearchResultAdapter {
	private LayoutInflater inflater;  
	private Airlines airlineView;
    private List<Flight> flightLineList;
    private Context context;
    private FlightLineFilter flightlineFilter;
	
	public TicketSearchResultListAdapter(Context context, Airlines airlineView) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
//		this.airlineViewList = new ArrayList<AirlineView>();
		this.airlineView = airlineView;
		this.flightLineList = airlineView.getFlights();
//		int sectionPointer = 0;
	}
	
	public boolean isGgroupExpandable(int groupPosition){
		if(getChildrenCount(groupPosition) == 1)
			return false;
		return true;
	}
	
	@Override
	public Cabin getChild(int groupPosition, int childPosition) {
		return getGroup(groupPosition).getFlightClassByPos(childPosition); 
	}

	@Override
    public int getGroupCount() { 
        return flightLineList.size(); 
    } 
 
    @Override
    public int getChildrenCount(int groupPosition) { 
            return getGroup(groupPosition).getFlightClassNum(); 
    } 
 
    @Override
    public Flight getGroup(int groupPosition) { 
            return flightLineList.get(groupPosition); 
    } 
 
    @Override
    public long getGroupId(int groupPosition) { 
            return groupPosition; 
    } 
    @Override
    public boolean hasStableIds() { 
            return true; 
    } 
	
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	// 实例化布局文件
    	GroupViewHolder holder;
		if (convertView == null) {  
            convertView = inflater.inflate(R.layout.list_item_ticket_search_result, null);
            holder = new GroupViewHolder();
            holder.flightStartTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightstarttime_textView);
            holder.flightEndTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightendtime_textView);
            holder.onTimeRateTextView = (TextView) convertView.findViewById(R.id.ticket_search_ontimeRate_textView);
            holder.flightPriceTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightprice_textView);
            holder.ticketLeftTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightticketleft_textView);
            holder.airlineCompanyTextView = (TextView) convertView.findViewById(R.id.ticket_search_airlinecompany_textView);
            holder.airlineCodeTextView = (TextView) convertView.findViewById(R.id.ticket_search_airlinecode_textView);
            holder.planeTypeSizeTextView = (TextView) convertView.findViewById(R.id.ticket_search_planeType_textView);
            holder.flightClassTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightclass_textView);
            holder.startAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_fromAirport_textView);
            holder.endAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_toAirport_textView);
            holder.moreClassInfoLinearLayout = (LinearLayout) convertView.findViewById(R.id.ticket_search_moreflightclassinfo_linearLayout);
            holder.moreClassInfoImageView = (ImageView)convertView.findViewById(R.id.group_more_class_imageView);
            holder.toOrderLinearLayout = (LinearLayout) convertView.findViewById(R.id.ticket_search_toOrder_linearLayout);
            
            convertView.setTag(holder);  
        } else {  
            holder = (GroupViewHolder) convertView.getTag(); 
            
        }  
		final Flight currentFlightLine = getGroup(groupPosition);
		String leavetime = currentFlightLine.getDptTime();
		String arrivetime = currentFlightLine.getArrTime();
		String flyingtime = Util.calculateFlyingtime(currentFlightLine.getDptDate(), currentFlightLine.getArrDate(), leavetime, arrivetime);
		
		holder.flightStartTimeTextView.setText(DateUtil.formatTime(leavetime)+" -");
		holder.flightEndTimeTextView.setText(DateUtil.formatTime(arrivetime));
		holder.flightPriceTextView.setText(currentFlightLine.getFlightPrice());
		if(currentFlightLine.getSeat().equals("A"))
			holder.ticketLeftTextView.setText(">9"); //To edit by class type passed by invoker.
		else
			holder.ticketLeftTextView.setText(currentFlightLine.getSeat());
		holder.airlineCompanyTextView.setText(currentFlightLine.getCarrierName());
		holder.airlineCodeTextView.setText(currentFlightLine.getCarrier()+currentFlightLine.getFlightNum());
		holder.planeTypeSizeTextView.setText("机型"+currentFlightLine.getPlantype());
		//此处设置舱位信息，要根据传入的参数决定。
		holder.startAirportTextView.setText(currentFlightLine.getDptAirportName());
		holder.endAirportTextView.setText(currentFlightLine.getArrAirportName());
		
		if(isExpanded){
			holder.moreClassInfoImageView.setImageResource(R.drawable.arrow_up_orange_small);
		}else{
			holder.moreClassInfoImageView.setImageResource(R.drawable.arrow_down_orange_small);
		}
		
		if(!isGgroupExpandable(groupPosition)){
			holder.moreClassInfoLinearLayout.setVisibility(View.GONE);		
		}
		
		holder.toOrderLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				((TicketSearchResultActivity)context).goToNextActivity(currentFlightLine);
			}
		});
		
        return convertView;  
	}
    
    
    private class GroupViewHolder{
        TextView flightStartTimeTextView;
        TextView flightEndTimeTextView;
        TextView onTimeRateTextView;
        TextView flightPriceTextView;
        TextView ticketLeftTextView;
        TextView airlineCompanyTextView;
        TextView airlineCodeTextView;
        TextView planeTypeSizeTextView;
        TextView flightClassTextView;
        TextView startAirportTextView;
        TextView endAirportTextView;
        LinearLayout moreClassInfoLinearLayout;
        ImageView moreClassInfoImageView;
        LinearLayout toOrderLinearLayout;
	}
    
    private class ChildViewHolder{
    	TextView leftTicketTextView; 
        TextView returnMoneyTextView; 
        TextView discountTextView; 
        TextView priceTextView; 
        ImageView buyImageView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) { 
        // 实例化布局文件
    	ChildViewHolder holder;
		if (convertView == null) {  
            convertView = inflater.inflate(R.layout.elist_item_class_info, null);
            holder = new ChildViewHolder();  
            holder.leftTicketTextView = (TextView)convertView.findViewById(R.id.child_class_left_ticket_textView); 
            holder.returnMoneyTextView = (TextView)convertView.findViewById(R.id.child_class_return_money_textView); 
            holder.discountTextView = (TextView)convertView.findViewById(R.id.child_class_discount_textView); 
            holder.priceTextView = (TextView)convertView.findViewById(R.id.child_class_price_textView); 
            holder.buyImageView  = (ImageView)convertView.findViewById(R.id.child_class_buy_imageView);            
            
            convertView.setTag(holder);  
        } else {  
            holder = (ChildViewHolder) convertView.getTag(); 
            
        }  
       
        Cabin flightClass = getChild(groupPosition, childPosition);
        
        if(flightClass.getStatus().equals("A"))
        	holder.leftTicketTextView.setText(">9");
        else{
        	holder.leftTicketTextView.setText(flightClass.getStatus());
        }
        holder.returnMoneyTextView.setText("5");
//        discountTextView.setTextij");
//        priceTextView.setText(flightClass.getPrice()+"");
        
        return convertView; 
    }
    
    @Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

	@Override
    public long getChildId(int groupPosition, int childPosition) { 
            return childPosition; 
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
			List<Flight> lines = new ArrayList<Flight>();
			Flight flightline = null;
			boolean tag = false;
			for (Iterator<Flight> iterator = flightLineList.iterator(); iterator.hasNext();) {
		        flightline = iterator.next();
		        tag = false;
		        //System.out.println("---> name=" + name);
		        if(timeConstraint.size() >0)
		        	for(String timeSeg : timeConstraint){
		        	//System.out.println(flightline.getLeaveTime() +"   " +timeSeg);
		        		if (Util.IsInTimeSegment(flightline.getDptTime(), (String)timeSeg)) {
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
		        		System.out.println(flightline.getCarrierName());
		        		if (flightline.getCarrierName().equals(company)) {
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
			flightLineList = (List<Flight>)results.values;
		    if (results.count > 0) {
		        notifyDataSetChanged();
		    } else {
		        notifyDataSetInvalidated();
		    }
			
		}
	}
	
	public void orderLinesByLeatime(boolean isAsc) {
		Collections.sort(flightLineList, new Flight.LeatimeComp(isAsc));
		notifyDataSetChanged();
	}

	public void orderLinesByPrice(boolean isAsc) {
		// TODO Auto-generated method stub
		Collections.sort(flightLineList, new Flight.PriceComp(isAsc));
		notifyDataSetChanged();
	}

}