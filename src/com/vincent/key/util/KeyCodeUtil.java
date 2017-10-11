package com.vincent.key.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.melloware.jintellitype.JIntellitype;
import com.vincent.key.domain.FunctionCode;
import com.vincent.key.domain.KeyCode;

public class KeyCodeUtil {
	//按键表
	public static List<KeyCode> KEYCODE_LIST = new ArrayList<>();
	//按键表
	public static Map<Integer, KeyCode> KEYCODE_MAP = new HashMap<>();
	//热键表
	public static List<KeyCode> HOTKEY_LIST = new ArrayList<>();
	//功能键表
	public static List<FunctionCode> FUNCTION_LIST = new ArrayList<>();
	//功能键表
	public static Map<Integer,FunctionCode> FUNCTION_MAP = new HashMap<>();
	
	private static String[] hot_key_exclude = new String[]{"45","61","92","91","93","59","222","44","46","47"};
	
	static {
		KEYCODE_LIST.add(new KeyCode(112,101,"F1"));
		KEYCODE_LIST.add(new KeyCode(113,102,"F2"));
		KEYCODE_LIST.add(new KeyCode(114,103,"F3"));
		KEYCODE_LIST.add(new KeyCode(115,104,"F4"));
		KEYCODE_LIST.add(new KeyCode(116,105,"F5"));
		KEYCODE_LIST.add(new KeyCode(117,106,"F6"));
		KEYCODE_LIST.add(new KeyCode(118,107,"F7"));
		KEYCODE_LIST.add(new KeyCode(119,108,"F8"));
		KEYCODE_LIST.add(new KeyCode(120,109,"F9"));
		KEYCODE_LIST.add(new KeyCode(121,110,"F10"));
		KEYCODE_LIST.add(new KeyCode(122,111,"F11"));
		KEYCODE_LIST.add(new KeyCode(123,112,"F12"));
		KEYCODE_LIST.add(new KeyCode(192,200,"~"));
		KEYCODE_LIST.add(new KeyCode(49,201,"1"));
		KEYCODE_LIST.add(new KeyCode(50,202,"2"));
		KEYCODE_LIST.add(new KeyCode(51,203,"3"));
		KEYCODE_LIST.add(new KeyCode(52,204,"4"));
		KEYCODE_LIST.add(new KeyCode(53,205,"5"));
		KEYCODE_LIST.add(new KeyCode(54,206,"6"));
		KEYCODE_LIST.add(new KeyCode(55,207,"7"));
		KEYCODE_LIST.add(new KeyCode(56,208,"8"));
		KEYCODE_LIST.add(new KeyCode(57,209,"9"));
		KEYCODE_LIST.add(new KeyCode(48,210,"0"));
		KEYCODE_LIST.add(new KeyCode(45,211,"-"));
		KEYCODE_LIST.add(new KeyCode(61,212,"="));
		KEYCODE_LIST.add(new KeyCode(92,213,"\\"));
		KEYCODE_LIST.add(new KeyCode(81,301,"Q"));
		KEYCODE_LIST.add(new KeyCode(87,302,"W"));
		KEYCODE_LIST.add(new KeyCode(69,303,"E"));
		KEYCODE_LIST.add(new KeyCode(82,304,"R"));
		KEYCODE_LIST.add(new KeyCode(84,305,"T"));
		KEYCODE_LIST.add(new KeyCode(89,306,"Y"));
		KEYCODE_LIST.add(new KeyCode(85,307,"U"));
		KEYCODE_LIST.add(new KeyCode(73,308,"I"));
		KEYCODE_LIST.add(new KeyCode(79,309,"O"));
		KEYCODE_LIST.add(new KeyCode(80,310,"P"));
		KEYCODE_LIST.add(new KeyCode(91,311,"["));
		KEYCODE_LIST.add(new KeyCode(93,312,"]"));
		KEYCODE_LIST.add(new KeyCode(65,401,"A"));
		KEYCODE_LIST.add(new KeyCode(83,402,"S"));
		KEYCODE_LIST.add(new KeyCode(68,403,"D"));
		KEYCODE_LIST.add(new KeyCode(70,404,"F"));
		KEYCODE_LIST.add(new KeyCode(71,405,"G"));
		KEYCODE_LIST.add(new KeyCode(72,406,"H"));
		KEYCODE_LIST.add(new KeyCode(74,407,"J"));
		KEYCODE_LIST.add(new KeyCode(75,408,"K"));
		KEYCODE_LIST.add(new KeyCode(76,409,"L"));
		KEYCODE_LIST.add(new KeyCode(59,410,";"));
		KEYCODE_LIST.add(new KeyCode(222,411,"'"));
		KEYCODE_LIST.add(new KeyCode(90,501,"Z"));
		KEYCODE_LIST.add(new KeyCode(88,502,"X"));
		KEYCODE_LIST.add(new KeyCode(67,503,"C"));
		KEYCODE_LIST.add(new KeyCode(86,504,"V"));
		KEYCODE_LIST.add(new KeyCode(66,505,"B"));
		KEYCODE_LIST.add(new KeyCode(78,506,"N"));
		KEYCODE_LIST.add(new KeyCode(77,507,"M"));
		KEYCODE_LIST.add(new KeyCode(44,508,","));
		KEYCODE_LIST.add(new KeyCode(46,509,"."));
		KEYCODE_LIST.add(new KeyCode(47,510,"/"));
		KEYCODE_LIST.add(new KeyCode(38, 709, "↑"));
		KEYCODE_LIST.add(new KeyCode(40, 711, "↓"));
		KEYCODE_LIST.add(new KeyCode(37, 710, "←"));
		KEYCODE_LIST.add(new KeyCode(39, 712, "→"));
		KEYCODE_LIST.add(new KeyCode(96,800,"小键盘0"));
		KEYCODE_LIST.add(new KeyCode(97,801,"小键盘1"));
		KEYCODE_LIST.add(new KeyCode(98,802,"小键盘2"));
		KEYCODE_LIST.add(new KeyCode(99,803,"小键盘3"));
		KEYCODE_LIST.add(new KeyCode(100,804,"小键盘4"));
		KEYCODE_LIST.add(new KeyCode(101,805,"小键盘5"));
		KEYCODE_LIST.add(new KeyCode(102,806,"小键盘6"));
		KEYCODE_LIST.add(new KeyCode(103,807,"小键盘7"));
		KEYCODE_LIST.add(new KeyCode(104,808,"小键盘8"));
		KEYCODE_LIST.add(new KeyCode(105,809,"小键盘9"));
		KEYCODE_LIST.add(new KeyCode(111,811,"小键盘/"));
		KEYCODE_LIST.add(new KeyCode(106,812,"小键盘*"));
		KEYCODE_LIST.add(new KeyCode(109,813,"小键盘-"));
		KEYCODE_LIST.add(new KeyCode(107,814,"小键盘+"));
		KEYCODE_LIST.add(new KeyCode(110,816,"小键盘."));
		//排序
		Collections.sort(KEYCODE_LIST);
		//放入map
		for(KeyCode code : KEYCODE_LIST) {
			KEYCODE_MAP.put(code.getKeyCode(), code);
			if(Arrays.asList(hot_key_exclude).
					contains(String.valueOf(code.getKeyCode()))){
				continue;
			}
			HOTKEY_LIST.add(code);
		}
		
		FUNCTION_LIST.add(new FunctionCode(0,0,"无"));
		FUNCTION_LIST.add(new FunctionCode(18,JIntellitype.MOD_ALT,"Alt"));
		FUNCTION_LIST.add(new FunctionCode(17,JIntellitype.MOD_CONTROL,"Ctrl"));
		FUNCTION_LIST.add(new FunctionCode(16,JIntellitype.MOD_SHIFT,"Shift"));
		FUNCTION_LIST.add(new FunctionCode(17 + 18,JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT,"Ctrl+Alt"));
		FUNCTION_LIST.add(new FunctionCode(18 + 16,JIntellitype.MOD_ALT + JIntellitype.MOD_SHIFT,"Alt+Shift"));
		FUNCTION_LIST.add(new FunctionCode(17 + 16,JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT,"Ctrl+Shift"));
		
		for(FunctionCode codes : FUNCTION_LIST){
			FUNCTION_MAP.put(codes.getKeyCode(), codes);
		}
	}
	
	/** 获取键码表 */
	public static KeyCode getKeyCode(int keyCode) {
		return KEYCODE_MAP.get(keyCode);
	}
	
	public static List<KeyCode> getCodeList(){
		return KEYCODE_LIST;
	}

	public static List<KeyCode> getHotCodeList() {
		return HOTKEY_LIST;
	}

	public static List<FunctionCode> getFunctionCodeList() {
		return FUNCTION_LIST;
	}

	public static FunctionCode getFunctionCode(int keyCode) {
		return FUNCTION_MAP.get(keyCode);
	}
}
