package com.boding.view;

import com.boding.R;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;

public class FilterDialogFragment extends DialogFragment{
	private Button artistButton;
	private Button albumButton;
	private Button songButton;
	private TabHost tabhost;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 //setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Dialog);
		 setStyle(DialogFragment.STYLE_NO_FRAME, 0);
	 }
	 
	 @SuppressLint("NewApi")
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
		 View v = inflater.inflate(R.layout.filter_dialog, container, false);
		 tabhost = (TabHost) v.findViewById(R.id.filter_dialog_tabhost);
		 tabhost.setup();
		 
		 
		 initView(v);
		 
		 System.out.println("...."+tabhost+"..." + getResources().getDrawable(R.drawable.contact_list_icon));
		 tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("第一个标签").setContent(R.id.tab1));
	     tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("第二个标签").setContent(R.id.tab2));
	     tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("第三个标签").setContent(R.id.tab3));
	     
	     Display display = getActivity().getWindowManager().getDefaultDisplay();
	     Point size = new Point();
	     display.getSize(size);
	     v.setMinimumWidth(size.x);
	     
	     Window window = getDialog().getWindow();
	     WindowManager.LayoutParams p = window.getAttributes();
	     p.width = LayoutParams.MATCH_PARENT;
	     p.gravity = Gravity.BOTTOM | Gravity.FILL_HORIZONTAL;
	     p.verticalMargin = 0;
	     window.setAttributes(p);
	     
	     System.out.println("$$$$"+v.getParent());
	     ViewGroup.LayoutParams para = v.getLayoutParams();
	     
	     //v.setMinimumWidth(300);
	     
		 return v;
	 }
	 
	 private void initView(View v){
		 artistButton = (Button)v.findViewById(R.id.artist_id);
		 albumButton = (Button)v.findViewById(R.id.album_id);
		 songButton = (Button)v.findViewById(R.id.song_id);
		 
		 artistButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tabHandler(v);
			}
		});
		 
		 albumButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					tabHandler(v);
				}
			});
		 
		 songButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					tabHandler(v);
				}
			});
	 }
	 
	 public void tabHandler(View target){
		    artistButton.setSelected(false);
		    albumButton.setSelected(false);
		    songButton.setSelected(false);
		    if(target.getId() == R.id.artist_id){
		        tabhost.setCurrentTab(0);
		        artistButton.setSelected(true);
		    } else if(target.getId() == R.id.album_id){
		        tabhost.setCurrentTab(1);
		        albumButton.setSelected(true);
		    } else if(target.getId() == R.id.song_id){
		        tabhost.setCurrentTab(2);
		        songButton.setSelected(true);
		    }
		}
}
