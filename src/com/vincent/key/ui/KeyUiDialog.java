package com.vincent.key.ui;

import javax.swing.JOptionPane;

import com.vincent.key.util.UpdateUtil;

public enum KeyUiDialog {
	SELECT_START_KEY_ERROR{
		@Override
		public void show(){
			JOptionPane.showMessageDialog(UI.getContentPane(),"请选择按键开启的快捷键！", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	SELECT_STOP_KEY_ERROR{
		@Override
		public void show(){
			JOptionPane.showMessageDialog(UI.getContentPane(),"请选择按键关闭的快捷键！", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	KEY_LIST_IS_NULL{
		@Override
		public void show(){
			JOptionPane.showMessageDialog(UI.getContentPane(),"按键列表是空的哦！╭(╯^╰)╮", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	START_STOP_CONFLICT_ERROR{
		@Override
		public void show(){
			JOptionPane.showMessageDialog(UI.getContentPane(),"开启或关闭热键不可与按键列表中的相同哦！╭(╯^╰)╮", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	KEY_MS_ERROR{
		@Override
		public void show(){
			JOptionPane.showMessageDialog(UI.getContentPane(),"按键速度输入错误哦！(〃'▽'〃)", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	
	LIST_LIMIT_ERROR{
		@Override
		public void show(){
			JOptionPane.showMessageDialog(UI.getContentPane(),"按键列表超出上限了！╭(╯^╰)╮", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	
	MS_VALUE_ERROR{
		@Override
		public void show(){
			JOptionPane.showMessageDialog(UI.getContentPane(),"按键速度不能小于10哦！(ﾉ´▽｀)ﾉ", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	UPDATE_COMM_INFO{
		@Override
		public void show() {
			int result = JOptionPane.showConfirmDialog(null ,"当前有新的版本可以更新，要更新吗亲？(〃'▽'〃)", "更新", JOptionPane.YES_NO_OPTION);
			if(result == 0){
				UpdateUtil.updateBrower();
			}
		}
	},
	UPDATE_FORCE_INFO{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(null ,"当前版本已经太旧啦，更新到最新版本吧！╭(╯^╰)╮", "错误", JOptionPane.ERROR_MESSAGE);
			
			UpdateUtil.updateBrower();
		}
	},
	UPDATE_INFO{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(UI.getContentPane(),"当前版本已经是最新版本啦！ (〃'▽'〃)", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	},
	NO_INTERNET_ERROR{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(null,"按键驱动程序需要联网验证，请检查下您的网络吧！〒▽〒", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	INIT_RESOURCE_ERROR{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(null,"初始化资源数据错误，无法启动程序！〒▽〒", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	DRIVER_ERROR{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(UI.getContentPane(),"当前驱动加载错误，请尝试切换驱动！〒▽〒", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	LOAD_DD_DRIVER_ERROR{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(UI.getContentPane(),"DD驱动加载失败，如果提示网络错误请稍后再试！", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	LOAD_TS_DRIVER_ERROR{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(UI.getContentPane(),"TS驱动加载失败，可尝试重启程序！", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	CHANGE_DRIVER_TIPS{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(UI.getContentPane(),"当前驱动加载失败，请尝试切换驱动后使用！", "错误", JOptionPane.ERROR_MESSAGE);
		}
	},
	CHANGE_DRIVER_SUCCESS{
		@Override
		public void show() {
			JOptionPane.showMessageDialog(UI.getContentPane(),"切换成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	public abstract void show();
	
	public static PressKeyUI UI;
}
