package com.boding.view.dialog;

import java.util.List;

import com.boding.R;
import com.boding.constants.GlobalVariables;

import android.app.Dialog;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class SelectionDialog extends Dialog{
	private ListView selectorListView;
	private TextView titleTextView;
	private SelectionAdapter selectorAdapter;
	private Context context;
	private List<String> selectionList;
	
	private OnItemSelectedListener onItemSelectedListener;
	
	public SelectionDialog(Context context, int theme, String title, List<String> selectionList){
		super(context,theme);
		this.context = context;
		this.selectionList = selectionList;
		setContentView(R.layout.dialog_selection);
		setWidthHeight();
		initView();
		setTitle(title);
	}
	
	private void setTitle(String title){
		titleTextView.setText(title);
	}
	
	private void setWidthHeight(){
		if(selectionList.size()>6){
			//set dialog width
			WindowManager.LayoutParams lp = this.getWindow().getAttributes();
			lp.height = GlobalVariables.Screen_Height*2/3;
			this.getWindow().setAttributes(lp);
		}
	}
	
	private void initView(){
		selectorListView = (ListView)findViewById(R.id.selection_selector_listView);
		
		selectorAdapter = new SelectionAdapter(this.context, selectionList);
		selectorListView.setAdapter(selectorAdapter);
		
		selectorListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//响应监听事件
				if(onItemSelectedListener!=null)
					onItemSelectedListener.OnItemSelected(position);
				SelectionDialog.this.dismiss();
			}
			
		});
		
		titleTextView = (TextView) findViewById(R.id.selection_title_textView);
	}
	
	//给控件设置监听事件
	public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
		this.onItemSelectedListener =  onItemSelectedListener;
	}
	//监听接口
	public interface OnItemSelectedListener {
		void OnItemSelected(int position);
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
