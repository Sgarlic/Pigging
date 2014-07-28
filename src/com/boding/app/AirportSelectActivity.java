package com.boding.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.model.Airport;
import com.boding.util.CityUtil;
import com.boding.util.SharedPreferenceUtil;
import com.boding.util.Util;
import com.boding.view.listview.LetterSelectListView;
import com.boding.view.listview.LetterSelectListView.OnTouchingLetterChangedListener;
public class AirportSelectActivity extends FragmentActivity {
	private AirportAdapter adapter;  
    private ListView allAirportsListView;
    private LetterSelectListView letterListView;
    private HashMap<String, Integer> alphaIndexer;//存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;//存放存在的汉语拼音首字母

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_airport_select);
       
        initView();
    }

    private void initView(){
        LinearLayout returnLogoLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
        returnLogoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(AirportSelectActivity.this, IntentRequestCode.AIRPORT_SELECT);
			}
        });
        
        allAirportsListView = (ListView) findViewById(R.id.airport_select_listView);
    	allAirportsListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//parent	The AdapterView where the selection happened
				//view	The view within the AdapterView that was clicked
				//position	The position of the view in the adapter
				//id	The row id of the item that is selected
				Airport selectedAirport = adapter.getItem(position);
				SharedPreferenceUtil.addDomesticHistoryAirport(selectedAirport.getAirportcode(),AirportSelectActivity.this);
				GlobalVariables.SELECTED_AIRPORT = selectedAirport;
				Util.returnToPreviousPage(AirportSelectActivity.this, IntentRequestCode.AIRPORT_SELECT);
			}
    		
    	});
        
        letterListView = (LetterSelectListView) findViewById(R.id.airport_select_letter_listView);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        
//        asyncQuery = new MyAsyncQueryHandler(getContentResolver());
        alphaIndexer = new HashMap<String, Integer>();
        
        setAdapter();
    }
    
    private void setAdapter() {
		List<Airport> historyAirport = SharedPreferenceUtil.getDomesticHistoryAirport(this);
		adapter = new AirportAdapter(this, historyAirport,
				GlobalVariables.domHotAirportsList, GlobalVariables.domAirportList);
        allAirportsListView.setAdapter(adapter);  
    }
    
    private class AirportAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<Airport> contentList;
    	
    	public AirportAdapter(Context context, List<Airport> historyCityList, List<Airport> hotCityList, List<Airport> cityList) {
    		if(cityList == null)
    			return;
    		this.inflater = LayoutInflater.from(context);
    		this.contentList = new ArrayList<Airport>();
    		alphaIndexer = new HashMap<String, Integer>();
    		int sectionSize = 0;
    		if(GlobalVariables.CurrentCity!=null){
    			sectionSize+=1;
    			if(GlobalVariables.currentAirport == null){
    				GlobalVariables.currentAirport = new Airport();
    				String airportCode = GlobalVariables.CurrentCity.getAirportcode();
    				
    				GlobalVariables.currentAirport.setAirportcode(airportCode);
    				GlobalVariables.currentAirport.setAirportname(CityUtil.getAirportNameByCode(airportCode));;
    			}
    			contentList.add(GlobalVariables.currentAirport);
    		}
    		
    		if(historyCityList.size()!= 0 && historyCityList != null){
    			contentList.addAll(historyCityList);
    			sectionSize+=historyCityList.size();
    		}
    		if(hotCityList.size() != 0 && hotCityList != null){
    			contentList.addAll(hotCityList);
    			sectionSize += hotCityList.size();
    		}
    		contentList.addAll(cityList);
    		
    		sectionSize += contentList.size();
    		sections = new String[sectionSize];
    		
    		int sectionPointer = 0;
    		
    		if(GlobalVariables.CurrentCity!=null){
    			sections[sectionPointer] = Constants.LOCATION;
    			alphaIndexer.put(Constants.LOCATION, sectionPointer);
    			sectionPointer++;
    		}
    		
    		if(historyCityList.size()!= 0 && historyCityList != null){
	    		alphaIndexer.put(Constants.HISTORY, sectionPointer);
	    		sections[sectionPointer] = Constants.HISTORY;
	    		sectionPointer += historyCityList.size();
    		}
    		
    		if(hotCityList.size() != 0 && hotCityList != null){
	    		alphaIndexer.put(Constants.HOT, sectionPointer);
	    		sections[sectionPointer] = Constants.HOT;
	    		sectionPointer += hotCityList.size();
    		}
    		for (int i = 0; i < cityList.size(); i++,sectionPointer++) {
    			//当前汉语拼音首字母
    			String currentStr = Util.getAlpha(cityList.get(i).getAirportPinyin());
    			//上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? Util.getAlpha(cityList.get(i - 1).getAirportPinyin()) : " ";
                if (!previewStr.equals(currentStr)) {
                	alphaIndexer.put(currentStr, sectionPointer);  
                	sections[sectionPointer] = currentStr; 
                }
            }
    	}
    	
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public Airport getItem(int position) {
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
                convertView = inflater.inflate(R.layout.list_item_city, null);
                holder = new ViewHolder();  
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);  
                holder.name = (TextView) convertView.findViewById(R.id.name);  
                convertView.setTag(holder);  
            } else {  
                holder = (ViewHolder) convertView.getTag();  
            }  
            Airport cv = contentList.get(position);  
            holder.name.setText(cv.getAirportname());
            String currentStr = sections[position];
            if (currentStr!=null) {  
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
    
    private class LetterListViewListener implements OnTouchingLetterChangedListener{

		@Override
		public void onTouchingLetterChanged(final String s) {
			if(alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				allAirportsListView.setSelection(position);
			} 
		}
    	
    }
}
