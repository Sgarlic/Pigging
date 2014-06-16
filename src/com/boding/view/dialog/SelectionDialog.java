package com.boding.view.dialog;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.app.CitySelectActivity;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.util.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

public class SelectionDialog extends Dialog{
	
	public SelectionDialog(Context context, int theme){
		super(context,theme);
		setContentView(R.layout.dialog_calendar);
		setWidthHeight();
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
	
}