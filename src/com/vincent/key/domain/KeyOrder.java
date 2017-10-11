package com.vincent.key.domain;

public class KeyOrder {
	//按键顺序
	private int order;
	//描述
	private String desc;
	
	public KeyOrder(int order, String desc){
		this.order = order;
		this.desc = desc;
	}
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
