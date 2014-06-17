package com.boding.view.dialog;

import com.boding.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class SeatChangeBackDialog extends Dialog{
	private ImageView closedialogImageView;
	private TextView refundTextView;
	private TextView rescheduleTextView;
	private TextView signedTransferTextView;
	public SeatChangeBackDialog(Context context, int theme){
		super(context,theme);
		setContentView(R.layout.dialog_seatbackchange_info);
		initView();
	}
	
	private void initView(){
		closedialogImageView = (ImageView) findViewById(R.id.closedialog_imageView);
		refundTextView = (TextView)findViewById(R.id.refund_condition_textView);
		rescheduleTextView = (TextView)findViewById(R.id.rescheduling_condition_textView);
		signedTransferTextView = (TextView)findViewById(R.id.signedtransfer_condition_textView);
		closedialogImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SeatChangeBackDialog.this.dismiss();
				
			}
		});
	}
	
	public void setRefundCondition(String condition){
		refundTextView.setText(condition);
	}
	public void setRescheduleCondition(String condition){
		rescheduleTextView.setText(condition);
	}
	public void setSignedTransferCondition(String condition){
		signedTransferTextView.setText(condition);
	}
	
}
