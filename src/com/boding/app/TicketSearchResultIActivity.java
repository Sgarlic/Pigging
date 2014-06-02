package com.boding.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.R.id;
import com.boding.R.layout;
import com.boding.R.menu;
import com.boding.constants.CityProperty;
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
import android.widget.TextView;
import android.os.Build;

public class TicketSearchResultIActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_search_result_i);
	}

}
