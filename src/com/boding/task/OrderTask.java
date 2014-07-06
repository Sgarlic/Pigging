package com.boding.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.boding.app.AddDeliveryAddrActivity;
import com.boding.app.OrderDetailActivity;
import com.boding.app.OrderFormActivity;
import com.boding.app.OrderListActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.model.Order;
import com.boding.model.OrderFlight;
import com.boding.model.Passenger;
import com.boding.util.Encryption;

public class OrderTask extends AsyncTask<Object,Void,Object>{
	private Context context;
	private HTTPAction action;

	public OrderTask(Context context, HTTPAction action){
		this.context = context;
		this.action = action;
	}
	
	public static String createOrderDomestic(String flightInfo, 
			String passengerInfo, String contactInfo, String receiveInfo){
		String resultCode = "";
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		sb.append(flightInfo);
		sb.append(passengerInfo);
		sb.append(contactInfo);
		sb.append(receiveInfo);
		sb.append("0");
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "";
		String urlFormat = "http://api.iboding.com/API/User/Order/CreateOrder.ashx?userid=%s&CardNo=%s&FlightInfo=%s&PassengerInfo=%s&ContactInfo=%s&ReceiveInfo=%s&InternalFlag=%s&sign=%s";
		try {
			urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
					URLEncoder.encode(flightInfo,"UTF-8"),URLEncoder.encode(passengerInfo,"UTF-8"), 
					URLEncoder.encode(contactInfo,"UTF-8"),URLEncoder.encode(receiveInfo,"UTF-8"),
					"0",sign);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			resultCode = resultJson.getString("result");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return resultCode;
	}
	
	public static Order getOrderDetail(String orderCode){
		Order order = new Order();
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(orderCode);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/User/Order/QueryOrderDetailInfo.ashx?userid=%s&cardno=%s&order_code=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
				orderCode,sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			order.setOrderCode(orderCode);
			order.setOrderStatus(resultJson.getString("flag"));
			order.setInternalFlag(Boolean.getBoolean(resultJson.getString("internal_flag")));
			order.setBookingDate(resultJson.getString("booking_date"));
			order.setSourceFlag(resultJson.getString("source_flag"));
			order.setLinkMan(resultJson.getString("linkman"));
			order.setContactPhone(resultJson.getString("contactphone"));
			order.setPayMode(resultJson.getString("pay_mode"));
			order.setShouldRecvMoney(resultJson.getString("should_recv_money"));
			order.setTicketPrice(resultJson.getString("ticketprice"));
			order.setTax(resultJson.getString("tax"));
			order.setInsurance(resultJson.getString("insurance"));
			order.setInsuranceNum(resultJson.getString("insure_num"));
			order.setPayAmount(resultJson.getString("pay_amount"));
			order.setPrePayment(resultJson.getString("prepayment"));
			order.setLeaveCity(resultJson.getString("leave_city"));
			order.setArriveCity(resultJson.getString("arrive_city"));
			order.setFlightDateTime(resultJson.getString("flight_datetime"));
			order.setAirlineType(resultJson.getString("airline_type"));
			JSONArray passengersJson = resultJson.getJSONArray("passengers");
			for(int i = 0;i<passengersJson.length();i++){
				JSONObject passengerJson = passengersJson.getJSONObject(i);
				order.addPassenger(new Passenger(passengerJson.getString("passenger"),
					passengerJson.getString("paper_type"),passengerJson.getString("id_code")));
			}
			JSONArray flightsJson = resultJson.getJSONArray("flights");
			for(int i=0;i<flightsJson.length();i++){
				JSONObject flightJson = flightsJson.getJSONObject(i);
				order.addOrderFlight(new OrderFlight(flightJson.getString("id"),
					flightJson.getString("triptype"),flightJson.getString("carrier"),
					flightJson.getString("carrier_name"),flightJson.getString("flight_num"),
					flightJson.getString("discount"),flightJson.getString("leave_airport"),
					flightJson.getString("arrive_airport"),flightJson.getString("flight_date"),
					flightJson.getString("leave_time_dt"),flightJson.getString("arrive_time_dt"),
					flightJson.getString("leaterminal"),flightJson.getString("arrterminal"),
					flightJson.getString("reles"),flightJson.getString("planetype"),
					flightJson.getString("duration"),flightJson.getString("tran_city"),
					flightJson.getString("tran_stay_date")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	public static List<Order> getOrderList(String pageSize, String page){
		List<Order> orders = new ArrayList<Order>();
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		sb.append(pageSize);
		sb.append(page);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/User/Order/QueryOrderInfo.ashx?userid=%s&cardno=%s&pageSize=%s&page=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
				pageSize,page,sign);
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			JSONArray jsonArray = resultJson.getJSONArray("data");
			for(int i = 0;i<jsonArray.length();i++){
				JSONObject orderJson = jsonArray.getJSONObject(i);
				orders.add(new Order(orderJson.getString("order_code"),
						Boolean.parseBoolean(orderJson.getString("internal_flag")),orderJson.getString("airline_type"),
						orderJson.getString("flag"),orderJson.getString("passenger"),
						orderJson.getString("booking_date"),orderJson.getString("leave_city"),
						orderJson.getString("arrive_city"),orderJson.getString("leave_airport"),
						orderJson.getString("arrive_airport"),orderJson.getString("leave_time_dt"),
						orderJson.getString("arrive_time_dt"),orderJson.getString("leaterminal"),
						orderJson.getString("arrterminal"),orderJson.getString("tran_city")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return orders;
	}
	

	public static String connectingServer(String urlStr){
		System.out.println(urlStr);
		StringBuilder sbr = new StringBuilder();
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpc = (HttpURLConnection)url.openConnection();
			httpc.connect();
			
			InputStream is = httpc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String lines;
			while((lines = reader.readLine()) != null){
				sbr.append(lines);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sbr.toString();
	}

	@Override
	protected Object doInBackground(Object... params) {
		Object result = new Object();
		switch (action) {
			case GET_ORDER_LIST:
				result = getOrderList((String)params[0],(String)params[1]);
				break;
			case GET_ORDER_DETAIL:
				result = getOrderDetail((String)params[0]);
				break;
			case CREATE_ORDER_DOMESTIC:
				result = createOrderDomestic((String)params[0],(String)params[1],(String)params[2],(String)params[3]);
				break;
			default:
				break;
		}
		return result;
	}
	
	@Override  
	 protected void onPostExecute(Object result) {
		switch (action) {
			case GET_ORDER_LIST:
				OrderListActivity orderActivity = (OrderListActivity)context;
				orderActivity.setOrderList((List<Order>)result);
				break;
			case GET_ORDER_DETAIL:
				OrderDetailActivity orderDetailActivity = (OrderDetailActivity)context;
				orderDetailActivity.setOrderInfo((Order)result);
				break;
			case CREATE_ORDER_DOMESTIC:
				OrderFormActivity orderFormActivity = (OrderFormActivity)context;
				orderFormActivity.setCreateOrderResult((String)result);
				break;
			default:
				break;
		}
	}
}
