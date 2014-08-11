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
    private List<Flight> originList;
	
	public TicketSearchResultListAdapter(Context context, Airlines airlineView) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
//		this.airlineViewList = new ArrayList<AirlineView>();
		this.airlineView = airlineView;
		this.originList = this.flightLineList = airlineView.getFlights();
//		int sectionPointer = 0;
	}
	
	public boolean isGgroupExpandable(int groupPosition){
		if(getChildrenCount(groupPosition) < 1)
			return false;
		return true;
	}
	
	@Override
	public Cabin getChild(int groupPosition, int childPosition) {
		return getGroup(groupPosition).getSelectedCabins().get(childPosition+1);
	}

	@Override
    public int getGroupCount() { 
        return flightLineList.size(); 
    } 
 
    @Override
    public int getChildrenCount(int groupPosition) { 
        return getGroup(groupPosition).getSelectedCabins().size()-1; 
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	// 实例化布局文件
    	GroupViewHolder holder;
		if (convertView == null) {  
            convertView = inflater.inflate(R.layout.list_item_ticket_search_result, null);
            holder = new GroupViewHolder();
            holder.flightStartTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightstarttime_textView);
            holder.flightEndTimeTextView = (TextView) convertView.findViewById(R.id.ticket_search_flightendtime_textView);
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
		String flyingtime = DateUtil.calculateFlyingtime(currentFlightLine.getDptDate(), currentFlightLine.getArrDate(), leavetime, arrivetime);
		
		holder.flightStartTimeTextView.setText(DateUtil.formatTime(leavetime)+" -");
		holder.flightEndTimeTextView.setText(DateUtil.formatTime(arrivetime));
		holder.flightClassTextView.setText(currentFlightLine.getCabins().get(currentFlightLine.getDefaultShowedCabinPos()).getCabinName());
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
		}else{
			holder.moreClassInfoLinearLayout.setVisibility(View.VISIBLE);
			holder.moreClassInfoLinearLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(onColExpClickListener!=null){
						onColExpClickListener.ColExp(groupPosition);
					}
				}
			});
		}
//		holder.toOrderLinearLayout.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				((TicketSearchResultActivity)context).goToNextActivity(currentFlightLine);
//			}
//		});
		
        return convertView;  
	}
    
    
    private class GroupViewHolder{
        TextView flightStartTimeTextView;
        TextView flightEndTimeTextView;
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
            
            final Flight currentFlightLine = getGroup(groupPosition);
            
            final int pos = childPosition;
//            holder.buyImageView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					currentFlightLine.setSelectedCabinPos(pos+1);
//					System.out.println("HEEELOO" + currentFlightLine.getSelectedCabins().size());
//					((TicketSearchResultActivity)context).goToNextActivity(currentFlightLine);
//				}
//			});
            
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
        holder.priceTextView.setText(String.valueOf((int)flightClass.getAdultPrice()));
        holder.discountTextView.setText(flightClass.getCabinName());
        holder.returnMoneyTextView.setText("5");
//        discountTextView.setTextij");
//        priceTextView.setText(flightClass.getPrice()+"");
        
        return convertView; 
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
			//System.out.println(originList.size());
			for (Iterator<Flight> iterator = originList.iterator(); iterator.hasNext();) {
		        flightline = iterator.next();
		        tag = false;
		        //System.out.println("---> name=" + flightline.getDptTime());
		        if(timeConstraint.size() >0)
		        	for(String timeSeg : timeConstraint){
		        	//System.out.println(flightline.getLeaveTime() +"   " +timeSeg);
		        		if (DateUtil.IsInTimeSegment(flightline.getDptTime(), (String)timeSeg)) {
				          //lines.add(flightline);
				          tag = true;
				          break;
		        		}
		        	}
		        else
		        	tag = true;
		        if(tag) tag = false;
		        else
		        	continue;
		        if(classConstraint.size() > 0)
		        	for(String flightclass : classConstraint){
		        	//待添加舱位判断
		        		//System.out.println(flightclass);
		        		flightline.setDefaultShowedCabins(flightclass);
		        		//System.out.println("%%%%%%%%%%" + flightline.getSelectedCabins().size());
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
		        	continue;
		        if(compConstraint.size() > 0)
		        	for(String company : compConstraint){
		        		//System.out.println(flightline.getCarrierName());
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
			
			int size = (flightLineList != null) ? flightLineList.size() : 0;
		    if (size > 0) {
		        notifyDataSetChanged();
		        ((TicketSearchResultActivity)context).hideNoResult();
		    }else if(size == 0){
		    	((TicketSearchResultActivity)context).showNoResult();
		    }else {
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