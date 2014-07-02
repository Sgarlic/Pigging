package com.boding.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

public class VPagerAdapter extends VerticalPagerAdapter{
	private List container;
	public VPagerAdapter(List list){
		container = list;
	}

	@Override
	public int getCount() {
		return container.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == (object);
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
