package com.vincent.key.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RandomUtil {
	
	private static SecureRandom random = new SecureRandom();
	
	public static int randomIntWithoutZero(int limit) {
		if (limit <= 0) {
			throw new IllegalArgumentException("随机数上限错误:" + limit);
		}
		int result = Math.abs(random.nextInt()) % limit;
		if( 0  == result ){
			return 1;
		}
		return  result + 1;
	}
	
	
	public static int randomInt(int limit){
		if (limit <= 0) {
			throw new IllegalArgumentException("随机数上限错误:" + limit);
		}
		return Math.abs(random.nextInt()) % limit;
	}
	
	
	public static int randomInt(int min, int max){
		int diff = max - min;
		if(diff <= 0){
			throw new IllegalArgumentException("随机数最大值必须大于最小值");
		}
		return randomInt(diff) + min;
	}
	
	public static int randomInt(){
		return Math.abs(random.nextInt());
	}
	
	/**
	 * 随机一组不重复的随机数
	 * @param limit 随机数的最大范围值
	 * @param count 需要的随机数个数
	 * @return
	 */
	public static List<Integer> randomIntList(int limit, int count){
		if(limit <= 0 || count <= 0){
			throw new IllegalArgumentException("参数错误,必须大于0 limit = " + limit +" count = "+ count);
		}
		List<Integer> randomList = new ArrayList<>(count);
		while(true){
			if(randomList.size() >= count){
				break;
			}
			int random = randomInt(limit);
			if(randomList.contains(random)){
				continue;
			}
			randomList.add(random);
		}
		return randomList;
	}
	
}
