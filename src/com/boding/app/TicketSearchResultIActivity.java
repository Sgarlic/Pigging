package com.boding.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.R.id;
import com.boding.R.layout;
import com.boding.R.menu;
import com.boding.adapter.TicketSearchResultListIAdapter;
import com.boding.constants.CityProperty;
import com.boding.model.AirlineView;
import com.boding.model.FlightLine;
import com.boding.task.XMLTask;
import com.boding.util.Util;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class TicketSearchResultIActivity extends Activity {
	private TicketSearchResultListIAdapter adapter;
	private ListView searchResultListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_search_result_i);
		
		searchResultListView = (ListView)findViewById(R.id.international_ticket_search_result_listView);
	
		XMLTask xmltask = new XMLTask(this);
		String urlstr = "http://192.168.0.22:10381/FakeBodingServer/XMLServlet";
		xmltask.execute(urlstr);
	}
	

	public void setAdapter(AirlineView airlineView) {
    	adapter = new TicketSearchResultListIAdapter(this,airlineView);
    	searchResultListView.setAdapter(adapter);
    }

}
