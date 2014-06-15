package com.boding.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.boding.adapter.HPagerAdapter;
import com.boding.adapter.VPagerAdapter;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.task.InitCityTask;
import com.boding.util.Util;
import com.boding.view.dialog.VerticalViewPager;
import com.boding.R;
import com.boding.model.City;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	
	/**
	 * Left page widgets
	 */
	private TextView leftpageFlyFromTextView;
	private TextView leftPageFlyFromCodeTextView;
	private TextView leftpageFlyToTextView;
	private TextView leftpageFlyToCodeTextView;
	private ImageView switchCityImageView;
	private LinearLayout leftpageFlyFromLinearLayout;
	private LinearLayout leftpageFlyToLinearLayout;
	private ImageView leftpageFlightWayChooseImageView;
	private LinearLayout leftPageSinglewayDateLinearLayout;
	private LinearLayout leftpageDateDividerLinearLayout;
	private LinearLayout leftpageReturnwayDateLinearLayout;
	private TextView leftpageFlyFromDateTextView;
	private TextView leftpageFlyToDateTextView;
	private ImageView leftpageVoiceSearchImageView;
	
	
	
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
		
		// May not need anymore
		getScreenSize();
		
		initHorizontalViewPager();
		initVerticalViewPager();
		
		initCityList();
		System.out.println("*********************");
		initLeftPageView();
		
		System.out.print(GlobalVariables.domesticCitiesList);
	}
	
	private void initCityList(){
		String testUrl = "http://api.iboding.com/API/Base/QueryAirportCity.ashx?userid=boding&sign=14AD779B4209D8DDC95BD2336D36C015";
		
		new InitCityTask().execute(testUrl);
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
//		leftpageFlyFromTextView.setOnClickListener(openCitySelectOnClickListener);
		
		leftPageFlyFromCodeTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_from_code_textView);
//		leftPageFlyFromCodeTextView.setOnClickListener(openCitySelectOnClickListener);
		
		leftpageFlyToTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_to_textView);
//		leftpageFlyToTextView.setOnClickListener(openCitySelectOnClickListener);
		
		leftpageFlyToCodeTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_to_code_textView);
//		leftpageFlyToCodeTextView.setOnClickListener(openCitySelectOnClickListener);
		
		leftpageFlyFromLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_fly_from_linearlayout);
		leftpageFlyToLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_fly_to_linearlayout);
		leftpageFlyFromLinearLayout.setOnClickListener(openCitySelectOnClickListener);
		leftpageFlyToLinearLayout.setOnClickListener(openCitySelectOnClickListener);
		
		
		ImageView leftpageFlightSearchTicketImageView = (ImageView)leftPageView.findViewById(R.id.leftpage_flight_search_ticket_imageView);
		leftpageFlightSearchTicketImageView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, TicketSearchResultIActivity.class);
				startActivityForResult(intent,IntentRequestCode.START_TICKET_SEARCH.getRequestCode());
			}
		});
		
		setFlyFromToCity();
		
		switchCityImageView = (ImageView)leftPageView.findViewById(R.id.leftpage_swithcity_imageView);
		switchCityImageView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				City tempCity = GlobalVariables.Fly_From_City;
				GlobalVariables.Fly_From_City = GlobalVariables.Fly_To_City;
				GlobalVariables.Fly_To_City = tempCity;
				setFlyFromToCity();
			}
		});
		
		leftpageVoiceSearchImageView = (ImageView)leftPageView.findViewById(R.id.leftpage_voice_search_imageView);
		leftpageVoiceSearchImageView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, VoiceSearchActivity.class);
				startActivityForResult(intent,IntentRequestCode.START_VOICE_SEARCH.getRequestCode());
			}
			
		});
	}
	
	private void switchToSingleWay(){
		leftpageFlightWayChooseImageView.setImageResource(R.drawable.leftpage_singleway_line_480_800);
		leftpageDateDividerLinearLayout.setVisibility(View.INVISIBLE);
		leftpageReturnwayDateLinearLayout.setVisibility(View.INVISIBLE);
		isSingleWay = true;
	}
	
	private void swithToReturnWay(){
		leftpageFlightWayChooseImageView.setImageResource(R.drawable.leftpage_returnway_line_480_800);
		leftpageDateDividerLinearLayout.setVisibility(View.VISIBLE);
		leftpageReturnwayDateLinearLayout.setVisibility(View.VISIBLE);
		isSingleWay = false;
	}
	
	private void setFlyFromReturnDate(){
		if(GlobalVariables.Fly_From_Date==null){
			Calendar calendar = Calendar.getInstance();
			String flyFromDate = Util.getFormatedDate(calendar);
			GlobalVariables.Fly_From_Date = flyFromDate;
		}
		leftpageFlyFromDateTextView.setText(GlobalVariables.Fly_From_Date);
		
		if(GlobalVariables.Fly_Return_Date==null || (Util.compareDateString(GlobalVariables.Fly_Return_Date, GlobalVariables.Fly_From_Date) == -1)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(Util.getDateFromString(GlobalVariables.Fly_From_Date));
			calendar.add(Calendar.HOUR, 7*24);
			String flyToDate = Util.getFormatedDate(calendar);
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
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, CitySelectActivity.class);
//			GlobalVariables.isFlyToCitySelection = isFlyToCitySelection;
			intent.putExtras(bundle);
			startActivityForResult(intent,IntentRequestCode.START_CITY_SELECTION.getRequestCode());
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
			startActivityForResult(intent,IntentRequestCode.START_DATE_SELECTION.getRequestCode());
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
	  if(requestCode==IntentRequestCode.START_DATE_SELECTION.getRequestCode()){
		  setFlyFromReturnDate();
	  }
	  if(requestCode == IntentRequestCode.START_CITY_SELECTION.getRequestCode())
		  setFlyFromToCity();
	 }
}
