package com.boding.task;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.boding.app.BodingBaseActivity;
import com.boding.app.OrderDetailActivity;
import com.boding.app.OrderListActivity;
import com.boding.app.OrderPaymentActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.model.Order;
import com.boding.model.OrderFlight;
import com.boding.model.Passenger;
import com.boding.model.PaymentMethod;
import com.boding.util.Encryption;
import com.boding.util.Util;

public class OrderTask extends BodingBaseAsyncTask{
	public OrderTask(Context context, HTTPAction action){
		super(context, action);
	}
	
	public String createOrder(String flightInfo, String passengerInfo, 
		String contactInfo, String receiveInfo, boolean internalFlag, PaymentMethod payMethod){
		System.out.println(flightInfo);
		System.out.println(passengerInfo);
		System.out.println(contactInfo);
		System.out.println(receiveInfo);
		int flag = 0;
		if(internalFlag)
			flag = 1;
		String resultCode = "";
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		String sign = "";
		sb.append(flightInfo);
		sb.append(passengerInfo);
		sb.append(contactInfo);
		sb.append(receiveInfo);
		sb.append(flag);
		sb.append(payMethod);
		
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "http://api.iboding.com/API/User/Order/CreateOrder.ashx";
//		String urlFormat = "http://api.iboding.com/API/User/Order/CreateOrder.ashx?userid=%s&CardNo=%s&FlightInfo=%s&PassengerInfo=%s&ContactInfo=%s&ReceiveInfo=%s&InternalFlag=%s&PayModel=%s&sign=%s";
//		try {
//			urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
//					URLEncoder.encode(flightInfo,"UTF-8"),URLEncoder.encode(passengerInfo,"UTF-8"), 
//					URLEncoder.encode(contactInfo,"UTF-8"),URLEncoder.encode(receiveInfo,"UTF-8"),
//					"0","BB",sign);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		URL postURL;
		try {
			postURL = new URL(urlStr);
			System.out.println(urlStr);
			HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
		    connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
            connection.setReadTimeout(Constants.READ_TIMEOUT);
            connection.connect();
            
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            
            String content = "userid=" + Constants.BODINGACCOUNT;
            content += "&CardNo=" + GlobalVariables.bodingUser.getCardno();
            content += "&FlightInfo=" + URLEncoder.encode(flightInfo, "UTF-8");
            content += "&PassengerInfo=" + URLEncoder.encode(passengerInfo, "UTF-8");
            content += "&ContactInfo=" + URLEncoder.encode(contactInfo, "UTF-8");
            content += "&ReceiveInfo=" + URLEncoder.encode(receiveInfo, "UTF-8");
            content += "&InternalFlag="+flag;
            content += "&PayModel="+payMethod;
            content += "&sign="+sign;
            
            out.writeBytes(content);
            out.flush();
            out.close(); // flush and close
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            String result = "";
            while ((line = reader.readLine()) != null){
                System.out.println(line);
                result += line;
            }
          
            reader.close();
            connection.disconnect();
            
			JSONObject resultJson = new JSONObject(result);
			resultCode = resultJson.getString("result");
			System.out.println(resultCode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			isTimeout = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultCode;
	}
	
	public Order getOrderDetail(String orderCode){
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
			String flag = resultJson.getString("flag");
			if(flag.equals(""))
				return null;
			order.setOrderStatus(flag);
			order.setInternalFlag(Boolean.getBoolean(resultJson.getString("internal_flag")));
			order.setBookingDate(resultJson.getString("booking_date"));
			order.setSourceFlag(resultJson.getString("source_flag"));
			order.setLinkMan(resultJson.getString("linkman"));
			order.setContactPhone(resultJson.getString("contactphone"));
			order.setPayMode(resultJson.getString("pay_mode"));
			order.setShouldRecvMoney(resultJson.getString("should_recv_money"));
			order.setTicketPrice(Util.getIntStringFromDoubleString(resultJson.getString("ticketprice")));
			order.setTax(Util.getIntStringFromDoubleString(resultJson.getString("tax")));
			order.setInsurance(Util.getIntStringFromDoubleString(resultJson.getString("insurance")));
			order.setInsuranceNum(Util.getIntStringFromDoubleString(resultJson.getString("insure_num")));
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
					passengerJson.getString("paper_type"),passengerJson.getString("id_code"),
					order.isInternalFlag()));
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
			return null;
		}
		return order;
	}
	
	public List<Order> getOrderList(int flag, String pageSize, String page){
		List<Order> orders = new ArrayList<Order>();
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		sb.append(GlobalVariables.bodingUser.getCardno());
		if(flag != -1)
			sb.append(flag);
		sb.append(pageSize);
		sb.append(page);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "";
		if(flag != -1){
			String urlFormat = "http://api.iboding.com/API/User/Order/QueryOrderInfo.ashx?userid=%s&cardno=%s&flag=%s&pageSize=%s&page=%s&sign=%s";
			urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
					flag,pageSize,page,sign);
		}else{
			String urlFormat = "http://api.iboding.com/API/User/Order/QueryOrderInfo.ashx?userid=%s&cardno=%s&pageSize=%s&page=%s&sign=%s";
			urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,GlobalVariables.bodingUser.getCardno(),
					pageSize,page,sign);
		}
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

	@Override
	protected Object doInBackground(Object... params) {
		Object result = new Object();
		switch (action) {
			case GET_ORDER_LIST:
				result = getOrderList((Integer)params[0],(String)params[1],(String)params[2]);
				break;
			case GET_ORDER_DETAIL:
				result = getOrderDetail((String)params[0]);
				break;
			case CREATE_ORDER_DOMESTIC:
				result = createOrder((String)params[0],(String)params[1],
					(String)params[2],(String)params[3],(Boolean)params[4],(PaymentMethod)params[5]);
				break;
			default:
				break;
		}
		return result;
	}
	
	@Override  
	protected void onPostExecute(Object result) {
		if(isTimeout){
			((BodingBaseActivity)context).handleTimeout();
			return;
		}
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
				OrderPaymentActivity prderPaymentActivity = (OrderPaymentActivity)context;
				prderPaymentActivity.setCreateOrderResult((String)result);
				break;
			default:
				break;
		}
	}
}
