package com.boding.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.boding.adapter.HPagerAdapter;
import com.boding.adapter.VPagerAdapter;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;
import com.boding.view.VerticalViewPager;
import com.boding.R;
import com.boding.model.City;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private List<View> hList;
	private List<View> vList;
	private ViewPager hpager;
	private VerticalViewPager vpager;
	private LayoutInflater mInflater;
	private VPagerAdapter vAdapter;
	private HPagerAdapter hAdapter;
	
	private TextView flyFromDateTextView;
	private TextView leftpageFlyFromTextView;
	private TextView leftPageFlyFromCodeTextView;
	private TextView leftpageFlyToTextView;
	private TextView leftpageFlyToCodeTextView;
	private ImageView switchCityImageView;
	
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
		initLeftPageView();
	}
	
	private void initLeftPageView(){
		LinearLayout leftPageSinglewayDateLinearLayout = (LinearLayout)leftPageView.findViewById(R.id.leftpage_singleway_date_linearlayout);
		leftPageSinglewayDateLinearLayout.setClickable(true);
		leftPageSinglewayDateLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DateSelectActivity.class);
				startActivityForResult(intent,IntentRequestCode.START_DATE_SELECTION.getRequestCode());
			}
		});
		
		flyFromDateTextView = (TextView)leftPageView.findViewById(R.id.fly_from_date_textView);
		setFlyFromDate();
		
		leftpageFlyFromTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_from_textView);
		leftpageFlyFromTextView.setOnClickListener(openCitySelectOnClickListener);
		
		leftPageFlyFromCodeTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_from_code_textView);
		leftPageFlyFromCodeTextView.setOnClickListener(openCitySelectOnClickListener);
		
		leftpageFlyToTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_to_textView);
		leftpageFlyToTextView.setOnClickListener(openCitySelectOnClickListener);
		
		leftpageFlyToCodeTextView = (TextView)leftPageView.findViewById(R.id.leftpage_fly_to_code_textView);
		leftpageFlyToCodeTextView.setOnClickListener(openCitySelectOnClickListener);
		
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
	}
	
	private void setFlyFromDate(){
		if(GlobalVariables.Fly_From_Date!=null){
			flyFromDateTextView.setText(GlobalVariables.Fly_From_Date);
		}
		else{
			Calendar calendar = Calendar.getInstance();
			String flyFromDate = Util.getFormatedDate(calendar);
			flyFromDateTextView.setText(flyFromDate);
			GlobalVariables.Fly_From_Date = flyFromDate;
		}
	}
	
	private void setFlyFromToCity(){
		if(GlobalVariables.Fly_From_City!=null){
			leftpageFlyFromTextView.setText(GlobalVariables.Fly_From_City.getCityName());
			leftPageFlyFromCodeTextView.setText(GlobalVariables.Fly_From_City.getCityCode());
		}else{
			GlobalVariables.Fly_From_City = new City("上海","SHA",false,"中国");
		}
		if(GlobalVariables.Fly_To_City!=null){
			leftpageFlyToTextView.setText(GlobalVariables.Fly_To_City.getCityName());
			leftpageFlyToCodeTextView.setText(GlobalVariables.Fly_To_City.getCityCode());
		}else{
			GlobalVariables.Fly_To_City = new City("北京","PEK",false,"中国");
		}
	}
	
	OnClickListener openCitySelectOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			int viewId = arg0.getId();
			boolean isFlyToCitySelection = false;
			if(viewId==R.id.leftpage_fly_to_code_textView || viewId==R.id.leftpage_fly_to_textView)
				isFlyToCitySelection = true;
			
			Bundle bundle  = new Bundle();
			bundle.putBoolean(Constants.IS_FLY_TO_CITY_SELECTION, isFlyToCitySelection);
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, CitySelectActivity.class);
//			GlobalVariables.isFlyToCitySelection = isFlyToCitySelection;
			intent.putExtras(bundle);
			startActivityForResult(intent,IntentRequestCode.START_CITY_SELECTION.getRequestCode());
		}
		
	};
	
	@SuppressLint("NewApi")
	private void getScreenSize(){
		Display display = getWindowManager().getDefaultDisplay();
		Point screenSize = new Point();
		display.getSize(screenSize);
		Constants.ScreenHeight = screenSize.y;
		Constants.ScreenWidth = screenSize.x;
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
		  setFlyFromDate();
	  }
	  if(requestCode == IntentRequestCode.START_CITY_SELECTION.getRequestCode())
		  setFlyFromToCity();
	 }
}
