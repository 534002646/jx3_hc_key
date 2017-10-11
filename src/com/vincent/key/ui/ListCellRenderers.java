package com.vincent.key.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.SwingConstants;

import com.vincent.key.domain.FunctionCode;
import com.vincent.key.domain.KeyCode;
import com.vincent.key.domain.KeyOrder;

public enum ListCellRenderers {
	KEY_LIST{
		@Override
		public DefaultListCellRenderer getListCellRenderer() {
			return new DefaultListCellRenderer(){ 
				private static final long serialVersionUID = -6164303918764873800L;
				@Override  
			    public Component getListCellRendererComponent(
			    		JList<?> list,Object value, int index, boolean isSelected, boolean cellHasFocus) {
					if(value instanceof KeyCode){
						KeyCode code = (KeyCode)value;
			            setIcon(code.getImageIcon());
			            setText(String.format("按键[%s]>>键码[%s]", code.getDesc(), code.getKeyCode()));
			            setFont(UI.defaultFont_12);
			            if (isSelected) {  
			                setBackground(list.getSelectionBackground());  
			                setForeground(list.getSelectionForeground());
			            } else {  
			                // 设置选取与取消选取的前景与背景颜色
			                setBackground(list.getBackground());  
			                setForeground(list.getForeground());  
			            }
					}
					return this;  
		        }  
			};
		}
	},
	ORDER_LIST{
		@Override
		public DefaultListCellRenderer getListCellRenderer() {
			return new DefaultListCellRenderer(){
				private static final long serialVersionUID = -1268991162721244457L;
				@Override  
			    public Component getListCellRendererComponent(
		    		JList<?> list,Object value, int index, boolean isSelected, boolean cellHasFocus) {
					if(value instanceof KeyOrder){
						KeyOrder order = (KeyOrder)value;
						setHorizontalAlignment(SwingConstants.CENTER);
				        setText(order.getDesc());
				        setFont(UI.defaultFont_12);
				        if (isSelected) {  
			                setBackground(list.getSelectionBackground());  
			                setForeground(list.getSelectionForeground());  
			            } else {  
			                // 设置选取与取消选取的前景与背景颜色
			                setBackground(list.getBackground());  
			                setForeground(list.getForeground());  
			            }
					}
					return this;
				}
			};
		}
	},
	START_STOP_LIST{
		@Override
		public DefaultListCellRenderer getListCellRenderer() {
			return new DefaultListCellRenderer() {
				private static final long serialVersionUID = 6949572623575119644L;
				
				@Override  
			    public Component getListCellRendererComponent(
		    		JList<?> list,Object value, int index, boolean isSelected, boolean cellHasFocus) {
					if(value instanceof KeyCode){
						KeyCode code = (KeyCode)value;
						setHorizontalAlignment(SwingConstants.CENTER);
				        setText(code.getDesc());
				        setFont(UI.defaultFont_12);
				        if (isSelected) {  
			                setBackground(list.getSelectionBackground());  
			                setForeground(list.getSelectionForeground());  
			            } else {
			                // 设置选取与取消选取的前景与背景颜色
			                setBackground(list.getBackground());  
			                setForeground(list.getForeground());  
			            }
					}
					return this;
				}
			};
		}
		
	},
	START_STOP_TOP{
		@Override
		public DefaultListCellRenderer getListCellRenderer() {
			
			return new DefaultListCellRenderer() {
				private static final long serialVersionUID = 6949572623575119644L;
				@Override  
			    public Component getListCellRendererComponent(
		    		JList<?> list,Object value, int index, boolean isSelected, boolean cellHasFocus) {
					if(value instanceof FunctionCode){
						FunctionCode code = (FunctionCode)value;
						setHorizontalAlignment(SwingConstants.CENTER);
						setFont(UI.defaultFont_12);
						setText(code.getDesc());
				        if (isSelected) {  
			                setBackground(list.getSelectionBackground());  
			                setForeground(list.getSelectionForeground());  
			            } else {  
			                // 设置选取与取消选取的前景与背景颜色
			                setBackground(list.getBackground());  
			                setForeground(list.getForeground());  
			            }
					}
					return this;
				}
			};
		}
	};
		
	public abstract DefaultListCellRenderer getListCellRenderer();
	
	public static PressKeyUI UI;
}
