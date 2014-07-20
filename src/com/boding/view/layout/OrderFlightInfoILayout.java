package com.boding.view.layout;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.model.FlightLine;
import com.boding.model.Segment;
import com.boding.util.DateUtil;
import com.boding.util.Util;
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
	private TextView fromPlaneSizeTextView;
	private TextView fromFlightClassTextView;
	private TextView fromPlaneTypeTextView;
	private TextView fromDateDayTextView;
	private TextView fromDateTimeTextView;
	private TextView fromTerminalTextView;
	private TextView arrivalDateDayTextView;
	private TextView arrivalDateTimeTextView;
	private TextView arrivalTerminalTextView;
	private TextView fromEstimateTimeTextView;
	
	
	private LinearLayout seatBackChangeInfoLinearLayout; 
	
	private TextView ticketPricePriceTextView;
	private TextView ticketTaxPriceTextView;
	
	private FlightLine flightLine;
	private boolean isReturn = false;
	
	private LinearLayout fromInfoLinearLayout;
	private LinearLayout transitInfoLinearLayout;
	
	private LinearLayout segmentInfoLinearLayout;
	
	// if init complete
	private boolean flag = false;
	public OrderFlightInfoILayout(Context context, FlightLine flightLine, boolean isReturn) {
		super(context);
		this.context = context;
		this.flightLine = flightLine;
		this.isReturn = isReturn;
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
			setViewContent();
			flag = true;
		}
	}
	
	
	private void setViewContent(){
		if(isReturn){
			fromTextView.setText(GlobalVariables.To_City);
			toTextView.setText(GlobalVariables.From_City);
		}else{
			fromTextView.setText(GlobalVariables.From_City);
			toTextView.setText(GlobalVariables.To_City);
		}
		
		dateTextView.setText(flightLine.getLeaveDate());
		
		int segmentSize = flightLine.getDeparture().getSegments().size();
		for(int i = 0; i<segmentSize - 1; i ++){
			OrderFlightInfoISegmentLayout orderFlightInfoISegmentLinearLayout = new OrderFlightInfoISegmentLayout(context,
					flightLine.getDeparture().getSegments().get(i));
			segmentInfoLinearLayout.addView(orderFlightInfoISegmentLinearLayout);
		}

		Segment segment = flightLine.getDeparture().getSegments().get(segmentSize - 1);
		fromCompanyLogoImageView.setImageBitmap(Util.getFlightCompanyLogo(context, segment.getCarrier()));
		fromCompanyTextView.setText(segment.getCarname());
		fromPlaneCodeTextView.setText(segment.getCarrier() + segment.getNum());
		fromPlaneSizeTextView.setText(segment.getPlane());
//		fromFlightClassTextView.setText(segment.getFclasslist().g);
		fromPlaneTypeTextView.setText(segment.getPlane());
		fromDateDayTextView.setText(segment.getLeadate());
		fromDateTimeTextView.setText(DateUtil.getFormatedTime(segment.getLeatime()));
		fromTerminalTextView.setText(segment.getLeaTerminal());
		arrivalDateDayTextView.setText(segment.getArrdate());
		arrivalDateTimeTextView.setText(DateUtil.getFormatedTime(segment.getArrtime()));
		arrivalTerminalTextView.setText(segment.getArrTerminal());
		fromEstimateTimeTextView.setText(segment.getEstimatedTime());
		ticketPricePriceTextView.setText(flightLine.getFlightPrice());
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

		
		fromCompanyLogoImageView = (ImageView)view.findViewById(R.id.flightinfo_i_toCompanyLogo_imageView);
		fromCompanyTextView = (TextView)view.findViewById(R.id.flightinfo_i_toCompany_textView);
		fromPlaneCodeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toPlanecode_textView);
		fromPlaneSizeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toPlanesize_textView);
		fromFlightClassTextView = (TextView)view.findViewById(R.id.flightinfo_i_toFlightclass_textView);
		fromPlaneTypeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toPlaneType_textView);
		fromDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_i_departureDateDay_textView);
		fromDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_departureDateTime_textView);
		fromTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_i_departureTerminal_textView);
		arrivalDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_i_toDateDay_textView);
		arrivalDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toDateTime_textView);
		arrivalTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_i_toTerminal_textView);
		fromEstimateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_i_toEstimateTime_textView);
		seatBackChangeInfoLinearLayout = (LinearLayout)view.findViewById(R.id.flightinfo_i_seatBackChangeInfo_linearLayout);
		
		ticketPricePriceTextView = (TextView)view.findViewById(R.id.orderform_ticketPrice_price_textView);
		ticketTaxPriceTextView = (TextView)view.findViewById(R.id.orderform_ticketTax_price_textView);
		
		segmentInfoLinearLayout = (LinearLayout) view.findViewById(R.id.flightinfo_i_segmentinfo_linearLayout);
		
		this.addView(view);
		addListeners();
	}
	
	private void addListeners(){
		seatBackChangeInfoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SeatChangeBackDialog seatChangeBackDialog = new SeatChangeBackDialog(OrderFlightInfoILayout.this.context,"");
				seatChangeBackDialog.show();
			}
        });
	}
}
 