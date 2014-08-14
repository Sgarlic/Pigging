package com.boding.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.util.Config;

import com.alipay.android.app.net.HttpClient;
import com.boding.app.BodingBaseActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.HTTPAction;
import com.boding.util.Encryption;
import com.boding.view.dialog.TwoOptionsDialog;
import com.boding.view.dialog.TwoOptionsDialog.OnOptionSelectedListener;

public class UpdateAppTask extends BodingBaseAsyncTask{
	private int versionCode;
	private String versionName;

	public UpdateAppTask(Context context, HTTPAction action) {
		super(context, action);
	}

	private void checkUpdates(){
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BODINGACCOUNT);
//		sb.append(cardNo);
//		sb.append(newPwd);
		String sign = "";
		try {
			sb.append(Encryption.getMD5(Constants.BODINGKEY).toUpperCase());
			sign = Encryption.getMD5(sb.toString()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlStr = "http://api.iboding.com/API/VersionControl/Android/VersionCheck.txt";
		
		String result = connectingServer(urlStr);
		try {
			JSONObject resultJson = new JSONObject(result);
			versionCode = Integer.parseInt(resultJson.getString("versionCode")); 
			versionName = resultJson.getString("VersionName");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
//	private void downloadApk(){
////		String urlStr = intent.getStringExtra(Constants.APK_DOWNLOAD_URL);
//		String urlStr = "http://api.iboding.com/API/VersionControl/Android/BoDing.apk";
//		HttpClient client = new DefaultHttpClient();  
//        HttpGet get = new HttpGet(urlStr);  
//        HttpResponse response;  
//        try {  
//            response = client.execute(get);  
//            HttpEntity entity = response.getEntity();  
//            long length = entity.getContentLength();  
//            InputStream is = entity.getContent();  
//            FileOutputStream fileOutputStream = null;  
//            if (is != null) {  
//                File file = new File(  
//                        Environment.getExternalStorageDirectory(),  
//                        "BoDing.aplk");  
//                fileOutputStream = new FileOutputStream(file);  
//                byte[] buf = new byte[1024];  
//                int ch = -1;  
//                int count = 0;  
//                while ((ch = is.read(buf)) != -1) {  
//                    fileOutputStream.write(buf, 0, ch);  
//                    count += ch;  
//                    if (length > 0) {  
//                    }  
//                }  
//            }  
//            fileOutputStream.flush();  
//            if (fileOutputStream != null) {  
//                fileOutputStream.close();  
//            }  
////            down();  
//        } catch (ClientProtocolException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
//	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		Object result = new Object();
		switch (action) {
			case CHECK_UPDATES:
				checkUpdates();
				break;
		}
		return result;
	}
	
	@Override  
	protected void onPostExecute(Object result) {
		if(isTimeout){
			if(action != HTTPAction.LAUNCHER_LOGIN){
				((BodingBaseActivity)context).handleTimeout();
				return;
			}
		}
		switch (action) {
			case CHECK_UPDATES:
				if(versionCode > GlobalVariables.Version_Code){
					GlobalVariables.Latest_Version_Name = versionName;
				}
				break;
		}
	}

}
