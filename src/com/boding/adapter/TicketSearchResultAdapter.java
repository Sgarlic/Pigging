package com.boding.adapter;

import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;

public abstract class TicketSearchResultAdapter extends BaseExpandableListAdapter{
	protected OnColExpClickListener onColExpClickListener;
	public abstract boolean isGgroupExpandable(int groupPosition);
	public abstract Filter getFilter();
	public abstract void orderLinesByLeatime(boolean isAsc);
	public abstract void orderLinesByPrice(boolean isAsc);

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {	
	    return true;
	}
	
	//监听接口
	public interface OnColExpClickListener {
		void ColExp(int position);
	}
	
	//给控件设置监听事件
	public void setOnColExpClickListener(OnColExpClickListener onColExpClickListener){
		this.onColExpClickListener =  onColExpClickListener;
	}
}
