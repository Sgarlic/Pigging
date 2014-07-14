package com.boding.app;


import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.task.BodingUserTask;
import com.boding.util.RegularExpressionsUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VerifyPhonenumActivity extends Activity {
	private TextView titleTextView;
	private EditText phoneNumEditText;
	private EditText verificationNumEditText;
	private LinearLayout sendVerificationNumLinearLayout;
	private LinearLayout confirmLinearLayout;
	
	private String verifyPhonenumType = "1";
	private String verifyCode = "";
	
	private ProgressBarDialog progressBarDialog;
	private WarningDialog warningDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verfiy_phonenum);
		warningDialog = new WarningDialog(this);
		progressBarDialog = new ProgressBarDialog(this);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null)
        	verifyPhonenumType = arguments.getString(IntentExtraAttribute.VERIFY_PHONENUM_TYPE);
        
		initView();
		setTitle();
		
	}
	
	private void setTitle(){
		if(verifyPhonenumType.equals("4")){
			titleTextView.setText("忘记密码");
		}
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(VerifyPhonenumActivity.this, IntentRequestCode.VERIFY_PHONENUM);
			}
			
		});

		titleTextView = (TextView) findViewById(R.id.verifyphonenum_title_textView);
		phoneNumEditText = (EditText) findViewById(R.id.verifyphonenum_input_phoneNum_editText);
		verificationNumEditText = (EditText) findViewById(R.id.verifyphonenum_input_verificationNum_editText);
		
		sendVerificationNumLinearLayout = (LinearLayout) findViewById(R.id.verifyphonenum_sendVerificationNum_linearLayout);
		confirmLinearLayout = (LinearLayout) findViewById(R.id.verifyphonenum_confirm_linearLayout);

		addListeners();
	}
	
	private void addListeners(){
		phoneNumEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			@Override
			public void afterTextChanged(Editable arg0) {
				verifyCode = "";
			}
		});
		sendVerificationNumLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String phoneNum = phoneNumEditText.getText().toString();
				if(phoneNum.equals("") || !RegularExpressionsUtil.checkMobile(phoneNum)){
					warningDialog.setContent("请输入正确的手机号码");
					warningDialog.show();
					return;
				}
				progressBarDialog.show();
				(new BodingUserTask(VerifyPhonenumActivity.this, HTTPAction.VERIFY_OLD_PHONENUM_VERIFYPHOENACTIVITY))
				.execute(phoneNum);
			}
		});
		
		confirmLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String phoneNum = phoneNumEditText.getText().toString();
				if(phoneNum.equals("") || !RegularExpressionsUtil.checkMobile(phoneNum)){
					warningDialog.setContent("请输入正确的手机号码");
					warningDialog.show();
					return;
				}
				
				if(verifyCode.equals("")){
					warningDialog.setContent("请先发送验证码");
					warningDialog.show();
					return;
				}
				
				String verifyNumber = verificationNumEditText.getText().toString();
				if(verifyNumber.equals("")|| !verifyNumber.equals(verifyCode)){
					warningDialog.setContent("请输入正确的验证码");
					warningDialog.show();
					return;
				}
				
				if(verifyPhonenumType.equals("4")){
					Intent intent = new Intent();
					intent.setClass(VerifyPhonenumActivity.this, SetNewPasswordActivity.class);
					startActivity(intent);
					VerifyPhonenumActivity.this.finish();
				}else{
					progressBarDialog.show();
					(new BodingUserTask(VerifyPhonenumActivity.this, HTTPAction.ACTIVIATE)).execute(phoneNum);
				}
			}
		});
	}
	
	public void verifyOldPhoneNumResult(boolean isSuccess){
		if(isSuccess){
			(new BodingUserTask(VerifyPhonenumActivity.this, HTTPAction.VERIFY_PHONENUMBER))
			.execute(phoneNumEditText.getText().toString(),verifyPhonenumType);
		}else{
			progressBarDialog.dismiss();
			Util.showToast(this, "请输入正确的原手机号码");
		}
	}
	
	public void setVerifyCode(String verifyCode){
		progressBarDialog.dismiss();
		String warningContent = "获取验证码失败，请重新获取！";
		if(!verifyCode.equals("")){
			this.verifyCode = verifyCode;
			warningContent = "发送验证码成功";
		}
		Util.showToast(this, warningContent);
	}
	
	public void setActiviteResult(boolean isSuccess){
		progressBarDialog.dismiss();
		if(isSuccess){
			Util.showToast(this, "激活成功");
			GlobalVariables.bodingUser.setActivated_state(true);
			Util.returnToPreviousPage(VerifyPhonenumActivity.this, IntentRequestCode.VERIFY_PHONENUM);
		}else{
			Util.showToast(this, "激活失败");
		}
	}
}
