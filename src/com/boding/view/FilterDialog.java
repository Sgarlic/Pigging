package com.boding.view;

import com.boding.R;
import com.boding.constants.GlobalVariables;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.TabHost;

public class FilterDialog extends Dialog{
	private Context context;
	
	public FilterDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		setContentView(R.layout.dialog_filter);
		setWidthHeight();
		initView();
	}

	private ImageView flightTimeSegmentImageView;
	private ImageView flightClassImageView;
	private ImageView flightCompanyImageView;
	private TabHost tabhost;
	
	 private void setWidthHeight(){
			//set dialog width
			WindowManager.LayoutParams lp = this.getWindow().getAttributes();
			lp.width = GlobalVariables.Screen_Width; //设置宽度
			//lp.height = GlobalVariables.Screen_Height;
			
			// set dialog location
			this.getWindow().setGravity(Gravity.LEFT | Gravity.BOTTOM);
			lp.x = 0;
			lp.y = 0;
			
			this.getWindow().setAttributes(lp);
		}
	 
	 public void initView() {
		 tabhost = (TabHost) findViewById(R.id.filter_dialog_tabhost);
		 tabhost.setup();
		 tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("第一个标签").setContent(R.id.filter_flighttimesegment_tab));
	     tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("第二个标签").setContent(R.id.filter_flightclass_tab));
	     tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("第三个标签").setContent(R.id.filter_flightcompnay_tab));

	     flightTimeSegmentImageView = (ImageView)findViewById(R.id.filter_flightTimeSegment_imageView);
		 flightClassImageView = (ImageView)findViewById(R.id.filter_flightclass_imageView);
		 flightCompanyImageView = (ImageView)findViewById(R.id.filter_flightcompany_imageView);
		 
		 flightTimeSegmentImageView.setOnClickListener(tabOnClickListener);
		 
		 flightClassImageView.setOnClickListener(tabOnClickListener);
		 
		 flightCompanyImageView.setOnClickListener(tabOnClickListener);
		 flightTimeSegmentImageView.setSelected(true);
	 }
	 
	 View.OnClickListener tabOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			flightTimeSegmentImageView.setSelected(false);
			flightClassImageView.setSelected(false);
			flightCompanyImageView.setSelected(false);
		    if(view.getId() == R.id.filter_flightTimeSegment_imageView){
			     tabhost.setCurrentTab(0);
			     flightTimeSegmentImageView.setSelected(true);
			 } else if(view.getId() == R.id.filter_flightclass_imageView){
			     tabhost.setCurrentTab(1);
			     flightClassImageView.setSelected(true);
			 } else if(view.getId() == R.id.filter_flightcompany_imageView){
			     tabhost.setCurrentTab(2);
			     flightCompanyImageView.setSelected(true);
			 }
		}
	};
}
