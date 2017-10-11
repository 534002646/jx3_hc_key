package com.vincent.key.driver;

public abstract class Driver {
	
	public static Driver initDdDriver() throws Exception{
		Driver driver = new DdDriver();
		driver.init();
		return driver;
	}
	
	public static Driver initTsDriver() throws Exception{
		Driver driver = new TsDriver();
		driver.init();
		return driver;
	}
	
	/** 初始化按键驱动 */
	public abstract void init() throws Exception;
	
	/**
	 * 虚拟按键
	 * @param keyCode
	 */
	public abstract void KeyPress(int keyCode);
	
	/**
	 * 启用超级模拟
	 * @param mode 0:正常模式(默认模式) 1: 超级模拟(隐藏驱动)
	 */
	public void SetSimMode(int mode){}
	
	/**
	 * 获取机器码
	 * @return
	 */
	public String GetMachineCode() {
		return "";
	}
	
	/** 驱动类型 */
	public enum DriverType {
		
		DD("1"),
		TS("2");
		
		private String type;
		
		private DriverType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
		
		public DriverType valueof(String type) {
			for(DriverType t : DriverType.values()) {
				if(t.getType().equals(type)) {
					return t;
				}
			}
			return DD;
		}
	}
}
