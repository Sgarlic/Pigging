package com.boding.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.model.BoardingPeople;
import com.boding.util.Util;
import com.boding.view.calendar.DateSelectCalendarView;
import com.boding.view.calendar.DateSelectCalendarView.OnItemClickListener;
import com.boding.view.layout.CalendarLayout;
import com.boding.view.layout.OrderFlightInfoILayout;
import com.boding.view.layout.OrderFlightInfoLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class OrderFormActivity extends Activity {
	private boolean isReturnDateSelection = false;
	
	private boolean isInternalJourney = false;
	private boolean isRoundTrip = false;
	
	private LinearLayout flightInfoLinearLayout;
	private TextView ticketPriceTextView;
	private TextView ticketPricePriceTextView;
	private LinearLayout planeBuildingLinearLayout; 
	private TextView planeBuildingPriceTextView;
	private LinearLayout fuelOilLinearLayout; 
	private TextView fuelOilPriceTextView;
	private LinearLayout ticketTaxLinearLayout; 
	private TextView ticketTaxPriceTextView;
	private TextView boardingPeopleAmountTextView;
	private ListView boardingPeopleListView;
	private LinearLayout addBoardingPeopleLinearLayout;
	private TextView phoneNumberTextView;
	private LinearLayout insuranceLinearLayout;
	private TextView insurancePriceTextView;
	private TextView insuranceAmountTextView;
	private LinearLayout journeySheetLinearLayout; 
	private TextView journeySheetTextView;
	private TextView totalPriceTextView;
	private LinearLayout nextStepLinearLayout; 
	
	private List<BoardingPeople> peopleList;
	private BoardingPeopleAdapter peopleAdapter;
	
//	private PopupWindow insuranceAmountSelector;
//	private ListView insuranceSelectorListView;
//	private int insurancePopupParentWidth;

	// if init complete
	private boolean flag = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_form);
//		Bundle arguments = getIntent().getExtras();
//        if(arguments != null)
//        	isReturnDateSelection = arguments.getBoolean(Constants.IS_RETURN_DATE_SELECTION);
//        
//		initView();
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
		peopleList = new ArrayList<BoardingPeople>();
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(OrderFormActivity.this, IntentRequestCode.START_DATE_SELECTION);
			}
			
		});
		flightInfoLinearLayout = (LinearLayout) findViewById(R.id.orderform_flightInfo_linearLayout);
		
		OrderFlightInfoLayout orderFlightInfoLinearLayout = new OrderFlightInfoLayout(this);
		flightInfoLinearLayout.addView(orderFlightInfoLinearLayout);
//		OrderFlightInfoLayout orderFlightInfoReturnLinearLayout = new OrderFlightInfoLayout(this);
//		flightInfoLinearLayout.addView(orderFlightInfoReturnLinearLayout);
//		
//		OrderFlightInfoILayout orderFlightInfoILinearLayout = new OrderFlightInfoILayout(this);
//		OrderFlightInfoILayout orderFlightInfoIReturnLinearLayout = new OrderFlightInfoILayout(this);
//		flightInfoLinearLayout.addView(orderFlightInfoILinearLayout);
//		flightInfoLinearLayout.addView(orderFlightInfoIReturnLinearLayout);
		
		ticketPriceTextView = (TextView) findViewById(R.id.orderform_ticketPrice_textView);
		ticketPricePriceTextView = (TextView) findViewById(R.id.orderform_ticketPrice_price_textView);
		planeBuildingLinearLayout = (LinearLayout) findViewById(R.id.orderform_planeBuilding_linearLayout); 
		planeBuildingPriceTextView = (TextView) findViewById(R.id.orderform_planeBuilding_price_textView);
		fuelOilLinearLayout = (LinearLayout) findViewById(R.id.orderform_fuelOil_linearLayout); 
		fuelOilPriceTextView = (TextView) findViewById(R.id.orderform_fuelOil_price_textView);
		ticketTaxLinearLayout = (LinearLayout) findViewById(R.id.orderform_ticketTax_linearLayout); 
		ticketTaxPriceTextView = (TextView) findViewById(R.id.orderform_ticketTax_price_textView);
		boardingPeopleAmountTextView = (TextView) findViewById(R.id.orderform_boardingPeopleAmount_textView);
		boardingPeopleListView = (ListView) findViewById(R.id.orderform_boardingPeople_listView);
		addBoardingPeopleLinearLayout = (LinearLayout) findViewById(R.id.orderform_addBoardingPeople_linearLayout);
		phoneNumberTextView = (TextView) findViewById(R.id.orderform_phoneNumber_textView);
		insuranceLinearLayout = (LinearLayout) findViewById(R.id.orderform_insurance_linearLayout);
		insurancePriceTextView = (TextView) findViewById(R.id.orderform_insurance_price_textView);
		insuranceAmountTextView = (TextView) findViewById(R.id.orderform_insurance_amount_textView);
		journeySheetLinearLayout = (LinearLayout) findViewById(R.id.orderform_journeySheet_linearLayout); 
		journeySheetTextView = (TextView) findViewById(R.id.orderform_journeySheet_textView);
		totalPriceTextView = (TextView) findViewById(R.id.orderform_totalPrice_textView);
		nextStepLinearLayout = (LinearLayout) findViewById(R.id.orderform_nextStep_linearLayout); 
		
		if(isInternalJourney){
			planeBuildingLinearLayout.setVisibility(View.GONE);
			fuelOilLinearLayout.setVisibility(View.GONE);
		}else{
			ticketTaxLinearLayout.setVisibility(View.GONE);
		}
		
		if(isRoundTrip)
			ticketPriceTextView.setText("往返总价");
		
		peopleAdapter = new BoardingPeopleAdapter(this, peopleList);
		boardingPeopleListView.setAdapter(peopleAdapter);
		addListeners();
		
//		insurancePopupParentWidth = insuranceLinearLayout.getWidth();
    }
	
	private static int count = 0;
	
	private void addListeners(){
		DataSetObserver observer=new DataSetObserver(){  
	        public void onChanged() {  
	            Util.setListViewHeightBasedOnChildren(boardingPeopleListView);
	            boardingPeopleAmountTextView.setText(String.valueOf(peopleList.size()));
	        }  
	    };
	    peopleAdapter.registerDataSetObserver(observer);
		
		addBoardingPeopleLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(OrderFormActivity.this, ChooseBoardingPeopleActivity.class);
				startActivityForResult(intent,IntentRequestCode.START_CHOOSE_BOARDINGPEOPLE.getRequestCode());
				
//				BoardingPeople people = new BoardingPeople(true, "李大嘴"+(count++), "356258745985653241"+(count++)); 
//				peopleAdapter.addNewPeople(people);
			}
		});
	}
	
	private class BoardingPeopleAdapter extends BaseAdapter {
		private List<BoardingPeople> peopleList;
		private Context context;
		public BoardingPeopleAdapter(Context context, List<BoardingPeople> peopleList) {
			this.context = context;
			this.peopleList = peopleList;
		}
		@Override
		public int getCount() {
			return peopleList.size();
		}

		@Override
		public BoardingPeople getItem(int position) {
			return peopleList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void addNewPeople(BoardingPeople people){
			peopleList.add(people);
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_boardingpeople, null);
	            holder = new ViewHolder();  
	            
	            holder.nameTextView = (TextView) convertView.findViewById(R.id.boardingpeople_name_textView);
	            holder.idNumberTextView = (TextView) convertView.findViewById(R.id.boardingpeople_idnumber_textView);
	            holder.idTypeTextView = (TextView) convertView.findViewById(R.id.boardingpeople_idtype_textView);
	            holder.deleteLinearLayout = (LinearLayout) convertView.findViewById(R.id.boardingpeople_delete_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			BoardingPeople people = getItem(position);
            holder.nameTextView.setText(people.getName());
            holder.idNumberTextView.setText(people.getCardNumber());
			holder.deleteLinearLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						peopleList.remove(position); 
						Log.d("poding",position+"");
						notifyDataSetChanged();
					}
				});
	        return convertView;  
		}
		
		private class ViewHolder {
			TextView nameTextView;
			TextView idNumberTextView;
			TextView idTypeTextView;
			LinearLayout deleteLinearLayout;
		}
	}
//	
//	private void initPopupWindow(){
//		View popupWindow =  LayoutInflater.from(this).inflate(R.layout.popup_insurance_selector, null);
//		insuranceSelectorListView = (ListView)popupWindow.findViewById(R.id.insurance_select_list);
//		insuranceAmountSelector = new PopupWindow(popupWindow,insurancePopupParentWidth,LayoutParams.WRAP_CONTENT,true);
//		insuranceAmountSelector.setOutsideTouchable(true);
//		insuranceAmountSelector.setBackgroundDrawable(new BitmapDrawable());
//	}
//	
//	private void popupWindowShowing(){
//		insuranceAmountSelector.showAsDropDown(insuranceLinearLayout, 0, -3);
//	}
//	
//	private void popupWindowDismiss(){
//		insuranceAmountSelector.dismiss();
//	}
}
