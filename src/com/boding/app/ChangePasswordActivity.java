package com.boding.app;


import com.boding.R;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentRequestCode;
import com.boding.task.BodingUserTask;
import com.boding.util.RegularExpressionsUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChangePasswordActivity extends Activity {
	private LinearLayout completeLinearLayout;
	private EditText currentPasswordEditText;
	private EditText newPasswordEditText;
	private EditText confirmPasswordEditText;
	
	private ProgressBarDialog progressBarDialog;
	private WarningDialog warningDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepassword);
		progressBarDialog = new ProgressBarDialog(this);
		warningDialog = new WarningDialog(this);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
        
		initView();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(ChangePasswordActivity.this, IntentRequestCode.CHANGE_PASSWORD);
			}
			
		});
		completeLinearLayout = (LinearLayout) findViewById(R.id.changepassword_complete_linearLayout);

		currentPasswordEditText = (EditText) findViewById(R.id.changepassword_input_currentPassword_editText);
		newPasswordEditText = (EditText) findViewById(R.id.changepassword_input_newPassword_editText);
		confirmPasswordEditText = (EditText) findViewById(R.id.changepassword_input_confirmPassword_editText);

		addListeners();
	}
	private void addListeners(){
		completeLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String curPwd = currentPasswordEditText.getText().toString();
				String newPwd = newPasswordEditText.getText().toString();
				if(curPwd.equals("") || !RegularExpressionsUtil.checkPassword(curPwd)
					|| newPwd.equals("") || !RegularExpressionsUtil.checkPassword(newPwd)){
					warningDialog.setContent("请输入正确的密码！");
					warningDialog.show();
					return;
				}
				if(!newPwd.equals(confirmPasswordEditText.getText().toString())){
					warningDialog.setContent("请确认新密码！");
					warningDialog.show();
					return;
				}
				progressBarDialog.show();
				(new BodingUserTask(ChangePasswordActivity.this, HTTPAction.CHANGE_PASSWORD)).execute(curPwd,newPwd);
			}
		});
	}
	
	public void setChangePwdResult(boolean isSuccess){
		progressBarDialog.dismiss();
		if(isSuccess){
			Util.showToast(this, "修改密码成功");
			Util.returnToPreviousPage(this, IntentRequestCode.CHANGE_PASSWORD);
		}else{
			Util.showToast(this, "修改密码失败，请输入正确的当前密码");
		}
	}
}
