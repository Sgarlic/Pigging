package com.boding.app;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.adapter.TicketSearchResultAdapter;
import com.boding.adapter.TicketSearchResultListAdapter;
import com.boding.adapter.TicketSearchResultListIAdapter;
import com.boding.adapter.TicketSearchResultListIAdapter.FlightLineFilter;
import com.boding.constants.ActivityNumber;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.AirlineInterface;
import com.boding.model.AirlineView;
import com.boding.model.City;
import com.boding.model.FlightInterface;
import com.boding.model.FlightLine;
import com.boding.model.FlightQuery;
import com.boding.model.domestic.Airlines;
import com.boding.model.domestic.Flight;
import com.boding.task.DomeFlightQueryTask;
import com.boding.task.XMLTask;
import com.boding.util.CityUtil;
import com.boding.util.DateUtil;
import com.boding.util.Encryption;
import com.boding.util.Util;
import com.boding.view.dialog.CalendarDialog;
import com.boding.view.dialog.CalendarDialog.OnItemClickListener;
import com.boding.view.dialog.FilterDialog;
import com.boding.view.dialog.NetworkUnavaiableDialog;

public class TicketSearchResultActivity extends BodingBaseActivity {
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
    private TextView noResultTextView;
    
    private boolean isLeatimeAsc = true;
    private boolean isPriceAsc = true;
    
    private int orderFlag;
    
    private FlightQuery flightQuery;
    
    //测试用
    private String tempurl = "http://192.168.0.22:9404/FakeBodingServer/XMLServlet?day=today"; 
	
	private City from;
	private City to;
	private String startdate;
	
	private boolean isSingleWay = true;
	private boolean goToOrder = true;
	private boolean isInternational = true;
	
	FilterDialog filterDialog;
	private String classInfo = Constants.COUCH_CLASS;
	
	private CalendarDialog calendarDialog;

	
	private FlightInterface choosedGoFlight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_search_result);
		calendarDialog = new CalendarDialog(TicketSearchResultActivity.this);
		Bundle bundle = getIntent().getExtras();
		int pre_activity_num = bundle.getInt(IntentExtraAttribute.PREVIOUS_ACTIVITY);
		if(bundle.containsKey(IntentExtraAttribute.CHOOSED_ROUNDWAY_GOFLIGHT)){
			choosedGoFlight = bundle.getParcelable(IntentExtraAttribute.CHOOSED_ROUNDWAY_GOFLIGHT);
		}
		flightQuery = (FlightQuery)bundle.getParcelable(IntentExtraAttribute.FLIGHT_QUERY);
		System.out.println("$$$$$$$$$$" + flightQuery.getFromcity());
		if(bundle.containsKey(IntentExtraAttribute.CLASS_INFO))
			classInfo = bundle.getString(IntentExtraAttribute.CLASS_INFO);

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
			isSingleWay = false;
			City temp = from;
			from = to;
			to = temp;
			startdate = GlobalVariables.Fly_Return_Date;
		}	
		
		networkUnavaiableDialog = new NetworkUnavaiableDialog (this);
		loadData(isInternational);
		
		initView();
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
        lastDayPriceTextView.setText("");
        todayLinearLayout= (LinearLayout)findViewById(R.id.search_result_today_linearLayout);
        todayDateTextView= (TextView)findViewById(R.id.search_result_today_textView);
        todayDateTextView.setText("");
        todayPriceTextView= (TextView)findViewById(R.id.search_result_today_price_textView);
        todayPriceTextView.setText("");
        nextDayLinearLayout= (LinearLayout)findViewById(R.id.search_result_nextday_linearLayout);
        nextDayPriceTextView= (TextView)findViewById(R.id.search_result_nextday_price_textView);
        nextDayPriceTextView.setText("");
        filterLinearLayout= (LinearLayout)findViewById(R.id.search_result_filter_linearLayout);
        startFlyingLinearLayout= (LinearLayout)findViewById(R.id.search_result_startflying_linearLayout);
        priceLinearLayout= (LinearLayout)findViewById(R.id.search_result_price_linearLayout);
        
        leatimeOrderImageview = (ImageView)findViewById(R.id.leatime_order_imageview);
        priceOrderImageview = (ImageView)findViewById(R.id.price_order_imageview);
        noResultTextView = (TextView) findViewById(R.id.international_ticket_search_result_noResultTextView);
        noResultTextView.setVisibility(View.GONE);
        
        fromCityTextView.setText(from.getCityName());
        fromCityCodeTextView.setText(from.getCityCode());
        toCityTextView.setText(to.getCityName());
        toCityCodeTextView.setText(to.getCityCode());
        
        //setTextViewInfo();
        
        addListeners();
  }

	private void addListeners(){
		searchResultListView.setOnGroupClickListener(new OnGroupClickListener() { 
			@Override 
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//				if(adapter.isGgroupExpandable(groupPosition)){
//					return false;
//				}
//				return true;
				FlightInterface currentFlightLine = (FlightInterface) adapter.getGroup(groupPosition);
				currentFlightLine.setSelectedClassPos(0);
				goToNextActivity(currentFlightLine);
				return true;
			} 
		});
		todayLinearLayout.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
	            calendarDialog = new CalendarDialog(TicketSearchResultActivity.this);
	            calendarDialog.setOnItemClickListener(new OnItemClickListener() {
	    			@Override
	    			public void OnItemClick(Date date) {
	    				String selectedDate = DateUtil.getFormatedDate(date);
	    				if(!startdate.equals(selectedDate)){
	    					startdate = selectedDate;
	    					if(choosedGoFlight == null){
	    						GlobalVariables.Fly_From_Date = selectedDate;
	    						DateUtil.setRetunrnWayDate();
	    					}else{
	    						GlobalVariables.Fly_Return_Date = selectedDate;
	    					}
	    					loadData(isInternational);
	    				}
	    			}
	    		});
	            if(choosedGoFlight != null){
	      			  calendarDialog.setMinDate(DateUtil.getDateFromString(GlobalVariables.Fly_From_Date));
	      			  calendarDialog.setDate(DateUtil.getDateFromString(GlobalVariables.Fly_Return_Date));
	      	  	}else{
	      	  		calendarDialog.setDate(DateUtil.getDateFromString(GlobalVariables.Fly_From_Date));
	      	  	}
	            	
	            	calendarDialog.show();
	            }
            
		});
        lastDayLinearLayout.setOnClickListener(new OnClickListener(){
              @Override
              public void onClick(View arg0) {
                  if(lastDayAirline == null)
                	  return;
                  
                  if(startdate.equals(DateUtil.getFormatedDate(Calendar.getInstance())))
                	  return;
                  
                  nextDayAirline = todayAirline;
                  todayAirline = lastDayAirline;
                  lastDayAirline = null;
                  
                  refreshOrder();
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
                   
                   refreshOrder();
                   setAdapter();
                   startdate = DateUtil.getNextDay(startdate);
                   loadDataOfDay(3);
                   
                   //invokeXmlTask(tempurl, 3);
              }
              
        });
        
        startFlyingLinearLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				orderFlag = Constants.ORDERBYTIME;
				if(!isLeatimeAsc){
					adapter.orderLinesByLeatime(true);
					leatimeOrderImageview.setImageResource(R.drawable.triangle_up_orange);
					adapter.notifyDataSetChanged();
					isLeatimeAsc = true;
				}else{
					adapter.orderLinesByLeatime(false);
					leatimeOrderImageview.setImageResource(R.drawable.triangle_down_orange);
					adapter.notifyDataSetChanged();
					isLeatimeAsc = false;
				}
			}
        	
        });
        
        priceLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				orderFlag = Constants.ORDERBYPRICE;
				if(!isPriceAsc){
					adapter.orderLinesByPrice(true);
					priceOrderImageview.setImageResource(R.drawable.triangle_up_orange);
					isPriceAsc = true;
				}else{
					adapter.orderLinesByPrice(false);
					priceOrderImageview.setImageResource(R.drawable.triangle_down_orange);
					isPriceAsc = false;
				}
			}
        	
        });
        
        filterLinearLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(filterDialog == null)
					filterDialog = new FilterDialog(TicketSearchResultActivity.this);
				filterDialog.setCompanyList(todayAirline.getCompanyInfo());
				filterDialog.show();
			}
        });
	}
  
	  public void setTextViewInfo(){
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
	       //doDefaultFilter();
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
		  adapter.setOnColExpClickListener(new TicketSearchResultAdapter.OnColExpClickListener() {
				@Override
				public void ColExp(int position) {
					if(searchResultListView.isGroupExpanded(position)){
						searchResultListView.collapseGroup(position);
					}else{
						searchResultListView.expandGroup(position);
					}
				}
			});
		  doDefaultFilter();
		  
		  if(adapter.getGroupCount() > 0){
	    	  hideNoResult();
	      }else{
	    	  showNoResult();
	      }
	      searchResultListView.setAdapter(adapter);
	      setTextViewInfo();
	      searchResultListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, 
					int groupPosition, int childPosition, long id) {
				FlightInterface flight = (FlightInterface) adapter.getGroup(groupPosition);
				flight.setSelectedClassPos(childPosition +1);
				goToNextActivity(flight);
				return false;
				}
	      });
	      
	      progressBarDialog.dismiss();
	  }

	  private void invokeXmlTask(String url, int whichday){
		  	XMLTask xmltask = new XMLTask(this, whichday);
			xmltask.execute(url);//To Change
	  }

	  public void doFilter(List<String> timeConstraints, List<String> classConstraints, List<String> compConstrains){
		  android.widget.Filter filter = null;
		  if(isInternational){
			   filter = (TicketSearchResultListIAdapter.FlightLineFilter)adapter.getFilter();
			   ((FlightLineFilter) filter).setConstraint(timeConstraints, classConstraints, compConstrains);
			   filter.filter("");
		  }
		  else{
			  filter = (TicketSearchResultListAdapter.FlightLineFilter)adapter.getFilter();
			  ((TicketSearchResultListAdapter.FlightLineFilter) filter).setConstraint(timeConstraints, classConstraints, compConstrains);
			   filter.filter("");
		  }
	  }
	  
	  public void showNoResult(){
		  noResultTextView.setVisibility(View.VISIBLE);
	  }
	  
	  public void hideNoResult(){
		  noResultTextView.setVisibility(View.GONE);
	  }
	  
	  private void queryDomesticFlight(String date, String fromcity, String tocity, int whichday){
		  DomeFlightQueryTask dfq = new DomeFlightQueryTask(this, whichday);
		  dfq.execute(date, fromcity, tocity);
	  }
	  
	  private void loadData(boolean isInternational){
		  calendarDialog.dismiss();
		  if(!Util.isNetworkAvailable(this)){
				networkUnavaiableDialog.show();
				return;
		  }
		  progressBarDialog.show();
		  String fromcode = from.getCityCode();
		  String tocode = to.getCityCode();
		  if(isInternational){//国际
				//此处先使用同一个xml测试
				//String urlstr = "http://115.231.73.25:1368/av.ashx?userid=boding&PassengerType=C001&fromcity=SHA&tocity=NYC&godate=2014-07-24&backdate=2014-07-26&sign=A3AF7E76AF44B0A2055D262C5329929E";
				//invokeXmlTask(urlstr, 2);
				//invokeXmlTask(urlstr, 1);
				//invokeXmlTask(urlstr, 3);
				queryInternationalFlight(startdate, fromcode, tocode, 2);
				queryInternationalFlight(startdate, fromcode, tocode, 1);
				queryInternationalFlight(startdate, fromcode, tocode, 3);
			}else{//国内
				//setDomesticAdapter();
				
				queryDomesticFlight(startdate, fromcode, tocode, 2);
				queryDomesticFlight(DateUtil.getLastDay(startdate), fromcode, tocode, 1);
				queryDomesticFlight(DateUtil.getNextDay(startdate), fromcode, tocode, 3);
			}
	  }
	  
	  private void queryInternationalFlight(String startdate, String fromcode, String tocode, int whichday){
		  String passengerType = "C001";
		  String urlformat = "http://115.231.73.25:1368/av.ashx?userid=%s&PassengerType=%s&fromcity=%s&tocity=%s&godate=%s&sign=%s";
		  String sign = null;
		  try {
			  sign = Encryption.getSign(Constants.BODINGACCOUNT, passengerType, fromcode, tocode, startdate);
		  } catch (NoSuchAlgorithmException e) {
			  e.printStackTrace();
		  }
		  String url = String.format(urlformat, Constants.BODINGACCOUNT, passengerType, fromcode, tocode, startdate, sign);
		  invokeXmlTask(url, whichday);
	  }
	  
	  private void loadDataOfDay(int whichday){
		  if(isInternational){
			  //String urlstr = "http://192.168.0.22:9404/FakeBodingServer/XMLServlet?day=today";
			  queryInternationalFlight(startdate, from.getCityCode(), to.getCityCode(), whichday);
		  }else{
			  queryDomesticFlight(startdate, from.getCityCode(), to.getCityCode(), whichday);
		  }
	  }
	  
	  public void goToNextActivity(FlightInterface flightInterface){
		  Intent intent = new Intent();
		  if(goToOrder){
			  Bundle bundle = new Bundle();
			  bundle.putBoolean(IntentExtraAttribute.IS_DOMESTIC_ORDER, !isInternational);
			  bundle.putBoolean(IntentExtraAttribute.IS_ROUNDWAY_ORDER, !isSingleWay);
			  if(!isSingleWay){
				  if(!isInternational){
					  bundle.putParcelable(IntentExtraAttribute.FLIGHT_LINE_INFO, 
							  (Flight)choosedGoFlight);
					  bundle.putParcelable(IntentExtraAttribute.FLIGHT_LINE_INFO_ROUNDWAY, 
							  (Flight)flightInterface);
				  }else{
					  GlobalVariables.roundWayFlightLine = (FlightLine)flightInterface;
//					  bundle.putParcelable(IntentExtraAttribute.FLIGHT_LINE_INFO, 
//							  (FlightLine)choosedGoFlight);
//					  bundle.putParcelable(IntentExtraAttribute.FLIGHT_LINE_INFO_ROUNDWAY, 
//							  (Flight)flightInterface);
				  }
			  }else{
				  if(!isInternational){
					  bundle.putParcelable(IntentExtraAttribute.FLIGHT_LINE_INFO, 
							  (Flight)flightInterface);
				  }else{
					  GlobalVariables.flightLine = (FlightLine)flightInterface;
//					  bundle.putParcelable(IntentExtraAttribute.FLIGHT_LINE_INFO, 
//							  (Flight)flightInterface);
				  }
			  }
			  intent.putExtras(bundle);
			  intent.setClass(this, OrderFormActivity.class);
		  }else{
			  intent.setClass(this, TicketSearchResultActivity.class);
			  Bundle bundle = new Bundle();
			  bundle.putInt(IntentExtraAttribute.PREVIOUS_ACTIVITY, ActivityNumber.TICKET_SEARCH_RESULT.getActivityNum());
			  bundle.putParcelable(IntentExtraAttribute.FLIGHT_QUERY, flightQuery);
			  if(!isInternational)
				  bundle.putParcelable(IntentExtraAttribute.CHOOSED_ROUNDWAY_GOFLIGHT, (Flight)flightInterface);
			  else{
				  GlobalVariables.flightLine = (FlightLine)flightInterface;
			  }
			  bundle.putString(IntentExtraAttribute.CLASS_INFO, classInfo);
			  intent.putExtras(bundle);
		  }
		  startActivityForResult(intent,IntentRequestCode.ORDER_FORM.getRequestCode());
	  }
	  
	  private void refreshOrder(){
		  switch(orderFlag){
		  case Constants.ORDERBYTIME:
			  todayAirline.orderLinesByLeatime(isLeatimeAsc);
			  break;
		  case Constants.ORDERBYPRICE:
			  todayAirline.orderLinesByPrice(isPriceAsc);
		  }
	  }
	  
	  public void doDefaultFilter(){
		  if(filterDialog == null){
			  List<String> classConstraints = new ArrayList<String>();
			  classConstraints.add(classInfo);
			  doFilter(new ArrayList<String>(), classConstraints, new ArrayList<String>());
		  }else{
			  doFilter(filterDialog.getTimeSegmentList(), filterDialog.getClassList(), filterDialog.getCompanyList());
		  }
	  }
}
