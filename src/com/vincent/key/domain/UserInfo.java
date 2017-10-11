package com.vincent.key.domain;

import com.vincent.key.util.RecordUtil;
import com.vincent.key.util.ResourceUtil;
import com.vincent.key.util.ResourceUtil.ConfigEnum;

import sun.misc.BASE64Encoder;

public class UserInfo {
	private String user_id;
	
	private String user_name;
	
	private String sys_name;
	
	private String sys_arch;
	
	private String sys_version;
	
	private String java_version;
	
	private String error;
	
	private String version;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getSys_name() {
		return sys_name;
	}

	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}

	public String getSys_arch() {
		return sys_arch;
	}

	public void setSys_arch(String sys_arch) {
		this.sys_arch = sys_arch;
	}

	public String getSys_version() {
		return sys_version;
	}

	public void setSys_version(String sys_version) {
		this.sys_version = sys_version;
	}

	public String getJava_version() {
		return java_version;
	}

	public void setJava_version(String java_version) {
		this.java_version = java_version;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public UserInfo(String error) throws Exception {
		 String[] si = RecordUtil.getSysInfo();
		 setUser_id(ResourceUtil.getConfig(ConfigEnum.UUID));
		 setUser_name(si[0]);
		 setSys_name(si[1]);
		 setSys_arch(si[2]);
		 setSys_version(si[3]);
		 setJava_version(si[4]);
		 setVersion(ResourceUtil.getCurrVersion());
		 setError(error);
	}
	
	public String base64Encode() {
		StringBuffer sb = new StringBuffer();
		sb.append(user_id).append("|")
		.append(user_name).append("|")
		.append(sys_name).append("|")
		.append(sys_arch).append("|")
		.append(sys_version).append("|")
		.append(java_version).append("|")
		.append(version).append("|")
		.append(error);
		return new BASE64Encoder().encode(sb.toString().getBytes());
	}
}
