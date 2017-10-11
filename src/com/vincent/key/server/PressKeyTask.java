package com.vincent.key.server;

import javax.swing.DefaultListModel;

import com.vincent.key.domain.KeyCode;
import com.vincent.key.driver.DdDriver;
import com.vincent.key.driver.Driver;
import com.vincent.key.driver.TsDriver;
import com.vincent.key.ui.KeyUiDialog;
import com.vincent.key.ui.PressKeyUI;
import com.vincent.key.util.AudioUtil;


public class PressKeyTask extends Thread {
	//按键列表
	private DefaultListModel<KeyCode> dlm;
	//默认休眠时间
	private long sleep = 500;
	//按键运行标识
	private boolean isRuning = false;
	//0:顺序按键 1:同时按键
	private int order = 0;
	//按键驱动
	private Driver driver;
	
	@Override
	public void run() {
		
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
			if(dlm == null || dlm.size() <= 0 || sleep <= 0){
				continue;
			}
			while(isRuning){
				for(int i = 0; i < dlm.size(); i++){
					if(!isRuning){
						break;
					}
					KeyCode code = dlm.get(i);
					try {
						DD_PRESS_KEY(code);
					} catch (Exception e) {
						e.printStackTrace();
						stopKey();
						KeyUiDialog.DRIVER_ERROR.show();
						break;
					}
					if(order == 0){
						sleep();
					}
				}
				if(order == 1){
					sleep();
				}
			}
		}
	}
	
	public void sleep(){
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {}
	}
	
	/** 开始按键 */
	public void startKey(DefaultListModel<KeyCode> dlm, long sleep, int order){
		if(isRuning){
			return;
		}
		if(driver == null){
			KeyUiDialog.CHANGE_DRIVER_TIPS.show();
			return;
		}
		this.sleep = sleep;
		this.dlm = dlm;
		this.order = order;
		this.isRuning = true;
		if(PressKeyHandler.getInstance().isAudio())
			AudioUtil.play(PressKeyUI.START_KEY_MARK);
	}
	
	/** 停止按键 */
	public void stopKey(){
		if(!isRuning){
			return;
		}
		this.isRuning = false;
		if(PressKeyHandler.getInstance().isAudio())
			AudioUtil.play(PressKeyUI.STOP_KEY_MARK);
	}
	
	public boolean isRuning() {
		return isRuning;
	}

	/** 模拟按键 */
	public void DD_PRESS_KEY(KeyCode keyCode) throws Exception{
		if(driver instanceof DdDriver){
			getDriver().KeyPress(keyCode.getDdCode());
		}else if(driver instanceof TsDriver){
			getDriver().KeyPress(keyCode.getKeyCode());
		}
	}
	
	public synchronized void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	public synchronized Driver getDriver(){
		return this.driver;
	}
}
