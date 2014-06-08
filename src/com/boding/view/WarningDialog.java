package com.boding.view;

import com.boding.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class WarningDialog extends Dialog{
	private TextView warningContentTextView;
	private TextView warningKnownTextView;
	public WarningDialog(Context context, int theme){
		super(context,theme);
		setContentView(R.layout.wraning_info_dialog);
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
