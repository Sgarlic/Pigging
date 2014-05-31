package com.boding.app;

import java.util.ArrayList;
import java.util.List;
import com.boding.adapter.HPagerAdapter;
import com.boding.adapter.VPagerAdapter;
import com.boding.view.VerticalViewPager;
import com.boding.R;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends FragmentActivity {
	private List<View> hList;
	private List<View> vList;
	private ViewPager hpager;
	private VerticalViewPager vpager;
	private LayoutInflater mInflater;
	private VPagerAdapter vAdapter;
	private HPagerAdapter hAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);	
		
		mInflater = getLayoutInflater();
		
		initHorizontalViewPager();
		initVerticalViewPager();
	}
	
	private void initHorizontalViewPager(){
		hList = new ArrayList<View>();
		hList.add(mInflater.inflate(R.layout.layout_left, null));
		hList.add(mInflater.inflate(R.layout.layout_middle, null));
		hList.add(mInflater.inflate(R.layout.layout_right, null));
		
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
		vList.add(mInflater.inflate(R.layout.layout_up, null));
		vList.add(hpager);
		vList.add(mInflater.inflate(R.layout.layout_down, null));
		
		vAdapter = new VPagerAdapter(vList);		
		vpager = (VerticalViewPager)findViewById(R.id.vpager);
		vpager.setAdapter(vAdapter);	
		vpager.setCurrentItem(1);
		
	}
}
