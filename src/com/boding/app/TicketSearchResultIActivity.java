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

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class TicketSearchResultIActivity extends Activity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_search_result_i);
		
		initView();
		
		XMLTask xmltask = new XMLTask(this);
		String urlstr = "http://192.168.0.22:10381/FakeBodingServer/XMLServlet";
		xmltask.execute(urlstr);
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
        
        fromCityTextView.setText(GlobalVariables.Fly_From_City.getCityName());
        fromCityCodeTextView.setText(GlobalVariables.Fly_From_City.getCityCode());
        toCityTextView.setText(GlobalVariables.Fly_To_City.getCityName());
        toCityCodeTextView.setText(GlobalVariables.Fly_To_City.getCityCode());
        
        setTextViewInfo();
        
        setListeners();
  }

	private void setListeners(){
        lastDayLinearLayout.setOnClickListener(new OnClickListener(){

              @Override
              public void onClick(View arg0) {
                  if(lastDayAriline == null)
                	  return;
                  nextDayAirline = todayAirline;
                  todayAirline = lastDayAriline;
                  lastDayAriline = null;
                  setAdapter();
                  // add task here
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
              }
              
        });
	}
  
	  private void setTextViewInfo(){
	        if(lastDayAriline!=null)
	              lastDayPriceTextView.setText("1232");
	        else
	              lastDayPriceTextView.setText("");
	        
	        if(todayAirline!=null){
	              todayDateTextView.setText(todayAirline.getGoDate());
	              todayPriceTextView.setText("2222");
	        }else{
	              todayDateTextView.setText(GlobalVariables.Fly_From_Date);
	              todayPriceTextView.setText("");
	        }
	        
	        if(nextDayAirline!=null)
	              nextDayPriceTextView.setText("");
	        else
	              nextDayPriceTextView.setText("");
	        
	  }
	  
	  public void setTodayAirlineView(AirlineView todayAV){
	        this.todayAirline = todayAV;
	        setAdapter();
	  }
	  
	  public void setLastdayAirlineView(AirlineView lastdayAV){
	        this.lastDayAriline = lastdayAV;
	        setTextViewInfo();
	  }
	  
	  public void setNextdayAirlineView(AirlineView nextdayAV){
	        this.nextDayAirline = nextdayAV;
	        setTextViewInfo();
	  }
	  
	  private void setAdapter(){
	        adapter = new TicketSearchResultListIAdapter(this, todayAirline);
	        searchResultListView.setAdapter(adapter);
	        setTextViewInfo();
	  }

}
