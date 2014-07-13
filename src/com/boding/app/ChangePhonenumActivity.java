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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChangePhonenumActivity extends Activity {
	private EditText currentPhonenumEditText;
	private EditText newPhonenumEditText;
	private EditText verificationNumEditText;
	private LinearLayout sendVerificationNumLinearLayout;
	private LinearLayout confirmLinearLayout;
	
	private ProgressBarDialog progressBarDialog;
	private WarningDialog warningDialog;
	
	private String verifyCode = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phonenum);
	
		progressBarDialog = new ProgressBarDialog(this);
		warningDialog = new WarningDialog(this);
//		selectedIDType = IdentityType.values()[0];
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
				Util.returnToPreviousPage(ChangePhonenumActivity.this, IntentRequestCode.CHANGE_PHONENUM);
			}
			
		});

		currentPhonenumEditText = (EditText) findViewById(R.id.changephonenum_input_currentPhonenum_editText);
		newPhonenumEditText = (EditText) findViewById(R.id.changephonenum_input_newPhoneNum_editText);
		verificationNumEditText = (EditText) findViewById(R.id.changephonenum_input_verificationNum_editText);
		
		sendVerificationNumLinearLayout = (LinearLayout) findViewById(R.id.changephonenum_sendVerificationNum_linearLayout);
		confirmLinearLayout = (LinearLayout) findViewById(R.id.changephonenum_confirm_linearLayout);

		addListeners();
	}
	private void addListeners(){
		sendVerificationNumLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String currentPhoneNum = currentPhonenumEditText.getText().toString();
				String newPhoneNum = newPhonenumEditText.getText().toString();
				
				if(currentPhoneNum.equals("") || !RegularExpressionsUtil.checkMobile(currentPhoneNum)
					|| newPhoneNum.equals("") || !RegularExpressionsUtil.checkMobile(newPhoneNum)){
					warningDialog.setContent("��������ȷ���ֻ���");
					warningDialog.show();
					return;
				}
				progressBarDialog.show();
				(new BodingUserTask(ChangePhonenumActivity.this, HTTPAction.VERIFY_PHONENUMBER))
					.execute(newPhoneNum,"3");
			}
		});
		
		confirmLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String currentPhoneNum = currentPhonenumEditText.getText().toString();
				String newPhoneNum = newPhonenumEditText.getText().toString();
				
				if(currentPhoneNum.equals("") || !RegularExpressionsUtil.checkMobile(currentPhoneNum)
					|| newPhoneNum.equals("") || !RegularExpressionsUtil.checkMobile(newPhoneNum)){
					warningDialog.setContent("��������ȷ���ֻ���");
					warningDialog.show();
					return;
				}

				if(verifyCode.equals("")){
					warningDialog.setContent("���ȷ�����֤��");
					warningDialog.show();
					return;
				}
				
				String verifyNumber = verificationNumEditText.getText().toString();
				if(verifyNumber.equals("")|| !verifyNumber.equals(verifyCode)){
					warningDialog.setContent("��������ȷ����֤��");
					warningDialog.show();
					return;
				}
				
				progressBarDialog.show();
				(new BodingUserTask(ChangePhonenumActivity.this, HTTPAction.VERIFY_OLD_PHONENUM_CHANGEPHONEACTIVITY))
					.execute(currentPhoneNum);
			}
		});
		
		newPhonenumEditText.addTextChangedListener(new TextWatcher() {
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
	}
	
	public void setVerifyCode(String verifyCode){
		progressBarDialog.dismiss();
		String warningContent = "��ȡ��֤��ʧ�ܣ������»�ȡ��";
		if(!verifyCode.equals("")){
			this.verifyCode = verifyCode;
			warningContent = "������֤��ɹ�";
		}
		Util.showToast(this, warningContent);
	}
	
	public void verifyOldPhoneNumResult(boolean isSuccess){
		if(isSuccess){
			(new BodingUserTask(this, HTTPAction.BIND_NEW_PHONENUM))
			.execute(newPhonenumEditText.getText().toString());
		}else{
			progressBarDialog.dismiss();
			Util.showToast(this, "��������ȷ��ԭ�ֻ�����");
		}
	}
	
	public void bindNewPhoneNumResult(boolean isSuccess){
		progressBarDialog.dismiss();
		if(isSuccess){
			Util.showToast(this, "���ֻ��ųɹ�");
			Util.returnToPreviousPage(this, IntentRequestCode.CHANGE_PHONENUM);
		}else{
			Util.showToast(this, "���ֻ���ʧ�ܣ����ֻ����ѱ�ʹ�ã�");
		}
	}
}
