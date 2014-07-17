package com.boding.view.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.model.City;
import com.boding.util.Util;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class SearchCityDialog extends Dialog{
	private EditText citySearchEditText;
	private LinearLayout cancelCitySearchLinearLayout;
	private LinearLayout searchCityListLinearLayout;
	private ListView searchResultListView;
	private boolean isFlyToCitySelection;
	private Context context;
	public SearchCityDialog(Context context, boolean isFlyToCitySelection){
		super(context,Constants.DIALOG_STYLE);
		setContentView(R.layout.dialog_search_city);
		setWidthHeight();
		this.isFlyToCitySelection = isFlyToCitySelection;
		this.context = context;
		initView();
	}
	
	private void setWidthHeight(){
		//set dialog width
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = GlobalVariables.Screen_Width; //设置宽度
		lp.height = GlobalVariables.Screen_Height;
		
		// set dialog location
		this.getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
		lp.x = 0;
		lp.y = 0;
		
		this.getWindow().setAttributes(lp);
	}
	
	private void initView(){
		citySearchEditText = (EditText)findViewById(R.id.city_search_editText);
		citySearchEditText.addTextChangedListener(citySearchTextWatcher);
		
		cancelCitySearchLinearLayout = (LinearLayout)findViewById(R.id.cancel_citysearch_linearLayout);
		cancelCitySearchLinearLayout.setClickable(true);
		cancelCitySearchLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SearchCityDialog.this.dismiss();
			}
		});
		
		searchCityListLinearLayout = (LinearLayout)findViewById(R.id.search_city_list_linearLayout);
		
		searchResultListView = (ListView)findViewById(R.id.city_search_result_listView);
		searchResultListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Util.selectCityOperation(searchResultListView, position, isFlyToCitySelection, SearchCityDialog.this.context, SearchCityDialog.this);
			}
			
		});
		
		hideListView();
	}
	
	public void setContent(String content){
		citySearchEditText.setText(content);
	}
	
	/**
	 * 搜索框事件
	 */
	private TextWatcher citySearchTextWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			String searchText = s.toString();
//			Log.d("poding",searchText);
			if(searchText.equals("")){
				hideListView();
			}else{
				showListView();
				SearchResultCityListAdapter adapter = new SearchResultCityListAdapter(SearchCityDialog.this.getContext(), searchCity(searchText));
				searchResultListView.setAdapter(adapter);
			}
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	};
    
	private void hideListView(){
		searchCityListLinearLayout.setVisibility(View.INVISIBLE);
	}
	private void showListView(){
		searchCityListLinearLayout.setVisibility(View.VISIBLE);
	}
	
	private class SearchResultCityListAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<City> contentList;
    	
    	public SearchResultCityListAdapter(Context context, List<City> cityList) {
    		this.inflater = LayoutInflater.from(context);
    		this.contentList = cityList;
    	}
    	
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public City getItem(int position) {
			return contentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {  
                convertView = inflater.inflate(R.layout.list_item_citysearch_city, null);
                holder = new ViewHolder();  
                holder.name = (TextView) convertView.findViewById(R.id.name);  
//                holder.number = (TextView) convertView.findViewById(R.id.number);  
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag(); 
                
            }  
            City cv = contentList.get(position);  
            holder.name.setText(cv.getCityName());
//            holder.number.setText(cv.getAsString(NUMBER));
//            String currentStr = getAlpha(list.get(position).getAsString(SORT_KEY));
//            String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getAsString(SORT_KEY)) : " ";
            return convertView;  
		}
		
		private class ViewHolder {
            TextView name;  
//            TextView number;
		}
    	
    }
	
	private List<City> searchCity(String searchText){
		searchText = searchText.toLowerCase();
		List<City> searchResult = new ArrayList<City>();
		for(City city : GlobalVariables.allCitiesList){
			if(city.getCityName().toLowerCase().contains(searchText) 
					|| city.getPinyin().toLowerCase().contains(searchText) 
					|| city.getCityCode().toLowerCase().contains(searchText)
					|| city.getInitial().toLowerCase().contains(searchText)){
				searchResult.add(city);
				continue;
			}
		}
		
		return searchResult;
	}
}
