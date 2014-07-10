package com.boding.view.dialog;

import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

public class ProgressBarDialog extends Dialog{
	private Context context;
	private ProgressBar progressBar;
	
	public ProgressBarDialog(Context context){
		super(context, Constants.DIALOG_STYLE);
		this.context = context;
		this.setCanceledOnTouchOutside(false);
		setContentView(R.layout.dialog_progressbar);
		initView();
	}
	
	public ProgressBarDialog(Context context, int theme){
		super(context,theme);
		this.context = context;
		this.setCanceledOnTouchOutside(false);
		setContentView(R.layout.dialog_progressbar);
		initView();
	}
	
	private void initView(){
		progressBar = (ProgressBar) findViewById(R.id.progressbardialog_progresBar);
	}
}
