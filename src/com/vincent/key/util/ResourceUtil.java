package com.vincent.key.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.vincent.key.driver.Driver.DriverType;
import com.vincent.key.ui.PressKeyUI;

public class ResourceUtil {
	/****************************** 资源文件名  *************************/
//	public final static String img_cj = "cangjian.png";
//	public final static String img_cy = "chunyang.png";
//	public final static String img_mj = "mingjiao.png";
//	public final static String img_qx = "qixiu.png";
//	public final static String img_sl = "shaoling.png";
//	public final static String img_tm = "tangmen.png";
//	public final static String img_tc = "tiance.png";
//	public final static String img_wh = "wanhua.png";
//	public final static String img_wd = "wudu.png";
	
	private final static String dd_dll64 = "dd_x64.dll";
	private final static String dd_dll32 = "dd_x86.dll";
	private final static String config = "config.dat";
	private final static String start_audio = "start.wav";
	private final static String stop_audio = "stop.wav";
	private final static String version = "version.properties";
	/***************************************************************/
	
	//本地路径
	private final static String resource_path = "com/vincent/key/resources.dat";
	//程序文件
	private static Map<String, byte[]> fileMap = new HashMap<>();
	//配置
	private static Map<String,String> configMap = new HashMap<>();
	
	private static String recordurl = "";
	//当前版本
	private static String currVersion = "";
	//获取版本更新地址
	private static String[] updateUrl = new String[2];;
	
	/** 初始化资源 */
	@SuppressWarnings("unchecked")
	public static void init() throws Exception {
		InputStream is = null;
		ObjectInputStream in = null;
		try {
			is = ClassLoader.getSystemClassLoader().getResourceAsStream(resource_path);
            in = new ObjectInputStream(is);
            fileMap = (Map<String, byte[]>) in.readObject();
		}finally{
			if(is != null){
				is.close();
			}
			if(in != null){
				in.close();
			}
		}
		//初始化版本信息
		initVersion();
		//初始化配置
		initConfig();
	}
	
    private static void initConfig() throws Exception{
		InputStream in = null;
		try {
			File file = new File(config);
			if(!file.exists()){
				file.createNewFile();
				initDefaultConfig();
				saveConfig();
				return;
			}
			Properties prop = new Properties();
			in = new BufferedInputStream (new FileInputStream(config));
			prop.load(in);     ///加载属性列表
			Iterator<String> it=prop.stringPropertyNames().iterator();
			while(it.hasNext()){
		      String key=it.next();
		      configMap.put(key, prop.getProperty(key));
			}
		}finally {
			if(in != null){
				 try {
					 in.close();
				} catch (IOException e1) {}
			}
		}
    }
    
    /** 初始化默认配置 */
    public static void initDefaultConfig(){
    	configMap.put(ConfigEnum.KEY_MS.getKey(), "500");
    	configMap.put(ConfigEnum.CLOSE_AUDIO.getKey(), "0");
    	configMap.put(ConfigEnum.KEYS.getKey(), "");
    	configMap.put(ConfigEnum.ORDER.getKey(), "0");
    	configMap.put(ConfigEnum.START.getKey(), "112");
    	configMap.put(ConfigEnum.START_FUNCTION.getKey(), "0");
    	configMap.put(ConfigEnum.STOP.getKey(), "112");
    	configMap.put(ConfigEnum.STOP_FUNCTION.getKey(), "0");
    	configMap.put(ConfigEnum.UUID.getKey(), RecordUtil.getUserID());
    	configMap.put(ConfigEnum.DRIVER_TYPE.getKey(), DriverType.DD.getType());
    }
    
    /** 初始化版本信息 */
    public static void initVersion() throws Exception{
    	InputStream in = null;
		try {
			byte[] verData = fileMap.get(version);
			in = new ByteArrayInputStream(verData);
			Properties prop = new Properties();
			prop.load(in);     ///加载属性列表
			currVersion = prop.getProperty("version");
			updateUrl[0] = prop.getProperty("updateurl_1");
			updateUrl[1] = prop.getProperty("updateurl_2");
			recordurl = prop.getProperty("recordurl");
		} finally{
			if(in != null){
				in.close();
			}
		}
	}
    
    public static String getConfig(ConfigEnum e) throws Exception{
    	if(configMap == null || configMap.isEmpty()){
    		initConfig();
    	}
    	return configMap.get(e.getKey());
    }
    
    public static void saveConfig() throws IOException{
    	if(configMap == null || configMap.isEmpty()){
    		return;
    	}
    	FileOutputStream oFile = null;
		try {
			Properties prop = new Properties();
			oFile = new FileOutputStream(config);
			for(Map.Entry<String, String> entry : configMap.entrySet()){
				prop.setProperty(entry.getKey(), entry.getValue());
			}
			prop.store(oFile,"");
		}finally {
			if(oFile != null){
				 try {
					oFile.close();
				} catch (IOException e1) {}
			}
		}
       
    }
    
	public static String getDD_DllPath() {
		if(getArch() == 64) {
			return dd_dll64;
		}else {
			return dd_dll32;
		}
    }
	
	public static byte[] getAudioData(int market) {
		if(market == PressKeyUI.START_KEY_MARK) {
			return fileMap.get(start_audio);
		}else {
			return fileMap.get(stop_audio);
		}
	}
	
	public static int getArch() {
		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
		if(arch.endsWith("64") || (wow64Arch != null && wow64Arch.endsWith("64"))){
			return 64;
		}else{
			return 32;
		}
	}
	
	public static Map<String, byte[]> getFileMap() {
		return fileMap;
	}
	
	public static String getCurrVersion() {
		return currVersion;
	}
	
	public static String[] getUpdateUrl() {
		return updateUrl;
	}
	
	public static Map<String, String> getConfigMap() {
		return configMap;
	}
	
	public static String getRecordurl() {
		return recordurl;
	}

	public enum ConfigEnum{
		UUID("uuid"),
		KEYS("keys"),
		KEY_MS("key_ms"),
		ORDER("order"),
		START("start"),
		STOP("stop"),
		CLOSE_AUDIO("closeAudio"),
		START_FUNCTION("start_function"),
		STOP_FUNCTION("stop_function"),
		DRIVER_TYPE("driver_type");
		
		private String key;
		
		private ConfigEnum(String key){
			this.key = key;
		}

		public String getKey() {
			return key;
		}
	}
}
