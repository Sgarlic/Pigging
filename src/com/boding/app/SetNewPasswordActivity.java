package com.boding.app;


import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.BodingUser;
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

public class SetNewPasswordActivity extends Activity {
	private LinearLayout confirmLinearLayout;
	private EditText newPasswordEditText;
	private EditText confirmPasswordEditText;
	
	private ProgressBarDialog progressBarDialog;
	private WarningDialog warningDialog;
	
	private String cardNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setnewpassword);
		progressBarDialog = new ProgressBarDialog(this);
		warningDialog = new WarningDialog(this);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	cardNo = arguments.getString(IntentExtraAttribute.FORGETPWD_USERCARDNO);
        }
		initView();
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(SetNewPasswordActivity.this, IntentRequestCode.CHANGE_PASSWORD);
			}
			
		});
		confirmLinearLayout = (LinearLayout) findViewById(R.id.setnewpassword_confirm_linearLayout);

		newPasswordEditText = (EditText) findViewById(R.id.setnewpassword_input_newPassword_editText);
		confirmPasswordEditText = (EditText) findViewById(R.id.setnewpassword_input_confirmPassword_editText);

		addListeners();
	}
	private void addListeners(){
		confirmLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String newPwd = newPasswordEditText.getText().toString();
				if(newPwd.equals("") || !RegularExpressionsUtil.checkPassword(newPwd)){
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
				(new BodingUserTask(SetNewPasswordActivity.this, HTTPAction.SET_NEW_PASSWORD)).execute(cardNo, newPwd);
			}
		});
	}
	
	public void loginResult(){
		progressBarDialog.dismiss();
		Util.showToast(this, "设置新密码成功");
		Util.returnToPreviousPage(this, IntentRequestCode.CHANGE_PASSWORD);
	}
	
	public void setNewPasswordResut(boolean isSuccess){
		if(isSuccess){
			(new BodingUserTask(SetNewPasswordActivity.this, HTTPAction.SETNEWPWD_LOGIN)).execute(cardNo, newPasswordEditText.getText().toString());
		}else{
			progressBarDialog.dismiss();
			Util.showToast(this, "设置新密码失败");
		}
	}
}
