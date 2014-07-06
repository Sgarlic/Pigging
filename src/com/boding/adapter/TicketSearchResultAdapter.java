package com.boding.adapter;

import java.util.Collections;
import java.util.List;

import com.boding.adapter.TicketSearchResultListIAdapter.FlightLineFilter;
import com.boding.model.FlightLine;
import com.boding.model.domestic.Flight;

import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;

public abstract class TicketSearchResultAdapter extends BaseExpandableListAdapter{
	public abstract boolean isGgroupExpandable(int groupPosition);
	public abstract Filter getFilter();
	public abstract void orderLinesByLeatime(boolean isAsc);
	public abstract void orderLinesByPrice(boolean isAsc);
	
}
