package com.vincent.key.domain;

public class FunctionCode {

	private int keyCode;
	
	private int functionCode;
	
	private String desc;
	
	public FunctionCode(int keyCode, int functionCode, String desc){
		this.keyCode = keyCode;
		this.functionCode = functionCode;
		this.desc = desc;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(int functionCode) {
		this.functionCode = functionCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
