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
    
    //�ļ��洢
    private File updateDir = null;
    private File updateFile = null;

    //֪ͨ��
    private NotificationManager updateNotificationManager = null;
    private Notification updateNotification = null;
    //֪ͨ����תIntent
    private Intent updateIntent = null;
    private PendingIntent updatePendingIntent = null;
    
    
    private String fileName;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //�����ļ�
        if(android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())){
            updateDir = new File(Environment.getExternalStorageDirectory(),"app/download/");
            fileName = "BoDing_V"+GlobalVariables.Latest_Version_Name+".apk";
            updateFile = new File(updateDir.getPath(),fileName);
            GlobalVariables.Latest_Version_Name = "";
        }

        this.updateNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        this.updateNotification = new Notification();

        //�������ع����У����֪ͨ�����ص�������
        updateIntent = new Intent(this, MainActivity.class);
        updatePendingIntent = PendingIntent.getActivity(this,0,updateIntent,0);
        //����֪ͨ����ʾ����
        updateNotification.icon = R.drawable.ic_launcher;
        updateNotification.tickerText = "��ʼ���� "+fileName;
        updateNotification.setLatestEventInfo(this,fileName,"0%",updatePendingIntent);
        //����֪ͨ
        updateNotificationManager.notify(0,updateNotification);

        //����һ���µ��߳����أ����ʹ��Serviceͬ�����أ��ᵼ��ANR���⣬Service����Ҳ������
        new Thread(new UpdateRunnable()).start();//��������ص��ص㣬�����صĹ���
        
        return super.onStartCommand(intent, flags, startId);
    }
	
	private Handler updateHandler = new  Handler(){
        @Override
        public void handleMessage(Message msg) {
        	switch(msg.what){
            case DOWNLOAD_COMPLETE:
                //�����װPendingIntent
                Uri uri = Uri.fromFile(updateFile);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                updatePendingIntent = PendingIntent.getActivity(DownloadApkService.this, 0, installIntent, 0);
                
                updateNotification.defaults = Notification.DEFAULT_SOUND;//�������� 
                updateNotification.setLatestEventInfo(DownloadApkService.this, fileName, "�������,�����װ��", updatePendingIntent);
                updateNotificationManager.notify(0, updateNotification);
                
                //ֹͣ����
                stopSelf();
                break;
            case DOWNLOAD_FAIL:
                //����ʧ��
                updateNotification.setLatestEventInfo(DownloadApkService.this, fileName, "�������,�����װ��",updatePendingIntent);
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
                //����Ȩ��;
                if(!updateDir.exists()){
                    updateDir.mkdirs();
                }
                if(!updateFile.exists()){
                    updateFile.createNewFile();
                }
                //����Ȩ��;
                long downloadSize = downloadUpdateFile("http://api.iboding.com/API/VersionControl/Android/BoDing.apk",updateFile);
                if(downloadSize>0){
                    //���سɹ�
                    updateHandler.sendMessage(message);
                }
            }catch(Exception ex){
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                //����ʧ��
                updateHandler.sendMessage(message);
            }
        }
    }

    public long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
            //���������ش���ܶ࣬�ҾͲ��������˵��
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
                    //Ϊ�˷�ֹƵ����֪ͨ����Ӧ�óԽ����ٷֱ�����10��֪ͨһ��
                    if((downloadCount == 0)||(int) (totalSize*100/updateTotalSize)-10>downloadCount){ 
                        downloadCount += 10;
                        updateNotification.setLatestEventInfo(DownloadApkService.this, "��������"+fileName, (int)totalSize*100/updateTotalSize+"%", updatePendingIntent);
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
