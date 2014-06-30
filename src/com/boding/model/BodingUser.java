package com.boding.model;

public class BodingUser {
	private boolean activated_state;//0��δ����(�輤��) 1���Ѽ���(���������¼�ɹ�)
	private String mobile;//�ֻ���Ϊ�ձ�ʾδ���ֻ�����
	private String cardno;//�û�����/����
	private String cardid;//�û�id
	private String name;//����
	private String nickname;//�ǳ�
	private String portrait;//ͷ��
	private String login_type;//��¼��ʽ :cardno �û�����/���š�email ���� ��mobile �ֻ� ��kuaijie ��ݵ�¼
	
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
