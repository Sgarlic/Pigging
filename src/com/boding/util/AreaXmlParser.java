package com.boding.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.boding.constants.GlobalVariables;
import com.boding.model.AirlineView;
import com.boding.model.Bind;
import com.boding.model.Departure;
import com.boding.model.FlightClass;
import com.boding.model.FlightLine;
import com.boding.model.FlightReturn;
import com.boding.model.Price;
import com.boding.model.Province;
import com.boding.model.ReturnList;
import com.boding.model.Rule;
import com.boding.model.Segment;
import com.boding.model.Tax;

public class AreaXmlParser {
	
	public static void parse(InputStream is) throws Exception{
		Province province = null;
		String city = null;
		ArrayList<String> districtList = null;
		
		XmlPullParser parser = Xml.newPullParser(); //��android.util.Xml����һ��XmlPullParserʵ��  
        parser.setInput(is, "UTF-8");               //���������� ��ָ�����뷽ʽ  
  
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
		
        //�о���Ӧ�÷�������رգ���ʱ����ôд�ɡ�
        if(is != null){
        	is.close();
        }
	}
}