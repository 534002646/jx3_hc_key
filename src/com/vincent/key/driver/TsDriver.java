package com.vincent.key.driver;

import java.io.IOException;

import com.jacob.activeX.ActiveXComponent;

/** 天使驱动 */
public class TsDriver extends Driver{
	
	private ActiveXComponent com = null;
	
	/** 初始化天使驱动 */
	@Override
	public void init() throws Exception {
		if(com != null){
			return;
		}
		try {
			com = new ActiveXComponent("ts.tssoft");
		} catch (Exception e) {
			Runtime.getRuntime().exec("regsvr32 /s ts_x86.dll");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {}
			com = new ActiveXComponent("ts.tssoft");
		}
		if(com == null){
			throw new Exception("TS驱动异常！");
		}
		//设置为驱动模拟
		SetSimMode(1);
	}
	
	/**
	 * 启用超级模拟
	 * @param mode 0:正常模式(默认模式) 1: 超级模拟(隐藏驱动)
	 */
	@Override
	public void SetSimMode(int mode){
		com.invoke("SetSimMode", mode);
	}
	/**
	 * 虚拟按键
	 * @param keyCode
	 */
	@Override
	public void KeyPress(int keyCode){
		com.invoke("KeyPress", keyCode);
	}
	
	@Override
	public String GetMachineCode() {
		return com.invoke("GetMachineCode").toString();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
//		Runtime.getRuntime().exec("regsvr32 /s ts_x86.dll");
//		Runtime.getRuntime().exec("regsvr32 /u ts_x86.dll");
		
		Driver t = new TsDriver();
		try {
			t.init();
			System.out.println(t.GetMachineCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
////		while(true) {
////			System.out.println(t.zkem.invoke("WaitKey", new Variant(51), new Variant(0)));
////			Thread.sleep(1000);
////		}
//		
//		while(true) {
//			t.KeyPress(51);
//			Thread.sleep(1);
//		}
////		System.out.println(System.currentTimeMillis() - a);
	}
}
