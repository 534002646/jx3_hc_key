package com.vincent.key.domain;

import javax.swing.ImageIcon;

public class KeyCode  implements Comparable<KeyCode>{
	//系统键码
	private int keyCode;
	//dd键码
	private int ddCode;
	//键码描述
	private String desc;
	//键位图标
	private ImageIcon imageIcon;
	
	public KeyCode(int keyCode, int ddCode, String desc) {
		this.keyCode = keyCode;
		this.ddCode = ddCode;
		this.desc = desc;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getDdCode() {
		return ddCode;
	}

	public void setDdCode(int ddCode) {
		this.ddCode = ddCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}
	
	@Override
	public int compareTo(KeyCode code) {
		return  this.ddCode - code.getDdCode();
	}
}
