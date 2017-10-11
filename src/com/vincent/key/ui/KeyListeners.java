package com.vincent.key.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.vincent.key.domain.KeyCode;
import com.vincent.key.util.IconUtil;
import com.vincent.key.util.KeyCodeUtil;

public enum KeyListeners {
	
	TEXT_KEY{
		@Override
		public KeyAdapter getKeyAdapter() {
			return new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent event) {
					if(!UI.isKeyEntered){
						return;
					}
					KeyCode code = KeyCodeUtil.getKeyCode(event.getKeyCode());
					if(code != null){
						code.setImageIcon(IconUtil.getIcon());
						UI.text_key.setText("按键：".concat(code.getDesc()));
						UI.currCode = code;
					}else{
						UI.text_key.setText("不支持该键位");
					}
				}
			};
		}
	},
	TEXT_MS{
		@Override
		public KeyAdapter getKeyAdapter() {
			return new KeyAdapter() {
				@Override  
	            public void keyTyped(KeyEvent e) { 
	                int temp = e.getKeyChar();
	                String s = UI.text_ms.getText();
                    //下面检查是不是在0~9之间；  
                    if((temp != 8 && (temp < 48 || temp > 57))|| s.length() >= 7 ){  
                        e.consume();    		//如果不是则消除key事件,也就是按了键盘以后没有反应;  
                    }
	             }
			};
		}
	};
	
	public abstract KeyAdapter getKeyAdapter();
	
	public static PressKeyUI UI;
}
