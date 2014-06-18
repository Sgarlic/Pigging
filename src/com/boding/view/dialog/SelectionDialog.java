package com.boding.view.dialog;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelectionDialog extends Dialog{
	private ListView selectorListView;
	private TextView titleTextView;
	private SelectionAdapter selectorAdapter;
	private Context context;
	private List<String> selectionList;
	
	public SelectionDialog(Context context, int theme, String title, List<String> selectionList){
		super(context,theme);
		this.context = context;
		this.selectionList = selectionList;
		setContentView(R.layout.dialog_selection);
		initView();
		setTitle(title);
	}
	
	private void setTitle(String title){
		titleTextView.setText(title);
	}
	
	private void initView(){
		selectorListView = (ListView)findViewById(R.id.selection_selector_listView);
		
		selectorAdapter = new SelectionAdapter(this.context, selectionList);
		selectorListView.setAdapter(selectorAdapter);
		
		titleTextView = (TextView) findViewById(R.id.selection_title_textView);
	}
 
	private class SelectionAdapter extends BaseAdapter {
		private List<String> selectionList;
		private Context context;
		public SelectionAdapter(Context context, List<String> selectionList) {
			this.context = context;
			this.selectionList = selectionList;
		}
		@Override
		public int getCount() {
			return selectionList.size();
		}

		@Override
		public String getItem(int position) {
			return selectionList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_selection, null);
	            holder = new ViewHolder();  
	            
	            holder.itemTextView = (TextView) convertView.findViewById(R.id.selector_item_textView);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
            holder.itemTextView.setText(getItem(position));
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView itemTextView;
		}
	}
}
