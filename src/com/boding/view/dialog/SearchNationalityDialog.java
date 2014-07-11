package com.boding.view.dialog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.model.Country;
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

public class SearchNationalityDialog extends Dialog{
	private EditText citySearchEditText;
	private LinearLayout cancelCitySearchLinearLayout;
	private LinearLayout searchCityListLinearLayout;
	private ListView searchResultListView;
	private Context context;
	
	private List<Country> countryList;
	
	public SearchNationalityDialog(Context context, List<Country> countryList){
		super(context,Constants.DIALOG_STYLE);
		setContentView(R.layout.dialog_search_city);
		setWidthHeight();
		this.context = context;
		this.countryList = countryList;
		initView();
	}
	
	private void setWidthHeight(){
		//set dialog width
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = GlobalVariables.Screen_Width; //���ÿ���
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
				SearchNationalityDialog.this.dismiss();
			}
		});
		
		searchCityListLinearLayout = (LinearLayout)findViewById(R.id.search_city_list_linearLayout);
		
		searchResultListView = (ListView)findViewById(R.id.city_search_result_listView);
		searchResultListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Util.selectCityOperation(searchResultListView, position, isFlyToCitySelection, SearchNationalityDialog.this.context, SearchNationalityDialog.this);
			}
			
		});
		
		hideListView();
	}
	
	public void setContent(String content){
		citySearchEditText.setText(content);
	}
	
	/**
	 * �������¼�
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
				SearchCountryResultAdapter adapter = 
						new SearchCountryResultAdapter(SearchNationalityDialog.this.getContext(), searchCity(searchText));
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
	
	private class SearchCountryResultAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<Country> contentList;
    	
    	public SearchCountryResultAdapter(Context context, List<Country> cityList) {
    		this.inflater = LayoutInflater.from(context);
    		this.contentList = cityList;
    	}
    	
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public Country getItem(int position) {
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
			Country country = contentList.get(position);  
            holder.name.setText(country.getCountryName());
            return convertView;  
		}
		
		private class ViewHolder {
            TextView name;  
		}
    	
    }
	
	private List<Country> searchCity(String searchText){
		searchText = searchText.toLowerCase();
		List<Country> searchResult = new ArrayList<Country>();
		for(Country country : countryList){
			if(country.getCountryName().contains(searchText)||country.getCountryPinyin().contains(searchText)){
				searchResult.add(country);
			}
		}
		return searchResult;
	}
	
}
