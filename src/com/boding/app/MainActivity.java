package com.boding.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.boding.adapter.HPagerAdapter;
import com.boding.adapter.VPagerAdapter;
import com.boding.constants.ActivityNumber;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.task.InitCityTask;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.VerticalViewPager;
import com.boding.view.layout.CalendarLayout;
import com.boding.view.layout.OrderFlightInfoLayout;
import com.boding.view.layout.CalendarLayout.OnItemClickListener;
import com.boding.R;
import com.boding.model.City;
import com.boding.model.FlightQuery;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private boolean isSingleWay = true;
	
	private List<View> hList;
	private List<View> vList;
	private ViewPager hpager;
	private VerticalViewPager vpager;
	private LayoutInflater mInflater;
	private VPagerAdapter vAdapter;
	private HPagerAdapter hAdapter;
	
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
	
	
	private View leftPageView;
	private View rightPageView;
	private View middlePageView;
	private View upPageView;
	private View downPageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);	
		
		mInflater = getLayoutInflater();
		
		getScreenSize();
		
		initHorizontalViewPager();
		initVerticalViewPager();
		
		initLeftPageView();
		initDownPageView();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		System.out.println("resume");
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
		setFlyFromReturnDate();
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
		
		setFlyFromToCity();
		
		leftpageSwitchCityImageView = (ImageView)leftPageView.findViewById(R.id.leftpage_swithcity_imageView);
		leftpageSwitchCityImageView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				City tempCity = GlobalVariables.Fly_From_City;
				GlobalVariables.Fly_From_City = GlobalVariables.Fly_To_City;
				GlobalVariables.Fly_To_City = tempCity;
				setFlyFromToCity();
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
		
		downpageCommonInfoLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CommonInfoActivity.class);
				startActivityForResult(intent, IntentRequestCode.COMMON_INFO.getRequestCode());				
			}
		});
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
		}else{
			GlobalVariables.Fly_From_City = new City("上海","SHA",false,"中国");
		}
		if(GlobalVariables.Fly_To_City!=null){
			leftpageFlyToTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_To_City.getCityName()));
			leftpageFlyToCodeTextView.setText(Util.getFourCharofString(GlobalVariables.Fly_To_City.getCityCode()));
		}else{
			GlobalVariables.Fly_To_City = new City("北京","PEK",false,"中国");
		}
	}
	
	OnClickListener openCitySelectOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			int viewId = arg0.getId();
			boolean isFlyToCitySelection = false;
			if(viewId==R.id.leftpage_fly_to_linearlayout)
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
//			GlobalVariables.isFlyToCitySelection = isFlyToCitySelection;
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
		middlePageView = mInflater.inflate(R.layout.layout_middle_withwidget, null);
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initVerticalViewPager(){
		vList = new ArrayList<View>();	
		upPageView = mInflater.inflate(R.layout.layout_up, null);
		downPageView = mInflater.inflate(R.layout.layout_down, null);
		vList.add(upPageView);
		vList.add(hpager);
		vList.add(downPageView);
		
		vAdapter = new VPagerAdapter(vList);		
		vpager = (VerticalViewPager)findViewById(R.id.vpager);
		vpager.setAdapter(vAdapter);	
		vpager.setCurrentItem(1);
		
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

}
