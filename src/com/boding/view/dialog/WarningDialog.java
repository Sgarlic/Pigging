package com.boding.view.dialog;

import com.boding.R;
import com.boding.constants.Constants;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import android.view.View;

public class WarningDialog extends Dialog{
	private TextView warningContentTextView;
	private TextView warningKnownTextView;
	public WarningDialog(Context context){
		super(context,Constants.DIALOG_STYLE);
		setContentView(R.layout.dialog_wraning_info);
		initView();
	}
	public WarningDialog(Context context, int theme){
		super(context,theme);
		setContentView(R.layout.dialog_wraning_info);
		initView();
	}
	
	private void initView(){
		warningContentTextView = (TextView)findViewById(R.id.warning_content_textView);
		warningKnownTextView = (TextView)findViewById(R.id.warning_known_textView);
		warningKnownTextView.setClickable(true);
		warningKnownTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				WarningDialog.this.dismiss();
				
			}
		});
	}
	
	public void setContent(String content){
		warningContentTextView.setText(content);
	}
	
	public void setKnown(String known){
		warningKnownTextView.setText(known);
	}
    
}
