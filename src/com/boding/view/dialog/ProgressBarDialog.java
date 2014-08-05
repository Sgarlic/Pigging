package com.boding.view.dialog;

import android.app.Dialog;
import android.content.Context;

import com.ant.liao.GifView;
import com.boding.R;
import com.boding.constants.Constants;

public class ProgressBarDialog extends Dialog{
	private Context context;
	private GifView gifView;
	
	public ProgressBarDialog(Context context){
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.context = context;
		setContentView(R.layout.dialog_progressbar);
		initView();
	}
	
	private void initView(){
		gifView = (GifView) findViewById(R.id.progressbardialog_loadingPic);
		gifView.setGifImage(R.drawable.loadingpic);
	}
}
