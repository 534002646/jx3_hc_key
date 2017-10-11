package com.vincent.key.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;

public class IconUtil {
	
	private static AtomicInteger loopId = new AtomicInteger(0);
	//图标列表
	private static List<ImageIcon> iconList = new ArrayList<>();
	
	public static void init() throws Exception{
		Map<String, byte[]> fileMap = ResourceUtil.getFileMap();
		for(Map.Entry<String, byte[]> entry : fileMap.entrySet()) {
			if(!entry.getKey().endsWith("png")) {
				continue;
			}
			iconList.add(new ImageIcon(entry.getValue()));
		}
	}
	/** 获取图标 */
	public static ImageIcon getIcon(){
		int id = loopId.incrementAndGet();
		int index = id % iconList.size();
		return iconList.get(index);
	}
	
	/** 获取随机图标 */
	public static ImageIcon getRandomIcon(){
		int index = RandomUtil.randomInt(0, iconList.size());
		return iconList.get(index);
	}
}
