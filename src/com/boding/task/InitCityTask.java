package com.boding.task;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.boding.constants.GlobalVariables;
import com.boding.model.City;

public class InitCityTask extends AsyncTask<Object,Void,Object>{ 
	private Context context;
	public InitCityTask(Context context){
		this.context = context;
	}
	
	
	@Override
	protected Object doInBackground(Object... params) {
		boolean isok = false;
		try {
			isok = restoreCitiesFromFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!isok){
			System.out.println("FFFFFFFFFFFFFirst!");
			parseJson(requestCityJSON((String)params[0])); 
			sortCityLists();
			saveCitiesToFile();
		}
		GlobalVariables.allCitiesList.addAll(GlobalVariables.domesticCitiesList);
		GlobalVariables.allCitiesList.addAll(GlobalVariables.interCitiesList);
//		List<City> domesticCities = GlobalVariables.domesticCitiesList;
//		List<City> interC = GlobalVariables.interCitiesList;
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
			String type = null;
			for(int i=0; i<data.length(); ++i){
				jo = (JSONObject) data.opt(i);
				city = new City();
				city.setCityName(jo.getString("cityName"));
				city.setCityCode(jo.getString("citycode"));
				city.setBelongsToCountry(jo.getString("area"));
				city.setAirportcode(jo.getString("airportcode"));
				if("True".equals(jo.getString("IsDomestic"))){
					city.setInternationalCity(false);
					GlobalVariables.domesticCitiesList.add(city);
				}
				else{
					city.setInternationalCity(true);
					GlobalVariables.interCitiesList.add(city);
				}
				type = jo.getString("type");
				if(type.contains("A")){
					GlobalVariables.domHotCitiesList.add(city);
				}
				if(type.contains("B")){
					GlobalVariables.interHotCitiesList.add(city);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void sortCityLists(){
		City.CityNameComp comp = new City.CityNameComp();
		Collections.sort(GlobalVariables.domesticCitiesList, comp);
		//Collections.sort(GlobalVariables.domHotCitiesList, comp);
		//Collections.sort(GlobalVariables.domHistoryCitiesList, comp);
		Collections.sort(GlobalVariables.interCitiesList, comp);
		//Collections.sort(GlobalVariables.interHotCitiesList, comp);
		//Collections.sort(GlobalVariables.interHistoryCitiesList, comp);
	}
	
	private void saveCitiesToFile(){
		ObjectOutputStream oos = null;		
		try {
			oos = new ObjectOutputStream(context.openFileOutput(GlobalVariables.domeCityFile, context.MODE_PRIVATE));
			oos.writeObject(GlobalVariables.domesticCitiesList);
			oos.close();
			oos = new ObjectOutputStream(context.openFileOutput(GlobalVariables.domHotCityFile, context.MODE_PRIVATE));
			oos.writeObject(GlobalVariables.domHotCitiesList);
			oos.close();
			oos = new ObjectOutputStream(context.openFileOutput(GlobalVariables.interCityFile, context.MODE_PRIVATE));
			oos.writeObject(GlobalVariables.interCitiesList);
			oos.close();
			oos = new ObjectOutputStream(context.openFileOutput(GlobalVariables.interHotCityFile, context.MODE_PRIVATE));
			oos.writeObject(GlobalVariables.interHotCitiesList);
			oos.close();
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}
	
	private boolean restoreCitiesFromFile() throws Exception{
		boolean isok = true;
		ObjectInputStream ois;
		String path = GlobalVariables.filepath;
		File domefile = new File(path + GlobalVariables.domeCityFile);
		if(domefile.exists()){
			System.out.println("EEEEEEEEEEEEEexits");
			ois = new ObjectInputStream(context.openFileInput(GlobalVariables.domeCityFile));
			GlobalVariables.domesticCitiesList = (List<City>)(ois.readObject());
			ois.close();
		}else
			isok = false;
		
		File domehotfile = new File(path + GlobalVariables.domHotCityFile);
		if(domehotfile.exists()){
			ois = new ObjectInputStream(context.openFileInput(GlobalVariables.domHotCityFile));
			GlobalVariables.domHotCitiesList = (List<City>)(ois.readObject());
			ois.close();
		}else
			isok = false;
		
		File interfile = new File(path + GlobalVariables.interCityFile);
		if(interfile.exists()){
			ois = new ObjectInputStream(context.openFileInput(GlobalVariables.interCityFile));
			GlobalVariables.interCitiesList = (List<City>)(ois.readObject());
			ois.close();
		}else
			isok = false;
		
		File interhotfile = new File(path + GlobalVariables.interHotCityFile);
		if(interhotfile.exists()){
			ois = new ObjectInputStream(context.openFileInput(GlobalVariables.interHotCityFile));
			GlobalVariables.interHotCitiesList = (List<City>)(ois.readObject());
			ois.close();
		}else
			isok = false;
		return isok;
	}
}
