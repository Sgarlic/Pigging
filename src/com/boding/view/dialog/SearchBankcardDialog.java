package com.boding.view.dialog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.model.BankCard;
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

public class SearchBankcardDialog extends Dialog{
	private EditText bankcardEditText;
	private LinearLayout cancelBankcardSearchLinearLayout;
	private LinearLayout searchBankcardLinearLayout;
	private ListView searchResultListView;
	private Context context;
	
	private List<BankCard> bankcardList;
	
	public SearchBankcardDialog(Context context, int theme,List<BankCard> bankcardList){
		super(context,theme);
		setContentView(R.layout.dialog_search_city);
		setWidthHeight();
		this.context = context;
		this.bankcardList = bankcardList;
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
		bankcardEditText = (EditText)findViewById(R.id.city_search_editText);
		bankcardEditText.addTextChangedListener(bankcardSearchTextWatcher);
		
		cancelBankcardSearchLinearLayout = (LinearLayout)findViewById(R.id.cancel_citysearch_linearLayout);
		cancelBankcardSearchLinearLayout.setClickable(true);
		cancelBankcardSearchLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SearchBankcardDialog.this.dismiss();
			}
		});
		
		searchBankcardLinearLayout = (LinearLayout)findViewById(R.id.search_city_list_linearLayout);
		
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
		bankcardEditText.setText(content);
	}
	
	/**
	 * 搜索框事件
	 */
	private TextWatcher bankcardSearchTextWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			String searchText = s.toString();
//			Log.d("poding",searchText);
			if(searchText.equals("")){
				hideListView();
			}else{
				showListView();
				SearchBankcardResultListAdapter adapter = 
						new SearchBankcardResultListAdapter(SearchBankcardDialog.this.getContext(), searchBankcard(searchText));
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
		searchBankcardLinearLayout.setVisibility(View.INVISIBLE);
	}
	private void showListView(){
		searchBankcardLinearLayout.setVisibility(View.VISIBLE);
	}
	
	private class SearchBankcardResultListAdapter extends BaseAdapter {
    	private LayoutInflater inflater;  
        private List<BankCard> contentList;
    	
    	public SearchBankcardResultListAdapter(Context context, List<BankCard> bankcardList) {
    		this.inflater = LayoutInflater.from(context);
    		this.contentList = bankcardList;
    	}
    	
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public BankCard getItem(int position) {
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
			BankCard bankcard = contentList.get(position);  
            holder.name.setText(bankcard.getBankName());
            return convertView;  
		}
		
		private class ViewHolder {
            TextView name;  
		}
    	
    }
	
	private List<BankCard> searchBankcard(String searchText){
		searchText = searchText.toLowerCase();
		List<BankCard> searchResult = new ArrayList<BankCard>();
		for(BankCard bankcard : bankcardList){
			if(bankcard.getBankName().contains(searchText)||bankcard.getBankPinyin().contains(searchText)){
				searchResult.add(bankcard);
			}
		}
		return searchResult;
	}
	
}
