package com.boding.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.boding.R;
import com.boding.constants.Constants;

public class ProgressBarDialog extends Dialog{
	private Context context;
//	private ProgressBar progressBar;
//	private ImageView gifPicImageView;
	
	public ProgressBarDialog(Context context){
		super(context, Constants.DIALOG_STYLE);
		this.context = context;
//		GIFView gifView = new GIFView(context);
//	    setContentView(gifView);
//		this.setCanceledOnTouchOutside(false);
		setContentView(R.layout.dialog_progressbar);
//		initView();
	}
	
	private void initView(){
//		progressBar = (ProgressBar) findViewById(R.id.progressbardialog_progresBar);
//		gifPicImageView = (ImageView) findViewById(R.id.progressbardialog_loadingPic);
//		
		
	}
}
