package com.boding.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;

import android.content.Context;
import android.os.AsyncTask;

public abstract class BodingBaseAsyncTask extends AsyncTask<Object,Void,Object> {
	protected Context context;
	protected HTTPAction action;
	protected boolean isTimeout;

	public BodingBaseAsyncTask(Context context, HTTPAction action){
		this.context = context;
		this.action = action;
	}

	@Override
	protected abstract Object doInBackground(Object... arg0);

	protected String connectingServer(String urlStr){
		System.out.println(urlStr);
		StringBuilder sbr = new StringBuilder();
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpc = (HttpURLConnection)url.openConnection();
			httpc.setConnectTimeout(Constants.CONNECT_TIMEOUT);
			httpc.setReadTimeout(Constants.READ_TIMEOUT);
			httpc.connect();
			
			InputStream is = httpc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String lines;
			while((lines = reader.readLine()) != null){
				sbr.append(lines);
			}
		} catch (SocketTimeoutException e) {
			isTimeout = true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sbr.toString();
	}
}
