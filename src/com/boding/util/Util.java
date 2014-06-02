package com.boding.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import com.boding.R;
import com.boding.constants.Constants;
import com.boding.constants.IntentRequestCode;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.DatePicker;

public class Util {
	/**
	 * PageNumber refers to left, middle, and right page. Use 0,1,2 to represent
	 * @param activity
	 * @param pageNumber
	 * @return
	 */
	@SuppressLint("NewApi")
	public static void setActivityBackground(Activity activity,int pageNumber,View view){
		Resources resources = activity.getResources();
		Bitmap bitmap=BitmapFactory.decodeResource(resources,R.drawable.main_background_2x);
		
		int newWidth = bitmap.getWidth()/3;
		int newHeight = bitmap.getHeight();
		
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, newWidth*pageNumber, 0, newWidth, newHeight);
		
		float scaleWidth = ((float) Constants.ScreenWidth ) / newWidth; 
	    float scaleHeight = ((float) Constants.ScreenHeight ) / newHeight;
	    
		
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight); 
	    
	    Bitmap backgroudBitmap = Bitmap.createBitmap(newBitmap, 0, 0,newWidth, newHeight, matrix, true);
	    
//	    FileOutputStream out = null;
//		try {
//		       out = new FileOutputStream("/mnt/sdcard2/backgroudBitmap.png");
//		       backgroudBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
//		       out.flush();
//		} catch (Exception e) {
//		    e.printStackTrace();
//		} finally {
//		       try{
//		           out.close();
//		       } catch(Throwable ignore) {}
//		}
//		
//		Log.d("Poding", "success");
	    
	    Drawable imagebakground = new BitmapDrawable(activity.getResources(), backgroudBitmap);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//		 	Log.i("Poding", "Background");
		 	view.setBackground(imagebakground);
		} else {
//		   	Log.i("Poding", "Drawable");
		   	view.setBackgroundDrawable(imagebakground);
		}
	}
	
	public static void setFullScreen(Activity activity){
		// 使屏幕不显示标题栏(必须要在setContentView方法执行前执行)
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏，使内容全屏显示(必须要在setContentView方法执行前执行)
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public static String getFormatedDate(int year,int month,int dayOfMonth){
		month+=1;
		String selectedDate = "%s-%s-%s";
		return String.format(
				selectedDate, String.valueOf(year),String.valueOf(month),String.valueOf(dayOfMonth));
	}
	
	public static String getFormatedDate(Calendar calendar){
		return getFormatedDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	public static long getMillIsFromDate(String date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String[] temp = date.split("-");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		newDate.setYear(Integer.valueOf(temp[0]));
		return newDate.getTime();
	}
	
	@SuppressLint("NewApi")
	public static void fixSetMinDateForDatePickerCalendarView(Calendar date, DatePicker datePicker) {
	    // Workaround for CalendarView bug relating to setMinDate():
	    // https://code.google.com/p/android/issues/detail?id=42750
	    // Set then reset the date on the calendar so that it properly
	    // shows today's date. The choice of 24 months is arbitrary.
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        CalendarView cal = datePicker.getCalendarView();
	        if (cal != null) {
	            date.add(Calendar.MONTH, 24);
	            cal.setDate(date.getTimeInMillis(), false, true);
	            date.add(Calendar.MONTH, -24);
	            cal.setDate(date.getTimeInMillis(), false, true);
	        }
	    }
	}
	
	/** 
	* 将汉字转换为全拼 
	* @param src 
	* @return String 
	*/  
	public static String getPinYin(String src)  
	{  
		char[] t1 = null; 
        t1 = src.toCharArray();  
        // System.out.println(t1.length); 
        String[] t2 = new String[t1.length]; 
        // System.out.println(t2.length); 
        // 设置汉字拼音输出的格式  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat(); 
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length; 
        try {  
            for (int i =0; i < t0; i++) {  
                // 判断能否为汉字字符  
                // System.out.println(t1[i]); 
               if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) { 
                   t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中 
                    t4 += t2[0]+" ";// 取出该汉字全拼的第一种读音并连接到字符串t4后 
               } else { 
                   // 如果不是汉字字符，间接取出字符并连接到字符串t4后 
                    t4 += Character.toString(t1[i]);  
                }  
            }  
       } catch (BadHanyuPinyinOutputFormatCombination e) { 
           e.printStackTrace();  
        }  
       return t4;  
    }
	
	   
		//获得汉语拼音首字母
	   public static String getAlpha(String str) {  
	        if (str == null) {  
	            return "#";  
	        }  
	  
	        if (str.trim().length() == 0) {  
	            return "#";  
	        }  
	  
	        char c = str.trim().substring(0, 1).charAt(0);  
	        // 正则表达式，判断首字母是否是英文字母  
	        Pattern pattern = Pattern.compile("^[A-Za-z]+$");  
	        if (pattern.matcher(c + "").matches()) {  
	            return (c + "").toUpperCase();  
	        } else {  
	            return "#";  
	        }  
	    }  
	    
	
	public static void returnToPreviousPage(Activity activity, IntentRequestCode requestCode){
		Intent intent=new Intent();
//		if(citySelected)
//			intent.putExtra("selectedDate", Util.getFormatedDate(year, month, dayOfMonth));
		activity.setResult(requestCode.getRequestCode(), intent);
		activity.finish();
	}
	
	public static String calculateFlyingtime(String leavedate, String arrivedate, String leavetime, String arrivetime){
		int duration = 0; // unit is second.
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date leaDate, arrDate;
		try {
			leaDate = sdf.parse(leavedate+" "+leavetime.substring(0, 2)+":"+leavetime.substring(2));
			arrDate = sdf.parse(arrivedate+" "+arrivetime.substring(0, 2)+":"+arrivetime.substring(2));
			duration = (int)(arrDate.getTime() - leaDate.getTime()) / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dhour = (int)(duration/ 3600);
		int dmin = (int)((duration % 3600) / 60);
		
		return "约" + dhour + "小时" + dmin + "分钟";
	}
	
	//给时间加上冒号， 如0322 -> 03:22
	public static String formatTime(String time){
		return time.substring(0, 2) + ":" + time.substring(2);
	}
}
