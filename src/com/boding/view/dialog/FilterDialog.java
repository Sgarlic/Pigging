package com.boding.view.dialog;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.app.CitySelectFragment;
import com.boding.constants.GlobalVariables;
import com.boding.model.AirlineView;
import com.boding.model.FlightLine;
import com.boding.util.Util;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class FilterDialog extends Dialog{
	private Context context;
	
	private List<String> timeSegmentList;
	private List<String> classList;
	private List<String> companyList;
	
	private FilterItemListAdapter timeSegmentAdapter;
	private FilterItemListAdapter classAdapter;
	private FilterItemListAdapter companyAdapter;
	
	public FilterDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		setContentView(R.layout.dialog_filter);
		setWidthHeight();
		initView();
	}

	private ImageView timeSegmentImageView;
	private ImageView classImageView;
	private ImageView companyImageView;
	private TabHost tabhost;
	
	private ListView timeSegmentListView;
	private ListView classListView;
	private ListView companyListView;
	
	 private void setWidthHeight(){
			//set dialog width
			WindowManager.LayoutParams lp = this.getWindow().getAttributes();
			lp.width = GlobalVariables.Screen_Width; //设置宽度
			//lp.height = GlobalVariables.Screen_Height;
			
			// set dialog location
			this.getWindow().setGravity(Gravity.LEFT | Gravity.BOTTOM);
			lp.x = 0;
			lp.y = 0;
			
			this.getWindow().setAttributes(lp);
		}
	 
	 public void initView() {
		 timeSegmentList = new ArrayList<String>();
		 timeSegmentList.add("不限");
		 timeSegmentList.add("00:00--6:00");
		 timeSegmentList.add("6:00--12:00");
		 timeSegmentList.add("12:00-18:00");
		 timeSegmentList.add("18:00-24:00");
		 classList = new ArrayList<String>();
		 classList.add("不限");
		 classList.add("经济舱");
		 classList.add("商务舱");
		 classList.add("头等舱");
		 companyList = new ArrayList<String>();
		 companyList.add("不限");
		 companyList.add("上海航空");
		 companyList.add("春秋航空");
		 companyList.add("吉祥航空");
		 companyList.add("东方航空");
		 companyList.add("山东航空");
		 companyList.add("上海航空");
		 companyList.add("春秋航空");
		 companyList.add("吉祥航空");
		 companyList.add("东方航空");
		 companyList.add("山东航空");
		 
		 tabhost = (TabHost) findViewById(R.id.filter_dialog_tabhost);
		 tabhost.setup();
		 tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("第一个标签").setContent(R.id.filter_flighttimesegment_tab));
	     tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("第二个标签").setContent(R.id.filter_flightclass_tab));
	     tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("第三个标签").setContent(R.id.filter_flightcompnay_tab));
	     

	     timeSegmentImageView = (ImageView)findViewById(R.id.filter_flightTimeSegment_imageView);
		 classImageView = (ImageView)findViewById(R.id.filter_flightclass_imageView);
		 companyImageView = (ImageView)findViewById(R.id.filter_flightcompany_imageView);
		 
		 timeSegmentImageView.setOnClickListener(tabOnClickListener);
		 
		 classImageView.setOnClickListener(tabOnClickListener);
		 
		 companyImageView.setOnClickListener(tabOnClickListener);
		 timeSegmentImageView.setSelected(true);
		 
		 
		 timeSegmentListView = (ListView) findViewById(R.id.filter_flighttimesegment_ListView);
		 classListView = (ListView) findViewById(R.id.filter_flightclasst_ListView);
		 companyListView = (ListView) findViewById(R.id.filter_flightcompany_ListView);
		 
		 
		 timeSegmentAdapter = new FilterItemListAdapter(context,timeSegmentList,false);
		 classAdapter = new FilterItemListAdapter(context,classList,false);
		 companyAdapter = new FilterItemListAdapter(context,companyList,true);
		 
		 timeSegmentListView.setAdapter(timeSegmentAdapter);
		 classListView.setAdapter(classAdapter);
		 companyListView.setAdapter(companyAdapter);
	 }
	 
	 View.OnClickListener tabOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			timeSegmentImageView.setSelected(false);
			classImageView.setSelected(false);
			companyImageView.setSelected(false);
		    if(view.getId() == R.id.filter_flightTimeSegment_imageView){
			     tabhost.setCurrentTab(0);
			     timeSegmentImageView.setSelected(true);
			 } else if(view.getId() == R.id.filter_flightclass_imageView){
			     tabhost.setCurrentTab(1);
			     classImageView.setSelected(true);
			 } else if(view.getId() == R.id.filter_flightcompany_imageView){
			     tabhost.setCurrentTab(2);
			     companyImageView.setSelected(true);
			 }
		}
	};
	
	private class FilterItemListAdapter extends BaseAdapter {
		private List<String> itemList;
		private Context context;
		private boolean showCompanyLogo;
		public FilterItemListAdapter(Context context, List itemList, boolean showCompanyLogo) {
			this.context = context;
			this.itemList = itemList;
			this.showCompanyLogo = showCompanyLogo;
		}
		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public String getItem(int position) {
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_filter, null);
	            holder = new ViewHolder();  
	            
	            holder.filterItemLinearLayout = (LinearLayout) convertView.findViewById(R.id.filter_item_linearLayout);
	            holder.filterItemCompanyImageView = (ImageView) convertView.findViewById(R.id.filter_companyicon_imageView);
	            holder.filterItemTextView = (TextView) convertView.findViewById(R.id.filter_item_textView);
	            holder.filterItemCheckBox = (CheckBox) convertView.findViewById(R.id.filter_item_checkBox);
	            
	            holder.filterItemTextView.setText(getItem(position));
	            
	            holder.filterItemLinearLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(holder.filterItemCheckBox.isChecked()){
							holder.filterItemCheckBox.setChecked(false);
						}else{
							holder.filterItemCheckBox.setChecked(true);
						}
					}
				});
	            
	            if(showCompanyLogo){
	            	// show corresponding company logo
	            }else{
	            	holder.filterItemCompanyImageView.setVisibility(View.INVISIBLE);
	            }
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
	        return convertView;  
		}
		
		private class ViewHolder {
			LinearLayout filterItemLinearLayout;
			ImageView filterItemCompanyImageView;
			TextView filterItemTextView;
			CheckBox filterItemCheckBox;
//			TextView flightEndTimeTextView;
//			TextView ontimeRateTextView;
//			TextView flightPriceTextView;
//			TextView fromAirportTextView;
//			TextView toAirportTextView;
//			TextView ticketLeftTextView;
//			TextView airlineCompanyTextView;
//			TextView airlineCodeTextView;
//			TextView planTypeTextView;
//			TextView flightClassTextView;
//			LinearLayout moreClassInfoLinearLayout; 
			//ExpandableListView moreClassListView;
		}
	}
}
