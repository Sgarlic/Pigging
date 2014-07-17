package com.boding.app;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.Gender;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.constants.IdentityType;
import com.boding.constants.IntentExtraAttribute;
import com.boding.constants.IntentRequestCode;
import com.boding.model.DeliveryAddress;
import com.boding.model.FlightInterface;
import com.boding.model.FlightLine;
import com.boding.model.Passenger;
import com.boding.model.domestic.Cabin;
import com.boding.model.domestic.Flight;
import com.boding.util.CityUtil;
import com.boding.util.RegularExpressionsUtil;
import com.boding.util.Util;
import com.boding.view.dialog.ProgressBarDialog;
import com.boding.view.dialog.WarningDialog;
import com.boding.view.layout.OrderFlightInfoILayout;
import com.boding.view.layout.OrderFlightInfoLayout;
import com.boding.task.OrderTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OrderFormActivity extends Activity {
	private LinearLayout flightInfoLinearLayout;
	private TextView passengerAmountTextView;
	private ListView passengerListView;
	private LinearLayout addPassengerLinearLayout;
	private EditText phoneNumberEditText;
	private LinearLayout insuranceLinearLayout;
	private TextView insurancePriceTextView;
	private TextView insuranceAmountTextView;
	private LinearLayout journeySheetLinearLayout; 
	private TextView journeySheetTextView;
	private TextView totalPriceTextView;
	private LinearLayout nextStepLinearLayout; 
	
	private PassengerAdapter peopleAdapter;
	
	private ArrayList<Passenger> passengerList;
	private DeliveryAddress selectedAddr;
	private boolean needInsurance;
	
	private boolean isDomestic;
	private boolean isRoundWay;
	
	private FlightInterface selectedFlight;
	private FlightInterface selectedRoundwayFlight;
//	private PopupWindow insuranceAmountSelector;
//	private ListView insuranceSelectorListView;
//	private int insurancePopupParentWidth;

	// if init complete
	private boolean flag = false;
	
	private int ticketPrice;
	private int deliveryPrice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_form);
		Bundle arguments = getIntent().getExtras();
        if(arguments != null){
        	if(arguments.containsKey(IntentExtraAttribute.IS_DOMESTIC_ORDER)){
        		isDomestic = arguments.getBoolean(IntentExtraAttribute.IS_DOMESTIC_ORDER);
        	}
        	if(arguments.containsKey(IntentExtraAttribute.IS_ROUNDWAY_ORDER)){
        		isRoundWay = arguments.getBoolean(IntentExtraAttribute.IS_ROUNDWAY_ORDER);
        	}
        	if(arguments.containsKey(IntentExtraAttribute.FLIGHT_LINE_INFO)){
        		selectedFlight = arguments.getParcelable(IntentExtraAttribute.FLIGHT_LINE_INFO);
        		System.out.println("PASS TO ORDER" + ((Flight)selectedFlight).getSelectedCabins().size());
        	}
        	if(arguments.containsKey(IntentExtraAttribute.FLIGHT_LINE_INFO_ROUNDWAY)){
        		selectedRoundwayFlight = arguments.getParcelable(IntentExtraAttribute.FLIGHT_LINE_INFO_ROUNDWAY);
        	}
        }
	}
	
	private void setDeliveryAddr(){
		if(selectedAddr == null){
			journeySheetTextView.setText("不需要行程单");
		}else{
			journeySheetTextView.setText(selectedAddr.getRecipientName());
		}
		calculateTotalPrice();
	}
	
	private void calculateTotalPrice(){
		ticketPrice = 0;
		if(needInsurance){
			if(passengerList!=null){
				ticketPrice += passengerList.size() * Integer.parseInt(insurancePriceTextView.getText().toString());
			}
		}else{
			insuranceAmountTextView.setText("0");
			
		}
		
		Flight flight = (Flight)selectedFlight;
		Cabin cabin = flight.getCabins().get(flight.getSelectedClassPos());
		if(passengerList != null){
	        for(Passenger passenger : passengerList){
				if(passenger.isAdult()){
					ticketPrice += (cabin.getAdultPrice() + 
						Integer.parseInt(flight.getAdultAirportFee()) + Integer.parseInt(flight.getAdultFuelFee()));
				}else{
					ticketPrice += (cabin.getChildPrice() +
						cabin.getChildAirportFee() + cabin.getChildFuelFee());
				}
			}
		}
		
		if(selectedAddr != null)
			ticketPrice += deliveryPrice;
		totalPriceTextView.setText(ticketPrice+"");
	}
	
	private void setInsusrance(){
		if(needInsurance){
			if(passengerList!=null){
				insuranceAmountTextView.setText(passengerList.size()+"");
			}
		}else{
			insuranceAmountTextView.setText("0");
		}
		calculateTotalPrice();
	}
		
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		while(!flag){
			initView();
			setDeliveryAddr();
			setInsusrance();
			flag = true;
		}
	}
	
	private void initView(){
		LinearLayout returnLinearLayout = (LinearLayout)findViewById(R.id.return_logo_linearLayout);
		returnLinearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Util.returnToPreviousPage(OrderFormActivity.this, IntentRequestCode.DATE_SELECTION);
			}
			
		});
		flightInfoLinearLayout = (LinearLayout) findViewById(R.id.orderform_flightInfo_linearLayout);
		
		if(isDomestic){
			OrderFlightInfoLayout orderFlightInfoLinearLayout = new OrderFlightInfoLayout(this,
					(Flight)selectedFlight, false);
			flightInfoLinearLayout.addView(orderFlightInfoLinearLayout);
			if(isRoundWay){
				OrderFlightInfoLayout orderFlightInfoRLinearLayout = new OrderFlightInfoLayout(this,
						(Flight)selectedRoundwayFlight, true);
				flightInfoLinearLayout.addView(orderFlightInfoRLinearLayout);
			}
		}else{
			OrderFlightInfoILayout orderFlightInfoILinearLayout = new OrderFlightInfoILayout(this,
					(FlightLine)selectedFlight, false);
			flightInfoLinearLayout.addView(orderFlightInfoILinearLayout);
			if(isRoundWay){
				OrderFlightInfoILayout orderFlightInfoIRLinearLayout = new OrderFlightInfoILayout(this,
					(FlightLine)selectedFlight, true);
				flightInfoLinearLayout.addView(orderFlightInfoIRLinearLayout);
			}
		}
		
		
		passengerAmountTextView = (TextView) findViewById(R.id.orderform_passengerAmount_textView);
		passengerListView = (ListView) findViewById(R.id.orderform_passenger_listView);
		addPassengerLinearLayout = (LinearLayout) findViewById(R.id.orderform_addPassenger_linearLayout);
		phoneNumberEditText = (EditText) findViewById(R.id.orderform_phoneNumber_editText);
		insuranceLinearLayout = (LinearLayout) findViewById(R.id.orderform_insurance_linearLayout);
		insurancePriceTextView = (TextView) findViewById(R.id.orderform_insurance_price_textView);
		insuranceAmountTextView = (TextView) findViewById(R.id.orderform_insurance_amount_textView);
		journeySheetLinearLayout = (LinearLayout) findViewById(R.id.orderform_journeySheet_linearLayout); 
		journeySheetTextView = (TextView) findViewById(R.id.orderform_journeySheet_textView);
		totalPriceTextView = (TextView) findViewById(R.id.orderform_totalPrice_textView);
		nextStepLinearLayout = (LinearLayout) findViewById(R.id.orderform_nextStep_linearLayout); 
		
//		insurancePopupParentWidth = insuranceLinearLayout.getWidth();
		addListeners();
    }
	
	private void setPassengerView(){
		Util.setListViewHeightBasedOnChildren(passengerListView);
        passengerAmountTextView.setText(String.valueOf(peopleAdapter.getCount()));
        
        setInsusrance();
	}
	
	private void addListeners(){
		addPassengerLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Passenger people = new Passenger("李大嘴"+(count++), "35247412569853265"+(count++),IdentityType.GA); 
//				peopleAdapter.addPassenger(people);
				if(GlobalVariables.bodingUser == null){
					openLoginActivity();
					return;
				}else if(!GlobalVariables.bodingUser.isActivated_state()){
					Intent intent = new Intent();
					intent.setClass(OrderFormActivity.this, VerifyPhonenumActivity.class);
					intent.putExtra(IntentExtraAttribute.VERIFY_PHONENUM_TYPE, "2");
					startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
					return;
				}
				Intent intent = new Intent();
				if(passengerList!=null){
					Bundle bundle = new Bundle();
					bundle.putParcelableArrayList(IntentExtraAttribute.CHOOSED_PASSENGERS_EXTRA, passengerList);
					intent.putExtras(bundle);
				}
				intent.setClass(OrderFormActivity.this, ChoosePassengerActivity.class);
				startActivityForResult(intent,IntentRequestCode.CHOOSE_PASSENGER.getRequestCode());
			}
		});
		
		
		journeySheetLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if(selectedAddr!=null){
					Bundle bundle = new Bundle();
					bundle.putParcelable(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA,
							selectedAddr);
					intent.putExtras(bundle);
				}
				intent.setClass(OrderFormActivity.this, JourneySheetDeliveryActivity.class);
				startActivityForResult(intent,IntentRequestCode.JOURNEYSHEET_DELIVERY.getRequestCode());
			}
		});
		
		nextStepLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				WarningDialog dialog = new WarningDialog(OrderFormActivity.this);
				if(passengerList == null){
					dialog.setContent("请选择乘客");
					dialog.show();
					return;
				}
				if(passengerList.size() > selectedFlight.getSelectedClassLeftTicket()){
					dialog.setContent("选择的乘客数量超过可购买的机票数量");
					dialog.show();
					return;
				}
				
				boolean containsAdult = false;
				for(Passenger passenger : passengerList){
					if(passenger.isAdult()){
						containsAdult = true;
						break;
					}
				}
				if(!containsAdult){
					dialog.setContent("必须选择一名成人");
					dialog.show();
					return;
				}
				
				if(phoneNumberEditText.getText().toString().equals("") || 
						!RegularExpressionsUtil.checkMobile(phoneNumberEditText.getText().toString())){
					dialog.setContent("请填写正确的手机号码");
					dialog.show();
					return;
				}
				
//				System.out.println(getFlightInfoStringDomestic());
//				System.out.println(getPassengerInfoString());
//				System.out.println(getContactInfo());
//				System.out.println(getReceiveInfo());
//				progressBarDialog.show();
//				if(isDomestic){
//					(new OrderTask(OrderFormActivity.this,HTTPAction.CREATE_ORDER_DOMESTIC))
//					.execute(getFlightInfoStringDomestic(),getPassengerInfoString(),
//						getContactInfo(),getReceiveInfo());
//				}
//				if(isDomestic){
//					(new OrderTask(OrderFormActivity.this,HTTPAction.CREATE_ORDER_DOMESTIC))
//					.execute(getFlightInfoStringDomestic(),"1|饶礼仁|NI|350783199011156575|0|0",
//						"饶礼仁|15689654125","");
//				}
				
				Intent intent = new Intent();
				intent.putExtra(IntentExtraAttribute.FLIGHT_INFO_EXTRA, getFlightInfoStringDomestic());
				intent.putExtra(IntentExtraAttribute.PASSENGER_INFO_EXTRA, getPassengerInfoString());
				intent.putExtra(IntentExtraAttribute.CONTACT_INFO_EXTRA, getContactInfo());
				intent.putExtra(IntentExtraAttribute.RECEIVE_INFO_EXTRA, getReceiveInfo());
				intent.putExtra(IntentExtraAttribute.INTERNAL_FLAG_EXTRA, !isDomestic);
				intent.putExtra(IntentExtraAttribute.FLY_FROM_TO_EXTRA, 
					selectedFlight.getFlyFromCity() + "-" + selectedFlight.getFlyToCity());
				intent.putExtra(IntentExtraAttribute.IS_ROUNDWAY_ORDER, isRoundWay);
				intent.putExtra(IntentExtraAttribute.ORDER_TOTAL_EXTRA, ticketPrice);
				intent.setClass(OrderFormActivity.this, OrderPaymentActivity.class);
				startActivityForResult(intent, IntentRequestCode.ORDER_PAYEMNT.getRequestCode());
			}
		});
		
		insuranceLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				if(needInsurance){
					Bundle bundle = new Bundle();
					bundle.putBoolean(IntentExtraAttribute.CHOOSED_INSURANCE,
							true);
					intent.putExtras(bundle);
				}
				intent.setClass(OrderFormActivity.this, InsuranceSelectionActivity.class);
				startActivityForResult(intent,IntentRequestCode.INSURANCE_SELECTION.getRequestCode());
			}
		});
	}
	
	private class PassengerAdapter extends BaseAdapter {
		private List<Passenger> passengerList;
		private Context context;
		public PassengerAdapter(Context context, List<Passenger> passengerList) {
			this.context = context;
			this.passengerList = passengerList;
		}
		@Override
		public int getCount() {
			return passengerList.size();
		}

		@Override
		public Passenger getItem(int position) {
			return passengerList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void addPassenger(Passenger passenger){
			passengerList.add(passenger);
			notifyDataSetChanged();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {  
	            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_passenger, null);
	            holder = new ViewHolder();  
	            
	            holder.nameTextView = (TextView) convertView.findViewById(R.id.passenger_name_textView);
	            holder.idNumberTextView = (TextView) convertView.findViewById(R.id.passenger_idnumber_textView);
	            holder.idTypeTextView = (TextView) convertView.findViewById(R.id.passenger_idtype_textView);
	            holder.deleteLinearLayout = (LinearLayout) convertView.findViewById(R.id.passenger_delete_linearLayout);
	            
	            convertView.setTag(holder);  
	        } else {  
	            holder = (ViewHolder) convertView.getTag();  
	        }  
			
			Passenger people = getItem(position);
            holder.nameTextView.setText(people.getDiaplayName());
            holder.idTypeTextView.setText(people.getIdentityType().getIdentityName());
            holder.idNumberTextView.setText(people.getCardNumber());
			holder.deleteLinearLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						passengerList.remove(position);
						setInsusrance();
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
	
	private void openLoginActivity(){
		Intent intent = new Intent();
		intent.setClass(OrderFormActivity.this, LoginActivity.class);
		startActivityForResult(intent, IntentRequestCode.LOGIN.getRequestCode());
	}
	
	private String getFlightInfoStringDomestic(){
		Flight flight = (Flight)selectedFlight;
		Cabin cabin = flight.getCabins().get(flight.getSelectedClassPos());
		String flightInfo = getFlightInfoStringDomestic(flight, cabin);
		if(isRoundWay){
			Flight returnFlight = (Flight)selectedRoundwayFlight;
			Cabin returnCabin = returnFlight.getCabins().get(returnFlight.getSelectedClassPos());
			flightInfo += "$";
			flightInfo += getFlightInfoStringDomestic(returnFlight, returnCabin);
		}
		  return flightInfo;
	}
	
	private String getFlightInfoStringDomestic(Flight flight, Cabin cabin){
		String flightInfo = "|"+flight.getCarrierName()+"|"+flight.getCarrier()+"|"
				+flight.getFlightNum()+"|"+flight.getPlantype()+"|"+cabin.getCode()+"|"
				+cabin.getClassType()+"|"+"|"+"|"+flight.getDptDate()+"|"+flight.getArrDate()+"|"
				+flight.getDptTime()+"|"+flight.getArrTime()+"|"+"|"+"|"
				+flight.getDptAirport()+"|"+flight.getArrAirport()+"|"+flight.getDptTerminal()
				+"|"+flight.getArrTerminal()+"|"+cabin.getAdultPrice()+"|"+cabin.getChildPrice()
				+"|0|0|"+flight.hasStops()+"|"+cabin.getStatus()+"|"+flight.getDuration()
				+"|"+cabin.getFilePrice()+"|"+cabin.getRule()+"|||"+flight.getAdultFuelFee()
				+"|"+flight.getAdultAirportFee()+"|"+cabin.getChildFuelFee()+"|"
				+cabin.getChildAirportFee()+"|"+cabin.getTid()+"|"+cabin.getGid()+"|"
				+cabin.getCabinName()+"|"+cabin.getCabinNameLogogram() + "|0|0|0";
		return flightInfo;
	}
	
	private String getContactInfo(){
		return passengerList.get(0).getDiaplayName()+"|"+phoneNumberEditText.getText().toString();
	}
	
	private String getReceiveInfo(){
		String receiveInfo = "";
		if(selectedAddr != null)
		{
			receiveInfo += selectedAddr.getRecipientName() + "|" + selectedAddr.getDisplayAddr()
				+ "|" + selectedAddr.getZipcode() + "|" + selectedAddr.getMobile() + "|"
				+ selectedAddr.getPhone();
		}
		return receiveInfo;
	}
	
	private String getPassengerInfoString(){
		String passengerInfo = "";
		
		for(Passenger passenger : passengerList){
			if(passenger.getIdentityType() == IdentityType.NI){
				passengerInfo += "1|"+passenger.getName()+"|"+passenger.getIdentityType();
			}else{
				passengerInfo += "1|"+passenger.geteName()+"|"+passenger.getIdentityType();
			}
			passengerInfo += "|"+passenger.getCardNumber();
			if(needInsurance){
				if(isRoundWay)
					passengerInfo += "|1|1";
				else
					passengerInfo += "|1|0";
			}else{
				passengerInfo += "|0|0";
			}
			if(passenger.getIdentityType() != IdentityType.NI){
				passengerInfo += "|" + passenger.getBirthday() + "|" + passenger.getValidDate()
					+"|" + passenger.getNationality().split("-")[1] + "|"
					+ passenger.getNationality().split("-")[1] + "|";
				if(passenger.getGender() == null)
					passengerInfo += Gender.Male.getGenderCode();
				else
					passengerInfo += passenger.getGender().getGenderCode();
			}
			
			passengerInfo += "$";
		}
		
		return passengerInfo.substring(0,passengerInfo.length()-1);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		if(requestCode==IntentRequestCode.CHOOSE_PASSENGER.getRequestCode()){
			if(data.getExtras() == null)
				return;
			if(data.getExtras().containsKey(IntentExtraAttribute.CHOOSED_PASSENGERS_EXTRA)){
//				Passenger passenger = (Passenger) data.getExtras().get(IntentExtraAttribute.ADD_PASSENGER_EXTRA);
//				peopleAdapter.addPassenger(passenger);
				ArrayList<Passenger> passengers = data.getParcelableArrayListExtra(IntentExtraAttribute.CHOOSED_PASSENGERS_EXTRA);
				passengerList = passengers;
				peopleAdapter = new PassengerAdapter(this, passengers);
				peopleAdapter.registerDataSetObserver(new DataSetObserver(){  
			        public void onChanged() {  
			        	setPassengerView();
			        }  
			    });
				passengerListView.setAdapter(peopleAdapter);
				setPassengerView();
			}
		}
		if(requestCode==IntentRequestCode.JOURNEYSHEET_DELIVERY.getRequestCode()){
			if(data.getExtras() == null)
				return;
			if(data.getExtras().containsKey(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA)){
				selectedAddr = data.getExtras().getParcelable(IntentExtraAttribute.CHOOSED_DELIVERADDR_EXTRA);
			}else
				selectedAddr = null;
			if(data.getExtras().containsKey(IntentExtraAttribute.CHOOSED_DELIVERPRICE_EXTRA)){
				deliveryPrice = data.getExtras().getInt(IntentExtraAttribute.CHOOSED_DELIVERPRICE_EXTRA);
			}else
				deliveryPrice = 0;
			
			setDeliveryAddr();
		}
		if(requestCode==IntentRequestCode.INSURANCE_SELECTION.getRequestCode()){
			if(data.getExtras() == null)
				return;
			if(data.getExtras().containsKey(IntentExtraAttribute.CHOOSED_INSURANCE)){
				needInsurance= data.getExtras().getBoolean(IntentExtraAttribute.CHOOSED_INSURANCE);
			}else
				needInsurance = false;
			setInsusrance();
		}
	}
}
