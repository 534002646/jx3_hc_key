package com.vincent.key.server;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.melloware.jintellitype.JIntellitype;
import com.vincent.key.driver.Driver;
import com.vincent.key.driver.Driver.DriverType;
import com.vincent.key.ui.KeyUiDialog;
import com.vincent.key.ui.PressKeyUI;
import com.vincent.key.util.IconUtil;
import com.vincent.key.util.RecordUtil;
import com.vincent.key.util.ResourceUtil;
import com.vincent.key.util.ResourceUtil.ConfigEnum;
import com.vincent.key.util.UpdateUtil;

public class Main{
	
	public static String title = "剑三红尘按键";
	
	private static PressKeyUI frame = null;
	
	public static void main(String[] args) {
		try {
			init();
			start();
		} catch (Exception e) {
			e.printStackTrace();
			RecordUtil.uploadInfo(e.toString());
			PressKeyHandler.getInstance().SysExit();
			return;
		}
		
		//加载驱动
		try {
			String type = ResourceUtil.getConfig(ConfigEnum.DRIVER_TYPE);
			if(DriverType.DD.getType().equals(type)) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
							Driver driver = Driver.initDdDriver();
							PressKeyHandler.getInstance().getKeyTask().setDriver(driver);
							if(frame != null) {
								frame.changeTitle();
							}
						} catch (Exception e) {}
					}
				}).start();
			}else {
				Driver driver = Driver.initTsDriver();
				PressKeyHandler.getInstance().getKeyTask().setDriver(driver);
				while(true) {
					try {
						Thread.sleep(2000);
					} catch (Exception e) {}
					if(frame != null) {
						frame.changeTitle();
						break;
					}
				}
			}
		} catch (Exception e) {
			KeyUiDialog.DRIVER_ERROR.show();
			e.printStackTrace();
		}
		
		RecordUtil.uploadInfo("");
	}
	
	public static void start() {
		//启动按键线程
		PressKeyTask task = new PressKeyTask();
		task.start();
		//保存线程对象
		PressKeyHandler.getInstance().setKeyTask(task);
		
		//窗体
		EventQueue.invokeLater(
				new Runnable() {
					@Override
					public void run() {
						try {
							frame = new PressKeyUI(title, ResourceUtil.getCurrVersion());
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
							RecordUtil.uploadInfo(e.toString());
							PressKeyHandler.getInstance().SysExit();
						}
					}
				});
	}
	
	
	private static void init() throws Exception{
		try {
			//程序主题
			UIManager.setLookAndFeel(
					"javax.swing.plaf.nimbus.NimbusLookAndFeel");
			//设置错误提示字体
			UIManager.put("OptionPane.buttonFont", PressKeyUI.defaultFont_13);
			UIManager.put("OptionPane.messageFont", PressKeyUI.defaultFont_13);
			
			//初始化资源模块
			ResourceUtil.init();
			//初始化图标
			IconUtil.init();
			//初始化热键dll
			JIntellitype.getInstance().loadJItypeDll();
		} catch (Exception e) {
			KeyUiDialog.INIT_RESOURCE_ERROR.show();
			throw e;
		}
		
		try {
			//初始化更新模块
			UpdateUtil.init();
			//检查更新
			UpdateUtil.checkUpdate();
		} catch (Exception e) {
			KeyUiDialog.NO_INTERNET_ERROR.show();
			throw e;
		}
	}
}