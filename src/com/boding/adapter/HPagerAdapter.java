package com.boding.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

public class HPagerAdapter extends android.support.v4.view.PagerAdapter{
	private List container;
	public HPagerAdapter(List list){
		container = list;
	}

	@Override
	public int getCount() {
		return container.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (arg1);
	}
	
	@Override
	public Object instantiateItem(ViewGroup collection, int position) {

		
		(collection).addView((View)(container.get(position)));
		
		return container.get(position);
	}
	
	@Override
	public void destroyItem(ViewGroup collection, int position, Object view) {
		(collection).removeView((View)(container.get(position)));
	}

}
