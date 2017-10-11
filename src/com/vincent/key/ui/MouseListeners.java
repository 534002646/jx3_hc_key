package com.vincent.key.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public enum MouseListeners{
	//按键输入框
	TEXT_KEY{
		@Override
		public MouseAdapter getMouseAdapter() {
			return new MouseAdapter() {
				@Override
				public void mouseExited(MouseEvent e) {
					UI.text_key.setBackground(new Color(245, 245, 245));
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					UI.isKeyEntered = true;
					UI.text_key.setBackground(new Color(211, 211, 211));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					UI.isKeyEntered = true;
				}
			};
		}
	},
	//按键列表鼠标右键菜单
	DEL_KEY{
		@Override
		public MouseAdapter getMouseAdapter() {
			return new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					 if(e.getButton() == 3 
							 && UI.list_key.getSelectedIndex() >=0){
						 //展示菜单项
						 UI.list_popup.show(UI.list_key, e.getX() , e.getY());
					 }
				}
			};
		}
	},
	//托盘双击再现
	TRAY_VISIBLE{
		@Override
		public MouseAdapter getMouseAdapter() {
			return new MouseAdapter() {
				 @Override
					public void mouseClicked(MouseEvent e){  
                     if(e.getClickCount() == 2){//双击托盘窗口再现  
                    	 UI.setExtendedState(Frame.NORMAL);  
                    	 UI.setVisible(true);
                     }
                 }  
			};
		}
	},
	//点击空白处主窗口获得焦点
	REQUEST_FOCUS{
		@Override
		public MouseAdapter getMouseAdapter() {
			return new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					UI.requestFocus();
				}
			};
		}
	};
	
	public static PressKeyUI UI;
	
	public abstract MouseAdapter getMouseAdapter();
}
