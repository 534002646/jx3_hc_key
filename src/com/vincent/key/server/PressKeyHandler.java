package com.vincent.key.server;

import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;

import com.vincent.key.domain.KeyCode;
import com.vincent.key.domain.KeyOrder;

public class PressKeyHandler {
	
	//按键处理类
	private static PressKeyHandler keyHandler;
	//按键线程
	private PressKeyTask keyTask;
	//按键顺序Map
	private Map<Integer, KeyOrder> orderMap = new HashMap<>();
	//是否开启提示音
	private boolean isAudio = true;
	
	public PressKeyHandler(){
		orderMap.put(0, new KeyOrder(0, "顺序按键"));
		orderMap.put(1, new KeyOrder(1, "同时按键"));
	}
	
	public static PressKeyHandler getInstance(){
		if(keyHandler == null) {
			synchronized (PressKeyHandler.class) {
				keyHandler = new PressKeyHandler();
			}
		}
		return keyHandler;
	}
	
	/** 开始按键 
	 * @throws Exception */
	public void start(DefaultListModel<KeyCode> dlm, long sleep, int order) {
		getKeyTask().startKey(dlm, sleep, order);
		
	}
	
	/** 停止按键 
	 * @throws Exception */
	public void stop() {
		getKeyTask().stopKey();
	}
	
	public PressKeyTask getKeyTask() {
		return keyTask;
	}
	
	public void setKeyTask(PressKeyTask keyTask) {
		this.keyTask = keyTask;
	}

	public Map<Integer, KeyOrder> getOrderMap() {
		return orderMap;
	}
	
	public KeyOrder getKeyOrder(int order) {
		return orderMap.get(order);
	}

	public boolean isAudio() {
		return isAudio;
	}

	public void setAudio(boolean isAudio) {
		this.isAudio = isAudio;
	}
	
	/** 同一退出接口 */
	public void SysExit(){
		//退出系统
		System.exit(0);
	}
}
