package com.vincent.key.driver;

import com.sun.jna.Library;

/** DD驱动 */
public interface DDInterface extends Library{
		
	 /**
	  * 模拟鼠标结对移动(以屏幕左上角为原点)
	  * @param x
	  * @param y
	  * @return
	  */
	 public int DD_mov(int x, int y);
	 /**
	  * 模拟鼠标相对移动(以当前坐标为原点)
	  * @param dx
	  * @param dy
	  * @return
	  */
	 public int DD_movR(int dx, int dy);
	 
	 /**
	  * 模拟鼠标点击( 1 =左键按下 ,2 =左键放开,4 =右键按下 ,
	  * 8 =右键放开,16 =中键按下 ,32 =中键放开,64 =4键按下 ,
	  * 128 =4键放开,256 =5键按下 ，512 =5键放开 )
	  * @param btn
	  * @return
	  */
	 public int DD_btn(int btn);
	 /**
	  * 模拟鼠标滚轮
	  * @param whl  1=前 , 2 = 后
	  * @return
	  */
	 public int DD_whl(int whl);
	 /**
	  * 模拟键盘按键
	  * @param ddcode DD虚拟键盘码表
	  * @param flag 1=按下，2=放开
	  * @return
	  */
	 public int DD_key(int ddcode, int flag);
	 
	 /**
	  * 直接输入键盘上可见字符和空格
	  * @param s 字符串
	  * @return
	  */
	 public int DD_str(String s);
	 
	 /**
	  * 虚拟键码转dd键码
	  * @param code
	  * @return
	  */
	 public int DD_todc(int code);
}
