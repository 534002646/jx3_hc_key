package com.vincent.key.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.melloware.jintellitype.JIntellitype;
import com.vincent.key.domain.FunctionCode;
import com.vincent.key.domain.KeyCode;
import com.vincent.key.driver.Driver;
import com.vincent.key.driver.TsDriver;
import com.vincent.key.server.PressKeyHandler;
import com.vincent.key.server.PressKeyTask;
import com.vincent.key.util.UpdateUtil;

public enum ActionListeners implements ActionListener{
	//添加按键按钮
	BTN_ADDKEY{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(UI.currCode == null){
				return;
			}
			if(UI.dlm.size() >= 10) {
				KeyUiDialog.LIST_LIMIT_ERROR.show();
				return;
			}
			
			UI.dlm.addElement(UI.currCode);
			UI.list_key.setModel(UI.dlm);
			//设置不监听键盘按键
			UI.isKeyEntered = false;
		}
	},
	
	//按键列表删除按键
	DEL_KEY{
		@Override
		public void actionPerformed(ActionEvent e) {
			UI.dlm.remove(UI.list_key.getSelectedIndex());
			UI.list_key.setModel(UI.dlm);
		}
		
	},
	
	ORDER_SELECT{
		@Override
		public void actionPerformed(ActionEvent e) {
			UI.order = UI.order_model.getElementAt(UI.combo_order.getSelectedIndex());
		}
	},
	
	START_FUN_REG_HOTKEY{
		@Override
		public void actionPerformed(ActionEvent e) {
			FunctionCode new_f_code = UI.start_top_model.
					getElementAt(UI.combo_start_top.getSelectedIndex());
			
			int startKeyCode = UI.stratCode.getKeyCode();
			int stopKeyCode = UI.stopCode.getKeyCode();
			int start_function_code = UI.start_function.getFunctionCode();
			int stop_function_code = UI.stop_function.getFunctionCode();
			
			//卸载热键
			JIntellitype.getInstance().unregisterHotKey(PressKeyUI.START_KEY_MARK);
			
			//如果开始热键和结束热键一样，则需要重新注册结束热键
			if(startKeyCode + start_function_code
					== stopKeyCode + stop_function_code){
				JIntellitype.getInstance().unregisterHotKey(PressKeyUI.STOP_KEY_MARK);
				JIntellitype.getInstance().registerHotKey(PressKeyUI.STOP_KEY_MARK, stop_function_code , stopKeyCode);
			}
			JIntellitype.getInstance().registerHotKey(PressKeyUI.START_KEY_MARK, new_f_code.getFunctionCode(), startKeyCode);  
			
			UI.start_function = new_f_code;
		}
	},
	STOP_FUN_REG_HOTKEY{
		@Override
		public void actionPerformed(ActionEvent e) {
			FunctionCode new_f_code = 
					UI.stop_top_model.getElementAt(UI.combo_stop_top.getSelectedIndex());
			
			int startKeyCode = UI.stratCode.getKeyCode();
			int stopKeyCode = UI.stopCode.getKeyCode();
			int start_function_code = UI.start_function.getFunctionCode();
			int stop_function_code = UI.stop_function.getFunctionCode();
			
			//卸载热键
			JIntellitype.getInstance().unregisterHotKey(PressKeyUI.STOP_KEY_MARK);
			
			//如果开始热键和结束热键一样，则需要重新注册结束热键
			if(startKeyCode + start_function_code
					== stopKeyCode + stop_function_code){
				JIntellitype.getInstance().unregisterHotKey(PressKeyUI.START_KEY_MARK);
				JIntellitype.getInstance().registerHotKey(PressKeyUI.START_KEY_MARK, start_function_code , startKeyCode);
			}
			JIntellitype.getInstance().registerHotKey(PressKeyUI.STOP_KEY_MARK, new_f_code.getFunctionCode(), stopKeyCode);  
			
			UI.stop_function = new_f_code;
		}
	},
	
	START_REG_HOTKEY{
		@Override
		public void actionPerformed(ActionEvent e) {
			KeyCode newCode =  UI.start_model.getElementAt(UI.combo_start.getSelectedIndex());
			
			int startKeyCode = UI.stratCode.getKeyCode();
			int stopKeyCode = UI.stopCode.getKeyCode();
			int start_function_code = UI.start_function.getFunctionCode();
			int stop_function_code = UI.stop_function.getFunctionCode();
			
			//卸载热键
			JIntellitype.getInstance().unregisterHotKey(PressKeyUI.START_KEY_MARK);
			
			//如果开始热键和结束热键一样，则需要重新注册结束热键
			if(startKeyCode + start_function_code
					== stopKeyCode + stop_function_code){
				JIntellitype.getInstance().unregisterHotKey(PressKeyUI.STOP_KEY_MARK);
				JIntellitype.getInstance().registerHotKey(PressKeyUI.STOP_KEY_MARK, stop_function_code , stopKeyCode);
			}
			JIntellitype.getInstance().registerHotKey(PressKeyUI.START_KEY_MARK, start_function_code, newCode.getKeyCode());  
			
			UI.stratCode = newCode;
		}
	},
	STOP_REG_HOTKEY{
		@Override
		public void actionPerformed(ActionEvent e) {
			KeyCode newCode =  UI.stop_model.getElementAt(UI.combo_stop.getSelectedIndex());
			
			int startKeyCode = UI.stratCode.getKeyCode();
			int stopKeyCode = UI.stopCode.getKeyCode();
			int start_function_code = UI.start_function.getFunctionCode();
			int stop_function_code = UI.stop_function.getFunctionCode();
			
			JIntellitype.getInstance().unregisterHotKey(PressKeyUI.STOP_KEY_MARK);
			
			//如果开始热键和结束热键一样，则需要重新注册结束热键
			if(startKeyCode + start_function_code
					== stopKeyCode + stop_function_code){
				JIntellitype.getInstance().unregisterHotKey(PressKeyUI.START_KEY_MARK);
				JIntellitype.getInstance().registerHotKey(PressKeyUI.START_KEY_MARK,  start_function_code, startKeyCode);  
			}
			
			JIntellitype.getInstance().registerHotKey(PressKeyUI.STOP_KEY_MARK, stop_function_code, newCode.getKeyCode());
			
			UI.stopCode = newCode;
		}
	},
	BODY_VISIBLE{
		@Override
		public void actionPerformed(ActionEvent e) {
			UI.setExtendedState(Frame.NORMAL);  
			UI.setVisible(true);
		}
		
	},
	PAUSE_KEY{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(UI.stratCode == null || UI.stopCode == null) {
				return;
			}
			if(UI.isKeyState) {
				UI.pauseItem.setLabel("取消暂停使用");
				//停止按键
				PressKeyHandler.getInstance().stop();
				//卸载注册的热键
				JIntellitype.getInstance().unregisterHotKey(PressKeyUI.START_KEY_MARK);
				JIntellitype.getInstance().unregisterHotKey(PressKeyUI.STOP_KEY_MARK);
				UI.isKeyState = false;
			}else {
				UI.pauseItem.setLabel("暂停使用");
				//重新注册热键
				JIntellitype.getInstance().registerHotKey(
						PressKeyUI.START_KEY_MARK, UI.start_function.getFunctionCode(), UI.stratCode.getKeyCode());  
				JIntellitype.getInstance().registerHotKey(
						PressKeyUI.STOP_KEY_MARK,  UI.stop_function.getFunctionCode(), UI.stopCode.getKeyCode());
				UI.isKeyState = true;
			}
		}
	},
	TRAY_CLOSE_AUDIO{
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isAudio = PressKeyHandler.getInstance().isAudio();
			if(isAudio) {
				UI.closeAudio.setLabel("开启提示音");
				UI.checkbox_close.setSelected(true);
				PressKeyHandler.getInstance().setAudio(false);
			}else {
				UI.closeAudio.setLabel("关闭提示音");
				UI.checkbox_close.setSelected(false);
				PressKeyHandler.getInstance().setAudio(true);
			}
		}
	},
	CHECK_BOX_CLOSE_AUDIO{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(UI.checkbox_close.isSelected()){
				UI.closeAudio.setLabel("开启提示音");
				PressKeyHandler.getInstance().setAudio(false);
			}else{
				UI.closeAudio.setLabel("关闭提示音");
				PressKeyHandler.getInstance().setAudio(true);
			}
		}
	},
	CHANGE_DRIVER{
		@Override
		public void actionPerformed(ActionEvent e) {
			Driver driver = null;
			PressKeyHandler.getInstance().stop();
			PressKeyTask task = PressKeyHandler.getInstance().getKeyTask();
			if(task.getDriver() instanceof TsDriver){
				int result = JOptionPane.showConfirmDialog(UI.getContentPane(),"是否切换为DD驱动？（切换驱动请耐心等待）", "切换驱动", JOptionPane.YES_NO_OPTION);
				if(result == 0){
					try {
						driver = Driver.initDdDriver();
					} catch (Exception e1) {
						KeyUiDialog.LOAD_DD_DRIVER_ERROR.show();
						return;
					}
					task.setDriver(driver);
					UI.changeTitle();
					KeyUiDialog.CHANGE_DRIVER_SUCCESS.show();
				}
			}else{
				int result = JOptionPane.showConfirmDialog(UI.getContentPane(),"是否切换为TS驱动？（切换驱动请耐心等待）", "切换驱动", JOptionPane.YES_NO_OPTION);
				if(result == 0){
					try {
						driver = Driver.initTsDriver();
					} catch (Exception e1) {
						KeyUiDialog.LOAD_TS_DRIVER_ERROR.show();
						return;
					}
					task.setDriver(driver);
					UI.changeTitle();
					KeyUiDialog.CHANGE_DRIVER_SUCCESS.show();
				}
			}
		}
	},
	CHECK_UPDATE{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				UpdateUtil.init();
			}catch(Exception e1) {}
			
			if(!UpdateUtil.checkVersion()){
				KeyUiDialog.UPDATE_INFO.show();
				return;
			}
			UpdateUtil.checkUpdate();
		}
	},
	AUTHOR_INFO{
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(UI.getContentPane(),
					"使用过程中如遇到问题可联系作者（QQ：534002646）", "联系", JOptionPane.INFORMATION_MESSAGE);
		}
	},
	EXIT_SYS{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//保存配置
				UI.saveConfig();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			PressKeyHandler.getInstance().SysExit();
		}
	};
	
	public static PressKeyUI UI;
}
