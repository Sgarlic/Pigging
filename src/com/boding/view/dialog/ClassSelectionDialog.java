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

public class ClassSelectionDialog extends Dialog{
	private ListView classSelectorListView;
	private ClassSelectorAdapter classAdapter;
	private Context context;
	public ClassSelectionDialog(Context context, int theme){
		super(context,theme);
		this.context = context;
		setContentView(R.layout.dialog_class_selection);
		initView();
	}
	
	private void initView(){
		classSelectorListView = (ListView)findViewById(R.id.class_selector_listView);
		
		List<String> classList = new ArrayList<String>();
		classList.add("经济舱");
		classList.add("公务舱/头等舱");
		classAdapter = new ClassSelectorAdapter(this.context, classList);
		
		classSelectorListView.setAdapter(classAdapter);
	}
 
	private class ClassSelectorAdapter extends BaseAdapter {
		private List<String> classList;
		private Context context;
		public ClassSelectorAdapter(Context context, List<String> classList) {
			this.context = context;
			this.classList = classList;
		}
		@Override
		public int getCount() {
			return classList.size();
		}

		@Override
		public String getItem(int position) {
			return classList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_class_selection, null);
	            holder = new ViewHolder();  
	            
	            holder.classTextView = (TextView) convertView.findViewById(R.id.selector_item_class_textView);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
            holder.classTextView.setText(getItem(position));
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView classTextView;
		}
	}
}
