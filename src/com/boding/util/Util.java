package com.boding.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.model.City;
import com.boding.view.dialog.WarningDialog;

public class Util {
//	/**
//	 * PageNumber refers to left, middle, and right page. Use 0,1,2 to represent
//	 * @param activity
//	 * @param pageNumber
//	 * @return
//	 */
//	@SuppressLint("NewApi")
//	public static void setActivityBackground(Activity activity,int pageNumber,View view){
//		Resources resources = activity.getResources();
//		Bitmap bitmap=BitmapFactory.decodeResource(resources,R.drawable.main_background_2x);
//		
//		int newWidth = bitmap.getWidth()/3;
//		int newHeight = bitmap.getHeight();
//		
//		Bitmap newBitmap = Bitmap.createBitmap(bitmap, newWidth*pageNumber, 0, newWidth, newHeight);
//		
//		float scaleWidth = ((float) GlobalVariables.Screen_Width ) / newWidth; 
//	    float scaleHeight = ((float) GlobalVariables.Screen_Height) / newHeight;
//	    
//		
//	    Matrix matrix = new Matrix();
//	    matrix.postScale(scaleWidth, scaleHeight); 
//	    
//	    Bitmap backgroudBitmap = Bitmap.createBitmap(newBitmap, 0, 0,newWidth, newHeight, matrix, true);
//	    
////	    FileOutputStream out = null;
////		try {
////		       out = new FileOutputStream("/mnt/sdcard2/backgroudBitmap.png");
////		       backgroudBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
////		       out.flush();
////		} catch (Exception e) {
////		    e.printStackTrace();
////		} finally {
////		       try{
////		           out.close();
////		       } catch(Throwable ignore) {}
////		}
////		
////		Log.d("Poding", "success");
//	    
//	    Drawable imagebakground = new BitmapDrawable(activity.getResources(), backgroudBitmap);
//		
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////		 	Log.i("Poding", "Background");
//		 	view.setBackground(imagebakground);
//		} else {
////		   	Log.i("Poding", "Drawable");
//		   	view.setBackgroundDrawable(imagebakground);
//		}
//	}
	
	@SuppressLint("NewApi")
	public static void setViewBackground(View view, Drawable drawable){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
		 	view.setBackground(drawable);
		} else {
		   	view.setBackgroundDrawable(drawable);
		}
	}
	
	public static void setFullScreen(Activity activity){
		// 使屏幕不显示标题栏(必须要在setContentView方法执行前执行)
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏，使内容全屏显示(必须要在setContentView方法执行前执行)
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                    t4 += t2[0]+"";// 取出该汉字全拼的第一种读音并连接到字符串t4后 
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
		activity.setResult(requestCode.getRequestCode(), intent);
		activity.finish();
	}
	
	public static String getFourCharofString(String string){
		String newString = string;
		if(string.length()>4)
			newString = string.substring(0,4);
		return newString;
	}
	
	public static void selectCityOperation(ListView listView, int position, boolean isFlyToCitySelection, Context context, Dialog dialog){
		City city = (City) listView.getAdapter().getItem(position);
//		String cityName = city.getCityName();
//		String cityCode = city.getCityCode();
//		boolean isInternationalCity = false;
//		String cityCountry = "";
//		
//		City selectedCity = new City(cityName,cityCode,isInternationalCity,cityCountry);
		
		String toastInfo = null;
		if(isFlyToCitySelection){
			if(city.equals(GlobalVariables.Fly_From_City))
				toastInfo = "出发和到达不能为同一城市";
			else
				GlobalVariables.Fly_To_City = city;
		}
		else{
			if(city.equals(GlobalVariables.Fly_To_City))
				toastInfo = "出发和到达不能为同一城市";
			else
				GlobalVariables.Fly_From_City = city;
		}
		
		if(toastInfo!=null){
			WarningDialog warningDialog = new WarningDialog(context);
			warningDialog.setContent(toastInfo);
			warningDialog.setKnown("知道了");
			warningDialog.show();
//			currentView.getContext().
		}else{
			if(dialog!=null)
				dialog.dismiss();
			returnToPreviousPage((Activity)context, IntentRequestCode.CITY_SELECTION);
		}
//		Log.d("poding");
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
	    if(listView == null) return;

	    ListAdapter listAdapter = listView.getAdapter(); 
	    if (listAdapter == null) { 
	        return; 
	    } 

	    int totalHeight = 0; 
	    for (int i = 0; i < listAdapter.getCount(); i++) { 
	        View listItem = listAdapter.getView(i, null, listView); 
	        listItem.measure(0, 0); 
	        totalHeight += listItem.getMeasuredHeight(); 
	    } 

	    ViewGroup.LayoutParams params = listView.getLayoutParams(); 
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
	    listView.setLayoutParams(params); 
	}
	
	
	public static Bitmap getFlightCompanyLogo(Context context, String companyCode){
		AssetManager assetManager = context.getAssets();
		Bitmap image = null;
		try {
			InputStream ims = assetManager.open("clogos/"+companyCode.toLowerCase()+".png");
			image = BitmapFactory.decodeStream(ims);  
			ims.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static String getIntStringFromDoubleString(String doubleString){
		int intValue = (int) Double.parseDouble(doubleString);
		return String.valueOf(intValue);
	}
	
	public static void showToast(Context context, String toastContent){
		Toast toast = Toast.makeText(context,toastContent, Toast.LENGTH_SHORT);
	    toast.setGravity(Gravity.CENTER, 0, 0);
	    toast.show();
	}
	
	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public static void dialPhone(Context context){
		String number = Constants.BONDING_PHONENUM;
	    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" +number));
	    context.startActivity(intent);
	}
}
