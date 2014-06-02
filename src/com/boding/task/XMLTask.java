package com.boding.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.boding.model.AirlineView;
import com.boding.util.AvXmlParser;

import android.os.AsyncTask;
import android.util.Log;

public class XMLTask extends AsyncTask<String,Void,Object>{
	
	@Override
	protected Object doInBackground(String... params) {
		InputStream is = requestXML(params[0]);
		try {
			AirlineView av = AvXmlParser.parse(is);
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
		return "ok";
	}
	
//	@Override  
//	 protected void onPostExecute(Result result) {
//    } 
	
	private InputStream requestXML(String urlstr){
		//URL����Ҫ����������
		HttpGet httpGet = new HttpGet(urlstr);
		//HttpGet httpGet = new HttpGet("http://www.baidu.com/");
		httpGet.addHeader("Accept-Encoding", "gzip");
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpResponse mHttpResponse;
		HttpEntity mHttpEntity;
		
		InputStream inputStream = null;
		try
        {
            // �������󲢻����Ӧ����
            mHttpResponse = httpClient.execute(httpGet);
            System.out.println("mHttpResponse: ");
            System.out.println(mHttpResponse);
            // �����Ӧ����Ϣʵ��
            mHttpEntity = mHttpResponse.getEntity();
            // ��ȡһ��������
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
	
	
	
}
