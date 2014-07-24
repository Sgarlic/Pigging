package com.boding.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.model.Country;
import com.boding.util.Encryption;

public class InitCountryTask extends AsyncTask<Object,Void,Object>{
	private Context context;
	public InitCountryTask(Context context){
		this.context = context;
	}
	
	
	@Override
	protected Object doInBackground(Object... params) {
		boolean isok = false;
		try {
			isok = restoreCountriesFromFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!isok){
			System.out.println("FFFFFFFFFFFFFirst!");
			getCountryList();
			sortCountryLists();
			saveCountriesToFile();
		}
		return null;
	}
	
	
	public void getCountryList(){
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String urlFormat = "http://api.iboding.com/API/Base/QueryNationality.ashx?userid=%s&sign=%s";
		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,sign);
		String result = connectingServer(urlStr);
		System.out.println(result);
		try {
			JSONObject resultJson = new JSONObject(result);
			JSONArray jsonArray = resultJson.getJSONArray("data");
			for(int i = 0; i< jsonArray.length();i++){
				JSONObject countryJson = jsonArray.getJSONObject(i);
				GlobalVariables.allCountriesList.add(new Country(countryJson.getString("Code"),countryJson.getString("Name"),
						countryJson.getString("Eng"),countryJson.getString("PinYin"),
						countryJson.getBoolean("Hot"),countryJson.getInt("Sort")));
			}
			System.out.println(jsonArray.length());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String connectingServer(String urlStr){
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
	
	private void sortCountryLists(){
		Country.CountryCamp comp = new Country.CountryCamp();
		Collections.sort(GlobalVariables.allCountriesList, comp);
	}
	
	private void saveCountriesToFile(){
		ObjectOutputStream oos = null;		
		try {
			oos = new ObjectOutputStream(context.openFileOutput(GlobalVariables.allCountriesFile, context.MODE_PRIVATE));
			oos.writeObject(GlobalVariables.allCountriesList);
			oos.close();
			oos.close();
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private boolean restoreCountriesFromFile() throws Exception{
		boolean isok = true;
		ObjectInputStream ois;
		String path = GlobalVariables.filepath;
		File countryLisFile = new File(path + GlobalVariables.allCountriesFile);
 		if(countryLisFile.exists()){
			System.out.println("EEEEEEEEEEEEEexits");
			ois = new ObjectInputStream(context.openFileInput(GlobalVariables.allCountriesFile));
			GlobalVariables.allCountriesList = (List<Country>)(ois.readObject());
			ois.close();
		}else
			isok = false;
		return isok;
	}
}
