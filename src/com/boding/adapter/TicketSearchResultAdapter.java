package com.boding.adapter;

import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;

public abstract class TicketSearchResultAdapter extends BaseExpandableListAdapter{
	public abstract boolean isGgroupExpandable(int groupPosition);
	public abstract Filter getFilter();
}
