/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.boding.app;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.constants.CityProperty;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;
import com.boding.view.dialog.SearchCityDialog;
import com.boding.view.dialog.SearchNationalityDialog;
import com.boding.view.listview.LetterSelectListView;
public class NationalitySelectActivity extends FragmentActivity {
	private ListView nationalityListView;
	private LetterSelectListView letterListView;
	private NationalityListAdapter nationalityAdapter;
	private HashMap<String, Integer> alphaIndexer;//存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;//存放存在的汉语拼音首字母

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle arguments = getIntent().getExtras();
         
        setContentView(R.layout.activity_nationality_select);
       
        initView();
    }

    private void initView(){
        LinearLayout returnLogoLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
        returnLogoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(NationalitySelectActivity.this, IntentRequestCode.START_CITY_SELECTION);
			}
        });
        
        LinearLayout nationalitySearchLinearLayout = (LinearLayout)findViewById(R.id.nationality_search_linearLayout);
        nationalitySearchLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SearchNationalityDialog searchNationalityDialog = new SearchNationalityDialog(NationalitySelectActivity.this,R.style.Custom_Dialog_Theme);
				searchNationalityDialog.show();
			}
        });
        
        nationalityListView = (ListView) findViewById(R.id.nationality_select_listView);
        letterListView = (LetterSelectListView) findViewById(R.id.nationality_select_letter_listView);
        
        List<String> hotNationalityList = new ArrayList<String>();
        hotNationalityList.add("中国大陆");
        hotNationalityList.add("中国香港");
        hotNationalityList.add("中国澳门");
        hotNationalityList.add("中国台湾");
        hotNationalityList.add("美国");
        hotNationalityList.add("英国");
        hotNationalityList.add("日本");
        List<String> nationalityList = new ArrayList<String>();
        nationalityList.add("中国大陆");
        nationalityList.add("中国香港");
        nationalityList.add("中国澳门");
        nationalityList.add("中国台湾");
        nationalityList.add("美国");
        nationalityList.add("英国");
        nationalityList.add("日本");
        nationalityList.add("加拿大");
        nationalityList.add("法国");
        nationalityList.add("韩国");
        nationalityList.add("德国");
        nationalityList.add("巴西");
        nationalityList.add("西班牙");
        nationalityList.add("葡萄牙");
        
        nationalityAdapter = new NationalityListAdapter(this,hotNationalityList,nationalityList);
        nationalityListView.setAdapter(nationalityAdapter);
    }
    
    private class NationalityListAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<String> nationalityList;
    	
    	public NationalityListAdapter(Context context, List<String> hotNationalityList, List<String> nationalityList) {
    		Collections.sort(nationalityList, new SortByAlpha());
    		this.inflater = LayoutInflater.from(context);
    		this.nationalityList = new ArrayList<String>();
    		alphaIndexer = new HashMap<String, Integer>();
    		int sectionSize = 0;
    		
    		nationalityList.addAll(hotNationalityList);
    		nationalityList.addAll(nationalityList);
//    		GlobalVariables.allCitiesList.addAll(contentList);
    		sectionSize+=(hotNationalityList.size()+nationalityList.size());
    		sections = new String[sectionSize];
    		
    		int sectionPointer = 0;
    		
    		for(String hotNationality : hotNationalityList){
    			sections[sectionPointer] = Constants.HOTNATIONALITY;
    			if(!alphaIndexer.containsKey(Constants.HOTNATIONALITY))
    				alphaIndexer.put(Constants.HOTNATIONALITY, sectionPointer);
    			sectionPointer++;
    		}
    		
    		for (int i = 0; i < nationalityList.size(); i++,sectionPointer++) {
    			//当前汉语拼音首字母
    			String currentStr = Util.getAlpha(nationalityList.get(i));
    			//上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? Util.getAlpha(nationalityList.get(i - 1)) : " ";
                if (!previewStr.equals(currentStr)) {
                	String name = Util.getAlpha(nationalityList.get(i));
                	alphaIndexer.put(name, sectionPointer);  
                	sections[sectionPointer] = name; 
                }
            }
    	}
    	
		@Override
		public int getCount() {
			return nationalityList.size();
		}

		@Override
		public String getItem(int position) {
			return nationalityList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {  
                convertView = inflater.inflate(R.layout.list_item_city, null);
                holder = new ViewHolder();  
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);  
                holder.name = (TextView) convertView.findViewById(R.id.name);  
//                holder.number = (TextView) convertView.findViewById(R.id.number);  
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag();  
            }  
            String nationality = nationalityList.get(position);
            Log.d("poding",nationality);
            holder.name.setText(nationality);
//            holder.number.setText(cv.getAsString(NUMBER));
//            String currentStr = getAlpha(list.get(position).getAsString(SORT_KEY));
//            String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getAsString(SORT_KEY)) : " ";
            String currentStr = Util.getAlpha(nationality);
            String previewStr = (position - 1) >= 0 ? Util.getAlpha(nationalityList.get(position-1)) : " ";
            if (!previewStr.equals(currentStr)) {  
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(currentStr);
            } else {  
                holder.alpha.setVisibility(View.GONE);
            }  
            return convertView;  
		}
		
		private class ViewHolder {
			TextView alpha;  
            TextView name;  
//            TextView number;
		}
    	
    }
    
    class SortByAlpha implements Comparator<String>{
		@Override
		public int compare(String value0, String value1) {
    		return value0.compareTo(value1);
		}
    }
}
