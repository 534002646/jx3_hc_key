package com.vincent.key.util;

import java.util.UUID;

import org.jsoup.Jsoup;

import com.vincent.key.domain.UserInfo;
import com.vincent.key.driver.Driver;

public class RecordUtil {
	
	public static void uploadInfo(String log) {
		try {
			Jsoup.connect(getRecordUrl(log))
			  .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(4000).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 提交地址 */
	public static String getRecordUrl(String log) throws Exception{
		return ResourceUtil.getRecordurl().concat("?data=").concat(new UserInfo(log).base64Encode()).replaceAll("\r|\n", "");
		 
	}
	
	public static String getUserID() {
		try {
			Driver driver = Driver.initTsDriver();
			return driver.GetMachineCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String[] getSysInfo() throws Exception{
		String username = System.getProperty("user.name");//电脑名
		String osName = System.getProperty("os.name"); //操作系统名称
		String osArch = String.valueOf(ResourceUtil.getArch());
		String osVersion = System.getProperty("os.version"); //操作系统版本
		String java_version = System.getProperty("java.version");//jdk版本
		return new String[]{username, osName, osArch, osVersion, java_version};
	}
}
