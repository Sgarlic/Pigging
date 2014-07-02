package com.boding.model;

import java.util.List;

public class Segment {
	private String id;
	private String carrier;
	private String num;
	private String leacode;
	private String arrcode;
	private String leadate;
	private String arrdate;
	private String leatime;
	private String arrtime;
	private String plane;
	private String stop;
	private String shareFlight;
	private String leaTerminal;
	private String arrTerminal;
	private String duration;
	private String carname;
	private String leaname;
	private String arrname;
	private List<FlightClass> fclasslist;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getLeacode() {
		return leacode;
	}
	public void setLeacode(String leacode) {
		this.leacode = leacode;
	}
	public String getArrcode() {
		return arrcode;
	}
	public void setArrcode(String arrcode) {
		this.arrcode = arrcode;
	}
	public String getLeadate() {
		return leadate;
	}
	public void setLeadate(String leadate) {
		this.leadate = leadate;
	}
	public String getArrdate() {
		return arrdate;
	}
	public void setArrdate(String arrdate) {
		this.arrdate = arrdate;
	}
	public String getLeatime() {
		return leatime;
	}
	public void setLeatime(String leatime) {
		this.leatime = leatime;
	}
	public String getArrtime() {
		return arrtime;
	}
	public void setArrtime(String arrtime) {
		this.arrtime = arrtime;
	}
	public String getPlane() {
		return plane;
	}
	public void setPlane(String plane) {
		this.plane = plane;
	}
	public String getStop() {
		return stop;
	}
	public void setStop(String stop) {
		this.stop = stop;
	}
	public String getShareFlight() {
		return shareFlight;
	}
	public void setShareFlight(String shareFlight) {
		this.shareFlight = shareFlight;
	}
	public String getLeaTerminal() {
		return leaTerminal;
	}
	public void setLeaTerminal(String leaTerminal) {
		this.leaTerminal = leaTerminal;
	}
	public String getArrTerminal() {
		return arrTerminal;
	}
	public void setArrTerminal(String arrTerminal) {
		this.arrTerminal = arrTerminal;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getCarname() {
		return carname;
	}
	public void setCarname(String carname) {
		this.carname = carname;
	}
	public String getLeaname() {
		return leaname;
	}
	public void setLeaname(String leaname) {
		this.leaname = leaname;
	}
	public String getArrname() {
		return arrname;
	}
	public void setArrname(String arrname) {
		this.arrname = arrname;
	}
	public List<FlightClass> getFclasslist() {
		return fclasslist;
	}
	public void setFclasslist(List<FlightClass> fclasslist) {
		this.fclasslist = fclasslist;
	}
	
	
}
