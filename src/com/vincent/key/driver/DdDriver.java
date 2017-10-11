package com.vincent.key.driver;

import com.sun.jna.Native;
import com.vincent.key.util.ResourceUtil;

public class DdDriver extends Driver{
	
	private DDInterface INSTANCE;
	
	@Override
	public void init() throws Exception {
		 INSTANCE = (DDInterface) Native.loadLibrary(ResourceUtil.getDD_DllPath(), DDInterface.class);
	}
	
	@Override
	public void KeyPress(int ddCode){
		INSTANCE.DD_key(ddCode, 1);
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {}
		INSTANCE.DD_key(ddCode, 2);
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {}
	}
}
