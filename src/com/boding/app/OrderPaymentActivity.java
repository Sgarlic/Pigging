package com.boding.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.PaymentMethod;
import com.boding.task.OrderTask;
import com.boding.util.Util;
import com.boding.view.dialog.NetworkUnavaiableDialog;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.TwoOptionsDialog;
import com.boding.view.dialog.WarningDialog;
import com.hp.hpl.sparta.xpath.PositionEqualsExpr;

import com.boding.pay.alipay.Payment;

public class OrderPaymentActivity extends BodingBaseActivity {
	private TextView titleTextView;
	private LinearLayout completeLinearLayout;
	private TextView fromToTextView;
	private TextView singleorRoundWayTextView;
	private TextView orderTotalTextView;
	private LinearLayout bodingDeductionLinearLayout;
	private TextView bodingDeductionTextView;
	private CheckBox useBodingDeductionCheckBox;
	private LinearLayout paybyCreditCardLinearLayout;
	private LinearLayout paybySavingCardLinearLayout;
	private LinearLayout paybyAlipayLinearLayout;
	private LinearLayout paybyTenpayLinearLayout;
	
	private boolean isPaying = false;// if is order created
	
	private String flightInfo;
	private String passengerInfo;
	private String contactInfo;
	private String receiveInfo;
	private boolean internalFlag;
	private boolean isRoundWay;
	private String flyFromToCity;
	private int totalPrice;
	
	private TwoOptionsDialog twoOptionsDialog;
	
	private boolean isOrderCreated;
	private PaymentMethod selecPaymentMethod = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressBarDialog = new ProgressBarDialog(this);
		networkUnavaiableDialog = new NetworkUnavaiableDialog (this);
		twoOptionsDialog = new TwoOptionsDialog(this);
		twoOptionsDialog.setContent("����֧����δ��ɣ����ؽ�����֧�����Ժ���ڡ�������ѯ���м������֧��");
		twoOptionsDialog.setLeftOption("���غ����б�");
		twoOptionsDialog.setRightOption("����֧��");
		setContentView(R.layout.activity_orderpayment);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	flightInfo = arguments.getString(IntentExtraAttribute.FLIGHT_INFO_EXTRA);
        	passengerInfo = arguments.getString(IntentExtraAttribute.PASSENGER_INFO_EXTRA);
        	contactInfo = arguments.getString(IntentExtraAttribute.CONTACT_INFO_EXTRA);
        	receiveInfo = arguments.getString(IntentExtraAttribute.RECEIVE_INFO_EXTRA);
        	internalFlag = arguments.getBoolean(IntentExtraAttribute.INTERNAL_FLAG_EXTRA);
        	flyFromToCity = arguments.getString(IntentExtraAttribute.FLY_FROM_TO_EXTRA);
        	isRoundWay = arguments.getBoolean(IntentExtraAttribute.IS_ROUNDWAY_ORDER);
        	totalPrice = arguments.getInt(IntentExtraAttribute.ORDER_TOTAL_EXTRA);
        }
        
		initView();
		setTitle();
		setViewContent();
	}
	
	private void setViewContent(){
		fromToTextView.setText(flyFromToCity);
		if(isRoundWay){
			singleorRoundWayTextView.setText("����");
		}else{
			singleorRoundWayTextView.setText("����");
		}
		orderTotalTextView.setText(totalPrice+"");
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				returnToPreviousPage();
			}
			
		});
		
		titleTextView = (TextView) findViewById(R.id.orderpayment_title_textView);
		completeLinearLayout = (LinearLayout) findViewById(R.id.adddeliveryaddr_complete_linearLayout);
		fromToTextView = (TextView) findViewById(R.id.orderpayment_fromto_textView);
		singleorRoundWayTextView = (TextView) findViewById(R.id.orderpayment_singleorroundway_textView);
		orderTotalTextView = (TextView) findViewById(R.id.orderpayment_orderTotal_textView);
		bodingDeductionLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_bodingDeduction_linearLayout);
		bodingDeductionTextView = (TextView) findViewById(R.id.orderpayment_bodingDeduction_textView);
		useBodingDeductionCheckBox = (CheckBox) findViewById(R.id.orderpayment_usePodingDeduction__checkBox);
		paybyCreditCardLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_paybyCreditCard_linearLayout);
		paybySavingCardLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_paybySavingCard_linearLayout);
		paybyAlipayLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_paybyAlipay_linearLayout);
		paybyTenpayLinearLayout = (LinearLayout) findViewById(R.id.orderpayment_paybyTenpay_linearLayout);
		addListeners();
	}
	
	private void setTitle(){
		if(isPaying){
			titleTextView.setText("����֧��");
		}else{
			titleTextView.setText("ѡ��֧����ʽ");
		}
	}
	
	private void addListeners(){
		paybyCreditCardLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				warningDialog.setContent("Ŀǰֻ֧��֧��������");
				warningDialog.show();
//				openBankcardSelectionActivity(true);
			}
		});
		paybySavingCardLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				warningDialog.setContent("Ŀǰֻ֧��֧��������");
				warningDialog.show();
//				openBankcardSelectionActivity(false);
			}
		});
		paybyAlipayLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				selecPaymentMethod = PaymentMethod.Alipay;
				if(!isOrderCreated)
					createOrder();
				else{
					System.out.println("FlightInfo: " + flightInfo);
					System.out.println("CityInfo: " + flyFromToCity);
					System.out.println("PriceInfo: " + totalPrice);
					
					String[] infos = flightInfo.split("\\|");
					
					String subject = "��Ʊ���� " + flyFromToCity + "  �������ڣ� " + infos[9];
					System.out.println(subject);
					String body = flightInfo + " "+ flyFromToCity + " "+passengerInfo;
					
					new Payment(OrderPaymentActivity.this).doPay(Constants.AliPay_Subject, "��Ʊ����", "0.01");
				}
			}
		});
		paybyTenpayLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				warningDialog.setContent("Ŀǰֻ֧��֧��������");
				warningDialog.show();
//				selecPaymentMethod = PaymentMethod.WX;
//				if(!isOrderCreated)
//					createOrder();
				
			}
		});
		bodingDeductionLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(useBodingDeductionCheckBox.isChecked())
					useBodingDeductionCheckBox.setChecked(false);
				else
					useBodingDeductionCheckBox.setChecked(true);
			}
		});
		twoOptionsDialog.setOnOptionSelectedListener(new TwoOptionsDialog.OnOptionSelectedListener() {
			@Override
			public void OnItemClick(int option) {
				if(option == 0){
					Intent intent=new Intent();
					intent.putExtra(IntentExtraAttribute.IS_ORDER_CREATED, true);
					setResult(IntentRequestCode.ORDER_PAYEMNT.getRequestCode(), intent);
					finish();
				}
			}
		});
	}
	
	private void openBankcardSelectionActivity(boolean isCreditcardSelection){
		Bundle bundle  = new Bundle();
		bundle.putBoolean(Constants.IS_CREDITCARD_SELECTION, isCreditcardSelection);
		Intent intent = new Intent();
		intent.setClass(OrderPaymentActivity.this, BankCardSelectActivity.class);
//		GlobalVariables.isFlyToCitySelection = isFlyToCitySelection;
		intent.putExtras(bundle);
		startActivityForResult(intent,IntentRequestCode.BANKCARD_SELECTION.getRequestCode());
	}
	
	private void createOrder(){
		if(!Util.isNetworkAvailable(this)){
			networkUnavaiableDialog.show();
			return;
		}
		progressBarDialog.show();
		(new OrderTask(OrderPaymentActivity.this,HTTPAction.CREATE_ORDER_DOMESTIC))
		.execute(flightInfo,passengerInfo,contactInfo,receiveInfo,
				internalFlag, selecPaymentMethod);
//		if(isDomestic){
//			(new OrderTask(OrderFormActivity.this,HTTPAction.CREATE_ORDER_DOMESTIC))
//			.execute(getFlightInfoStringDomestic(),"1|������|NI|350783199011156575|0|0",
//				"������|15689654125","");
//		}
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
        	returnToPreviousPage();
        }  
        return false;  
    }  
	
	public void setCreateOrderResult(String result){
		progressBarDialog.dismiss();
		if(result.equals("")){
			WarningDialog dialog = new WarningDialog(this);
			dialog.setContent("����д��ȷ����Ϣ");
			dialog.show();
		}else{
			Util.showToast(this, "���������ɹ�");
			isOrderCreated = true;
			
			
			if(selecPaymentMethod != null){
				if(selecPaymentMethod == PaymentMethod.Alipay){
//					System.out.println("FlightInfo: " + flightInfo);
//					System.out.println("CityInfo: " + flyFromToCity);
//					System.out.println("PriceInfo: " + totalPrice);
//					
//					String[] infos = flightInfo.split("\\|");
//					
//					String subject = "��Ʊ���� " + flyFromToCity + "  �������ڣ� " + infos[9];
//					System.out.println(subject);
//					String body = flightInfo + " "+ flyFromToCity + " "+passengerInfo;
					
					new Payment(OrderPaymentActivity.this).doPay(Constants.AliPay_Subject, result, totalPrice + "");
				}
			}
		}
	}
	
	private void returnToPreviousPage(){
		if(isOrderCreated){
			twoOptionsDialog.show();
		}else{
			Util.returnToPreviousPage(OrderPaymentActivity.this, IntentRequestCode.ORDER_PAYEMNT);
		}
	}
}
