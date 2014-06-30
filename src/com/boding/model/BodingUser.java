package com.boding.model;

public class BodingUser {
	private boolean activated_state;//0、未激活(需激活) 1、已激活(激活后才算登录成功)
	private String mobile;//手机号为空表示未绑定手机号码
	private String cardno;//用户代码/卡号
	private String cardid;//用户id
	private String name;//姓名
	private String nickname;//昵称
	private String portrait;//头像
	private String login_type;//登录方式 :cardno 用户代码/卡号、email 邮箱 、mobile 手机 、kuaijie 快捷登录
	
	public BodingUser(boolean activated_state, String mobile, String cardno, String cardid, String name, String nickname, String portrait,
			String login_type){
		this.activated_state = activated_state;
		this.mobile = mobile;
		this.cardno = cardno;
		this.cardid = cardid;
		this.name = name;
		this.nickname = nickname;
		this.portrait = portrait;
		this.login_type = login_type;
	}
	
	public String getWelcomeName(){
		if(!name.equals(""))
			return name;
		if(!nickname.equals(""))
			return nickname;
		if(!mobile.equals(""))
			return mobile;
		return cardno;
	}
	
	public boolean isActivated_state() {
		return activated_state;
	}
	public void setActivated_state(boolean activated_state) {
		this.activated_state = activated_state;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
}
