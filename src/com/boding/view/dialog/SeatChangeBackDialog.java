package com.boding.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.constants.Constants;

public class SeatChangeBackDialog extends Dialog{
	private ImageView closedialogImageView;
	private LinearLayout buyInfoLinearLayout;
	private TextView buyInfoTextView;
	private TextView rulesTextView;
	private String rules;
	public SeatChangeBackDialog(Context context, String rules){
		super(context,Constants.DIALOG_STYLE);
		setContentView(R.layout.dialog_seatbackchange_info);
		this.rules = rules;
		initView();
	}
	
	private void initView(){
		closedialogImageView = (ImageView) findViewById(R.id.closedialog_imageView);
		buyInfoLinearLayout = (LinearLayout) findViewById(R.id.seatbackchangeinfo_buyinfo_linearLayout);
		rulesTextView = (TextView)findViewById(R.id.seatbackchangeinfo_rules_textView);
		closedialogImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SeatChangeBackDialog.this.dismiss();
			}
		});
		
		
		String[] tempArray = rules.split("\\^");
		String rulesString = "";
//		for(String temp : tempArray){
//			String[] tempRuleItem = temp.split(":");
//			rulesString += tempRuleItem[0] + ":" + "\n";
//			String[] tempLines = tempRuleItem[1].split(";");
//			for(String tempLine: tempLines){
//				rulesString += tempLine + "\n";
//			}
//		}
		for(String temp : tempArray){
			rulesString += temp + "\n" + "\n";
		}
		if( !(rules == null || rules.equals(""))){
			rulesTextView.setText(rulesString);
		}
	}
}
