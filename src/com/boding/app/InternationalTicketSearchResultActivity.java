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

public class InternationalTicketSearchResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_international_ticket_search_result);
	}

	private class CityListAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<ContentValues> list;
    	
    	public CityListAdapter(Context context, List<ContentValues> historyCityList, List<ContentValues> hotCityList, List<ContentValues> cityList) {
    		this.inflater = LayoutInflater.from(context);
    		this.list = new ArrayList<ContentValues>();
    		int sectionSize = 0;
    		
    		list.addAll(historyCityList);
    		list.addAll(hotCityList);
    		list.addAll(cityList);
    		
    		sectionSize+=(historyCityList.size()+hotCityList.size()+list.size());
    		
    		int sectionPointer = 0;
    	}
    	
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public ContentValues getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {  
                convertView = inflater.inflate(R.layout.city_list_item, null);
                holder = new ViewHolder();  
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);  
                holder.name = (TextView) convertView.findViewById(R.id.name);  
//                holder.number = (TextView) convertView.findViewById(R.id.number);  
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag();  
            }  
            ContentValues cv = list.get(position);  
//            holder.name.setText(cv.getAsString(CITY_NAME));
//            String currentStr = getContentValuesTitle(cv);
//            String previewStr = (position - 1) >= 0 ? getContentValuesTitle(list.get(position-1)) : " ";
//            if (!previewStr.equals(currentStr)) {  
//                holder.alpha.setVisibility(View.VISIBLE);
//                holder.alpha.setText(currentStr);
//            } else {  
//                holder.alpha.setVisibility(View.GONE);
//            }  
            return convertView;  
		}
		
		private class ViewHolder {
			TextView alpha;  
            TextView name;  
		}
    	
    }

}
