package com.boding.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.boding.R;
import com.boding.constants.CityProperty;
import com.boding.constants.Constants;
import com.boding.constants.IntentRequestCode;
import com.boding.model.City;
import com.boding.util.Util;
import com.boding.view.CitySelectLetterListView;
import com.boding.view.CitySelectLetterListView.OnTouchingLetterChangedListener;
import com.boding.view.WarningDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CitySelectActivity extends Activity {
	private BaseAdapter adapter;  
    private ListView allCitiesListView;
//    private TextView overlay;
    private CitySelectLetterListView letterListView;
    private static final String CITY_NAME = "name", CITY_CODE = "code", SORT_KEY = "sort_key";
    private static final String PROPERTY="property", LOCATION="��λ",HISTORY="��ʷ",HOT="����";
    private HashMap<String, Integer> alphaIndexer;//��Ŵ��ڵĺ���ƴ������ĸ����֮��Ӧ���б�λ��
    private String[] sections;//��Ŵ��ڵĺ���ƴ������ĸ
    private Handler handler;
//    private OverlayThread overlayThread;
    private String locatedCity = "��λ���ĳ���";
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_city_select);  
        
        initView();
    }  
    
    private void initView(){
    	setTitle();
    	
    	allCitiesListView = (ListView) findViewById(R.id.city_select_listView);
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
				if(Constants.isFlyToLocationSelection){
					if(selectedCity.equals(Constants.Fly_From_City))
						toastInfo = "�����͵��ﲻ��Ϊͬһ����";
					else
						Constants.Fly_To_City = selectedCity;
				}
				else{
					if(selectedCity.equals(Constants.Fly_To_City))
						toastInfo = "�����͵��ﲻ��Ϊͬһ����";
					else
						Constants.Fly_From_City = selectedCity;
				}
				
				if(toastInfo!=null){
					WarningDialog warningDialog = new WarningDialog(CitySelectActivity.this,
							R.layout.wraning_info_dialog,R.style.Warning_Dialog_Theme);
					warningDialog.setContent(toastInfo);
					warningDialog.setKnown("֪����");
					warningDialog.show();
				}else{
					Util.returnToPreviousPage(CitySelectActivity.this, IntentRequestCode.START_CITY_SELECTION);
				}
//				Log.d("poding");
			}
    		
    	});
    	
        letterListView = (CitySelectLetterListView) findViewById(R.id.city_select_letter_listView);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        
//        asyncQuery = new MyAsyncQueryHandler(getContentResolver());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
//        overlayThread = new OverlayThread();
//        initOverlay();
        
        LinearLayout returnLogoLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
        returnLogoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(CitySelectActivity.this, IntentRequestCode.START_CITY_SELECTION);
			}
        });
    }
    
    private void setTitle(){
    	TextView citySelectTitleTextView = (TextView)findViewById(R.id.city_select_title_textView);
    	if(Constants.isFlyToLocationSelection)
    		citySelectTitleTextView.setText("�������");
    	else
    		citySelectTitleTextView.setText("��������");
    }
  
    @Override  
    protected void onResume() {  
        super.onResume();  
        setAdapter();
    }  
    
    private void setAdapter() {
    	adapter = new ListAdapter(this, getHistoryCityList(),getHotCityList(),getCitiesList());
        allCitiesListView.setAdapter(adapter);  
  
    }
    
    private class ListAdapter extends BaseAdapter {
    	 private LayoutInflater inflater;  
         private List<ContentValues> list;
    	
    	public ListAdapter(Context context, List<ContentValues> historyCityList, List<ContentValues> hotCityList, List<ContentValues> cityList) {
    		this.inflater = LayoutInflater.from(context);
    		this.list = new ArrayList<ContentValues>();
    		alphaIndexer = new HashMap<String, Integer>();
    		int sectionSize = 0;
    		if(locatedCity!=null){
    			sectionSize+=1;
    			list.add(generateContentValues(locatedCity,CityProperty.LOCATECITY));
    		}
    		
    		list.addAll(historyCityList);
    		list.addAll(hotCityList);
    		list.addAll(cityList);
    		
    		sectionSize+=(historyCityList.size()+hotCityList.size()+list.size());
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
    			//��ǰ����ƴ������ĸ
    			String currentStr = getAlpha(cityList.get(i).getAsString(SORT_KEY));
    			//��һ������ƴ������ĸ�����������Ϊ�� ��
                String previewStr = (i - 1) >= 0 ? getAlpha(cityList.get(i - 1).getAsString(SORT_KEY)) : " ";
                if (!previewStr.equals(currentStr)) {
                	String name = getAlpha(cityList.get(i).getAsString(SORT_KEY));
                	alphaIndexer.put(name, sectionPointer);  
                	sections[sectionPointer] = name; 
                }
            }
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
            holder.name.setText(cv.getAsString(CITY_NAME));
//            holder.number.setText(cv.getAsString(NUMBER));
//            String currentStr = getAlpha(list.get(position).getAsString(SORT_KEY));
//            String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getAsString(SORT_KEY)) : " ";
            String currentStr = getContentValuesTitle(cv);
            String previewStr = (position - 1) >= 0 ? getContentValuesTitle(list.get(position-1)) : " ";
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
    		title = getAlpha(content.getAsString(SORT_KEY));
    	}
    	return title;
    }
    
//    //��ʼ������ƴ������ĸ������ʾ��
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
				//�ӳ�һ���ִ�У���overlayΪ���ɼ�
//				handler.postDelayed(overlayThread, 1500);
			} 
		}
    	
    }
//    
//    //����overlay���ɼ�
//    private class OverlayThread implements Runnable {
//
//		@Override
//		public void run() {
//			overlay.setVisibility(View.GONE);
//		}
//    	
//    }
//    
    
	//��ú���ƴ������ĸ
    private String getAlpha(String str) {  
        if (str == null) {  
            return "#";  
        }  
  
        if (str.trim().length() == 0) {  
            return "#";  
        }  
  
        char c = str.trim().substring(0, 1).charAt(0);  
        // ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ  
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");  
        if (pattern.matcher(c + "").matches()) {  
            return (c + "").toUpperCase();  
        } else {  
            return "#";  
        }  
    }  
    
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
         	"����","�Ϻ�","����","���","����",
           	"������","����","����","����","����","�ൺ","����","�ɶ�","�人","�Ͼ�","����","����","����","����",
           	"���Ƹ�","����","��Ǩ","����","�γ�","̩��","����","��","��ͨ","����","����","����"
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
    
    private List<ContentValues> getHistoryCityList(){
    	String[] historyCities = {"��ʷ1","��ʷ2","��ʷ3"};
    	List<ContentValues> list = new ArrayList<ContentValues>(); 
    	for(int i=0;i<historyCities.length;i++){
           	list.add(generateContentValues(historyCities[i],CityProperty.HISTORY));
        }
        return list;
    }
    
    private List<ContentValues> getHotCityList(){
    	String[] hotCities = {"����1","����2","����3"};
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
    

	private void returnToPreviousPage(boolean citySelected){
		Intent intent=new Intent();
//		if(citySelected)
//			intent.putExtra("selectedDate", Util.getFormatedDate(year, month, dayOfMonth));
		setResult(1, intent);
		finish();
	}
}  

