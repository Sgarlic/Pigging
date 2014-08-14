package com.boding.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.boding.R;
import com.boding.app.MainActivity;
import com.boding.constants.GlobalVariables;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class DownloadApkService extends Service{
	private final static int DOWNLOAD_COMPLETE = 0;
    private final static int DOWNLOAD_FAIL = 1;
    
    //文件存储
    private File updateDir = null;
    private File updateFile = null;

    //通知栏
    private NotificationManager updateNotificationManager = null;
    private Notification updateNotification = null;
    //通知栏跳转Intent
    private Intent updateIntent = null;
    private PendingIntent updatePendingIntent = null;
    
    
    private String fileName;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建文件
        if(android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())){
            updateDir = new File(Environment.getExternalStorageDirectory(),"app/download/");
            fileName = "BoDing_V"+GlobalVariables.Latest_Version_Name+".apk";
            updateFile = new File(updateDir.getPath(),fileName);
            GlobalVariables.Latest_Version_Name = "";
        }

        this.updateNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        this.updateNotification = new Notification();

        //设置下载过程中，点击通知栏，回到主界面
        updateIntent = new Intent(this, MainActivity.class);
        updatePendingIntent = PendingIntent.getActivity(this,0,updateIntent,0);
        //设置通知栏显示内容
        updateNotification.icon = R.drawable.ic_launcher;
        updateNotification.tickerText = "开始下载 "+fileName;
        updateNotification.setLatestEventInfo(this,fileName,"0%",updatePendingIntent);
        //发出通知
        updateNotificationManager.notify(0,updateNotification);

        //开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
        new Thread(new UpdateRunnable()).start();//这个是下载的重点，是下载的过程
        
        return super.onStartCommand(intent, flags, startId);
    }
	
	private Handler updateHandler = new  Handler(){
        @Override
        public void handleMessage(Message msg) {
        	switch(msg.what){
            case DOWNLOAD_COMPLETE:
                //点击安装PendingIntent
                Uri uri = Uri.fromFile(updateFile);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                updatePendingIntent = PendingIntent.getActivity(DownloadApkService.this, 0, installIntent, 0);
                
                updateNotification.defaults = Notification.DEFAULT_SOUND;//铃声提醒 
                updateNotification.setLatestEventInfo(DownloadApkService.this, fileName, "下载完成,点击安装。", updatePendingIntent);
                updateNotificationManager.notify(0, updateNotification);
                
                //停止服务
                stopSelf();
                break;
            case DOWNLOAD_FAIL:
                //下载失败
                updateNotification.setLatestEventInfo(DownloadApkService.this, fileName, "下载完成,点击安装。",updatePendingIntent);
                updateNotificationManager.notify(0, updateNotification);
                break;
            default:
                stopSelf();
        }
        }
    };
    
    class UpdateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();
        public void run() {
            message.what = DOWNLOAD_COMPLETE;
            try{
                //增加权限;
                if(!updateDir.exists()){
                    updateDir.mkdirs();
                }
                if(!updateFile.exists()){
                    updateFile.createNewFile();
                }
                //增加权限;
                long downloadSize = downloadUpdateFile("http://api.iboding.com/API/VersionControl/Android/BoDing.apk",updateFile);
                if(downloadSize>0){
                    //下载成功
                    updateHandler.sendMessage(message);
                }
            }catch(Exception ex){
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                //下载失败
                updateHandler.sendMessage(message);
            }
        }
    }

    public long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
            //这样的下载代码很多，我就不做过多的说明
            int downloadCount = 0;
            int currentSize = 0;
            long totalSize = 0;
            int updateTotalSize = 0;
            
            HttpURLConnection httpConnection = null;
            InputStream is = null;
            FileOutputStream fos = null;
            
            try {
                URL url = new URL(downloadUrl);
                httpConnection = (HttpURLConnection)url.openConnection();
                httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
                if(currentSize > 0) {
                    httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
                }
                httpConnection.setConnectTimeout(10000);
                httpConnection.setReadTimeout(20000);
                updateTotalSize = httpConnection.getContentLength();
                if (httpConnection.getResponseCode() == 404) {
                    throw new Exception("fail!");
                }
                is = httpConnection.getInputStream();                   
                fos = new FileOutputStream(saveFile, false);
                byte buffer[] = new byte[4096];
                int readsize = 0;
                while((readsize = is.read(buffer)) > 0){
                    fos.write(buffer, 0, readsize);
                    totalSize += readsize;
                    //为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
                    if((downloadCount == 0)||(int) (totalSize*100/updateTotalSize)-10>downloadCount){ 
                        downloadCount += 10;
                        updateNotification.setLatestEventInfo(DownloadApkService.this, "正在下载"+fileName, (int)totalSize*100/updateTotalSize+"%", updatePendingIntent);
                        updateNotificationManager.notify(0, updateNotification);
                    }                        
                }
            } finally {
                if(httpConnection != null) {
                    httpConnection.disconnect();
                }
                if(is != null) {
                    is.close();
                }
                if(fos != null) {
                    fos.close();
                }
            }
            return totalSize;
        }
}
