package com.boding.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.boding.adapter.HPagerAdapter;
import com.boding.adapter.VPagerAdapter;
import com.boding.constants.ActivityNumber;
import com.boding.constants.Constants;
import com.boding.constants.FlightStatus;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.task.FlightDynamicsTask;
import com.boding.util.CityUtil;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.dialog.NetworkUnavaiableDialog;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.VerticalViewPager;
import com.boding.view.dialog.WarningDialog;
import com.boding.R;
import com.boding.model.Airport;
import com.boding.model.City;
import com.boding.model.FlightDynamicQuery;
import com.boding.model.FlightDynamics;
import com.boding.model.FlightQuery;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends BodingBaseActivity {
	private boolean isSingleWay = true;
	
	private List<View> hList;
	private List<View> vList;
	private ViewPager hpager;
	private VerticalViewPager vpager;
	private LayoutInflater mInflater;
	private VPagerAdapter vAdapter;
	private HPagerAdapter hAdapter;

	private List<FlightDynamics> myFollowsFlightList = new ArrayList<FlightDynamics>();
	private List<View> myFollowsHList;
	private ViewPager myFollowsViewPager;
	private MyFollowsPagerAdapter myFollowsHAdapter;
	
	private String classInfo = Constants.COUCH_CLASS;
	
	/**
	 * Left page widgets
	 */
	private TextView leftpageFlyFromTextView;
	private TextView leftPageFlyFromCodeTextView;
	private TextView leftpageFlyToTextView;
	private TextView leftpageFlyToCodeTextView;
	private ImageView leftpageSwitchCityImageView;
	private LinearLayout leftpageFlyFromLinearLayout;
	private LinearLayout leftpageFlyToLinearLayout;
	private ImageView leftpageFlightWayChooseImageView;
	private LinearLayout leftPageSinglewayDateLinearLayout;
	private LinearLayout leftpageDateDividerLinearLayout;
	private LinearLayout leftpageReturnwayDateLinearLayout;
	private TextView leftpageFlyFromDateTextView;
	private TextView leftpageFlyToDateTextView;
	private LinearLayout leftpageVoiceSearchLinearLayout;
	private LinearLayout leftpageClassSelectionLinearLayout;
	private LinearLayout leftpagePassengerAdultLinearLayout;
	private LinearLayout leftpagePassengerChildLinearLayout;
	private TextView leftpagePassengerAdultTextView;
	private TextView leftpagePassengerChildTextView;
	private PopupWindow leftpagePassengerAudltPopup;
	private PopupWindow leftpagePassengerChildPopup;
	private ListView leftpagePassengerAmountSelectAudltListView; 
	private ListView leftpagePassengerAmountSelectChildListView; 
	private PassengerAmountAdapter leftpagePassengerAmountSelectAdultAdapter; 
	private PassengerAmountAdapter leftpagePassengerAmountSelectChildAdapter; 
	private TextView leftpageClassSelectionTextView;
	
	
	/**
	 * down page view
	 */
//	private TextView downpageTestLoginTextView;
	private LinearLayout downpageMyBodingLienarLayout;
	private LinearLayout downpageLowPriceSubscriptionLienarLayout;
	private LinearLayout downpageCommonInfoLayout;
	private LinearLayout downpageRentCarOnlineLienarLayout;
	private LinearLayout downpageContactBodingLienarLayout;
	private LinearLayout downpageAboutBodingLienarLayout;
	
	
	/**
	 * up page view
	 */
	private boolean isSearchByNum = false;
	private ImageView topPageSearchMethodImageView;
	private LinearLayout topPageSearchByFlightNumLinearLayout;
	private EditText topPageSearchByFlightNumEditText;
	private LinearLayout topPageSearchByFromToLinearLayout;
	private LinearLayout topPageFromLinearLayout;
	private TextView topPageFromTextView;
	private TextView topPageFromCodeTextView;
	private ImageView topPageSwitchCityImageView;
	private LinearLayout topPageToLinearLayout;
	private TextView topPageToTextView;
	private TextView topPageToCodeTextView;
	private LinearLayout topPageDateLinearLayout;
	private TextView topPageDateTextView;
	private LinearLayout topPageSearchLinearLayout;
	private LinearLayout topPageMyFollowsLinearLayout;
	
	/**
	 * right page view
	 */
	private LinearLayout rightPageChooseAirportLinearLayout;
	private TextView rightPageChoosedAirportTextView;
	private LinearLayout rightPageAirportWeatherLinearLayout;
	private LinearLayout rightPageAirportTransportLinearLayout;
	private LinearLayout rightPageAirportPhoneNumLinearLayout;
	private LinearLayout rightPageAirlineCompanyPhoneNumLinearLayout;
	
	private View leftPageView;
	private View rightPageView;
	private View middlePageView;
	private View upPageView;
	private View downPageView;
	private int curUpdatePager;
	
	private int unFollowFlightPost = -1; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);	
		
		warningDialog = new WarningDialog(this);
		progressBarDialog = new ProgressBarDialog(this);
		networkUnavaiableDialog = new NetworkUnavaiableDialog (this);
		
		mInflater = getLayoutInflater();
		
		getScreenSize();

		initMyFollowsViewPager();
		initHorizontalViewPager();
		initVerticalViewPager();
		
		initLeftPageView();
		initDownPageView();
		initTopPageView();
		initRightPageView();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		setFlyFromReturnDate();
	}
	
	
	private void initLeftPageView(){
		leftpageFlightWayChooseImageView = (ImageView)leftPageView.findViewById(R.id.left_page_flight_way_choose);
		leftpageFlightWayChooseImageView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(isSingleWay)
					swithToReturnWay();
				else
					switchToSingleWay();
			}
			
		});
		leftPageSinglewayDateLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_singleway_date_linearlayout);
		leftPageSinglewayDateLinearLayout.setOnClickListener(openDateSelectOnClickListener);
		leftpageDateDividerLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_chooseway_divider);
		leftpageReturnwayDateLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_returnway_date_linearlayout);
		leftpageReturnwayDateLinearLayout.setOnClickListener(openDateSelectOnClickListener);
		
		leftpageFlyFromDateTextView = (TextView)leftPageView.findViewById(R.id.fly_from_date_textView);
		leftpageFlyToDateTextView = (TextView)leftPageView.findViewById(R.id.fly_to_date_textView);
		switchToSingleWay();
		
		leftpageFlyFromTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_from_textView);
		leftPageFlyFromCodeTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_from_code_textView);
		leftpageFlyToTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_to_textView);
		leftpageFlyToCodeTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_to_code_textView);
		
		leftpageFlyFromLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_fly_from_linearlayout);
		leftpageFlyToLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_fly_to_linearlayout);
		leftpageFlyFromLinearLayout.setOnClickListener(openCitySelectOnClickListener);
		leftpageFlyToLinearLayout.setOnClickListener(openCitySelectOnClickListener);
		
		
		LinearLayout leftpageFlightSearchTicketLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_flight_search_ticket_linearLayout);
		leftpageFlightSearchTicketLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, TicketSearchResultActivity.class);
				FlightQuery fq = new FlightQuery();
				String fromcity = (String)leftpageFlyFromTextView.getText();
				String tocity = (String)leftpageFlyToTextView.getText();
				fq.setFromcity(fromcity);
				fq.setTocity(tocity);
				fq.setStartdate((String)leftpageFlyFromDateTextView.getText());
				if(!isSingleWay){
					fq.setReturndate((String)leftpageFlyToDateTextView.getText());
				}
				GlobalVariables.From_City = fromcity;
				GlobalVariables.To_City = tocity;
				Bundle bundle = new Bundle();
				bundle.putParcelable(IntentExtraAttribute.FLIGHT_QUERY, fq);
				bundle.putBoolean(Constants.IS_SINGLE_WAY, isSingleWay);
				bundle.putInt(IntentExtraAttribute.PREVIOUS_ACTIVITY, ActivityNumber.MAIN.getActivityNum());
				bundle.putString(IntentExtraAttribute.CLASS_INFO, classInfo);
				intent.putExtras(bundle);
				startActivityForResult(intent,IntentRequestCode.TICKET_SEARCH.getRequestCode());
			}
		});
		
		
		leftpageSwitchCityImageView = (ImageView)leftPageView.findViewById(R.id.leftpage_swithcity_imageView);
		leftpageSwitchCityImageView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				switchCity();
			}
		});
		
		leftpageVoiceSearchLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_voice_search_linearLayout);
		leftpageVoiceSearchLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, VoiceSearchActivity.class);
				startActivityForResult(intent,IntentRequestCode.VOICE_SEARCH.getRequestCode());
			}
			
		});
		
		leftpageClassSelectionLinearLayout = (LinearLayout) leftPageView.findViewById(R.id.flight_class_selection_linearLayout);
		leftpageClassSelectionTextView = (TextView)leftPageView.findViewById(R.id.flight_class_spinner_textView);
		leftpageClassSelectionLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				final List<String> classList = new ArrayList<String>();
				classList.add(Constants.COUCH_CLASS);
				classList.add(Constants.FIRST_CLASS);
				classList.add(Constants.BUSINESS_CLASS);
				
				SelectionDialog classSelectionDialog = new SelectionDialog(MainActivity.this,
						"选择舱位",classList);
				classSelectionDialog.setOnItemSelectedListener(new SelectionDialog.OnItemSelectedListener() {
					@Override
					public void OnItemSelected(int position) {
						classInfo = classList.get(position);
						leftpageClassSelectionTextView.setText(classInfo);
					}
				});
				classSelectionDialog.show();
			}
        });
		
		leftpagePassengerAdultLinearLayout = (LinearLayout) leftPageView.findViewById(R.id.flight_passengar_adult_linearLayout);
		leftpagePassengerAdultTextView = (TextView) leftPageView.findViewById(R.id.flight_passengar_adult_textView);
		leftpagePassengerAdultLinearLayout.addOnLayoutChangeListener(new OnLayoutChangeListener(){
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				int finalWidth = right-left;
				if(finalWidth > 0){
					initPopupWindow(true, finalWidth);
				}
			}
			
		});
		leftpagePassengerAdultLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popupWindowShowing(true);
			}
		});
		
		
		leftpagePassengerChildLinearLayout = (LinearLayout) leftPageView.findViewById(R.id.flight_passengar_child_linearLayout);
		leftpagePassengerChildTextView = (TextView) leftPageView.findViewById(R.id.flight_passengar_child_textView);
		leftpagePassengerChildLinearLayout.addOnLayoutChangeListener(new OnLayoutChangeListener(){
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				int finalWidth = right-left;
				if(finalWidth > 0){
					initPopupWindow(false, finalWidth);
				}
			}
			
		});
		leftpagePassengerChildLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popupWindowShowing(false);
			}
		});
		
	}
	
	private void switchCity(){
		City tempCity = GlobalVariables.Fly_From_City;
		GlobalVariables.Fly_From_City = GlobalVariables.Fly_To_City;
		GlobalVariables.Fly_To_City = tempCity;
		setFlyFromToCity();
	}
	
	private void initDownPageView(){
		downpageMyBodingLienarLayout = (LinearLayout) downPageView.findViewById(R.id.downpage_myboding_linearlayout);
		downpageLowPriceSubscriptionLienarLayout = (LinearLayout) downPageView.findViewById(R.id.downpage_lowpricesubscription_linearlayout);
		downpageCommonInfoLayout = (LinearLayout) downPageView.findViewById(R.id.downpage_commoninfo_linearlayout);
		downpageRentCarOnlineLienarLayout = (LinearLayout) downPageView.findViewById(R.id.downpage_rentcaronline_linearlayout);
		downpageContactBodingLienarLayout = (LinearLayout) downPageView.findViewById(R.id.downpage_contactboding_linearlayout);
		downpageAboutBodingLienarLayout = (LinearLayout) downPageView.findViewById(R.id.downpage_aboutboding_linearlayout);
		
		downpageMyBodingLienarLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MyBodingActivity.class);
				startActivityForResult(intent, IntentRequestCode.MYBODING.getRequestCode());
			}
		});
		
		downpageLowPriceSubscriptionLienarLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				if(GlobalVariables.bodingUser == null){
					intent.setClass(MainActivity.this, LoginActivity.class);
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}else if(!GlobalVariables.bodingUser.isActivated_state()){
					intent.setClass(MainActivity.this, VerifyPhonenumActivity.class);
					intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "2");
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}else{
					intent.setClass(MainActivity.this, LowPriceSubscribeActivity.class);
					startActivityForResult(intent, IntentRequestCode.LOWPRICE_SUBSCRIBE.getRequestCode());
				}
			}
		});
		
		downpageCommonInfoLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CommonInfoActivity.class);
				startActivityForResult(intent, IntentRequestCode.COMMON_INFO.getRequestCode());				
			}
		});
		
		downpageContactBodingLienarLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ContactBodingActivity.class);
				startActivityForResult(intent, IntentRequestCode.CONTACT_BODING.getRequestCode());				
			}
		});
		
		downpageAboutBodingLienarLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AboutBodingActivity.class);
				startActivityForResult(intent, IntentRequestCode.ABOUT_BODING.getRequestCode());				
			}
		});
	}
	
	private void initTopPageView(){
		topPageSearchMethodImageView = (ImageView) upPageView.findViewById(R.id.toppage_searchmethod_imageView);
		topPageSearchByFlightNumLinearLayout = (LinearLayout) upPageView.findViewById(R.id.toppage_searchby_flightNum_linearlayout);
		topPageSearchByFlightNumEditText = (EditText) upPageView.findViewById(R.id.toppage_searchbyflightnum_editText);
		topPageSearchByFromToLinearLayout = (LinearLayout) upPageView.findViewById(R.id.toppage_searchby_flyfromto_linearlayout);
		topPageFromLinearLayout = (LinearLayout) upPageView.findViewById(R.id.toppage_fly_from_linearlayout);
		topPageFromTextView = (TextView) upPageView.findViewById(R.id.toppage_fly_from_textView);
		topPageFromCodeTextView = (TextView) upPageView.findViewById(R.id.toppage_fly_from_code_textView);
		topPageSwitchCityImageView = (ImageView) upPageView.findViewById(R.id.toppage_swithcity_imageView);
		topPageToLinearLayout = (LinearLayout) upPageView.findViewById(R.id.toppage_fly_to_linearlayout);
		topPageToTextView = (TextView) upPageView.findViewById(R.id.toppage__fly_to_textView);
		topPageToCodeTextView = (TextView) upPageView.findViewById(R.id.toppage_fly_to_code_textView);
		topPageDateLinearLayout = (LinearLayout) upPageView.findViewById(R.id.toppage__date_linearlayout);
		topPageDateTextView = (TextView) upPageView.findViewById(R.id.toppage_date_textView);
		topPageSearchLinearLayout = (LinearLayout) upPageView.findViewById(R.id.toppage_search_linearLayout);
		topPageMyFollowsLinearLayout = (LinearLayout) upPageView.findViewById(R.id.toppage_myfollowed_linearLayout);
		
		setTopPageSearchMethod();
		
		setFlyFromReturnDate();
		setFlyFromToCity();
		/**
		 * Listeners
		 */
		topPageSearchMethodImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				isSearchByNum = !isSearchByNum;
				setTopPageSearchMethod();
			}
		});
		topPageFromLinearLayout.setOnClickListener(openCitySelectOnClickListener);
		topPageToLinearLayout.setOnClickListener(openCitySelectOnClickListener);
		topPageDateLinearLayout.setOnClickListener(openDateSelectOnClickListener);
		topPageSwitchCityImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				switchCity();
			}
		});
		topPageMyFollowsLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(GlobalVariables.bodingUser == null){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, LoginActivity.class);
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}else if(!GlobalVariables.bodingUser.isActivated_state()){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, VerifyPhonenumActivity.class);
					intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "2");
					startActivityForResult(intent, IntentRequestCode.VERIFY_PHONENUM.getRequestCode());
					return;
				}
				if(!Util.isNetworkAvailable(MainActivity.this)){
					networkUnavaiableDialog.show();
					return;
				}
				progressBarDialog.show();
				(new FlightDynamicsTask(MainActivity.this, HTTPAction.GET_MYFOLLOWED_FROM_MAIN)).execute();
			}
		});
		topPageSearchLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, FlightDynamicsListActivity.class);
				FlightDynamicQuery fdq = new FlightDynamicQuery();
				if(isSearchByNum){
					fdq.setFlightNum(topPageSearchByFlightNumEditText.getText().toString().toUpperCase());
				}else{
					String fromCityCode = GlobalVariables.Fly_From_City.getCityCode();
					String toCityCode = GlobalVariables.Fly_To_City.getCityCode();
					fdq.setFromCityCode(fromCityCode);
					fdq.setToCityCode(toCityCode);
					fdq.setFromCityName(GlobalVariables.Fly_From_City.getCityName());
					fdq.setToCityName(GlobalVariables.Fly_To_City.getCityName());
				}
				fdq.setDate(GlobalVariables.Fly_From_Date);
				Bundle bundle = new Bundle();
				bundle.putBoolean(IntentExtraAttribute.IS_FOLLOWEDLIST, false);
				bundle.putParcelable(IntentExtraAttribute.FLIGHT_DYNAMIC_QUERY, fdq);
				intent.putExtras(bundle);
				startActivityForResult(intent,IntentRequestCode.FLIGHTDYNAMICS_LIST.getRequestCode());				
			}
		});
	}
	
	private void initRightPageView(){
		rightPageChooseAirportLinearLayout = (LinearLayout) rightPageView.findViewById(R.id.rightpage_chooseairport_linearLayout);
		rightPageChoosedAirportTextView = (TextView) rightPageView.findViewById(R.id.rightpage_choosedairport_textView);
		rightPageAirportWeatherLinearLayout = (LinearLayout) rightPageView.findViewById(R.id.rightpage_airportweather_linearLayout);
		rightPageAirportTransportLinearLayout = (LinearLayout) rightPageView.findViewById(R.id.rightpage_airporttransport_linearLayout);
		rightPageAirportPhoneNumLinearLayout = (LinearLayout) rightPageView.findViewById(R.id.rightpage_airportphonenum_linearLayout);
		rightPageAirlineCompanyPhoneNumLinearLayout = (LinearLayout) rightPageView.findViewById(R.id.rightpage_airlinecompanyPhoneNum_linearLayout);
		
		rightPageChooseAirportLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AirportSelectActivity.class);
				startActivityForResult(intent, IntentRequestCode.AIRPORT_SELECT.getRequestCode());
			}
		});
		
		rightPageAirportWeatherLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AirportWeatherActivity.class);
				startActivityForResult(intent, IntentRequestCode.AIRPORT_WEATHER.getRequestCode());
			}
		});
		
		rightPageAirportTransportLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AirportTransportActivity.class);
				startActivityForResult(intent, IntentRequestCode.AIRPORT_TRANSPORT.getRequestCode());
			}
		});
		
		rightPageAirportPhoneNumLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AirportPhoneNumActivity.class);
				startActivityForResult(intent, IntentRequestCode.AIRPORT_PHONENUM.getRequestCode());
			}
		});
		
		rightPageAirlineCompanyPhoneNumLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AirlineCompanyPhoneNumActivity.class);
				startActivityForResult(intent, IntentRequestCode.AIRLINECOMPANY_PHONENUM.getRequestCode());
			}
		});
		
		setSelectedAirport();
	}
	
	private void setSelectedAirport(){
		if(GlobalVariables.SELECTED_AIRPORT == null){
			GlobalVariables.SELECTED_AIRPORT = new Airport();
			GlobalVariables.SELECTED_AIRPORT.setAirportcode("SHA");
			GlobalVariables.SELECTED_AIRPORT.setAirportname("上海虹桥机场");
		}
		rightPageChoosedAirportTextView.setText(GlobalVariables.SELECTED_AIRPORT.getAirportname());
	}
	
	private void setTopPageSearchMethod(){
		/**
		 * set view content
		 */
		if(isSearchByNum){
			topPageSearchByFlightNumLinearLayout.setVisibility(View.VISIBLE);
			topPageSearchByFromToLinearLayout.setVisibility(View.GONE);
			topPageSearchMethodImageView.setImageResource(R.drawable.toppage_searchmethod_flightnum);
		}else{
			topPageSearchByFlightNumLinearLayout.setVisibility(View.GONE);
			topPageSearchByFromToLinearLayout.setVisibility(View.VISIBLE);
			topPageSearchMethodImageView.setImageResource(R.drawable.toppage_searchmethod_flyfromto);
		}
	}
	
	public void setMyFollowsFlightList(List<FlightDynamics> dynamicsList){
		progressBarDialog.dismiss();
		if(dynamicsList.size() == 0){
			Util.showToast(this, "暂无已关注的航班");
			return;
		}
		myFollowsFlightList.clear();
		myFollowsFlightList.add(new FlightDynamics());
		
//		for(int i = 0; i < 5; i++){
//			myFollowsHList.add(mInflater.inflate(R.layout.layout_flightboard, null));
//			FlightDynamics flightDynamics = new FlightDynamics();
//			flightDynamics.setId("12");
//			flightDynamics.setDate("2012-23-23");
//			flightDynamics.setCarrier("8c");
//			flightDynamics.setNum("dddd");
//			flightDynamics.setCar_name("东方航空");
//			flightDynamics.setPlan_dep_time("09:20");
//			flightDynamics.setExpect_dep_time("09:20");
//			flightDynamics.setActual_dep_time("");
//			flightDynamics.setDep_airport_code("SHA");
//			flightDynamics.setArr_airport_code("PEK");
//			flightDynamics.setDep_airport_name("上海");
//			flightDynamics.setArr_airport_name("北京");
//			flightDynamics.setDep_terminal("T1");
//			flightDynamics.setArr_terminal("T1");
//			flightDynamics.setPlan_arr_time("09:20");
//			flightDynamics.setExpect_arr_time("09:20");
//			flightDynamics.setActual_arr_time("09:20");
//			flightDynamics.setFlightStatusByCode(FlightStatus.values()[i].getFlightStatusCode());
//			flightDynamics.setFollowed(true);
//			myFollowsFlightList.add(flightDynamics);
//		}
		for(int i = 0; i < dynamicsList.size(); i++){
			myFollowsHList.add(mInflater.inflate(R.layout.layout_flightboard, null));
			myFollowsFlightList.add(dynamicsList.get(i));
		}
		myFollowsHAdapter.notifyDataSetChanged();
		
		myFollowsViewPager.setCurrentItem(1);
		removeViewPagerItem(0);
	}
	
	private void initPopupWindow(boolean isAdult, int parentWidth){
		List<String> passengerAmountList = new ArrayList<String>();
		passengerAmountList.add("1");
		passengerAmountList.add("2");
		passengerAmountList.add("3");
		passengerAmountList.add("4");
		passengerAmountList.add("5");
		passengerAmountList.add("6");
		passengerAmountList.add("7");
		passengerAmountList.add("8");
		passengerAmountList.add("9");
		View popupWindow =  LayoutInflater.from(this).inflate(R.layout.popup_choose_passengeramount, null);
		if(isAdult){
			leftpagePassengerAmountSelectAudltListView = (ListView)popupWindow.findViewById(R.id.passengeramount_select_list);
			leftpagePassengerAmountSelectAdultAdapter = new PassengerAmountAdapter(passengerAmountList, true);
			leftpagePassengerAmountSelectAudltListView.setAdapter(leftpagePassengerAmountSelectAdultAdapter);
			leftpagePassengerAudltPopup = new PopupWindow(popupWindow,parentWidth,LayoutParams.WRAP_CONTENT,true);
			leftpagePassengerAudltPopup.setOutsideTouchable(true);
			leftpagePassengerAudltPopup.setBackgroundDrawable(new BitmapDrawable());
		}else{
			leftpagePassengerAmountSelectChildListView = (ListView)popupWindow.findViewById(R.id.passengeramount_select_list);
			leftpagePassengerAmountSelectChildAdapter = new PassengerAmountAdapter(passengerAmountList, false);
			leftpagePassengerAmountSelectChildListView.setAdapter(leftpagePassengerAmountSelectChildAdapter);
			leftpagePassengerChildPopup = new PopupWindow(popupWindow,parentWidth,LayoutParams.WRAP_CONTENT,true);
			leftpagePassengerChildPopup.setOutsideTouchable(true);
			leftpagePassengerChildPopup.setBackgroundDrawable(new BitmapDrawable());
		}
	}
	private void popupWindowShowing(boolean isAdult){
		if(isAdult){
			leftpagePassengerAudltPopup.showAsDropDown(leftpagePassengerAdultLinearLayout, 0, -3);
		}else{
			leftpagePassengerChildPopup.showAsDropDown(leftpagePassengerChildLinearLayout, 0, -3);
		}
	}
	
	private void popupWindowDismiss(boolean isAdult){
		if(isAdult){
			leftpagePassengerAudltPopup.dismiss();
		}else{
			leftpagePassengerChildPopup.dismiss();
		}
	}
	
	private void switchToSingleWay(){
		leftpageFlightWayChooseImageView.setImageResource(R.drawable.leftpage_singleway_line);
		leftpageDateDividerLinearLayout.setVisibility(View.INVISIBLE);
		leftpageReturnwayDateLinearLayout.setVisibility(View.INVISIBLE);
		isSingleWay = true;
		GlobalVariables.isRoundWaySelection = false;
	}
	
	private void swithToReturnWay(){
		leftpageFlightWayChooseImageView.setImageResource(R.drawable.leftpage_returnway_line);
		leftpageDateDividerLinearLayout.setVisibility(View.VISIBLE);
		leftpageReturnwayDateLinearLayout.setVisibility(View.VISIBLE);
		isSingleWay = false;
		GlobalVariables.isRoundWaySelection = true;
	}
	
	private void setFlyFromReturnDate(){
		if(GlobalVariables.Fly_From_Date==null){
			Calendar calendar = Calendar.getInstance();
			String flyFromDate = DateUtil.getFormatedDate(calendar);
			GlobalVariables.Fly_From_Date = flyFromDate;
		}
		leftpageFlyFromDateTextView.setText(GlobalVariables.Fly_From_Date);
		topPageDateTextView.setText(GlobalVariables.Fly_From_Date);
		
		if(GlobalVariables.Fly_Return_Date==null){
			DateUtil.setRetunrnWayDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtil.getDateFromString(GlobalVariables.Fly_From_Date));
			calendar.add(Calendar.HOUR, 7*24);
			String flyToDate = DateUtil.getFormatedDate(calendar);
			GlobalVariables.Fly_Return_Date = flyToDate;
		}
		leftpageFlyToDateTextView.setText(GlobalVariables.Fly_Return_Date);
	}
	
	private void setFlyFromToCity(){
		if(GlobalVariables.Fly_From_City!=null){
			leftpageFlyFromTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_From_City.getCityName()));
			leftPageFlyFromCodeTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_From_City.getCityCode()));
			topPageFromTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_From_City.getCityName()));
			topPageFromCodeTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_From_City.getCityCode()));
		}else{
			GlobalVariables.Fly_From_City = new City("上海","SHA",false,"中国");
		}
		if(GlobalVariables.Fly_To_City!=null){
			leftpageFlyToTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_To_City.getCityName()));
			leftpageFlyToCodeTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_To_City.getCityCode()));
			topPageToTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_To_City.getCityName()));
			topPageToCodeTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_To_City.getCityCode()));
		}else{
			GlobalVariables.Fly_To_City = new City("北京","PEK",false,"中国");
		}
	}
	
	OnClickListener openCitySelectOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			int viewId = arg0.getId();
			boolean isFlyToCitySelection = false;
			if(viewId==R.id.leftpage_fly_to_linearlayout || viewId == R.id.toppage_fly_to_linearlayout)
				isFlyToCitySelection = true;
			
//			ViewGroup.LayoutParams flyToParams = leftpageFlyToTextView.getLayoutParams();
//			flyToParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//			leftpageFlyToTextView.setLayoutParams(flyToParams);
//			ViewGroup.LayoutParams flyFromParams = leftpageFlyToTextView.getLayoutParams();
//			flyFromParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//			leftpageFlyFromTextView.setLayoutParams(flyFromParams);
			
			Bundle bundle  = new Bundle();
			bundle.putBoolean(Constants.IS_FLY_TO_CITY_SELECTION, isFlyToCitySelection);
			Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			intent.setClass(MainActivity.this, CitySelectActivity.class);
			intent.putExtras(bundle);
			startActivityForResult(intent,IntentRequestCode.CITY_SELECTION.getRequestCode());
		}
		
	};
	
	OnClickListener openDateSelectOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			int viewId = arg0.getId();
			boolean isReturnDateSelection = false;
			if(viewId == R.id.leftpage_returnway_date_linearlayout)
				isReturnDateSelection = true;
			
			Bundle bundle  = new Bundle();
			bundle.putBoolean(Constants.IS_RETURN_DATE_SELECTION, isReturnDateSelection);
//			bundle.putBoolean(Constants.IS_SINGLE_WAY, isSingleWay);
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, DateSelectActivity.class);
//			GlobalVariables.isFlyToCitySelection = isFlyToCitySelection;
			intent.putExtras(bundle);
			startActivityForResult(intent,IntentRequestCode.DATE_SELECTION.getRequestCode());
		}
		
	};
	
	@SuppressLint("NewApi")
	private void getScreenSize(){
		Display display = getWindowManager().getDefaultDisplay();
		Point screenSize = new Point();
		display.getSize(screenSize);
		GlobalVariables.Screen_Height = screenSize.y;
		GlobalVariables.Screen_Width = screenSize.x;
	}
	
	private void initHorizontalViewPager(){
		leftPageView = mInflater.inflate(R.layout.layout_left, null);
		middlePageView = mInflater.inflate(R.layout.layout_middle, null);
		rightPageView = mInflater.inflate(R.layout.layout_right, null);
		hList = new ArrayList<View>();
		hList.add(leftPageView);
		hList.add(middlePageView);
		hList.add(rightPageView);
		
		hpager = (ViewPager)(mInflater.inflate(R.layout.activity_main, null).findViewById(R.id.hpager));
		hAdapter = new HPagerAdapter(hList);
		hpager.setAdapter(hAdapter);
		hpager.setCurrentItem(1);
		
		hpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 != 1){
					vpager.SetCanScroll(false);
				}else{
					vpager.SetCanScroll(true);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	private void initVerticalViewPager(){
		vList = new ArrayList<View>();	
		downPageView = mInflater.inflate(R.layout.layout_down, null);
		vList.add(myFollowsViewPager);
//		vList.add(upPageView);
		vList.add(hpager);
		vList.add(downPageView);
		
		vAdapter = new VPagerAdapter(vList);		
		vpager = (VerticalViewPager)findViewById(R.id.vpager);
		vpager.setAdapter(vAdapter);	
		vpager.setCurrentItem(1);
		
	}
	
	private void initMyFollowsViewPager(){
		myFollowsHList = new ArrayList<View>();
		upPageView = mInflater.inflate(R.layout.layout_up, null);
		myFollowsHList.add(upPageView);
		
		myFollowsViewPager = (ViewPager)(mInflater.inflate(R.layout.layout_up_myfollows, null).findViewById(R.id.toppage_hpager));
		myFollowsHAdapter = new MyFollowsPagerAdapter();
		myFollowsViewPager.setAdapter(myFollowsHAdapter);
		myFollowsViewPager.setCurrentItem(0);
		
//		myFollowsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//			@Override
//			public void onPageSelected(int arg0) {
//				if(arg0 != 1){
//					vpager.SetCanScroll(false);
//				}else{
//					vpager.SetCanScroll(true);
//				}
//			}
//			
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//			}
//			
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//			}
//		}); 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  if(data == null)
			  return;
		  if(requestCode==IntentRequestCode.DATE_SELECTION.getRequestCode()){
			  setFlyFromReturnDate();
		  }
		  if(requestCode == IntentRequestCode.CITY_SELECTION.getRequestCode())
			  setFlyFromToCity();
		  if(requestCode == IntentRequestCode.AIRPORT_SELECT.getRequestCode())
			  setSelectedAirport();
	 }
	
	private class PassengerAmountAdapter extends BaseAdapter{
		List<String> passengerAmountList;
		boolean isAdult;
		
		public PassengerAmountAdapter(List<String> passengerAmountList, boolean isAdult){
			this.passengerAmountList = passengerAmountList;
			this.isAdult = isAdult;
		}
		
		
		@Override
		public int getCount() {
			return passengerAmountList.size();
		}

		@Override
		public String getItem(int position) {
			return passengerAmountList.get(position);
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
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item_passengeramount_selector, null);
				holder.textView = (TextView)convertView.findViewById(R.id.current_passengerselector_dialog_textView);
				holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.current_passengerselector_dialog_lienarLayout);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.textView.setText(passengerAmountList.get(position));
			holder.linearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					popupWindowDismiss(isAdult);
				}
			});
			return convertView;
		}
		
		class ViewHolder{
			LinearLayout linearLayout;
			TextView textView;
		}
		
	}

	private class MyFollowsPagerAdapter extends android.support.v4.view.PagerAdapter{
		@Override
		public int getCount() {
			return myFollowsHList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}
		
		@Override  
	    public int getItemPosition(Object object) {  
	        View view = (View)object;  
	        if(curUpdatePager == (Integer)view.getTag()){  
	            return POSITION_NONE;    
	        }else{  
	            return POSITION_UNCHANGED;  
	        }  
	    }
		
		@Override
		public Object instantiateItem(ViewGroup collection, final int position) {
			myFollowsHList.get(position).setTag(position);
			View view = (View)(myFollowsHList.get(position));
			(collection).addView(view);
			
			if(view.findViewById(R.id.layoutflightboard_fromCity_textView) != null){
				ImageView topLineImageView = (ImageView) view.findViewById(R.id.layoutflightboard_flightdynamics_topline_imageView);
				LinearLayout searchLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_flightdynamics_search_linearLayout);
				TextView flightCompanyNumTextView = (TextView) view.findViewById(R.id.layoutflightboard_flightcompanyNumTextView);
				TextView fromCityTextView = (TextView) view.findViewById(R.id.layoutflightboard_fromCity_textView);
				TextView fromCityWeatherTextView = (TextView) view.findViewById(R.id.layoutflightboard_fromCityWeather_textView);
				ImageView fromCityWeatherImageView = (ImageView) view.findViewById(R.id.layoutflightboard_fromCityWeather_imageView);
				TextView fromTerminalTextView = (TextView) view.findViewById(R.id.layoutflightboard_fromTerminal_textView);
				TextView planeFromTimeTextView = (TextView) view.findViewById(R.id.layoutflightboard_planFromTime_textView);
				LinearLayout leftTopLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_leftTop_lienarLayout);
				TextView actualFromTimeTextView = (TextView) view.findViewById(R.id.layoutflightboard_actualFromTime_textView);
				LinearLayout leftTopLineLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_leftTopLine_lienarLayout);
				LinearLayout leftBottomLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_leftBottom_lienarLayout);
				LinearLayout leftBottomLineLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_leftBottomLine_lienarLayout);
				TextView onTimeRateTextTextView = (TextView) view.findViewById(R.id.layoutflightboard_ontimeRateText_textView);
				TextView onTimeRateTextView = (TextView) view.findViewById(R.id.layoutflightboard_ontimeRate_textView);
				TextView dateTextView = (TextView) view.findViewById(R.id.layoutflightboard_date_textView);
				LinearLayout refreshLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_refresh_linearLayout);
				TextView toCityTextView = (TextView) view.findViewById(R.id.layoutflightboard_toCity_textView);
				TextView toCityWeatherTextView = (TextView) view.findViewById(R.id.layoutflightboard_toCityWeather_textView);
				ImageView toCityWeatherImageView = (ImageView) view.findViewById(R.id.layoutflightboard_toCityWeather_imageView);
				TextView toTerminalTextView = (TextView) view.findViewById(R.id.layoutflightboard_toTerminal_textView);
				TextView planToTimeTextView = (TextView) view.findViewById(R.id.layoutflightboard_planToTime_textView);
				LinearLayout rightTopLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_rightTop_lienarLayout);
				TextView actualToTimeTextView = (TextView) view.findViewById(R.id.layoutflightboard_actualToTime_textView);
				LinearLayout rightTopLineLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_rightTopLine_lienarLayout);
				LinearLayout rightBottomLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_rightBottom_lienarLayout);
				LinearLayout rightBottomLineLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_rightBottomLine_lienarLayout);
				TextView fromAirportInfoTextView = (TextView) view.findViewById(R.id.layoutflightboard_fromAirportInfo_textView);
				LinearLayout fromAirportInfoLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_fromAirportInfo_linearLayout);
				TextView toAirportInfoTextView = (TextView) view.findViewById(R.id.layoutflightboard_toAirportInfo_textView);
				LinearLayout toAirportInfoLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_toAirportInfo_linearLayout);
				LinearLayout unFollowLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_follow_linearLayout);
				ImageView pageIndicatorImageView = (ImageView) view.findViewById(R.id.layoutflightboard_pageIndicator_imageView);
				LinearLayout fromDividerLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_fromDivider_linearLayout);
				LinearLayout toDividerLinearLayout = (LinearLayout) view.findViewById(R.id.layoutflightboard_toDivider_linearLayout);
				
				ImageView statusImageView = (ImageView) view.findViewById(R.id.layoutflightboard_status_imageView);
				
				/**
				 * set content
				 */
				FlightDynamics dynamics = myFollowsFlightList.get(position);
				topLineImageView.setImageResource(dynamics.getFlightStatus().getFlightStatusLine());
				flightCompanyNumTextView.setText(dynamics.getCar_name() + dynamics.getCarrier() + dynamics.getNum());
				fromCityTextView.setText(CityUtil.getCityNameByCode(dynamics.getDep_airport_code()));
				if (dynamics.getDep_weather() == null){
					fromCityWeatherTextView.setVisibility(View.GONE);
					fromCityWeatherImageView.setVisibility(View.GONE);
				}else{
					fromCityWeatherTextView.setText(dynamics.getDep_weather().getWeatherName());
					fromCityWeatherImageView.setImageResource(dynamics.getDep_weather().getWeatherDrawable());
				}
				int color = MainActivity.this.getResources().getColor(dynamics.getFlightStatus().getFlightStatusColor());
				fromDividerLinearLayout.setBackgroundColor(color);
				toDividerLinearLayout.setBackgroundColor(color);
				
				fromTerminalTextView.setText(dynamics.getDep_airport_name() + dynamics.getDep_terminal());
				planeFromTimeTextView.setText(dynamics.getPlan_dep_time());
				if(!dynamics.getActual_dep_time().equals(""))
					actualFromTimeTextView.setText(dynamics.getActual_dep_time());
				onTimeRateTextView.setText(dynamics.getPunctuality());
				onTimeRateTextTextView.setTextColor(color);
				onTimeRateTextView.setTextColor(color);
				dateTextView.setText(dynamics.getDate());
				toCityTextView.setText(CityUtil.getCityNameByCode(dynamics.getArr_airport_code()));
				if(dynamics.getArr_weather() == null){
					toCityWeatherTextView.setVisibility(View.GONE);
					toCityWeatherImageView.setVisibility(View.GONE);
				}else{
					toCityWeatherTextView.setText(dynamics.getArr_weather().getWeatherName());
					toCityWeatherImageView.setImageResource(dynamics.getArr_weather().getWeatherDrawable());
				}
				toTerminalTextView.setText(dynamics.getArr_airport_name() + dynamics.getArr_terminal());
				planToTimeTextView.setText(dynamics.getPlan_arr_time());
				if(!dynamics.getActual_arr_time().equals(""))
					actualToTimeTextView.setText(dynamics.getActual_arr_time());
				fromAirportInfoTextView.setText(dynamics.getDep_airport_name());
				toAirportInfoTextView.setText(dynamics.getArr_airport_name());
				statusImageView.setImageResource(dynamics.getFlightStatus().getLayoutFlightStatus());
				
				int pageSize = myFollowsFlightList.size();
				switch(pageSize){
					case 1:
						pageIndicatorImageView.setVisibility(View.INVISIBLE);
					break;
					case 2:
						if(position == 0)
							pageIndicatorImageView.setImageResource(R.drawable.twopages_first);
						else
							pageIndicatorImageView.setImageResource(R.drawable.twopages_second);
					break;
					default:
						if(position == 0)
							pageIndicatorImageView.setImageResource(R.drawable.threepages_first);
						else if (position == pageSize - 1)
							pageIndicatorImageView.setImageResource(R.drawable.threepages_third);
						else
							pageIndicatorImageView.setImageResource(R.drawable.threepages_second);
					break;
				}
				
				BitmapDrawable bitmap = (BitmapDrawable)(MainActivity.this.getResources().getDrawable(R.drawable.layoutflightboard_arrive));
				int picSize = bitmap.getBitmap().getHeight() /2;
				LayoutParams params = (LayoutParams) leftTopLinearLayout.getLayoutParams();
				params.height = picSize;
				leftTopLinearLayout.setLayoutParams(params);

				params = (LayoutParams) leftBottomLinearLayout.getLayoutParams();
				params.height = picSize;
				leftBottomLinearLayout.setLayoutParams(params);
				
				params = (LayoutParams) rightTopLinearLayout.getLayoutParams();
				params.height = picSize;
				rightTopLinearLayout.setLayoutParams(params);
				
				params = (LayoutParams) rightBottomLinearLayout.getLayoutParams();
				params.height = picSize;
				rightBottomLinearLayout.setLayoutParams(params);
				
				int lineColor = MainActivity.this.getResources().getColor(dynamics.getFlightStatus().getLayoutLineColor());
				leftTopLineLinearLayout.setBackgroundColor(lineColor);
				leftBottomLineLinearLayout.setBackgroundColor(lineColor);
				rightTopLineLinearLayout.setBackgroundColor(lineColor);
				rightBottomLineLinearLayout.setBackgroundColor(lineColor);
				
				/**
				 * add listeners
				 */
				searchLinearLayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						returnToUpPage();
					}
				});
				
				unFollowLinearLayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						unFollowFlightPost = position;
						unFollowFlight();
					}
				});
			}
			return myFollowsHList.get(position);
		}
		
		@Override
		public void destroyItem(ViewGroup collection, int position, Object view) {
			(collection).removeView((View)(myFollowsHList.get(position)));
		}
	}
	
	private void unFollowFlight(){
		if(!Util.isNetworkAvailable(MainActivity.this)){
			networkUnavaiableDialog.show();
			return;
		}
		progressBarDialog.show();
		(new FlightDynamicsTask(this, HTTPAction.UNFOLLOW_FLIGHTDYNAMICS_FROM_MAIN)).execute(myFollowsFlightList.get(unFollowFlightPost).getId());
	}
	
	public void setUnFollowFlightResult(boolean isSuccess){
		progressBarDialog.dismiss();
		if(isSuccess){
			if(myFollowsFlightList.size() == 1){
				returnToUpPage();
				return;
			}
			if(unFollowFlightPost == 0){
				myFollowsViewPager.setCurrentItem(1);
				removeViewPagerItem(0);
			}else{
				myFollowsViewPager.setCurrentItem(unFollowFlightPost - 1);
				removeViewPagerItem(unFollowFlightPost);
				myFollowsViewPager.setCurrentItem(unFollowFlightPost - 1);
			}
		}else{
			Util.showToast(this, "取消关注失败");
		}
	}
	
	private void returnToUpPage(){
		myFollowsViewPager.setCurrentItem(0);
		updateViewPagerItem(upPageView, 0);
		
		myFollowsViewPager.setAdapter(null);
		for(int i = 1; i < myFollowsFlightList.size(); i++){
			myFollowsHList.remove(1);
		}
		myFollowsViewPager.setAdapter(myFollowsHAdapter);
	}
	
	private void removeViewPagerItem(int position){
		myFollowsViewPager.setAdapter(null);
		myFollowsHList.remove(position);
		myFollowsFlightList.remove(position); 
		myFollowsViewPager.setAdapter(myFollowsHAdapter);
	}
	
	/** 
	 * 更换pager的方法 
	 * @param view   新的pager 
	 * @param index  第几页 
	 *  
	 * 示例：updateViewPagerItem(fragment2_parentctrl_changepwd,1); 
	 */  
	private void updateViewPagerItem(View view,int index){  
	    curUpdatePager = index;  
	    myFollowsHList.remove(index);  
	    myFollowsHList.add(index, view);  
	    myFollowsViewPager.getAdapter().notifyDataSetChanged();  
	}
}
