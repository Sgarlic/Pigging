package com.boding.view.layout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.boding.R;
import com.boding.util.Util;
import com.boding.view.calendar.DateSelectCalendarView;
import com.boding.view.calendar.DateSelectCalendarView.OnItemClickListener;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class CalendarLayout extends LinearLayout{
	private TextView currYearTextView;
	private TextView currMonthTextView;
	private DateSelectCalendarView dateSelectCalendarView;
	private LinearLayout monthSelectorLinearLayout;
	
	private PopupWindow monthSelector;
	private ListAdapter monthAdapter;
	private List<String> monthList;
	private ListView monthListView;
	
	private Date minClickableDate;
	private Date curDate;
	
	private int parentWidth = 0;
	private int nextYearPosition = 6;
	
	private Context context;
	
	private OnItemClickListener onItemClickListener;
	// if init complete
	private boolean flag = false;
	public CalendarLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public CalendarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		while(!flag){
			initView();
			flag = true;
		}
	}
	
	private void initView(){
		if(this.minClickableDate == null)
			this.minClickableDate = new Date();
		if(this.curDate == null)
			this.curDate = new Date();
		if(this.curDate.before(minClickableDate))
			this.curDate = minClickableDate;
		
		this.setOrientation(HORIZONTAL); //水平布局  
		
		View v = LayoutInflater.from(context).inflate(R.layout.layout_calendar, null);  
		currMonthTextView = (TextView)v.findViewById(R.id.current_month_textView);
		currYearTextView = (TextView)v.findViewById(R.id.current_year_textView);
		monthSelectorLinearLayout = (LinearLayout)v.findViewById(R.id.calendar_month_selector_linearLayout);
		
		dateSelectCalendarView = (DateSelectCalendarView)v.findViewById(R.id.date_select_calendarView);
		dateSelectCalendarView.setOnItemClickListener(new DateSelectCalendarView.OnItemClickListener(){
			@Override
			public void OnItemClick(Date date) {
				//响应监听事件
				if(onItemClickListener!=null)
					onItemClickListener.OnItemClick(date);
			}
		});
		dateSelectCalendarView.setMinClickableDate(minClickableDate);
		dateSelectCalendarView.setDate(curDate);
		generateMonthData();
		
		monthSelectorLinearLayout.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(flag)
					popupWindowShowing();
			}
		});
		this.addView(v);
		currMonthTextView.addOnLayoutChangeListener(new OnLayoutChangeListener(){
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				int finalWidth = right-left;
				if(finalWidth > 0){
					parentWidth = finalWidth;
					initPopupWindow();
				}
			}
			
		});
	}
	private void generateMonthData(){
		monthList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		int todayMonth = calendar.get(Calendar.MONTH) + 1;
		
		calendar.setTime(minClickableDate);
		int minMonth = calendar.get(Calendar.MONTH) + 1;
		if(todayMonth > minMonth)
			minMonth += 12;
		
		
		int nextSixMonth = nextYearPosition;
		int currentMonth = todayMonth;
		for(int i=0;i<nextSixMonth;i++,currentMonth++){
			if(currentMonth < minMonth)
				continue;
			int tempMonth = currentMonth;
			if(tempMonth > 12){
				tempMonth -= 12;
				nextYearPosition = i;
			}
			monthList.add(tempMonth+"月");
		}
		
		calendar.setTime(curDate);
		int curDateMonth = calendar.get(Calendar.MONTH) + 1;
		if(todayMonth > curDateMonth)
			curDateMonth += 12;
		
		currMonthTextView.setText(monthList.get(curDateMonth - minMonth));
	}
	
	private void setAdapter(){
		monthAdapter = new MonthAdapter(monthList);
		monthListView.setAdapter(monthAdapter);
	}
	
	private void initPopupWindow(){
		View popupWindow =  LayoutInflater.from(context).inflate(R.layout.popup_month_selector, null);
		monthListView = (ListView)popupWindow.findViewById(R.id.calendar_month_select_list);
		setAdapter();
		monthSelector = new PopupWindow(popupWindow,parentWidth,LayoutParams.WRAP_CONTENT,true);
		monthSelector.setOutsideTouchable(true);
		monthSelector.setBackgroundDrawable(new BitmapDrawable());
	}
	private void popupWindowShowing(){
		monthSelector.showAsDropDown(currMonthTextView, 0, -3);
	}
	
	private void popupWindowDismiss(){
		monthSelector.dismiss();
	}
	
	private class MonthAdapter extends BaseAdapter{
		List<String> monthList;
		int previousPos = 0;
		
		public MonthAdapter(List<String> monthList){
			this.monthList = monthList;
		}
		
		
		@Override
		public int getCount() {
			return monthList.size();
		}

		@Override
		public String getItem(int position) {
			return monthList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(CalendarLayout.this.getContext()).inflate(R.layout.list_item_month_selector, null);
				holder.textView = (TextView)convertView.findViewById(R.id.current_month_selector_dialog_textView);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.textView.setText(monthList.get(position));
			holder.textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					currMonthTextView.setText(monthList.get(position));
					if(position < nextYearPosition){
						currYearTextView.setText(CalendarLayout.this.getResources().getString(R.string.thisYear));
					}else{
						currYearTextView.setText(CalendarLayout.this.getResources().getString(R.string.nextYear));
					}
					dateSelectCalendarView.gotoNextNMonth(position - previousPos);
					previousPos = position;
					popupWindowDismiss();
				}
			});
			return convertView;
		}
		
		class ViewHolder{
			TextView textView;
		}
		
	}
	
	public void setMinClickableDate(Date minClickableDate){
		this.minClickableDate = minClickableDate;
	}
	
	public void setDate(Date curDate){
		this.curDate = curDate;
	}
	
	//给控件设置监听事件
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.onItemClickListener =  onItemClickListener;
	}
	//监听接口
	public interface OnItemClickListener {
		void OnItemClick(Date date);
	}
}
