package com.boding.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.app.OrderFormActivity;
import com.boding.app.TicketSearchResultActivity;
import com.boding.constants.IntentRequestCode;
import com.boding.model.AirlineView;
import com.boding.model.FlightClass;
import com.boding.model.FlightLine;
import com.boding.util.DateUtil;
import com.boding.util.Util;

public class TicketSearchResultListIAdapter extends TicketSearchResultAdapter {
	private LayoutInflater inflater;  
	private AirlineView airlineView;
	private List<FlightLine> flightLineList;
    private Context context;
    private FlightLineFilter flightlineFilter;
    private List<FlightLine> originList;
    
	public TicketSearchResultListIAdapter(Context context, AirlineView airlineView) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.airlineView = airlineView;
		this.originList = this.flightLineList = airlineView.getLines();
	}
	
	public boolean isGgroupExpandable(int groupPosition){
		if(getChildrenCount(groupPosition) == 1)
			return false;
		return true;
	}
	
	@Override
	public FlightClass getChild(int groupPosition, int childPosition) {
		return getGroup(groupPosition).getSelectedCabins().get(childPosition);
	}

	@Override
    public int getGroupCount() { 
        return flightLineList.size(); 
    } 
 
    @Override
    public int getChildrenCount(int groupPosition) { 
            return getGroup(groupPosition).getSelectedCabins().size();
    } 
 
    @Override
    public FlightLine getGroup(int groupPosition) { 
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
            convertView = inflater.inflate(R.layout.list_item_ticket_search_result_i, null);
            holder = new GroupViewHolder();
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
            holder.moreClassInfoImageView = (ImageView)convertView.findViewById(R.id.group_more_class_imageView);
            holder.toOrderLinearLayout = (LinearLayout) convertView.findViewById(R.id.ticket_search_i_toOrder_linearLayout);
            
            
            convertView.setTag(holder);  
        } else {  
            holder = (GroupViewHolder) convertView.getTag(); 
            
        }  
    	
		final FlightLine currentFlightLine = getGroup(groupPosition);
		String leavetime = currentFlightLine.getLeaveTime();
		String arrivetime = currentFlightLine.getArriveTime();
		String flyingtime = Util.calculateFlyingtime(currentFlightLine.getLeaveDate(), currentFlightLine.getArriveDate(), leavetime, arrivetime);
		
		holder.flightStartTimeTextView.setText(DateUtil.formatTime(leavetime)+" -");
		holder.flightEndTimeTextView.setText(DateUtil.formatTime(arrivetime));
		holder.flightPriceTextView.setText(currentFlightLine.getFlightPrice());
		holder.flyingTimeTextView.setText(flyingtime);	
		//holder.flightClassTextView.setText(currentFlightLine.getCurrentClass());
		holder.ticketLeftTextView.setText(currentFlightLine.getSeat()); //To edit by class type passed by invoker.
		holder.airlineCompanyTextView.setText(currentFlightLine.getLeaveAirCompany());
		holder.airlineCodeTextView.setText(currentFlightLine.getLeaveCarrier()+currentFlightLine.getLeaveFlightNum());
		//此处设置舱位信息，要根据传入的参数决定。
		holder.startAirportTextView.setText(currentFlightLine.getLeaveAirport());
		holder.endAirportTextView.setText(currentFlightLine.getArriveAirport());
		//image view
		if(currentFlightLine.getSegmentSize() > 1){
			//有中转
			holder.needTransitImageView.setImageResource(R.drawable.line_transit);
		}else{
			holder.needTransitImageView.setImageResource(R.drawable.line_notransit);
		}
		
		if(isExpanded){
			holder.moreClassInfoImageView.setImageResource(R.drawable.arrow_up_orange_small);
		}else{
			holder.moreClassInfoImageView.setImageResource(R.drawable.arrow_down_orange_small);
		}
		
		if(!isGgroupExpandable(groupPosition)){
			holder.moreClassInfoLinearLayout.setVisibility(View.GONE);		
		}else{
			holder.moreClassInfoLinearLayout.setVisibility(View.VISIBLE);	
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
       
        FlightClass flightClass = getChild(groupPosition, childPosition);
        
        holder.leftTicketTextView.setText(">9");
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
			List<FlightLine> lines = new ArrayList<FlightLine>();
			FlightLine flightline = null;
			boolean tag = false;
			for (Iterator<FlightLine> iterator = originList.iterator(); iterator.hasNext();) {
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
			        		//System.out.println(flightclass);
			        		flightline.setDefaultShowedCabins(flightclass);
			        		System.out.println("%%%%%%%%%%" + flightline.getSelectedCabins().size());
			        		if (flightline.getSelectedCabins().size() > 0) {
					          tag = true;
					          break;
			        		}
			        }
		        else{
		        	flightline.resetShowedCabins();
		        	tag = true;
		        }
		        if(tag) tag = false;
		        else
		        	break;
		        if(compConstraint.size() > 0)
		        	for(String company : compConstraint){
		        		System.out.println(flightline.getLeaveAirCompany());
		        		if (flightline.getLeaveAirCompany().equals(company)) {
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
	
	public void orderLinesByLeatime(boolean isAsc){
		Collections.sort(flightLineList, new FlightLine.LeatimeComp(isAsc));
	}
	
	public void orderLinesByPrice(boolean isAsc){
		Collections.sort(flightLineList, new FlightLine.PriceComp(isAsc));
	}
}