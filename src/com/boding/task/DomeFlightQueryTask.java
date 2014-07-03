package com.boding.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boding.app.TicketSearchResultActivity;
import com.boding.model.AirlineView;
import com.boding.model.domestic.Airlines;
import com.boding.model.domestic.Cabin;
import com.boding.model.domestic.Flight;
import com.boding.util.DateUtil;
import com.boding.util.Encryption;

import android.content.Context;
import android.os.AsyncTask;

public class DomeFlightQueryTask extends AsyncTask<Object,Void,Object> {
	private Airlines mAirlines;
	private int whichday;
	private Context context;
	
	public DomeFlightQueryTask(Context context){
		this.context = context;
		this.whichday = 2; //Ä¬ÈÏÊÇtoday
	}
	
	public DomeFlightQueryTask(Context context, int whichday){
		this.context = context;
		this.whichday = whichday;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		String date = (String) params[0];
		String fromcity = (String)params[1];
		String tocity = (String)params[2];
		
		if(whichday == 1){
			date = DateUtil.getLastDay(date);
			if(DateUtil.isDayGone(date)){
				return new Airlines();
			}
		}else if(whichday == 3){
			date = DateUtil.getNextDay(date);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("boding");	
		sb.append(fromcity);
		sb.append(tocity);
		sb.append(date);
		//sb.append("android");
		String sign=null;
		try {
			System.out.println(Encryption.getMD5("f0bddfbf03602fd56991f546b3b81d8c"));
			sb.append(Encryption.getMD5("f0bddfbf03602fd56991f546b3b81d8c").toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
			//sign = Encryption.getMD5("bodingSHATAO2014-05-2132C1551AF9C962F9347A5841B76E4129");
			System.out.println(sign);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlFormat = "http://user.iboarding.cn/API/DataInterface/BBCData.ashx?userid=boding&dpt=%s&arr=%s&date=%s&sign=%s";
		String urlStr = String.format(urlFormat, fromcity, tocity, date, sign);
		//String urlStr = "http://user.iboarding.cn/API/DataInterface/BBCData.ashx?userid=boding&dpt="+fromcity+
				//"&arr="+tocity+"&date="+date+"&sign="+sign;
		//String urlStr = "http://user.iboarding.cn/API/DataInterface/BBCData.ashx?userid=boding&dpt=SHA&arr=PEK&date=2014-06-29&sign="+sign;
		System.out.println(urlStr);
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpc = (HttpURLConnection)url.openConnection();
			httpc.connect();
			InputStream is = httpc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sbr = new StringBuilder();
			String lines;
			while((lines = reader.readLine()) != null){
				sbr.append(lines);
			}
			
			mAirlines = parseBBCJson(sbr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mAirlines;
		
	}
	

	@Override  
	 protected void onPostExecute(Object result) {
		TicketSearchResultActivity tsri = (TicketSearchResultActivity) context;
		if(whichday == 1){//last day
			tsri.setLastdayAirlineView((Airlines)result);
		}else if(whichday == 2){//today
			tsri.setTodayAirlineView((Airlines)result);
		}else{// next day
			tsri.setNextdayAirlineView((Airlines)result);
		}
	}
	
	private Airlines parseBBCJson(String jsonStr) throws JSONException{
		Airlines airlines = new Airlines();
		
		JSONObject result = new JSONObject(jsonStr);
		airlines.setDpt(result.getString("Dpt"));
		airlines.setArr(result.getString("Arr"));
		airlines.setGoDate(result.getString("Date"));
		
		JSONArray flights = result.optJSONArray("Flights");
		List<Flight> flightlist = new ArrayList<Flight>();
		Flight flight = null;
		JSONObject flightj = null;
		JSONArray cabinsj = null;
		List<Cabin> cabins = null;
		Cabin cabin = null;
		JSONObject cabinj = null;
		for(int i=0; i< flights.length(); ++i){
			flight = new Flight();
			flightj = flights.getJSONObject(i);
			flight.setCarrier(flightj.getString("Carrier"));
			flight.setDptAirport(flightj.getString("DptAirport"));
			flight.setArrAirport(flightj.getString("ArrAirport"));
			flight.setDptDate(flightj.getString("DptDate"));
			flight.setArrDate(flightj.getString("ArrDate"));
			flight.setDptTime(flightj.getString("DptTime"));
			flight.setArrTime(flightj.getString("ArrTime"));
			flight.setFlightNum(flightj.getString("FlightNum"));
			flight.setCodeShare(flightj.getString("CodeShare"));
			flight.setAdultFuelFee(flightj.getString("AdultFuelFee"));
			flight.setAdultAirportFee(flightj.getString("AdultAirportFee"));
			flight.setYprice(flightj.getString("YPrice"));
			flight.setMeal(flightj.getString("Meal"));
			flight.setPlantype(flightj.getString("Plantype"));
			flight.setStops(flightj.getString("Stops"));
			flight.setDptTerminal(flightj.getString("DptTerminal"));
			flight.setArrTerminal(flightj.getString("ArrTerminal"));
			flight.setCarrierName(flightj.getString("CarrierName"));
			flight.setDptAirportName(flightj.getString("DptAirportName"));
			flight.setArrAirportName(flightj.getString("ArrAirportName"));
			flight.setDuration(flightj.getString("Duration"));
			
			cabinsj = flightj.getJSONArray("Cabins");
			cabins = new ArrayList<Cabin>();
			for(int j=0; j<cabinsj.length(); ++j){
				cabin = new Cabin();
				cabinj = cabinsj.getJSONObject(j);
				cabin.setCabin(cabinj.getString("Cabin"));
				cabin.setGid(cabinj.getString("GID"));
				cabin.setCabinName(cabinj.getString("CabinName"));
				cabin.setCabinNameLogogram(cabinj.getString("CabinNameLogogram"));
				cabin.setCode(cabinj.getString("Code"));
				cabin.setFilePrice(Double.parseDouble(cabinj.getString("FilePrice")));
				cabin.setStatus(cabinj.getString("Status"));
				cabin.setAdultPrice(Double.parseDouble(cabinj.getString("AdultPrice")));
				cabin.setChildPrice(Double.parseDouble(cabinj.getString("ChildPrice")));
				cabin.setChildFuelFee(Double.parseDouble(cabinj.getString("ChildFuelFee")));
				cabin.setChildAirportFee(Double.parseDouble(cabinj.getString("ChildAirportFee")));
				cabin.setClassType(Integer.parseInt(cabinj.getString("ClassType")));
				cabin.setTid(cabinj.getString("TId"));
				cabin.setRule(cabinj.getString("Rule"));
				cabins.add(cabin);
			}
			flight.setCabins(cabins);
			flightlist.add(flight);		
		}
		airlines.setFlights(flightlist);
		return airlines;
	}
}
