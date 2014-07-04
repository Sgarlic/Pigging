package com.boding.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.R.id;
import com.boding.R.layout;
import com.boding.R.menu;
import com.boding.adapter.TicketSearchResultAdapter;
import com.boding.adapter.TicketSearchResultListAdapter;
import com.boding.adapter.TicketSearchResultListIAdapter;
import com.boding.constants.ActivityNumber;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.AirlineInterface;
import com.boding.model.AirlineView;
import com.boding.model.City;
import com.boding.model.FlightLine;
import com.boding.model.FlightQuery;
import com.boding.model.domestic.Airlines;
import com.boding.task.DomeFlightQueryTask;
import com.boding.task.XMLTask;
import com.boding.util.CityUtil;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.dialog.CalendarDialog;
import com.boding.view.dialog.FilterDialog;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.SearchCityDialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.app.ExpandableListActivity;
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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class TicketSearchResultActivity extends FragmentActivity {
	private TicketSearchResultAdapter adapter;
	private ExpandableListView searchResultListView;
	
	private AirlineInterface lastDayAirline;
	private AirlineInterface todayAirline;
	private AirlineInterface nextDayAirline;

	//private AirlineView lastDayAriline;
    //private AirlineView todayAirline;
    //private AirlineView nextDayAirline;
    
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
    
    private FlightQuery flightQuery;
    
    private ProgressBarDialog progressDialog;
    
    //测试用
    private String tempurl = "http://192.168.0.22:9404/FakeBodingServer/XMLServlet?day=today"; 
	
	private City from;
	private City to;
	private String startdate;
	
	private boolean isSingleWay = true;
	private boolean goToOrder = true;
	private boolean isInternational = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_search_result);
		
		Bundle bundle = getIntent().getExtras();
		int pre_activity_num = bundle.getInt(IntentExtraAttribute.PREVIOUS_ACTIVITY);
		flightQuery = (FlightQuery)bundle.getParcelable(IntentExtraAttribute.FLIGHT_QUERY);
		System.out.println("$$$$$$$$$$" + flightQuery.getFromcity());

		String fromcity = flightQuery.getFromcity();
		String tocity = flightQuery.getTocity();
		startdate = flightQuery.getStartdate();
		from = CityUtil.getCityByName(fromcity);
		to = CityUtil.getCityByName(tocity);
		
		isInternational = from.isInternationalCity() || to.isInternationalCity();
		if(pre_activity_num == ActivityNumber.MAIN.getActivityNum()){//从主界面进入
			isSingleWay = bundle.getBoolean(Constants.IS_SINGLE_WAY);
			if(isSingleWay) goToOrder = true;
			else goToOrder = false;
		}
		else if(pre_activity_num == ActivityNumber.TICKET_SEARCH_RESULT.getActivityNum()){//返程进入
			City temp = from;
			from = to;
			to = temp;
			startdate = flightQuery.getReturndate();
		}	
		
		loadData(isInternational);
		
		initView();
		
		progressDialog = new ProgressBarDialog(TicketSearchResultActivity.this,R.style.Custom_Dialog_Theme);
		progressDialog.show();
	}
	

	private void initView(){
        LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
        returnLinearLayout.setOnClickListener(new OnClickListener(){
              @Override
              public void onClick(View arg0) {
                    Util.returnToPreviousPage(TicketSearchResultActivity.this, IntentRequestCode.DATE_SELECTION);
              }
              
        });
        searchResultListView = (ExpandableListView)findViewById(R.id.international_ticket_search_result_expandableListView);
        
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
        
        fromCityTextView.setText(from.getCityName());
        fromCityCodeTextView.setText(from.getCityCode());
        toCityTextView.setText(to.getCityName());
        toCityCodeTextView.setText(to.getCityCode());
        
        setTextViewInfo();
        
        addListeners();
  }

	private void addListeners(){
		searchResultListView.setOnGroupClickListener(new OnGroupClickListener() { 
			@Override 
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				if(adapter.isGgroupExpandable(groupPosition)){
					return false;
				}
				return true; 
			} 
		});
		
		todayLinearLayout.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
            	CalendarDialog calendarDialog = new CalendarDialog(TicketSearchResultActivity.this,R.style.Custom_Dialog_Theme);
            	calendarDialog.show();
            }
            
      });
        lastDayLinearLayout.setOnClickListener(new OnClickListener(){
              @Override
              public void onClick(View arg0) {
                  if(lastDayAirline == null)
                	  return;
                  nextDayAirline = todayAirline;
                  todayAirline = lastDayAirline;
                  lastDayAirline = null;
                  setAdapter();
                  startdate = DateUtil.getLastDay(startdate);
                  loadDataOfDay(1);
                  
                  //获取lastday的xml,填充av
                  //invokeXmlTask(tempurl, 1);
              }
              
        });
        nextDayLinearLayout.setOnClickListener(new OnClickListener(){
              @Override
              public void onClick(View arg0) {
            	  System.out.println("NEEEEEEEXXXXXXXTTTTTTT DDDDDDDAAAAAAYYYYYY");
                   if(nextDayAirline == null)
                	   return;
                   lastDayAirline = todayAirline;
                   todayAirline = nextDayAirline;
                   nextDayAirline = null;
                   setAdapter();
                   startdate = DateUtil.getNextDay(startdate);
                   loadDataOfDay(3);
                   
                   //invokeXmlTask(tempurl, 3);
              }
              
        });
        
        startFlyingLinearLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!isLeatimeAsc){
					todayAirline.orderLinesByLeatime(true);
					leatimeOrderImageview.setImageResource(R.drawable.triangle_up_orange);
					adapter.notifyDataSetChanged();
					isLeatimeAsc = true;
				}else{
					todayAirline.orderLinesByLeatime(false);
					leatimeOrderImageview.setImageResource(R.drawable.triangle_down_orange);
					adapter.notifyDataSetChanged();
					isLeatimeAsc = false;
				}
			}
        	
        });
        
        priceLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!isPriceAsc){
					todayAirline.orderLinesByPrice(true);
					priceOrderImageview.setImageResource(R.drawable.triangle_up_orange);
					adapter.notifyDataSetChanged();
					isPriceAsc = true;
				}else{
					todayAirline.orderLinesByPrice(false);
					priceOrderImageview.setImageResource(R.drawable.triangle_down_orange);
					adapter.notifyDataSetChanged();
					isPriceAsc = false;
				}
				Intent intent = new Intent();
				intent.setClass(TicketSearchResultActivity.this, OrderFormActivity.class);
				startActivityForResult(intent,IntentRequestCode.ORDER_FORM.getRequestCode());
			}
        	
        });
        
        filterLinearLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

//				FilterDialog fd = new FilterDialog();
//				
//				fd.show(getSupportFragmentManager(), "filterDialog");

				FilterDialog filterDialog = new FilterDialog(TicketSearchResultActivity.this,R.style.Custom_Dialog_Theme);
				filterDialog.show();
			}
        });
	}
  
	  private void setTextViewInfo(){
		  todayDateTextView.setText(startdate);
		  if(lastDayAirline!=null)
              lastDayPriceTextView.setText(lastDayAirline.getlowestPrice());
		  else
              lastDayPriceTextView.setText("");
        
		  if(todayAirline!=null){
              todayPriceTextView.setText(todayAirline.getlowestPrice());
		  }else{
              //todayDateTextView.setText(startdate);
              todayPriceTextView.setText("");
		  }
        
		  if(nextDayAirline!=null)
              nextDayPriceTextView.setText(nextDayAirline.getlowestPrice());
		  else
              nextDayPriceTextView.setText("");

	  }

	  public void setTodayAirlineView(AirlineInterface todayAV){
		  if(todayAV==null)
			  return;
	       this.todayAirline = todayAV;
	       setAdapter();
	       progressDialog.dismiss();
	  }

	  public void setLastdayAirlineView(AirlineInterface lastdayAV){
		  if(lastdayAV==null)
			  return;
	        this.lastDayAirline = lastdayAV;
	        setTextViewInfo();
	  }

	  public void setNextdayAirlineView(AirlineInterface nextdayAV){
		  if(nextdayAV==null)
			  return;
	        this.nextDayAirline = nextdayAV;
	        setTextViewInfo();
	  }

	  private void setAdapter(){
		  if(isInternational)
			  adapter = new TicketSearchResultListIAdapter(this, (AirlineView)todayAirline);
		  else
			  adapter = new TicketSearchResultListAdapter(this, (Airlines)todayAirline);
	      searchResultListView.setAdapter(adapter);
	      setTextViewInfo();
	  }

	  private void invokeXmlTask(String url, int whichday){
		  	XMLTask xmltask = new XMLTask(this, whichday);
			xmltask.execute(whichday);//To Change
	  }

	  public void doFilter(List<String> timeConstraints, List<String> classConstraints, List<String> compConstrains){
		  TicketSearchResultListIAdapter.FlightLineFilter filter = (TicketSearchResultListIAdapter.FlightLineFilter)adapter.getFilter();
		  filter.setConstraint(timeConstraints, classConstraints, compConstrains);
		  filter.filter("");
	  }
	  
	  private void queryDomesticFlight(String date, String fromcity, String tocity, int whichday){
		  DomeFlightQueryTask dfq = new DomeFlightQueryTask(this, whichday);
		  dfq.execute(date, fromcity, tocity);
	  }
	  
	  private void loadData(boolean isInternational){
		  if(isInternational){//国际
				//此处先使用同一个xml测试
				String urlstr = "http://192.168.0.22:9404/FakeBodingServer/XMLServlet?day=today";
				invokeXmlTask(urlstr, 2);
				invokeXmlTask(urlstr, 1);
				invokeXmlTask(urlstr, 3);
			}else{//国内
				//setDomesticAdapter();
				String fromcode = from.getCityCode();
				String tocode = to.getCityCode();
				queryDomesticFlight(startdate, fromcode, tocode, 2);
				queryDomesticFlight(DateUtil.getLastDay(startdate), fromcode, tocode, 1);
				queryDomesticFlight(DateUtil.getNextDay(startdate), fromcode, tocode, 3);
			}
	  }
	  
	  private void loadDataOfDay(int whichday){
		  if(isInternational){
			  String urlstr = "http://192.168.0.22:9404/FakeBodingServer/XMLServlet?day=today";
			  invokeXmlTask(urlstr, whichday);
		  }else{
			  queryDomesticFlight(startdate, from.getCityCode(), to.getCityCode(), whichday);
		  }
	  }
	  
	  public void goToNextActivity(){
		  Intent intent = new Intent();
		  if(goToOrder){
			  intent.setClass(this, OrderFormActivity.class);
		  }else{
			  intent.setClass(this, TicketSearchResultActivity.class);
			  Bundle bundle = new Bundle();
			  bundle.putInt(IntentExtraAttribute.PREVIOUS_ACTIVITY, ActivityNumber.TICKET_SEARCH_RESULT.getActivityNum());
			  bundle.putParcelable(IntentExtraAttribute.FLIGHT_QUERY, flightQuery);
			  intent.putExtras(bundle);
		  }
		  startActivityForResult(intent,IntentRequestCode.ORDER_FORM.getRequestCode());
	  }
}