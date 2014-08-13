package com.boding.task;

import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.boding.app.BodingBaseActivity;
import com.boding.constants.Constants;
import com.boding.constants.HTTPAction;
import com.boding.util.Encryption;
import com.boding.view.dialog.TwoOptionsDialog;
import com.boding.view.dialog.TwoOptionsDialog.OnOptionSelectedListener;

public class UpdateAppTask extends BodingBaseAsyncTask{

	public UpdateAppTask(Context context, HTTPAction action) {
		super(context, action);
	}

	private boolean checkUpdates(){
		boolean resultCode = true;
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
		String urlFormat = "http://api.iboding.com/API/User/MyInfo/SetPwd.ashx?userid=%s&cardno=%s&new_pwd=%s&sign=%s";
//		String urlStr =  String.format(urlFormat,Constants.BODINGACCOUNT,
//				cardNo,newPwd,sign);
		
//		String result = connectingServer(urlStr);
//		try {
//			JSONObject resultJson = new JSONObject(result);
//			String jsonResultCode = resultJson.getString("result"); 
//			if(jsonResultCode.equals("0")){
//				resultCode = true;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		return resultCode;
	}
	
	private void downloadApk(){
//		String urlStr = intent.getStringExtra(Constants.APK_DOWNLOAD_URL);
//		InputStream in=null;
//		FileOutputStream out = null;
//		try {
//		    URL url = new URL(urlStr);
//		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//		    urlConnection.setRequestMethod("GET");
//		    urlConnection.setDoOutput(false);
//		    urlConnection.setConnectTimeout(10 * 1000);
//		    urlConnection.setReadTimeout(10 * 1000);
//		    urlConnection.setRequestProperty("Connection", "Keep-Alive");
//		    urlConnection.setRequestProperty("Charset", "UTF-8");
//		    urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
//		    urlConnection.connect();
//		    long bytetotal = urlConnection.getContentLength();
//		    long bytesum = 0;
//		    int byteread = 0;
//		    in = urlConnection.getInputStream();
//		    File dir = StorageUtils.getCacheDirectory(this);
//		    String apkName=urlStr.substring(urlStr.lastIndexOf("/")+1, urlStr.length());
//		    File apkFile = new File(dir, apkName);
//		    out = new FileOutputStream(apkFile);
//		    byte[] buffer = new byte[BUFFER_SIZE];
//		    int oldProgress = 0;
//		    while ((byteread = in.read(buffer)) != -1) {
//		        bytesum += byteread;
//		        out.write(buffer, 0, byteread);
//		    }
	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		Object result = new Object();
		switch (action) {
			case CHECK_UPDATES:
				result = checkUpdates();
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
				if((Boolean) result){
					final TwoOptionsDialog dialog = new TwoOptionsDialog(context);
					dialog.setContent("发现新版本,建议立即更新使用");
					dialog.setLeftOption("更新");
					dialog.setRightOption("取消");
					dialog.setOnOptionSelectedListener(new OnOptionSelectedListener() {
						@Override
						public void OnItemClick(int option) {
							if(option == 0){
								System.out.println("update..............");
								downloadApk();
							}else{
								dialog.dismiss();
							}
						}
					});
					dialog.show();
				}
				break;
		}
	}

}
