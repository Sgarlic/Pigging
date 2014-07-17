package com.boding.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.boding.model.AirlineView;
import com.boding.model.Bind;
import com.boding.model.Departure;
import com.boding.model.FlightClass;
import com.boding.model.FlightLine;
import com.boding.model.FlightReturn;
import com.boding.model.Price;
import com.boding.model.ReturnList;
import com.boding.model.Rule;
import com.boding.model.Segment;
import com.boding.model.Tax;

public class AvXmlParser {
	
	public static AirlineView parse(InputStream is) throws Exception{
		AirlineView av = null;
		List<FlightLine> lines = null;
		FlightLine flight = null;
		Departure departure = null;
		List<Segment> segments = null;
		Segment segment = null;
		FlightClass flightClass = null;
		Price price = null;
		Tax tax = null;
		Bind bind = null;
		Rule rule = null;
		ReturnList returnlist = null;
		List<FlightReturn> frlist = null;
		FlightReturn flightreturn = null;
		List<FlightClass> fclasslist = null;
		
		XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例  
        parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式  
  
        int eventType = parser.getEventType();  
        while (eventType != XmlPullParser.END_DOCUMENT) {  
            switch (eventType) {  
            case XmlPullParser.START_DOCUMENT:
                break;  
            case XmlPullParser.START_TAG:
            	if(parser.getName().equals("av")){
                	av = new AirlineView();
                	av.setResult(parser.getAttributeValue(null, "result"));
                	av.setGoDate(parser.getAttributeValue(null, "godate"));
                	av.setBackDate(parser.getAttributeValue(null, "backdate"));
                	av.setFromCity(parser.getAttributeValue(null, "from"));
                	av.setToCity(parser.getAttributeValue(null, "to"));
                	av.setIsInternational(Integer.parseInt(parser.getAttributeValue(null, "international")));
                	lines = new ArrayList<FlightLine>(); 
            	}else if (parser.getName().equals("line")) {  
                    flight = new FlightLine();  
                    flight.setFlightType(parser.getAttributeValue(null, "flighttype"));
                }else if(parser.getName().equals("departure")){
                	departure = new Departure();
                	segments = new ArrayList<Segment>();
                }else if(parser.getName().equals("segment")){
                	//System.out.println("---"+parser.getPositionDescription());
                	segment = new Segment();
                	fclasslist = new ArrayList<FlightClass>();
                	segment.setId(parser.getAttributeValue(null, "id"));
                }else if (parser.getName().equals("carrier")) {  
                    eventType = parser.next();  
                    segment.setCarrier(parser.getText());  
                }else if (parser.getName().equals("num")) {  
                    eventType = parser.next();  
                    segment.setNum(parser.getText());  
                }else if (parser.getName().equals("leacode")) {  
                    eventType = parser.next();  
                    segment.setLeacode(parser.getText());  
                }else if (parser.getName().equals("arrcode")) {  
                    eventType = parser.next();  
                    segment.setArrcode(parser.getText());  
                }else if (parser.getName().equals("leadate")) {  
                    eventType = parser.next();  
                    segment.setLeadate(parser.getText());  
                }else if (parser.getName().equals("arrdate")) {  
                    eventType = parser.next();  
                    segment.setArrdate(parser.getText());  
                }else if (parser.getName().equals("leatime")) {  
                    eventType = parser.next();  
                    segment.setLeatime(parser.getText());  
                }else if (parser.getName().equals("arrtime")) {  
                    eventType = parser.next();  
                    segment.setArrtime(parser.getText());  
                }else if (parser.getName().equals("plane")) {  
                    eventType = parser.next();  
                    segment.setPlane(parser.getText());  
                }else if (parser.getName().equals("stop")) {  
                    eventType = parser.next();  
                    segment.setStop(parser.getText());  
                }else if (parser.getName().equals("shareflight")) {  
                    eventType = parser.next();  
                    segment.setShareFlight(parser.getText());  
                }else if (parser.getName().equals("leaterminal")) {  
                    eventType = parser.next();  
                    segment.setLeaTerminal(parser.getText());  
                }else if (parser.getName().equals("arrterminal")) {  
                    eventType = parser.next();  
                    segment.setArrTerminal(parser.getText());  
                }else if (parser.getName().equals("duration")) {  
                    eventType = parser.next();  
                    segment.setDuration(parser.getText());  
                }else if (parser.getName().equals("carname")) {  
                    eventType = parser.next();  
                    segment.setCarname(parser.getText());  
                }else if (parser.getName().equals("leaname")) {  
                    eventType = parser.next();  
                    segment.setLeaname(parser.getText());  
                }else if (parser.getName().equals("arrname")) {  
                    eventType = parser.next();  
                    segment.setArrname(parser.getText());  
                }else if(parser.getName().equals("class")){
                	flightClass = new FlightClass();
                	flightClass.setClassType(parser.getAttributeValue(null, "classtype"));
                }else if(parser.getName().equals("id") && parser.getDepth()==6){ //区分两个同名的id标签
                	eventType = parser.next();  
                	flightClass.setId(parser.getText());
                }else if (parser.getName().equals("code")) {  
                    eventType = parser.next();  
                    flightClass.setCode(parser.getText());  
                }else if (parser.getName().equals("seat")) {  
                    eventType = parser.next();  
                    flightClass.setSeat(parser.getText());  
                }else if (parser.getName().equals("price")) {  
                	//System.out.println("*****"+parser.getPositionDescription());
                    price = new Price();
                    parser.nextTag();
                    price.setId(parser.nextText());
                    //System.out.println("$$$$$$$$"+parser.getPositionDescription());
                    parser.nextTag();
                    //System.out.println("%%%%%%%"+parser.getPositionDescription());
                    price.setFile(parser.nextText());
                    parser.nextTag();
                    price.setAdult(parser.nextText());
                    parser.nextTag();
                    price.setChild(parser.nextText());
                    parser.nextTag();
                    price.setDiscount(parser.nextText());
                    eventType = parser.next();
                }else if (parser.getName().equals("tax")) {  
                    tax = new Tax();
                    parser.nextTag();
                    tax.setAdult(parser.nextText());
                    parser.nextTag();
                    tax.setChild(parser.nextText());
                    eventType = parser.next();
                }else if (parser.getName().equals("bind")) {  
                    bind = new Bind();
                    parser.nextTag();
                    bind.setType(parser.nextText());
                    parser.nextTag();
                    bind.setGocnt(parser.nextText());
                    parser.nextTag();
                    bind.setBackcnt(parser.nextText());
                    eventType = parser.next();
                }else if (parser.getName().equals("rule")) {  
                    rule = new Rule();
                    parser.nextTag();
                    rule.setStaymin(parser.nextText());
                    parser.nextTag();
                    rule.setStaymax(parser.nextText());
                    parser.nextTag();
                    rule.setIssue(parser.nextText());
                    parser.nextTag();
                    rule.setReturnRule(parser.nextText());
                    parser.nextTag();
                    rule.setChange(parser.nextText());
                    parser.nextTag();
                    rule.setLuggage(parser.nextText());
                    parser.nextTag();
                    rule.setChilddis(parser.nextText());
                    parser.nextTag();
                    rule.setBabydis(parser.nextText());
                    parser.nextTag();
                    rule.setNoshowfee(parser.nextText());
                    parser.nextTag();
                    rule.setRemark(parser.nextText());
                    eventType = parser.next();
                }else if(parser.getName().equals("returnlist")){
                	returnlist = new ReturnList();
                	frlist = new ArrayList<FlightReturn>();
                }else if(parser.getName().equals("return")){
                	flightreturn = new FlightReturn();
                }  
                break;  
            case XmlPullParser.END_TAG:  
                if(parser.getName().equals("av")){  
                	av.setLines(lines);
                	lines = null;
                }else if (parser.getName().equals("line")){  
                    lines.add(flight);
                    flight = null;
                }else if (parser.getName().equals("departure")){
                    departure.setSegments(segments);
                    flight.setDeparture(departure);
                    departure = null;
                    segments = null;
                }else if (parser.getName().equals("returnlist")){  
                    returnlist.setReturnlist(frlist);
                    flight.setReturnlist(returnlist);
                    returnlist = null;
                    frlist = null;
                }else if (parser.getName().equals("segment")){  
                    segment.setFclasslist(fclasslist);
                    segments.add(segment);
                    fclasslist = null;
                    segment = null;
                }else if (parser.getName().equals("class")){  
                    fclasslist.add(flightClass);
                    flightClass = null;
                }else if (parser.getName().equals("bind")){  
                	//System.out.println("Bind");
                    flightClass.setBind(bind);
                    bind = null;
                }else if (parser.getName().equals("price")){  
                    flightClass.setPrice(price);
                    price = null;
                }else if (parser.getName().equals("tax")){  
                    flightClass.setTax(tax);
                    tax = null;
                }else if (parser.getName().equals("rule")){  
                    flightClass.setRule(rule);
                    rule = null;
                }else if (parser.getName().equals("return")){
                	flightreturn.setSegments(segments);
                	frlist.add(flightreturn);
                	flightreturn = null;
                	segments = null;
                }  
                break;  
            }  
            eventType = parser.next();  
        }   
		
        //感觉不应该放在这里关闭，暂时先这么写吧。
        if(is != null){
        	is.close();
        }
		return av;
	}
}
