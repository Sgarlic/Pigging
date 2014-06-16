package com.boding.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.R.id;
import com.boding.R.layout;
import com.boding.R.menu;
import com.boding.adapter.TicketSearchResultListIAdapter;
import com.boding.constants.CityProperty;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.model.AirlineView;
import com.boding.model.FlightLine;
import com.boding.task.XMLTask;
import com.boding.util.Util;
import com.boding.view.dialog.CalendarDialog;
import com.boding.view.dialog.FilterDialog;
import com.boding.view.dialog.SearchCityDialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class TicketSearchResultIActivity extends FragmentActivity {
	private TicketSearchResultListIAdapter adapter;
	private ListView searchResultListView;
	
	private AirlineView lastDayAriline;
    private AirlineView todayAirline;
    private AirlineView nextDayAirline;
    
    private TextView fromCityTextView;
    private TextView fromCityCodeTextView;
    private TextView toCityTextView;
    private TextView toCityCodeTextView;
    private LinearLayout lastDayLinearLayout;
    private TextView lastDayPriceTextView;
    private LinearLayout todayLinearLayout;
    private TextView todayDateTextView;
    private TextView todayPriceTextView;
    private LinearLayout nextDayLinearLayout;
    private TextView nextDayPriceTextView;
    private LinearLayout filterLinearLayout;
    private LinearLayout startFlyingLinearLayout;
    private LinearLayout priceLinearLayout;
    
    private ImageView leatimeOrderImageview;
    private ImageView priceOrderImageview;
    
    private boolean isLeatimeAsc = true;
    private boolean isPriceAsc = true;
    
    //测试用
    private String tempurl = "http://192.168.0.22:8104/FakeBodingServer/XMLServlet?day=today";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_search_result_i);
		
		initView();
		
		//此处先使用同一个xml测试
		String urlstr = "http://192.168.0.22:8104/FakeBodingServer/XMLServlet?day=today";
		invokeXmlTask(urlstr, 2);
		invokeXmlTask(urlstr, 1);
		invokeXmlTask(urlstr, 3);
	}
	
	private void initView(){
        LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
        returnLinearLayout.setOnClickListener(new OnClickListener(){
              @Override
              public void onClick(View arg0) {
                    Util.returnToPreviousPage(TicketSearchResultIActivity.this, IntentRequestCode.START_DATE_SELECTION);
              }
              
        });
        searchResultListView = (ListView)findViewById(R.id.international_ticket_search_result_listView);
        
        fromCityTextView= (TextView)findViewById(R.id.search_result_title_from_textView);
        fromCityCodeTextView= (TextView)findViewById(R.id.search_result_title_from_code_textView);
        toCityTextView= (TextView)findViewById(R.id.search_result_title_to_textView);
        toCityCodeTextView= (TextView)findViewById(R.id.search_result_title_tocode_textView);
        lastDayLinearLayout= (LinearLayout)findViewById(R.id.search_result_lastday_linearLayout);
        lastDayPriceTextView= (TextView)findViewById(R.id.search_result_lastday_price_textView);
        todayLinearLayout= (LinearLayout)findViewById(R.id.search_result_today_linearLayout);
        todayDateTextView= (TextView)findViewById(R.id.search_result_today_textView);
        todayPriceTextView= (TextView)findViewById(R.id.search_result_today_price_textView);
        nextDayLinearLayout= (LinearLayout)findViewById(R.id.search_result_nextday_linearLayout);
        nextDayPriceTextView= (TextView)findViewById(R.id.search_result_nextday_price_textView);
        filterLinearLayout= (LinearLayout)findViewById(R.id.search_result_filter_linearLayout);
        startFlyingLinearLayout= (LinearLayout)findViewById(R.id.search_result_startflying_linearLayout);
        priceLinearLayout= (LinearLayout)findViewById(R.id.search_result_price_linearLayout);
        
        leatimeOrderImageview = (ImageView)findViewById(R.id.leatime_order_imageview);
        priceOrderImageview = (ImageView)findViewById(R.id.price_order_imageview);
        
        fromCityTextView.setText(GlobalVariables.Fly_From_City.getCityName());
        fromCityCodeTextView.setText(GlobalVariables.Fly_From_City.getCityCode());
        toCityTextView.setText(GlobalVariables.Fly_To_City.getCityName());
        toCityCodeTextView.setText(GlobalVariables.Fly_To_City.getCityCode());
        
        setTextViewInfo();
        
        setListeners();
  }

	private void setListeners(){
		todayLinearLayout.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
            	CalendarDialog calendarDialog = new CalendarDialog(TicketSearchResultIActivity.this,R.style.Warning_Dialog_Theme);
            	calendarDialog.show();
            }
            
      });
        lastDayLinearLayout.setOnClickListener(new OnClickListener(){
              @Override
              public void onClick(View arg0) {
                  if(lastDayAriline == null)
                	  return;
                  nextDayAirline = todayAirline;
                  todayAirline = lastDayAriline;
                  lastDayAriline = null;
                  setAdapter();

                  //获取lastday的xml,填充av
                  invokeXmlTask(tempurl, 1);
              }
              
        });
        nextDayLinearLayout.setOnClickListener(new OnClickListener(){
              @Override
              public void onClick(View arg0) {
                   if(nextDayAirline == null)
                	   return;
                   lastDayAriline = todayAirline;
                   todayAirline = nextDayAirline;
                   nextDayAirline = null;
                   setAdapter();
                   
                   invokeXmlTask(tempurl, 3);
              }
              
        });
        
        startFlyingLinearLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!isLeatimeAsc){
					todayAirline.orderLinesByLeatime(true);
					leatimeOrderImageview.setImageResource(R.drawable.datechoice2);
					adapter.notifyDataSetChanged();
					isLeatimeAsc = true;
				}else{
					todayAirline.orderLinesByLeatime(false);
					leatimeOrderImageview.setImageResource(R.drawable.datechoice);
					adapter.notifyDataSetChanged();
					isLeatimeAsc = false;
				}
			}
        	
        });
        
        priceLinearLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
//				if(!isPriceAsc){
//					todayAirline.orderLinesByPrice(true);
//					priceOrderImageview.setImageResource(R.drawable.datechoice);
//					adapter.notifyDataSetChanged();
//					isPriceAsc = true;
//				}else{
//					todayAirline.orderLinesByPrice(false);
//					priceOrderImageview.setImageResource(R.drawable.datechoicegrey);
//					adapter.notifyDataSetChanged();
//					isPriceAsc = false;
//				}
				Intent intent = new Intent();
				intent.setClass(TicketSearchResultIActivity.this, OrderFormActivity.class);
				startActivityForResult(intent,IntentRequestCode.START_ORDER_FORM.getRequestCode());
			}
        	
        });
        
        filterLinearLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
//				FilterDialog fd = new FilterDialog();
//				
//				fd.show(getSupportFragmentManager(), "filterDialog");
				
				FilterDialog filterDialog = new FilterDialog(TicketSearchResultIActivity.this,R.style.Warning_Dialog_Theme);
				filterDialog.show();
			}
        });
	}
  
	  private void setTextViewInfo(){
		  if(lastDayAriline!=null)
              lastDayPriceTextView.setText(lastDayAriline.getlowestPrice());
		  else
              lastDayPriceTextView.setText("");
        
		  if(todayAirline!=null){
              todayDateTextView.setText(todayAirline.getGoDate());
              todayPriceTextView.setText(todayAirline.getlowestPrice());
		  }else{
              todayDateTextView.setText(GlobalVariables.Fly_From_Date);
              todayPriceTextView.setText("");
		  }
        
		  if(nextDayAirline!=null)
              nextDayPriceTextView.setText(nextDayAirline.getlowestPrice());
		  else
              nextDayPriceTextView.setText("");
	        
	  }
	  
	  public void setTodayAirlineView(AirlineView todayAV){
		  if(todayAV==null)
			  return;
	       this.todayAirline = todayAV;
	       setAdapter();
	  }
	  
	  public void setLastdayAirlineView(AirlineView lastdayAV){
		  if(lastdayAV==null)
			  return;
	        this.lastDayAriline = lastdayAV;
	        setTextViewInfo();
	  }
	  
	  public void setNextdayAirlineView(AirlineView nextdayAV){
		  if(nextdayAV==null)
			  return;
	        this.nextDayAirline = nextdayAV;
	        setTextViewInfo();
	  }
	  
	  private void setAdapter(){
	        adapter = new TicketSearchResultListIAdapter(this, todayAirline);
	        searchResultListView.setAdapter(adapter);
	        setTextViewInfo();
	  }
	  
	  private void invokeXmlTask(String url, int whichday){
		  	XMLTask xmltask = new XMLTask(this, whichday);
			xmltask.execute(url);
	  }

}
