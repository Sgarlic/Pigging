package com.boding.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.boding.app.TicketSearchResultActivity;
import com.boding.model.AirlineView;
import com.boding.util.AvXmlParser;

public class XMLTask extends AsyncTask<Object,Void,Object>{
	private TicketSearchResultActivity tsri;
	private int whichday;
	
	public XMLTask(TicketSearchResultActivity tsri, int whichday){
		this.tsri = tsri;
		this.whichday = whichday;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		System.out.println((String)params[0]);
		
		InputStream is = requestXML((String)params[0]);
		//InputStream is = requestFakeData((Integer)params[0]);
		
		AirlineView av = null;
		try {
			av = AvXmlParser.parse(is);
			System.out.println("------" + av.getFromCity());
			
		}catch (Exception e) {
		
			e.printStackTrace();
		}finally{
			if(is !=null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}		
		return av;
	}
	
	@Override  
	 protected void onPostExecute(Object result) {
		if(whichday == 1){//last day
			tsri.setLastdayAirlineView((AirlineView)result);
		}else if(whichday == 2){//today
			tsri.setTodayAirlineView((AirlineView)result);
		}else{// next day
			tsri.setNextdayAirlineView((AirlineView)result);
		}
    } 
	
	private InputStream requestXML(String urlstr){
		//URL后面要放入配置中
		HttpGet httpGet = new HttpGet(urlstr);
		//HttpGet httpGet = new HttpGet("http://www.baidu.com/");
		httpGet.addHeader("Accept-Encoding", "gzip");
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
            // 获取一个输入流
            inputStream = mHttpEntity.getContent();
            
            System.out.println(mHttpEntity.getContentEncoding());
            //.get
            
            System.out.println(mHttpEntity.getContentEncoding().getValue());
            Log.i("Boding", mHttpEntity.getContentEncoding().getValue());
            if(mHttpEntity.getContentEncoding().getValue().contains("gzip")){
            	inputStream = new GZIPInputStream(inputStream);
            }
            
            //AvXmlParser.parse(inputStream);
        }
        catch (Exception e){
            e.printStackTrace();
        }
		return inputStream;
	}	
	
	private InputStream requestFakeData(int day){
		AssetManager assetManager = tsri.getAssets();
		InputStream ims = null;
	    try {
	    	switch(day){
	    	case 1: 
	    		ims = assetManager.open("fakeData/lastday.xml");
	    		break;
	    	case 2:
	    		ims = assetManager.open("fakeData/today.xml");
	    		break;
	    	case 3:
	    		ims = assetManager.open("fakeData/nextday.xml");
	    	}
	    }catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return ims;
	}
}
