package com.boding.view.layout;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.view.dialog.SeatChangeBackDialog;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderFlightInfoILayout extends LinearLayout{
	private Context context;
	
	private TextView fromTextView;
	private TextView toTextView;
	private TextView dateTextView;
	private ImageView fromCompanyLogoImageView;
	private TextView fromCompanyTextView;
	private TextView fromPlaneCodeTextView;
	private TextView fromPlanSizeTextView;
	private TextView fromFlightClassTextView;
	private TextView fromPlaneTypeTextView;
	private TextView fromDateDayTextView;
	private TextView fromDateTimeTextView;
	private TextView fromTerminalTextView;
	private TextView arrivalDateDayTextView;
	private TextView arrivalDateTimeTextView;
	private TextView arrivalTerminalTextView;
	private TextView fromEstimateTimeTextView;
	private TextView transitCityTextView;
	private TextView transitEstimateStayTimeTextView;
	private ImageView toCompanyLogoImageView;
	private TextView toCompanyTextView;
	private TextView toPlaneCodeTextView;
	private TextView toPlaneSizeTextView;
	private TextView toFligthClassTextView;
	private TextView toPlanTypeTextView;
	private TextView departureDateDayTextView;
	private TextView departureDteTimeTextView;
	private TextView departureTerminalTextView;
	private TextView toDateDayTextView;
	private TextView toDateTimeTextView;
	private TextView toTerminalTextView;
	private TextView toEstimateTimeTextView;
	private LinearLayout seatBackChangeInfoLinearLayout; 
	
	private TextView ticketPricePriceTextView;
	private TextView ticketTaxPriceTextView;
	
	// if init complete
	private boolean flag = false;
	public OrderFlightInfoILayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public OrderFlightInfoILayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		while(!flag){
			initView();
			flag = true;
		}
	}
	
	private void initView(){
		this.setOrientation(VERTICAL); //水平布局  
		this.setMinimumWidth(GlobalVariables.Screen_Width);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_order_flightinfo_i, null);
		LinearLayout orderFlightInfoILinearLayout = (LinearLayout) view.findViewById(R.id.order_flightinfo_i_linearLayout);
		orderFlightInfoILinearLayout.setMinimumWidth(GlobalVariables.Screen_Width);
		
		
		fromTextView = (TextView)view.findViewById(R.id.flightinfo_i_from_textView);
		toTextView = (TextView)view.findViewById(R.id.flightinfo_i_to_textView);
		dateTextView = (TextView)view.findViewById(R.id.flightinfo_i_date_textView);
		fromCompanyLogoImageView = (ImageView)view.findViewById(R.id.flightinfo_i_fromCompanyLogo_imageView);
		fromCompanyTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromCompany_textView);
		fromPlaneCodeTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromPlanecode_textView);
		fromPlanSizeTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromPlanesize_textView);
		fromFlightClassTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromFlightclass_textView);
		fromPlaneTypeTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromPlaneType_textView);
		fromDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromDateDay_textView);
		fromDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromDateTime_textView);
		fromTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromTerminal_textView);
		arrivalDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_i_arrivalDateDay_textView);
		arrivalDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_arrivalDateTime_textView);
		arrivalTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_i_arrivalTerminal_textView);
		fromEstimateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_fromEstimateTime_textView);
		transitCityTextView = (TextView)view.findViewById(R.id.flightinfo_i_tansitCity_textView);
		transitEstimateStayTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_tansitEstimateStayTime_textView);
		toCompanyLogoImageView = (ImageView)view.findViewById(R.id.flightinfo_i_toCompanyLogo_imageView);
		toCompanyTextView = (TextView)view.findViewById(R.id.flightinfo_i_toCompany_textView);
		toPlaneCodeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toPlanecode_textView);
		toPlaneSizeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toPlanesize_textView);
		toFligthClassTextView = (TextView)view.findViewById(R.id.flightinfo_i_toFlightclass_textView);
		toPlanTypeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toPlaneType_textView);
		departureDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_i_departureDateDay_textView);
		departureDteTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_departureDateTime_textView);
		departureTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_i_departureTerminal_textView);
		toDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_i_toDateDay_textView);
		toDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toDateTime_textView);
		toTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_i_toTerminal_textView);
		toEstimateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toEstimateTime_textView);
		seatBackChangeInfoLinearLayout = (LinearLayout)view.findViewById(R.id.flightinfo_i_seatBackChangeInfo_linearLayout);
		
		ticketPricePriceTextView = (TextView)view.findViewById(R.id.orderform_ticketPrice_price_textView);
		ticketTaxPriceTextView = (TextView)view.findViewById(R.id.orderform_ticketTax_price_textView);
		
		this.addView(view);
		addListeners();
	}
	
	private void addListeners(){
		seatBackChangeInfoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SeatChangeBackDialog seatChangeBackDialog = new SeatChangeBackDialog(OrderFlightInfoILayout.this.context);
				seatChangeBackDialog.show();
			}
        });
	}
}
 