package com.boding.app;


import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.task.BodingUserTask;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyPhonenumActivity extends Activity {
	private TextView phonenumTextView;
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
		setViewContent();
	}
	
	public void setVerifyPhoneNumberResult(boolean isSuccess){
		
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(VerifyPhonenumActivity.this, IntentRequestCode.VERIFY_PHONENUM);
			}
			
		});

		phonenumTextView = (TextView) findViewById(R.id.verifyphonenum_phoneNum_textView);
		verificationNumEditText = (EditText) findViewById(R.id.verifyphonenum_input_verificationNum_editText);
		
		sendVerificationNumLinearLayout = (LinearLayout) findViewById(R.id.verifyphonenum_sendVerificationNum_linearLayout);
		confirmLinearLayout = (LinearLayout) findViewById(R.id.verifyphonenum_confirm_linearLayout);

		addListeners();
	}
	
	private void setViewContent(){
		phonenumTextView.setText(GlobalVariables.bodingUser.getMobile());
	}
	private void addListeners(){
		sendVerificationNumLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				progressBarDialog.show();
				(new BodingUserTask(VerifyPhonenumActivity.this, HTTPAction.VERIFY_PHONENUMBER)).execute(verifyPhonenumType);
			}
		});
		
		confirmLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
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
				Util.showToast(VerifyPhonenumActivity.this, "正在激活");
				progressBarDialog.show();
				(new BodingUserTask(VerifyPhonenumActivity.this, HTTPAction.ACTIVIATE)).execute();
				System.out.println("验证成功");
				GlobalVariables.bodingUser.setActivated_state(true);
				Util.returnToPreviousPage(VerifyPhonenumActivity.this, IntentRequestCode.VERIFY_PHONENUM);
			}
		});
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
}
