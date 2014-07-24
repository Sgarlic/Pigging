package com.boding.app;

import com.boding.view.dialog.NetworkUnavaiableDialog;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
/**
 * This activity contains progressbardialog, warningdialog, networkunvaliabledialog
 * and time out method
 * @author shiyge
 *
 */
public class BodingBaseActivity extends FragmentActivity {
	protected ProgressBarDialog progressBarDialog;
	protected NetworkUnavaiableDialog networkUnavaiableDialog;
	protected WarningDialog warningDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressBarDialog = new ProgressBarDialog(this);
		networkUnavaiableDialog = new NetworkUnavaiableDialog (this);
		warningDialog = new WarningDialog(this);
	}
	
	public void handleTimeout(){
		progressBarDialog.dismiss();
		warningDialog.setContent("请求超时，稍后再试");
		warningDialog.show();
	}
}
