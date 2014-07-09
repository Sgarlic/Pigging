package com.boding.app;


import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.PaymentMethod;
import com.boding.task.OrderTask;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderPaymentActivity extends Activity {
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
	
	private ProgressBarDialog progressBarDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressBarDialog = new ProgressBarDialog(this, Constants.DIALOG_STYLE);
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
			singleorRoundWayTextView.setText("往返");
		}else{
			singleorRoundWayTextView.setText("单程");
		}
		orderTotalTextView.setText(totalPrice+"");
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(OrderPaymentActivity.this, IntentRequestCode.ORDER_PAYEMNT);
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
			titleTextView.setText("订单支付");
		}else{
			titleTextView.setText("选择支付方式");
		}
	}
	
	private void addListeners(){
		paybyCreditCardLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openBankcardSelectionActivity(true);
			}
		});
		paybySavingCardLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openBankcardSelectionActivity(false);
			}
		});
		paybyAlipayLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				createOrder(PaymentMethod.Alipay);
			}
		});
		paybyTenpayLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				createOrder(PaymentMethod.WX);
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
	
	private void createOrder(PaymentMethod paymentMethod){
		progressBarDialog.show();
		(new OrderTask(OrderPaymentActivity.this,HTTPAction.CREATE_ORDER_DOMESTIC))
		.execute(flightInfo,passengerInfo,contactInfo,receiveInfo,
				internalFlag, paymentMethod);
//		if(isDomestic){
//			(new OrderTask(OrderFormActivity.this,HTTPAction.CREATE_ORDER_DOMESTIC))
//			.execute(getFlightInfoStringDomestic(),"1|饶礼仁|NI|350783199011156575|0|0",
//				"饶礼仁|15689654125","");
//		}
	}
	
	public void setCreateOrderResult(String result){
		progressBarDialog.dismiss();
		if(result.equals("1")){
			System.out.println("ordercreated     pending audit");
//			Intent intent = new Intent();
//			intent.setClass(OrderPaymentActivity.this, OrderPaymentActivity.class);
//			startActivityForResult(intent, IntentRequestCode.ORDER_PAYEMNT.getRequestCode());
//			Order order = new Order();
//			order.setOrderStatus(OrderStatus.PENDING_AUDIT.getOrderStatusCode());
			
//			Intent intent = new Intent();
//			intent.setClass(OrderFormActivity.this, OrderDetailActivity.class);
//			startActivityForResult(intent,IntentRequestCode.ORDER_DETAIL.getRequestCode());
		}else if(result.equals("0")){
			System.out.println("ordercreated     OKKKKKKKKKKKKKKKKK");
		}else{
			WarningDialog dialog = new WarningDialog(this, Constants.DIALOG_STYLE);
			dialog.setContent("请填写正确的信息");
			dialog.show();
		}
	}
}
