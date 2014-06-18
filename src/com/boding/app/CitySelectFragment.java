package com.boding.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.boding.R;
import com.boding.app.NationalitySelectActivity.SortByAlpha;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.model.City;
import com.boding.util.Util;
import com.boding.view.listview.LetterSelectListView;
import com.boding.view.listview.LetterSelectListView.OnTouchingLetterChangedListener;

public class CitySelectFragment extends Fragment {
	private boolean isInternational = false;
	private boolean isFlyToCitySelection = false;
	private BaseAdapter adapter;  
    private ListView allCitiesListView;
    private LetterSelectListView letterListView;
    private HashMap<String, Integer> alphaIndexer;//存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;//存放存在的汉语拼音首字母
//    private Handler handler;
//    private OverlayThread overlayThread;
    private String locatedCity = "上海";
    private View currentView;
    int mNum;

//    /**
//     * Create a new instance of CountingFragment, providing "num"
//     * as an argument.
//     */
//    static CountingFragment newInstance(int num) {
//    	Log.d("poding",String.valueOf(num));
//    	Log.d("poding","newinstance");
//        CountingFragment f = new CountingFragment();
//
//        // Supply num input as an argument.
//        Bundle args = new Bundle();
//        args.putInt("num", num);
//        f.setArguments(args);
//
//        return f;
//    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments!=null){
        	isInternational = arguments.getBoolean(Constants.IS_INTERNATIONAL_CITY);
        	isFlyToCitySelection = arguments.getBoolean(Constants.IS_FLY_TO_CITY_SELECTION);
        	
        }
//        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	currentView = inflater.inflate(R.layout.fragment_city_select_list, container, false);
        initView();
//        View tv = v.findViewById(R.id.text);
//        ((TextView)tv).setText("Fragment #" + mNum);
//        tv.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.gallery_thumb));
        return currentView;
    }
    
    private void initView(){
//    	setTitle();
    	allCitiesListView = (ListView) currentView.findViewById(R.id.city_select_listView);
    	allCitiesListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//parent	The AdapterView where the selection happened
				//view	The view within the AdapterView that was clicked
				//position	The position of the view in the adapter
				//id	The row id of the item that is selected
				Util.selectCityOperation(allCitiesListView, position, isFlyToCitySelection, currentView.getContext(),null);
			}
    		
    	});
    	
        letterListView = (LetterSelectListView) currentView.findViewById(R.id.city_select_letter_listView);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        
//        asyncQuery = new MyAsyncQueryHandler(getContentResolver());
        alphaIndexer = new HashMap<String, Integer>();
//        handler = new Handler();
//        overlayThread = new OverlayThread();
//        initOverlay();
//        
//        LinearLayout returnLogoLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
//        returnLogoLinearLayout.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View arg0) {
//				Util.returnToPreviousPage(FragmentStackSupport.this, IntentRequestCode.START_CITY_SELECTION);
//			}
//        });
        
        setAdapter();
    }  
    
    private void setAdapter() {
    	if(isInternational)
    		adapter = new CityListAdapter(currentView.getContext(), new ArrayList<City>(), 
    				GlobalVariables.interHotCitiesList, GlobalVariables.interCitiesList);
    	else
    		adapter = new CityListAdapter(currentView.getContext(), new ArrayList<City>(),
    				GlobalVariables.domHotCitiesList, GlobalVariables.domesticCitiesList);
        allCitiesListView.setAdapter(adapter);  
    }
    
    private class CityListAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<City> contentList;
    	
    	public CityListAdapter(Context context, List<City> historyCityList, List<City> hotCityList, List<City> cityList) {
    		if(cityList == null)
    			return;
    		//Collections.sort(cityList, new SortByAlpha());
    		this.inflater = LayoutInflater.from(context);
    		this.contentList = new ArrayList<City>();
    		alphaIndexer = new HashMap<String, Integer>();
    		int sectionSize = 0;
    		if(locatedCity!=null){
    			sectionSize+=1;
    			City c = new City();
    			c.setCityName("aa");
    			c.setCityCode("123");
    			c.setInternationalCity(false);
    			c.setBelongsToCountry("china");
    			c.setPinyin("shanghai");
    			contentList.add(c);
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
    		
    		//Collections.sort(contentList, new City.CityNameComp());
    		
    		GlobalVariables.allCitiesList.addAll(contentList); 
    		sectionSize += contentList.size();
    		sections = new String[sectionSize];
    		
    		int sectionPointer = 0;
    		
    		if(locatedCity!=null){
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
    			String currentStr = Util.getAlpha(cityList.get(i).getPinyin());
    			//上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? Util.getAlpha(cityList.get(i - 1).getPinyin()) : " ";
                if (!previewStr.equals(currentStr)) {
                	//String name = Util.getAlpha(cityList.get(i).getPinyinofName();
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
                convertView = inflater.inflate(R.layout.list_item_city, null);
                holder = new ViewHolder();  
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);  
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
    
//    //初始化汉语拼音首字母弹出提示框
//    private void initOverlay() {
//    	Log.d("poding","init overlay");
//    	LayoutInflater inflater = LayoutInflater.from(this);
//    	overlay = (TextView) inflater.inflate(R.layout.wraning_info_overlay, null);
//    	overlay.setVisibility(View.INVISIBLE);
//		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//				WindowManager.LayoutParams.TYPE_APPLICATION,
//				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//				PixelFormat.TRANSLUCENT);
//		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//		windowManager.addView(overlay, lp);
//    }
    
    private class LetterListViewListener implements OnTouchingLetterChangedListener{

		@Override
		public void onTouchingLetterChanged(final String s) {
			if(alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				allCitiesListView.setSelection(position);
//				overlay.setText(sections[position]);
//				overlay.setVisibility(View.VISIBLE);
//				handler.removeCallbacks(overlayThread);
				//延迟一秒后执行，让overlay为不可见
//				handler.postDelayed(overlayThread, 1500);
			} 
		}
    	
    }
//    
//    //设置overlay不可见
//    private class OverlayThread implements Runnable {
//
//		@Override
//		public void run() {
//			overlay.setVisibility(View.GONE);
//		}
//    	
//    }
//    
    class SortByAlpha implements Comparator<City>{
		@Override
		public int compare(City value0, City value1) {
    		return value0.getPinyin().compareTo(value1.getPinyin());
		}
    	
    }
}
