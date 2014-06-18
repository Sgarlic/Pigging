package com.boding.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Country;
import com.boding.util.Util;
import com.boding.view.dialog.SearchNationalityDialog;
import com.boding.view.listview.LetterSelectListView;
import com.boding.view.listview.LetterSelectListView.OnTouchingLetterChangedListener;
public class NationalitySelectActivity extends FragmentActivity {
	private ListView nationalityListView;
	private LetterSelectListView letterListView;
	private CountryListAdapter countryAdapter;
	private HashMap<String, Integer> alphaIndexer;//存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;//存放存在的汉语拼音首字母
	private List<Country> allCountryList;

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
        
        allCountryList = new ArrayList<Country>();
        allCountryList.add(new Country("中国大陆"));
        allCountryList.add(new Country("中国香港"));
        allCountryList.add(new Country("中国澳门"));
        allCountryList.add(new Country("中国台湾"));
        allCountryList.add(new Country("美国"));
        allCountryList.add(new Country("英国"));
        allCountryList.add(new Country("日本"));
        allCountryList.add(new Country("加拿大"));
        allCountryList.add(new Country("法国"));
        allCountryList.add(new Country("韩国"));
        allCountryList.add(new Country("德国"));
        allCountryList.add(new Country("巴西"));
        allCountryList.add(new Country("西班牙"));
        allCountryList.add(new Country("葡萄牙"));
        LinearLayout nationalitySearchLinearLayout = (LinearLayout)findViewById(R.id.nationality_search_linearLayout);
        nationalitySearchLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SearchNationalityDialog searchNationalityDialog = new SearchNationalityDialog(NationalitySelectActivity.this,
						R.style.Custom_Dialog_Theme,allCountryList);
				searchNationalityDialog.show();
			}
        });
        
        nationalityListView = (ListView) findViewById(R.id.nationality_select_listView);
        letterListView = (LetterSelectListView) findViewById(R.id.nationality_select_letter_listView);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        
        List<Country> hotCountryList = new ArrayList<Country>();
        hotCountryList.add(new Country("中国大陆"));
        hotCountryList.add(new Country("中国香港"));
        hotCountryList.add(new Country("中国澳门"));
        hotCountryList.add(new Country("中国台湾"));
        hotCountryList.add(new Country("美国"));
        hotCountryList.add(new Country("英国"));
        hotCountryList.add(new Country("日本"));
        hotCountryList.add(new Country(""));
        hotCountryList.add(new Country(""));
        countryAdapter = new CountryListAdapter(this,hotCountryList,allCountryList);
        nationalityListView.setAdapter(countryAdapter);
    }
    
    private class CountryListAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<Country> countryList;
    	
    	public CountryListAdapter(Context context, List<Country> hotCountryList, List<Country> allCountryList) {
    		Collections.sort(allCountryList, new SortByAlpha());
    		this.inflater = LayoutInflater.from(context);
    		this.countryList = new ArrayList<Country>();
    		alphaIndexer = new HashMap<String, Integer>();
    		int sectionSize = 0;
    		
    		countryList.addAll(hotCountryList);
    		countryList.addAll(allCountryList);
//    		GlobalVariables.allCitiesList.addAll(contentList);
    		sectionSize+=(hotCountryList.size()+allCountryList.size());
    		sections = new String[sectionSize];
    		
    		int sectionPointer = 0;
    		
    		
    		alphaIndexer.put(Constants.HOT, sectionPointer);
    		sections[sectionPointer] = Constants.HOT;
    		sectionPointer+=hotCountryList.size();
    		
    		for (int i = 0; i < allCountryList.size(); i++,sectionPointer++) {
    			//当前汉语拼音首字母
    			String currentStr = Util.getAlpha(allCountryList.get(i).getCountryPinyin());
    			//上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? Util.getAlpha(allCountryList.get(i - 1).getCountryPinyin()) : " ";
                if (!previewStr.equals(currentStr)) {
                	alphaIndexer.put(currentStr, sectionPointer);  
                	sections[sectionPointer] = currentStr;
                }
            }
    	}
    	
		@Override
		public int getCount() {
			return countryList.size();
		}

		@Override
		public Country getItem(int position) {
			return countryList.get(position);
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
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag();  
            }  
			Country nationality = countryList.get(position);
            holder.name.setText(nationality.getCountryName());
            String currentStr = sections[position];
            if(currentStr!=null){
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(currentStr);
            }else{
            	holder.alpha.setVisibility(View.GONE);
            }
            return convertView;  
		}
		
		private class ViewHolder {
			TextView alpha;  
            TextView name;  
		}
    	
    }
    
    class SortByAlpha implements Comparator<Country>{
		@Override
		public int compare(Country value0, Country value1) {
    		return value0.getCountryPinyin().compareTo(value1.getCountryPinyin());
		}
    }
    
    private class LetterListViewListener implements OnTouchingLetterChangedListener{

		@Override
		public void onTouchingLetterChanged(final String s) {
			if(alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				nationalityListView.setSelection(position);
//				overlay.setText(sections[position]);
//				overlay.setVisibility(View.VISIBLE);
//				handler.removeCallbacks(overlayThread);
				//延迟一秒后执行，让overlay为不可见
//				handler.postDelayed(overlayThread, 1500);
			} 
		}
    	
    }
}
