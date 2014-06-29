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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.app.MainActivity;
import com.boding.app.OrderFormActivity;
import com.boding.app.TicketSearchResultActivity;
import com.boding.app.VoiceSearchActivity;
import com.boding.constants.IntentRequestCode;
import com.boding.model.AirlineView;
import com.boding.model.FlightClass;
import com.boding.model.FlightLine;
import com.boding.util.Util;

public class TicketSearchResultListAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater;  
	private AirlineView airlineView;
    private List<FlightLine> flightLineList;
    private Context context;
    private FlightLineFilter flightlineFilter;
	
	public TicketSearchResultListAdapter(Context context, AirlineView airlineView) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
//		this.airlineViewList = new ArrayList<AirlineView>();
		this.airlineView = airlineView;
		this.flightLineList = airlineView.getLines();
//		int sectionPointer = 0;
	}
	
	public boolean isGgroupExpandable(int groupPosition){
		if(getChildrenCount(groupPosition) == 1)
			return false;
		return true;
	}
	
	@Override
	public FlightClass getChild(int groupPosition, int childPosition) {
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
    	convertView = (View) LayoutInflater.from(context).inflate(R.layout.list_item_ticket_search_result, null); 
        TextView flightStartTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightstarttime_textView);
        TextView flightEndTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightendtime_textView);
        TextView onTimeRateTextView = (TextView) convertView.findViewById(R.id.ticket_search_ontimeRate_textView);
        TextView flightPriceTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightprice_textView);
        TextView ticketLeftTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightticketleft_textView);
        TextView airlineCompanyTextView = (TextView) convertView.findViewById(R.id.ticket_search_airlinecompany_textView);
        TextView airlineCodeTextView = (TextView) convertView.findViewById(R.id.ticket_search_airlinecode_textView);
        TextView planeTypeSizeTextView = (TextView) convertView.findViewById(R.id.ticket_search_planeType_textView);
        TextView flightClassTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightclass_textView);
        TextView startAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_fromAirport_textView);
        TextView endAirportTextView = (TextView) convertView.findViewById(R.id.ticket_search_toAirport_textView);

        LinearLayout moreClassInfoLinearLayout = (LinearLayout) convertView.findViewById(R.id.ticket_search_moreflightclassinfo_linearLayout);
        ImageView moreClassInfoImageView = (ImageView)convertView.findViewById(R.id.group_more_class_imageView);
        LinearLayout toOrderLinearLayout = (LinearLayout) convertView.findViewById(R.id.ticket_search_toOrder_linearLayout);
		
		FlightLine currentFlightLine = getGroup(groupPosition);
		String leavetime = currentFlightLine.getLeaveTime();
		String arrivetime = currentFlightLine.getArriveTime();
		String flyingtime = Util.calculateFlyingtime(currentFlightLine.getLeaveDate(), currentFlightLine.getArriveDate(), leavetime, arrivetime);
		
		flightStartTimeTextView.setText(Util.formatTime(leavetime)+" -");
		flightEndTimeTextView.setText(Util.formatTime(arrivetime));
		flightPriceTextView.setText(currentFlightLine.getFlightPrice());
		ticketLeftTextView.setText(currentFlightLine.getSeat()); //To edit by class type passed by invoker.
		airlineCompanyTextView.setText(currentFlightLine.getAirCompany());
		airlineCodeTextView.setText(currentFlightLine.getCarrier()+currentFlightLine.getNum());
		//此处设置舱位信息，要根据传入的参数决定。
		startAirportTextView.setText(currentFlightLine.getLeaveAirport());
		endAirportTextView.setText(currentFlightLine.getArriveAirport());
		
		if(isExpanded){
			moreClassInfoImageView.setImageResource(R.drawable.arrow_up_orange_small);
		}else{
			moreClassInfoImageView.setImageResource(R.drawable.arrow_down_orange_small);
		}
		
		if(!isGgroupExpandable(groupPosition)){
			moreClassInfoLinearLayout.setVisibility(View.GONE);		
		}
		
		toOrderLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, OrderFormActivity.class);
				((TicketSearchResultActivity)context).startActivityForResult(intent,IntentRequestCode.ORDER_FORM.getRequestCode());
			}
		});
		
		
		
        return convertView;  
	}
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) { 
            // 实例化布局文件
		View childView = (View) LayoutInflater.from(context).inflate(R.layout.elist_item_class_info, null); 
        TextView leftTicketTextView = (TextView)childView.findViewById(R.id.child_class_left_ticket_textView); 
        TextView returnMoneyTextView = (TextView)childView.findViewById(R.id.child_class_return_money_textView); 
        TextView discountTextView = (TextView)childView.findViewById(R.id.child_class_discount_textView); 
        TextView priceTextView = (TextView)childView.findViewById(R.id.child_class_price_textView); 
        ImageView buyImageView  = (ImageView)childView.findViewById(R.id.child_class_buy_imageView);
        
        FlightClass flightClass = getChild(groupPosition, childPosition);
        
        leftTicketTextView.setText(">9");
        returnMoneyTextView.setText("5");
//        discountTextView.setTextij");
//        priceTextView.setText(flightClass.getPrice()+"");
        
        return childView; 
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