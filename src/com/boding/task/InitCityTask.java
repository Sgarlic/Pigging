package com.boding.task;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boding.constants.GlobalVariables;
import com.boding.model.City;

import android.os.AsyncTask;

public class InitCityTask extends AsyncTask<Object,Void,Object>{ 
	
	@Override
	protected Object doInBackground(Object... params) {
		
		parseJson(requestCityJSON((String)params[0])); 
		return null;
	}
	
	private String requestCityJSON(String urlstr){
		//URL后面要放入配置中
		String jsonStr = null;
		HttpGet httpGet = new HttpGet(urlstr);
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpResponse mHttpResponse;
		HttpEntity mHttpEntity;
		
		InputStream inputStream = null;
		try
        {
            // 发送请求并获得响应对象
            mHttpResponse = httpClient.execute(httpGet);
            System.out.println("mHttpResponse: ");
            System.out.println(mHttpResponse);
            // 获得响应的消息实体
            mHttpEntity = mHttpResponse.getEntity();
            jsonStr = EntityUtils.toString(mHttpEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
		return jsonStr;
	}
	
	private void parseJson(String jsonStr){
		System.out.println(jsonStr);
		try {
			JSONArray data = new JSONObject(jsonStr).getJSONArray("data");
			JSONObject jo = null;
			City city = null;
			for(int i=0; i<data.length(); ++i){
				jo = (JSONObject) data.opt(i);
				city = new City();
				city.setCityName(jo.getString("cityName"));
				city.setCityCode(jo.getString("citycode"));
				city.setBelongsToCountry(jo.getString("area"));
				if("0".equals(jo.getString("IsDomestic"))){
					city.setInternationalCity(true);
					GlobalVariables.domesticCitiesList.add(city);
				}
				else{
					city.setInternationalCity(false);
					GlobalVariables.interCitiesList.add(city);
				}
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
