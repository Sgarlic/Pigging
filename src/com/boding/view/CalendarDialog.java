package com.boding.view;

import com.boding.R;
import com.boding.constants.GlobalVariables;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class CalendarDialog extends Dialog{
	private TextView currYearTextView;
	private TextView currMonthTextView;
	public CalendarDialog(Context context, int theme){
		super(context,theme);
		setContentView(R.layout.dialog_calendar);
		setWidthHeight();
		initView();
	}
	private void setWidthHeight(){
		//set dialog width
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = GlobalVariables.Screen_Width; //…Ë÷√øÌ∂»
		//lp.height = GlobalVariables.Screen_Height;
		
		// set dialog location
		this.getWindow().setGravity(Gravity.LEFT | Gravity.BOTTOM);
		lp.x = 0;
		lp.y = 0;
		
		this.getWindow().setAttributes(lp);
	}
	private void initView(){
		currYearTextView = (TextView)findViewById(R.id.current_year_textView);
		currMonthTextView = (TextView)findViewById(R.id.current_month_textView);
		float lineExtra = currYearTextView.getLineSpacingExtra();
		currYearTextView.setLineSpacing(0 - lineExtra, currYearTextView.getLineSpacingMultiplier());
	}
}
