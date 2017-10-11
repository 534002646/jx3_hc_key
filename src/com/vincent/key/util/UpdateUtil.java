package com.vincent.key.util;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vincent.key.server.PressKeyHandler;
import com.vincent.key.ui.KeyUiDialog;

public class UpdateUtil {
	
	private static Document doc;
	//最新版本，[0] = 版本号，[1] = 更新类型
	private static String[] version = new String[]{"0","0"};
	//更新地址
	private static String updateUrl = "";
	
	public static void init() throws IOException,Exception{
		String[] requestUrl = ResourceUtil.getUpdateUrl();
		try {
			doc = Jsoup.connect(requestUrl[0])
					  .userAgent("Mozilla").timeout(4000).get();
		} catch (Exception e) {
			doc = Jsoup.connect(requestUrl[1])
					  .userAgent("Mozilla").timeout(4000).get();
		}
		//获取版本信息
		version = initVersion();
		//获取更新地址
		updateUrl = initUpdateUrl();
	}
	
	/** 获取版本号 */
	public static String[] initVersion() throws Exception {
		Element e = doc.getElementById("kjbt");
		return e.text().split("_");
	}
	
	/** 版本更新url */
	public static String initUpdateUrl() throws Exception {
		Element e = doc.getElementById("sylj");
		Elements a = e.getElementsByTag("a");
		return a.get(0).attr("href");
	}
	
	/** 检查是否更新版本 */
	public static boolean checkVersion(){
		double currVer_d = Double.
				parseDouble(ResourceUtil.getCurrVersion());
		double ver_d = Double.parseDouble(version[0]);
		return ver_d > currVer_d;
	}
	
	/** 更新类型 */
	public static UpdateType getUpdateType(){
		return UpdateType.valueof(version[1]);
	}
	
	/** 打开更新页面 */
	public static void updateBrower(){
		 try {
			// 创建一个URI实例  
			URI uri = URI.create(updateUrl);  
			// 获取当前系统桌面扩展  
			Desktop dp = Desktop.getDesktop() ;   
			// 判断系统桌面是否支持要执行的功能  
			if (dp.isSupported(Action.BROWSE)) {  
			    // 获取系统默认浏览器打开链接   
			    dp.browse( uri ) ;  
			}
		} catch (IOException e) {
			try {
				Runtime.getRuntime().exec(String.format("rundll32 url.dll,FileProtocolHandler %s", updateUrl));
			} catch (IOException e1) {}
		}
	}
	
	/** 初始化更新 */
	public static void checkUpdate(){
		if(!checkVersion()){
			return;
		}
		//强制更新
		if(getUpdateType().
				equals(UpdateType.force_update)){
			KeyUiDialog.UPDATE_FORCE_INFO.show();
			PressKeyHandler.getInstance().SysExit();
		}else{
			//普通更新
			KeyUiDialog.UPDATE_COMM_INFO.show();
		}
	}

	public enum UpdateType{
		//强制更新
		force_update("0"),
		//普通更新
		comm_update("1");
		
		public String type;
		
		UpdateType(String type){
			this.type = type;
		}
		
		static UpdateType valueof(String type){
			for(UpdateType t : UpdateType.values()){
				if(t.type.equals(type)){
					return t;
				}
			}
			return comm_update;
		}
	}
}
