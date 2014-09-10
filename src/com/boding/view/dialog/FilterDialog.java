package com.boding.view.dialog;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.boding.R;
import com.boding.app.TicketSearchResultActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.util.Util;

public class FilterDialog extends Dialog{
	private Context context;
	
	private List<String> timeSegmentList;
	private List<String> classList;
	private List<String> companyList;
	
	private FilterItemListAdapter timeSegmentAdapter;
	private FilterItemListAdapter classAdapter;
	private FilterItemListAdapter companyAdapter;

	private ImageView timeSegmentImageView;
	private ImageView classImageView;
	private ImageView companyImageView;
	private TabHost tabhost;
	
	private ListView timeSegmentListView;
	private ListView classListView;
	private ListView companyListView;
	
	private TextView cancelBt;
	private TextView clearBt;
	private TextView okBt;
	
	public FilterDialog(Context context) {
		super(context, Constants.DIALOG_STYLE);
		this.context = context;
		setContentView(R.layout.dialog_filter);
		setWidthHeight();
		initView();
	}
	
	public void setCompanyList(HashSet<String> companies){
		companyList.clear();
		companyList.add("不限");
		companyList.addAll(companies);
		companyAdapter = new FilterItemListAdapter(context,companyList,true);
		companyListView.setAdapter(companyAdapter);
	}
	
	public List<String> getTimeSegmentList(){
		return timeSegmentAdapter.getConstraints();
	}
	
	public List<String> getClassList(){
		return classAdapter.getConstraints();
	}
	
	public List<String> getCompanyList(){
		return companyAdapter.getConstraints();
	}
	
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
		 timeSegmentList.add("00:00--06:00");
		 timeSegmentList.add("06:00--12:00");
		 timeSegmentList.add("12:00--18:00");
		 timeSegmentList.add("18:00--24:00");
		 classList = new ArrayList<String>();
		 classList.add("不限");
		 classList.add("经济舱");
		 classList.add("商务舱");
		 classList.add("头等舱");
		 companyList = new ArrayList<String>();
		 
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
		 
		 timeSegmentListView.setAdapter(timeSegmentAdapter);
		 classListView.setAdapter(classAdapter);
		 
		 
		 cancelBt = (TextView)findViewById(R.id.cancel_filter_textView);
		 clearBt = (TextView)findViewById(R.id.clear_filter_textView);
		 okBt = (TextView)findViewById(R.id.confirm_filter_textView);
		 
		 cancelBt.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				FilterDialog.this.hide();
			}
		 });
		 
		 clearBt.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				timeSegmentAdapter.clearAll();
				classAdapter.clearAll();
				companyAdapter.clearAll();
			}
		 });
		 
		 okBt.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				List<String> timeConstraint = timeSegmentAdapter.getConstraints();
				List<String> classConstraint = classAdapter.getConstraints();
				List<String> companyConstraint = companyAdapter.getConstraints();
				System.out.println(timeConstraint);
				((TicketSearchResultActivity)context).doFilter(timeConstraint,classConstraint, companyConstraint);
				FilterDialog.this.hide();	
			}
		 });
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
		private List<String> constraints;
		private List<CheckBox> checkedBoxs;
		private CheckBox nolimit = null;
		private int[] checkedTag;
		
		public FilterItemListAdapter(Context context, List itemList, boolean showCompanyLogo) {
			this.context = context;
			this.itemList = itemList;
			this.showCompanyLogo = showCompanyLogo;
			this.constraints = new ArrayList<String>();
			this.checkedBoxs = new ArrayList<CheckBox>();
			checkedTag = new int[itemList.size()];
		}
		
		public List<String> getConstraints(){
			return constraints;
		}
		
		public void clearConstraints(){
			for(CheckBox cb : checkedBoxs){
				cb.setChecked(false);
			}
			checkedBoxs.clear();
			constraints.clear();
		}
		
		private void clearCheckedTags(){
			for(int i=0; i<checkedTag.length; ++i)
				checkedTag[i] = 0;
		}
		
		private void clearAll(){
			if(nolimit != null)
				nolimit.setChecked(false);
			clearConstraints();
			clearCheckedTags();
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
			final ViewHolder holder = new ViewHolder(); 
	        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_filter, null);
	            
	        holder.filterItemLinearLayout = (LinearLayout) convertView.findViewById(R.id.filter_item_linearLayout);
	        holder.filterItemCompanyImageView = (ImageView) convertView.findViewById(R.id.filter_companyicon_imageView);
	        holder.filterItemTextView = (TextView) convertView.findViewById(R.id.filter_item_textView);
	        holder.filterItemCheckBox = (CheckBox) convertView.findViewById(R.id.filter_item_checkBox);
	            
	        holder.filterItemLinearLayout.setOnClickListener(new View.OnClickListener() {
        	@Override
				public void onClick(View v) {
					String constraint = "";
					int pos =((Integer)holder.filterItemCheckBox.getTag()).intValue();
					checkedTag[pos] = ~checkedTag[pos];
					System.out.println("*****" + pos);
					if(holder.filterItemCheckBox.isChecked()){
						holder.filterItemCheckBox.setChecked(false);
						
						if(pos != 0){
							checkedBoxs.remove(holder.filterItemCheckBox);
							constraints.remove((String)holder.filterItemTextView.getText());
						}
					}else{
						holder.filterItemCheckBox.setChecked(true);
						if(pos == 0){
							nolimit = holder.filterItemCheckBox;
							clearConstraints();
							clearCheckedTags();
							checkedTag[0] = -1;
						}else{
							if(nolimit != null){
								nolimit.setChecked(false);
								
							}
							checkedTag[0] = 0;
							checkedBoxs.add(holder.filterItemCheckBox);
							constraints.add((String)holder.filterItemTextView.getText());
						}
					}
				}
			});
	            
            holder.filterItemCheckBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					int pos = (Integer)cb.getTag();
					checkedTag[pos] = ~checkedTag[pos];
					if(cb.isChecked()){
						if(pos == 0){
							nolimit = holder.filterItemCheckBox;
							clearConstraints();
							clearCheckedTags();
							checkedTag[0] = -1;
						}else{
							if(nolimit != null){
								nolimit.setChecked(false);
								
							}
							checkedTag[0] = 0;
							checkedBoxs.add(cb);
							constraints.add((String)holder.filterItemTextView.getText());
						}						
					}else{
						if(pos != 0){
							checkedBoxs.remove(cb);
							constraints.remove((String)holder.filterItemTextView.getText());
						}
					}
				}
			});
	           
            
            if(showCompanyLogo){
            	// show corresponding company logo
            	String companyinfo = getItem(position);
            	if(companyinfo.contains("-")){
            		Bitmap image = Util.getFlightCompanyLogo(context, companyinfo.split("-")[1]);
            		holder.filterItemCompanyImageView.setImageBitmap(image);
            	}
            }else{
            	holder.filterItemCompanyImageView.setVisibility(View.INVISIBLE);
            }
            if(showCompanyLogo)
            System.out.println(this.getClass().toString() + ":  "+getItem(position).split("-")[0]);
			holder.filterItemTextView.setText(getItem(position).split("-")[0]);
			holder.filterItemCheckBox.setTag(position);	
			
			
			if(checkedTag[position] == -1){
				holder.filterItemCheckBox.setChecked(true);
				checkedBoxs.add(holder.filterItemCheckBox);
			}
			
	        return convertView;  
		}
		
		private class ViewHolder {
			LinearLayout filterItemLinearLayout;
			ImageView filterItemCompanyImageView;
			TextView filterItemTextView;
			CheckBox filterItemCheckBox;
		}
		
	}
}
