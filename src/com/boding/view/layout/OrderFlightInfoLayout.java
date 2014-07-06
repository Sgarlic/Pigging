package com.boding.view.layout;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.model.domestic.Flight;
import com.boding.util.DateUtil;
import com.boding.util.Util;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.SeatChangeBackDialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderFlightInfoLayout extends LinearLayout{
	private Context context;
	
	private boolean hasStopover = false;
	
	private TextView flyFromTextView;
	private TextView flyToTextView;
	private TextView dateTextView;
	private ImageView companyLogoImageView;
	private TextView companyTextView;
	private TextView planeCodeTextView;
	private TextView fromDateDayTextView;
	private TextView fromDateTimeTextView;
	private TextView fromTerminalTextView;
	private TextView estimateTimeTextView;
	private TextView toDateDayTextView;
	private TextView toDateTimeTextView;
	private TextView toTerminalTextView;
	private LinearLayout seatBackChangeInfoLinearLayout;
	private TextView changeSeatTextView;
	private LinearLayout stopOverLinearLayout;
	
	private Flight flightLine;
	
	// if init complete
	private boolean flag = false;
	public OrderFlightInfoLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public OrderFlightInfoLayout(Context context, Flight flightLine) {
		super(context);
		this.context = context;
		this.flightLine = flightLine;
	}
	
	public OrderFlightInfoLayout(Context context, AttributeSet attrs) {
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
	
	private void initView(){
		this.setOrientation(VERTICAL); //水平布局  
		this.setMinimumWidth(GlobalVariables.Screen_Width);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_order_flightinfo, null);
		LinearLayout orderFlightInfoLinearLayout = (LinearLayout) view.findViewById(R.id.order_flightinfo_linearLayout);
		orderFlightInfoLinearLayout.setMinimumWidth(GlobalVariables.Screen_Width);
		
		
		flyFromTextView = (TextView)view.findViewById(R.id.flightinfo_from_textView);
		flyToTextView = (TextView)view.findViewById(R.id.flightinfo_to_textView);
		dateTextView = (TextView)view.findViewById(R.id.flightinfo_date_textView);
		companyLogoImageView = (ImageView)view.findViewById(R.id.flightinfo_companyLogo_imageView);
		companyTextView = (TextView)view.findViewById(R.id.flightinfo_company_textView);
		planeCodeTextView = (TextView)view.findViewById(R.id.flightinfo_planeCode_textView);
		fromDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_fromDateDay_textView);
		fromDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_fromDateTime_textView);
		fromTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_fromTerminal_textView);
		estimateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_estimateTime_textView);
		toDateDayTextView = (TextView)view.findViewById(R.id.flightinfo_toDateDay_textView);
		toDateTimeTextView = (TextView)view.findViewById(R.id.flightinfo_toDateTime_textView);
		toTerminalTextView = (TextView)view.findViewById(R.id.flightinfo_toTerminal_textView);
		seatBackChangeInfoLinearLayout = (LinearLayout)view.findViewById(R.id.flightinfo_seatBackChangeInfo_linearLayout);
		changeSeatTextView = (TextView)view.findViewById(R.id.flightinfo_changeClass_textView);
		stopOverLinearLayout = (LinearLayout) view.findViewById(R.id.flightinfo_stopover_linearLayout);
		
		
		this.addView(view);
		
		addListeners();
	}
	
	private void setViewContent(){
		flyFromTextView.setText(flightLine.getFlyFromCity());
		flyToTextView.setText(flightLine.getFlyToCity());
		dateTextView.setText(flightLine.getDptDate());
		companyLogoImageView.setImageBitmap(Util.getFlightCompanyLogo(context, 
				flightLine.getCarrier()));
		companyTextView.setText(flightLine.getCarrierName());
		planeCodeTextView.setText(flightLine.getCarrier()+flightLine.getFlightNum());
		fromDateDayTextView.setText(flightLine.getDptDate());
		fromDateTimeTextView.setText(DateUtil.getFormatedTime(flightLine.getDptTime()));
		fromTerminalTextView.setText(flightLine.getDptAirportName()+flightLine.getDptTerminal());
		estimateTimeTextView.setText(DateUtil.getFormatedDuration(flightLine.getDuration()));
		toDateDayTextView.setText(flightLine.getArrDate());
		toDateTimeTextView.setText(DateUtil.getFormatedTime(flightLine.getArrTime()));
		toTerminalTextView.setText(flightLine.getArrAirportName()+flightLine.getArrTerminal());
		
		if(!hasStopover)
			stopOverLinearLayout.setVisibility(View.INVISIBLE);
	}
	
	
	private void addListeners(){
		seatBackChangeInfoLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				SeatChangeBackDialog seatChangeBackDialog = new SeatChangeBackDialog(OrderFlightInfoLayout.this.context,R.style.Custom_Dialog_Theme);
				seatChangeBackDialog.show();
			}
        });
		
		changeSeatTextView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				List<String> classList = new ArrayList<String>();
				classList.add("经济舱");
				classList.add("公务舱/头等舱");
				
				SelectionDialog classSelectionDialog = new SelectionDialog(OrderFlightInfoLayout.this.context,
						R.style.Custom_Dialog_Theme, "选择舱位",classList);
				classSelectionDialog.show();
			}
        });
	}
	
}
 