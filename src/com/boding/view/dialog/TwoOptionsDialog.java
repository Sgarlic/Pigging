package com.boding.view.dialog;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.util.Util;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import android.view.View;

public class TwoOptionsDialog extends Dialog{
	private TextView contentTextView;
	private TextView leftOptionTextView;
	private TextView rightOptionTextView;
	private OnOptionSelectedListener onOptionSelectedListener;
	private Context context;
	
	public TwoOptionsDialog(Context context){
		super(context,Constants.DIALOG_STYLE);
		setContentView(R.layout.dialog_twooption_info);
		this.context = context;
		initView();
	}
	
	private void initView(){
		contentTextView = (TextView)findViewById(R.id.twooption_content_textView);
		leftOptionTextView = (TextView)findViewById(R.id.twooption_leftOption_textView);
		rightOptionTextView = (TextView)findViewById(R.id.twooption_rightOption_textView);
		setListeners();
	}
	
	public void setContent(String content){
		contentTextView.setText(content);
	}
	
	public void setLeftOption(String leftOption){
		leftOptionTextView.setText(leftOption);
	}
    
	public void setRightOption(String rightOption){
		rightOptionTextView.setText(rightOption);
	}
	
	private void setListeners(){
		leftOptionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(onOptionSelectedListener != null)
					onOptionSelectedListener.OnItemClick(0);
				TwoOptionsDialog.this.dismiss();
			}
		});
		rightOptionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(onOptionSelectedListener == null){
					Util.dialPhone(context);
				}else
					onOptionSelectedListener.OnItemClick(1);
				TwoOptionsDialog.this.dismiss();
			}
		});
	}
	
	public void setOnOptionSelectedListener(OnOptionSelectedListener onOptionSelectedListener){
		this.onOptionSelectedListener = onOptionSelectedListener;
	}
	
	//¼àÌý½Ó¿Ú
	public interface OnOptionSelectedListener {
		// option = 0 ==> left option
		// option = 1 ==> right option
		void OnItemClick(int option);
	}
}
