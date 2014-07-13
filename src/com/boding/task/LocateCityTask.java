package com.boding.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.boding.app.TicketSearchResultActivity;
import com.boding.constants.GlobalVariables;
import com.boding.model.domestic.Airlines;
import com.boding.util.Encryption;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocateCityTask extends AsyncTask<Object,Void,Object> implements LocationListener{
	private static final String TAG = "LocationTask"; 
	private LocationManager locationManager;
	private Context context;
	private String provider;
	
	public LocateCityTask(Context context){
		this.context = context;
	}
	
	public void locate(){
		System.out.println("helloooooooooooooooooo");
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE); 		 

		if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) 
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);  
		else if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) 
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);  
		else Toast.makeText(context, "无法连接到网络，请检查网络设置。", Toast.LENGTH_SHORT).show();
		
		System.out.println("ANNOOOOOOTHER Heeellooooo");
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "Get the current position \n" + location); 
		this.execute(location);
		
		
		locationManager.removeUpdates(this);  
	}
	
	private String parseGeocoderJson(String jsonStr) throws JSONException{
		JSONObject json = new JSONObject(jsonStr);
		String city="";
		if("OK".equals(json.getString("status"))){
			JSONObject result = json.getJSONObject("result");
			JSONObject address = result.getJSONObject("addressComponent");
			city = address.getString("city");
		}
		return city;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Object doInBackground(Object... params) {
		Location location = (Location)params[0];
		String longti = String.valueOf(location.getLongitude());
		String lati = String.valueOf(location.getLatitude());
		String key = "nuGwwjg08k7bgZZ4eftqP6hH";
		
		String urlFormat = "http://api.map.baidu.com/geocoder?output=json&location=%s,%%20%s&key=%s";
		String urlStr = String.format(urlFormat, lati, longti, key);
		//urlStr = URLEncoder.encode(urlStr);
		System.out.println(urlStr);
		URL url;
		String city="";
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
			
			city = parseGeocoderJson(sbr.toString());
			System.out.println("-------------" + GlobalVariables.CurrentCity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return city;
	}

	@Override  
	 protected void onPostExecute(Object result) {
		GlobalVariables.CurrentCity = (String)result;
	}

}
