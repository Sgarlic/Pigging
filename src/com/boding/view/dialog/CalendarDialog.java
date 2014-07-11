package com.boding.view.dialog;


import java.util.Date;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.view.layout.CalendarLayout;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

public class CalendarDialog extends Dialog{
	private CalendarLayout calendarLayout;
	private OnItemClickListener onItemClickListener;
	
	public CalendarDialog(Context context){
		super(context,Constants.DIALOG_STYLE);
		setContentView(R.layout.dialog_calendar);
		setWidthHeight();
		initView();
	}
	private void setWidthHeight(){
		//set dialog width
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = GlobalVariables.Screen_Width; //���ÿ��
		//lp.height = GlobalVariables.Screen_Height;
		
		// set dialog location
		this.getWindow().setGravity(Gravity.LEFT | Gravity.BOTTOM);
		lp.x = 0;
		lp.y = 0;
		
		this.getWindow().setAttributes(lp);
	}
	
	private void initView(){
		calendarLayout = (CalendarLayout) findViewById(R.id.calendar_dialog_calendarLienarLayout);
		calendarLayout.setOnItemClickListener(new CalendarLayout.OnItemClickListener() {
			@Override
			public void OnItemClick(Date date) {
				//��Ӧ�����¼�
				if(onItemClickListener!=null)
					onItemClickListener.OnItemClick(date);
			}
		});;
	}
	
	//���ؼ����ü����¼�
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.onItemClickListener =  onItemClickListener;
	}
	//�����ӿ�
	public interface OnItemClickListener {
		void OnItemClick(Date date);
	}
	
	public void setMinDate(Date date){
		calendarLayout.setMinClickableDate(date);
	}
	
	public void setDate(Date date){
		calendarLayout.setDate(date);
	}
}