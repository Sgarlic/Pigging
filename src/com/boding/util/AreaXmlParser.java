package com.boding.util;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.boding.constants.GlobalVariables;
import com.boding.model.Province;

public class AreaXmlParser {
	
	public static void parse(InputStream is) throws Exception{
		Province province = null;
		String city = null;
		ArrayList<String> districtList = null;
		
		XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例  
        parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式  
  
        int eventType = parser.getEventType();  
        while (eventType != XmlPullParser.END_DOCUMENT) {  
            switch (eventType) {  
	            case XmlPullParser.START_DOCUMENT:
	                break;  
	            case XmlPullParser.START_TAG:
	            	if(parser.getName().equals("provinceName")){
	            		province = new Province(parser.nextText());
	            		GlobalVariables.allProvincesList.add(province);
	            	}else if (parser.getName().equals("cityName")) {  
	                    city = parser.nextText();
	                    districtList = new ArrayList<String>();
	                }else if(parser.getName().equals("string")){
	                	String district = parser.nextText();
	                	districtList.add(district);
	                }
	                break;  
	            case XmlPullParser.END_TAG:  
	                if (parser.getName().equals("array")){
	                    province.addCity(city, districtList);
	                }
	                break;  
            }  
            eventType = parser.next();  
        }   
		
        //感觉不应该放在这里关闭，暂时先这么写吧。
        if(is != null){
        	is.close();
        }
	}
}
