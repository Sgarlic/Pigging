package com.boding.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.boding.R;
import com.boding.constants.CityProperty;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.model.City;
import com.boding.util.Util;
import com.boding.view.CitySelectLetterListView;
import com.boding.view.WarningDialog;
import com.boding.view.CitySelectLetterListView.OnTouchingLetterChangedListener;

public class CitySelectFragment extends Fragment {
	private boolean isInternational = false;
	private boolean isFlyToCitySelection = false;
	private BaseAdapter adapter;  
    private ListView allCitiesListView;
    private CitySelectLetterListView letterListView;
    private static final String CITY_NAME = "name", CITY_CODE = "code", SORT_KEY = "sort_key";
    private static final String PROPERTY="property", LOCATION="定位",HISTORY="历史",HOT="热门";
    private HashMap<String, Integer> alphaIndexer;//存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;//存放存在的汉语拼音首字母
    private Handler handler;
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

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
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
				ContentValues content = (ContentValues) allCitiesListView.getAdapter().getItem(position);
				String cityName = content.getAsString(CITY_NAME);
				String cityCode = content.getAsString(CITY_CODE);
				boolean isInternationalCity = false;
				String cityCountry = "";
				
				City selectedCity = new City(cityName,cityCode,isInternationalCity,cityCountry);
				
				String toastInfo = null;
				if(isFlyToCitySelection){
					if(selectedCity.equals(GlobalVariables.Fly_From_City))
						toastInfo = "出发和到达不能为同一城市";
					else
						GlobalVariables.Fly_To_City = selectedCity;
				}
				else{
					if(selectedCity.equals(GlobalVariables.Fly_To_City))
						toastInfo = "出发和到达不能为同一城市";
					else
						GlobalVariables.Fly_From_City = selectedCity;
				}
				
				if(toastInfo!=null){
					WarningDialog warningDialog = new WarningDialog(currentView.getContext(),
							R.layout.wraning_info_dialog,R.style.Warning_Dialog_Theme);
					warningDialog.setContent(toastInfo);
					warningDialog.setKnown("知道了");
					warningDialog.show();
//					currentView.getContext().
				}else{
					Util.returnToPreviousPage((Activity)currentView.getContext(), IntentRequestCode.START_CITY_SELECTION);
				}
//				Log.d("poding");
			}
    		
    	});
    	
        letterListView = (CitySelectLetterListView) currentView.findViewById(R.id.city_select_letter_listView);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        
//        asyncQuery = new MyAsyncQueryHandler(getContentResolver());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
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
    		adapter = new CityListAdapter(currentView.getContext(), getHistoryCityListI(),getHotCityListI(),getCitiesListI());
    	else
    		adapter = new CityListAdapter(currentView.getContext(), getHistoryCityList(),getHotCityList(),getCitiesList());
        allCitiesListView.setAdapter(adapter);  
    }
    
    private class CityListAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<ContentValues> contentList;
    	
    	public CityListAdapter(Context context, List<ContentValues> historyCityList, List<ContentValues> hotCityList, List<ContentValues> cityList) {
    		this.inflater = LayoutInflater.from(context);
    		this.contentList = new ArrayList<ContentValues>();
    		alphaIndexer = new HashMap<String, Integer>();
    		int sectionSize = 0;
    		if(locatedCity!=null){
    			sectionSize+=1;
    			contentList.add(generateContentValues(locatedCity,CityProperty.LOCATECITY));
    		}
    		
    		contentList.addAll(historyCityList);
    		contentList.addAll(hotCityList);
    		contentList.addAll(cityList);
    		
    		sectionSize+=(historyCityList.size()+hotCityList.size()+contentList.size());
    		sections = new String[sectionSize];
    		
    		int sectionPointer = 0;
    		
    		if(locatedCity!=null){
    			sections[sectionPointer] = LOCATION;
    			alphaIndexer.put(LOCATION, sectionPointer);
    			sectionPointer++;
    		}
    		
    		for(ContentValues historyCity : historyCityList){
    			sections[sectionPointer] = HISTORY;
    			if(!alphaIndexer.containsKey(HISTORY))
    				alphaIndexer.put(HISTORY, sectionPointer);
    			sectionPointer++;
    		}
    		
    		for(ContentValues hotCity : hotCityList){
    			sections[sectionPointer] = HOT;
    			if(!alphaIndexer.containsKey(HOT))
    				alphaIndexer.put(HOT, sectionPointer);
    			sectionPointer++;
    		}
    		
    		for (int i = 0; i < cityList.size(); i++,sectionPointer++) {
    			//当前汉语拼音首字母
    			String currentStr = Util.getAlpha(cityList.get(i).getAsString(SORT_KEY));
    			//上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? Util.getAlpha(cityList.get(i - 1).getAsString(SORT_KEY)) : " ";
                if (!previewStr.equals(currentStr)) {
                	String name = Util.getAlpha(cityList.get(i).getAsString(SORT_KEY));
                	alphaIndexer.put(name, sectionPointer);  
                	sections[sectionPointer] = name; 
                }
            }
    	}
    	
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public ContentValues getItem(int position) {
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
            ContentValues cv = contentList.get(position);  
            holder.name.setText(cv.getAsString(CITY_NAME));
//            holder.number.setText(cv.getAsString(NUMBER));
//            String currentStr = getAlpha(list.get(position).getAsString(SORT_KEY));
//            String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getAsString(SORT_KEY)) : " ";
            String currentStr = getContentValuesTitle(cv);
            String previewStr = (position - 1) >= 0 ? getContentValuesTitle(contentList.get(position-1)) : " ";
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
    
    private String getContentValuesTitle(ContentValues content){
    	String title = content.getAsString(PROPERTY);
    	if(title.equals(CityProperty.CityList.getProperty())){
    		title = Util.getAlpha(content.getAsString(SORT_KEY));
    	}
    	return title;
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
    class SortBySortKey implements Comparator<ContentValues>{
		@Override
		public int compare(ContentValues value0, ContentValues value1) {
			String key0 = value0.getAsString(SORT_KEY);
    		String key1 = value1.getAsString(SORT_KEY);
    		return key0.compareTo(key1);
		}
    	
    }
    
    private List<ContentValues> getCitiesList(){
        List<ContentValues> list = new ArrayList<ContentValues>();  
        String[] cities = {
         	"北京","上海","广州","天津","重庆",
           	"哈尔滨","长春","沈阳","大连","济南","青岛","西安","成都","武汉","南京","杭州","宁波","厦门","深圳",
           	"连云港","徐州","宿迁","淮安","盐城","泰州","扬州","镇江","南通","常州","无锡","苏州"
        };
        for(int i=0;i<cities.length;i++){
           	list.add(generateContentValues(cities[i],CityProperty.CityList));
        }
        if (list.size() > 0) {  
           	Collections.sort(list,new SortBySortKey());
//            setAdapter(list);  
        }  
        return list;
    }
    
    private List<ContentValues> getCitiesListI(){
        List<ContentValues> list = new ArrayList<ContentValues>();  
        String[] cities = {
         	"香港(HKG/中国)","首尔(SEL/韩国)","澳门(MFM/中国)","东京(TYO/日本)","新加坡(SIN/新加坡)",
           	"台北(TEP/中国)"};
        for(int i=0;i<cities.length;i++){
           	list.add(generateContentValues(cities[i],CityProperty.CityList));
        }
        if (list.size() > 0) {  
           	Collections.sort(list,new SortBySortKey());
//            setAdapter(list);  
        }  
        return list;
    }
    
    private List<ContentValues> getHistoryCityList(){
    	String[] historyCities = {"厦门","南京","西安"};
    	List<ContentValues> list = new ArrayList<ContentValues>(); 
    	for(int i=0;i<historyCities.length;i++){
           	list.add(generateContentValues(historyCities[i],CityProperty.HISTORY));
        }
        return list;
    }
    
    private List<ContentValues> getHistoryCityListI(){
    	String[] historyCities = {"首尔(SEL/韩国)","澳门(MFM/中国)","东京(TYO/日本)"};
    	List<ContentValues> list = new ArrayList<ContentValues>(); 
    	for(int i=0;i<historyCities.length;i++){
           	list.add(generateContentValues(historyCities[i],CityProperty.HISTORY));
        }
        return list;
    }
    
    private List<ContentValues> getHotCityList(){
    	String[] hotCities = {"北京","上海","广州"};
    	List<ContentValues> list = new ArrayList<ContentValues>(); 
    	for(int i=0;i<hotCities.length;i++){
           	list.add(generateContentValues(hotCities[i],CityProperty.HOT));
        }
        return list;
    }
    private List<ContentValues> getHotCityListI(){
    	String[] hotCities = {"澳门(MFM/中国)","东京(TYO/日本)","新加坡(SIN/新加坡)"};
    	List<ContentValues> list = new ArrayList<ContentValues>(); 
    	for(int i=0;i<hotCities.length;i++){
           	list.add(generateContentValues(hotCities[i],CityProperty.HOT));
        }
        return list;
    }
    
    private ContentValues generateContentValues(String cityName, CityProperty cityProperty){
    	ContentValues cv = new ContentValues();
       	cv.put(CITY_NAME, cityName);
       	cv.put(CITY_CODE, Util.getPinYin(cityName));
       	cv.put(SORT_KEY, Util.getPinYin(cityName));
       	cv.put(PROPERTY, cityProperty.getProperty());
       	return cv;
    }
}
