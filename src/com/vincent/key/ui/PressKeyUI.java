package com.vincent.key.ui;

import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

import javax.swing.border.TitledBorder;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.vincent.key.domain.FunctionCode;
import com.vincent.key.domain.KeyCode;
import com.vincent.key.domain.KeyOrder;
import com.vincent.key.driver.DdDriver;
import com.vincent.key.driver.TsDriver;
import com.vincent.key.driver.Driver.DriverType;
import com.vincent.key.server.Main;
import com.vincent.key.server.PressKeyHandler;
import com.vincent.key.server.PressKeyTask;
import com.vincent.key.util.IconUtil;
import com.vincent.key.util.KeyCodeUtil;
import com.vincent.key.util.RecordUtil;
import com.vincent.key.util.ResourceUtil;
import com.vincent.key.util.ResourceUtil.ConfigEnum;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;

public class PressKeyUI extends JFrame implements HotkeyListener{

	private static final long serialVersionUID = -1819883572010298567L;

	private JPanel contentPane;
	
	protected TrayIcon trayIcon;//托盘图标
	protected SystemTray systemTray;//系统托盘  
    
	protected PopupMenu list_popup;
	protected JButton btn_addkey;
	protected JCheckBox checkbox_close;
	protected JList<KeyCode> list_key;
	protected JTextField text_key,text_ms;
	protected MenuItem pauseItem,closeAudio,changeDriver; //托盘菜单
	protected JComboBox<?> combo_order,combo_start_top,combo_stop_top,combo_start,combo_stop;
	protected JLabel label_list,label_speed,label_ms,label_start,label_stop,label_order,lblDd;
	
	//JList数据模型
	protected DefaultListModel<KeyCode> dlm = new DefaultListModel<KeyCode>();
	//JComboBox数据模型
	protected DefaultComboBoxModel<KeyOrder> order_model = new DefaultComboBoxModel<KeyOrder>();
	protected DefaultComboBoxModel<FunctionCode> start_top_model = new DefaultComboBoxModel<FunctionCode>();
	protected DefaultComboBoxModel<FunctionCode> stop_top_model = new DefaultComboBoxModel<FunctionCode>();
	protected DefaultComboBoxModel<KeyCode> start_model = new DefaultComboBoxModel<KeyCode>();
	protected DefaultComboBoxModel<KeyCode> stop_model = new DefaultComboBoxModel<KeyCode>();
	
	//默认字体
	public static Font defaultFont_13 = new Font("微软雅黑", Font.PLAIN, 13);
	//字体
	protected Font defaultFont_12 = new Font("微软雅黑", Font.PLAIN, 12);
	//默认边框
	protected Border defaultBorder = new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE);
	//是否可以获取按键
	protected boolean isKeyEntered = false;
	//通知是否提示过了
	protected boolean isTips = false;
	//当前程序图标
	protected ImageIcon icon;
	//当前键码,开始结束按键快捷键
	protected KeyCode currCode,stratCode, stopCode;
	//组合键快捷键
	protected FunctionCode start_function,stop_function;
	//按键顺序
	protected KeyOrder order;
	//true可以使用  false暂停使用
	protected boolean isKeyState = true;
	
	public final static int START_KEY_MARK = 0;
	public final static int STOP_KEY_MARK = 1;
	
	/** 初始化数据 */
	private void initData() throws Exception{
		//按键速度
		text_ms.setText(ResourceUtil.getConfig(ConfigEnum.KEY_MS));
		
		String select = ResourceUtil.getConfig(ConfigEnum.CLOSE_AUDIO);
		if(select.equals("1")){
			checkbox_close.setSelected(true);
			closeAudio.setLabel("开启提示音");
			PressKeyHandler.getInstance().setAudio(false);
		}
		//按键顺序
		order = PressKeyHandler.getInstance().getKeyOrder(
				Integer.valueOf(ResourceUtil.getConfig(ConfigEnum.ORDER)));
		combo_order.setSelectedItem(order);
		
		//开始组合键
		start_function = KeyCodeUtil.getFunctionCode(
				Integer.valueOf(ResourceUtil.getConfig(ConfigEnum.START_FUNCTION)));
		//结束组合键
		stop_function = KeyCodeUtil.getFunctionCode(
				Integer.valueOf(ResourceUtil.getConfig(ConfigEnum.STOP_FUNCTION)));
		//开始按键
		stratCode = KeyCodeUtil.getKeyCode(Integer.valueOf(ResourceUtil.getConfig(ConfigEnum.START)));
		//结束按键
		stopCode = KeyCodeUtil.getKeyCode(Integer.valueOf(ResourceUtil.getConfig(ConfigEnum.STOP)));
		
		//设置默认选择配置中的热键（会触发选择事件）
		combo_start_top.setSelectedItem(start_function);
		combo_stop_top.setSelectedItem(stop_function);
		combo_start.setSelectedItem(stratCode);
		combo_stop.setSelectedItem(stopCode);
		//设置按键列表
		String[] keys = ResourceUtil.getConfig(ConfigEnum.KEYS).split(",");
		if(keys != null && keys.length > 0){
			for(String key : keys){
				if(key == null || "".equals(key)){
					continue;
				}
				KeyCode keyCode = KeyCodeUtil.getKeyCode(Integer.valueOf(key));
				if(keyCode == null){
					continue;
				}
				keyCode.setImageIcon(IconUtil.getIcon());
				dlm.addElement(keyCode);
			}
			list_key.setModel(dlm);
		}
	}

	/** 初始化表格 */
	private void initLable() throws Exception{
		label_list = new JLabel("按键列表：");
		label_list.setFont(defaultFont_13);
		
		label_speed = new JLabel("按键速度：");
		label_speed.setFont(defaultFont_13);
		
		label_ms = new JLabel("毫秒");
		label_ms.setFont(defaultFont_13);
		
		label_start = new JLabel("按键开启：");
		label_start.setFont(defaultFont_13);
		
		label_stop = new JLabel("按键关闭：");
		label_stop.setFont(defaultFont_13);
		
		label_order = new JLabel("按键顺序：");
		label_order.setFont(defaultFont_13);
		
		lblDd = new JLabel("如果按键不能使用，可尝试切换驱动(〃'▽'〃)");
		lblDd.setForeground(new Color(70,70,70));
		lblDd.setFont(defaultFont_12);
	}
	
	/** 初始化按钮 */
	private void initButton() throws Exception{
		//添加按键按钮
		btn_addkey = new JButton("添加按键");
		btn_addkey.setFont(defaultFont_13);
		btn_addkey.addActionListener(ActionListeners.BTN_ADDKEY);
	}
	
	/** 初始化输入框 */
	private void initTextField() throws Exception{
		//按键输入框
		text_key = new JTextField();
		text_key.setBackground(new Color(245, 245, 245));
		text_key.setBorder(new LineBorder(new Color(128, 128, 128)));
		text_key.setHorizontalAlignment(SwingConstants.CENTER);
		text_key.setEditable(false);
		text_key.setFont(defaultFont_13);
		text_key.setText("点击输入按键");
		//鼠标事件监听
		text_key.addMouseListener(MouseListeners.TEXT_KEY.getMouseAdapter());
		//按键事件监听
		text_key.addKeyListener(KeyListeners.TEXT_KEY.getKeyAdapter());
		
		text_ms = new JTextField();
		text_ms.setHorizontalAlignment(SwingConstants.CENTER);
		text_ms.setBorder(new LineBorder(new Color(128, 128, 128)));
		text_ms.setFont(defaultFont_13);
		text_ms.setColumns(10);
		text_ms.addKeyListener(KeyListeners.TEXT_MS.getKeyAdapter());
	}
	
	/** 初始化表格 */
	private void initJList() throws Exception{
		list_key = new JList<KeyCode>();
		list_key.setBorder(defaultBorder);
		list_key.setFont(defaultFont_13);
		//重写表格内容绘制方法
		list_key.setCellRenderer(ListCellRenderers.KEY_LIST.getListCellRenderer());
		//添加右键菜单
		list_popup = new PopupMenu();
		//菜单项
	    MenuItem delKey = new MenuItem();
	    delKey.setFont(defaultFont_13);
	    delKey.setLabel("删除");
	    delKey.addActionListener(ActionListeners.DEL_KEY);
	    list_popup.setFont(defaultFont_13);
	    //添加菜单项
	    list_popup.add(delKey);
	    //添加到list
	    list_key.add(list_popup);
	    //添加鼠标事件监听
	    list_key.addMouseListener(MouseListeners.DEL_KEY.getMouseAdapter());
	}
	
	/** 初始化下拉框 */
	private void initComboBox() throws Exception{
		for(KeyCode code : KeyCodeUtil.getHotCodeList()){
			start_model.addElement(code);
			stop_model.addElement(code);
		}
		
		for(KeyOrder order : PressKeyHandler.getInstance().getOrderMap().values()){
			order_model.addElement(order);
		}
		
		for(FunctionCode codes : KeyCodeUtil.getFunctionCodeList()){
			start_top_model.addElement(codes);
			stop_top_model.addElement(codes);
		}
		
		//按键顺序下拉框
		combo_order = new JComboBox<KeyOrder>(order_model);
		combo_order.setRenderer(ListCellRenderers.ORDER_LIST.getListCellRenderer());
		combo_order.addActionListener(ActionListeners.ORDER_SELECT);
		
		
		combo_start_top = new JComboBox<FunctionCode>(start_top_model);
		combo_start_top.setRenderer(ListCellRenderers.START_STOP_TOP.getListCellRenderer());
		combo_start_top.addActionListener(ActionListeners.START_FUN_REG_HOTKEY);
		
		combo_stop_top = new JComboBox<FunctionCode>(stop_top_model);
		combo_stop_top.setRenderer(ListCellRenderers.START_STOP_TOP.getListCellRenderer());
		combo_stop_top.addActionListener(ActionListeners.STOP_FUN_REG_HOTKEY);
		
		//按键开始快捷键下拉框
		combo_start = new JComboBox<KeyCode>(start_model);
		combo_start.setRenderer(ListCellRenderers.START_STOP_LIST.getListCellRenderer());
		combo_start.addActionListener(ActionListeners.START_REG_HOTKEY);
		
		//按键停止快捷键下拉框
		combo_stop = new JComboBox<KeyCode>(stop_model);
		combo_stop.setRenderer(ListCellRenderers.START_STOP_LIST.getListCellRenderer());
		combo_stop.addActionListener(ActionListeners.STOP_REG_HOTKEY);
	}
	
	/** 初始化复选框 */
	private void initCheckBox() throws Exception{
		checkbox_close = new JCheckBox("关闭提示音");
		checkbox_close.setFont(defaultFont_13);
		checkbox_close.addActionListener(ActionListeners.CHECK_BOX_CLOSE_AUDIO);
	}
	
	/** 初始化系统托盘 */
	private void initTrayIcon() throws Exception{
		//创建菜单
		PopupMenu popup = new PopupMenu();
		
	    MenuItem bodyItem = new MenuItem();
	    bodyItem.setFont(defaultFont_13);
	    bodyItem.setLabel("显示主界面");
	    bodyItem.addActionListener(ActionListeners.BODY_VISIBLE);
	    
	    pauseItem = new MenuItem();
	    pauseItem.setFont(defaultFont_13);
	    pauseItem.setLabel("暂停使用");
	    pauseItem.addActionListener(ActionListeners.PAUSE_KEY);
	    
	    closeAudio = new MenuItem();
	    closeAudio.setFont(defaultFont_13);
	    closeAudio.setLabel("关闭提示音");
	    closeAudio.addActionListener(ActionListeners.TRAY_CLOSE_AUDIO);
	    
	    changeDriver = new MenuItem();
	    changeDriver.setFont(defaultFont_13);
	    changeDriver.setLabel("切换驱动");
	    changeDriver.addActionListener(ActionListeners.CHANGE_DRIVER);
	    
	    MenuItem updateItem = new MenuItem();
	    updateItem.setFont(defaultFont_13);
	    updateItem.setLabel("检查更新");
	    updateItem.addActionListener(ActionListeners.CHECK_UPDATE);
	    
	    MenuItem authorItem = new MenuItem();
	    authorItem.setFont(defaultFont_13);
	    authorItem.setLabel("联系作者");
	    authorItem.addActionListener(ActionListeners.AUTHOR_INFO);
	    
	    MenuItem exitItem = new MenuItem();
	    exitItem.setFont(defaultFont_13);
	    exitItem.setLabel("退出程序");
	    exitItem.addActionListener(ActionListeners.EXIT_SYS);
	    
	    popup.add(bodyItem);
	    popup.add(closeAudio);
	    popup.add(changeDriver);
	    popup.add(pauseItem);
	    popup.add(updateItem);
	    popup.add(authorItem);
	    popup.add(exitItem);
	    
		//获得系统托盘的实例  
	    systemTray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(icon.getImage(), "剑三红尘按键", popup);
		//设置托盘的图标，icon.png与该类文件同一目录  
		systemTray.add(trayIcon);
        trayIcon.addMouseListener(MouseListeners.TRAY_VISIBLE.getMouseAdapter());
	}
	
	/** 切换标题  */
	public void changeTitle() {
		PressKeyTask task = PressKeyHandler.getInstance().getKeyTask();
		if(task.getDriver() instanceof DdDriver) {
			this.setTitle(Main.title.concat("V").concat(ResourceUtil.getCurrVersion()).concat("_DD驱动"));
		}else if(task.getDriver() instanceof TsDriver) {
			this.setTitle(Main.title.concat("V").concat(ResourceUtil.getCurrVersion()).concat("_TS驱动"));
		}
	}
	
	/** 构建窗体  */
	public PressKeyUI(String title, String version) throws Exception {
		ActionListeners.UI = this;
		MouseListeners.UI = this;
		KeyListeners.UI = this;
		ListCellRenderers.UI = this;
		KeyUiDialog.UI = this;
		
		//随机一个托盘图标
		this.icon = IconUtil.getRandomIcon();
		
		this.setTitle(title.concat("V").concat(version).concat("_Loading..."));
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setBounds(100, 100, 454, 298);
		
		//设置窗口不可改变大小
		this.setResizable(false);
		this.setFont(defaultFont_13);
		//窗体居中显示
		this.setLocationRelativeTo(null);
		//设置程序图标
		this.setIconImage(icon.getImage());
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		this.setContentPane(contentPane);
		contentPane.addMouseListener(MouseListeners.REQUEST_FOCUS.getMouseAdapter());
		
		//添加窗口监听
		this.addWindowListener(
		    	new WindowAdapter(){
		    		@Override
		            public void windowIconified(WindowEvent e){    
		    			 dispose();//窗口最小化时dispose该窗口  
		                if(!isTips){
	                        trayIcon.displayMessage("通知：", "双击打开界面，右键可关闭程序！", TrayIcon.MessageType.INFO);
	                	}
	                	isTips = true;
	                }
	    			@Override
	                public void windowClosing(WindowEvent e) {
	                	 dispose();//窗口最小化时dispose该窗口  
	                     trayIcon.displayMessage("通知：", "双击打开界面，右键可关闭程序！", TrayIcon.MessageType.INFO);
//	                	 System.exit(0);
	                }
	            });
		
		//添加热键监听
		JIntellitype.getInstance().addHotKeyListener(this);
		
		//全局监听（需要获得焦点）
//		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
//			@Override
//			public void eventDispatched(AWTEvent event) {
//				if(((KeyEvent)event).getID() == KeyEvent.KEY_PRESSED){
//					PRESSKEY(((KeyEvent)event).getKeyCode());
//				}
//			}
//		}, AWTEvent.KEY_EVENT_MASK);

		//初始化系统托盘图标
		initTrayIcon();
		//初始化button
		initButton();
		//初始化lable
		initLable();
		//初始化输入框
		initTextField();
		//初始化表格
		initJList();
		//初始化下拉框
		initComboBox();
		//初始化复选框
		initCheckBox();
		try {
			//初始化数据
			initData();
		} catch (Exception e1) {
			//初始化默认配置
			ResourceUtil.initDefaultConfig();
			//初始化数据
			initData();
		}
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label_list)
						.addComponent(list_key, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(label_start)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(combo_start_top, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(label_stop)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(combo_stop_top, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(combo_start, 0, 82, Short.MAX_VALUE)
										.addComponent(combo_stop, Alignment.LEADING, 0, 82, Short.MAX_VALUE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(label_speed)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(text_ms, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
									.addComponent(label_ms)
									.addGap(36))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(text_key, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(btn_addkey, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(label_order)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(combo_order, 0, 90, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkbox_close, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblDd)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(text_key, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btn_addkey, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_order)
								.addComponent(combo_order, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkbox_close))
							.addGap(20)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_ms)
								.addComponent(label_speed)
								.addComponent(text_ms, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(21)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_start)
								.addComponent(combo_start, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(combo_start_top, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_stop)
								.addComponent(combo_stop_top, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(combo_stop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(lblDd))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_list)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(list_key, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	@Override
	public void onHotKey(int identifier) {
		switch(identifier){
			case START_KEY_MARK:
				PRESSKEY(start_function.getFunctionCode() + stratCode.getKeyCode());
				break;
			case STOP_KEY_MARK:
				PRESSKEY(stop_function.getFunctionCode() + stopCode.getKeyCode());
				break;
		}
	}
	
	/** 保存配置  */
	protected void saveConfig() throws IOException{
		Map<String, String> configMap = ResourceUtil.getConfigMap();
		for(ConfigEnum e : ConfigEnum.values()){
			String value = "";
			switch (e) {
				case KEYS:
					for(int i = 0; i < dlm.size(); i++){
						KeyCode code = dlm.get(i);
						value += code.getKeyCode() + ",";
					}
					break;
					
				case KEY_MS:
					value = text_ms.getText();
					break;
					
				case ORDER:
					KeyOrder order = order_model.
						getElementAt(combo_order.getSelectedIndex());
					value = String.valueOf(order.getOrder());
					break;
					
				case START:
					KeyCode start_code = start_model.
						getElementAt(combo_start.getSelectedIndex());
					value = String.valueOf(start_code.getKeyCode());
					break;
					
				case STOP:
					KeyCode stop_code = stop_model.
						getElementAt(combo_stop.getSelectedIndex());
					value = String.valueOf(stop_code.getKeyCode());
					break;
				case CLOSE_AUDIO:
					//1:关闭， 0：开启
					value = checkbox_close.isSelected() ? "1" : "0";
					break;
				case START_FUNCTION:
					FunctionCode start_top_code = start_top_model.
						getElementAt(combo_start_top.getSelectedIndex());
					value = String.valueOf(start_top_code.getKeyCode());
					break;
				case STOP_FUNCTION:
					FunctionCode stop_top_code = stop_top_model.
						getElementAt(combo_stop_top.getSelectedIndex());
					value = String.valueOf(stop_top_code.getKeyCode());
					break;
				case UUID:
					value = configMap.get(ConfigEnum.UUID.getKey());
					if(value == null) {
						value = RecordUtil.getUserID();
					}
					break;
				case DRIVER_TYPE:
					PressKeyTask task = PressKeyHandler.getInstance().getKeyTask();
					if(task.getDriver() instanceof DdDriver) {
						value = DriverType.DD.getType();
					}else {
						value = DriverType.TS.getType();
					}
					break;
					
			}
			
			configMap.put(e.getKey(), value);
		}
		ResourceUtil.saveConfig();
	}
	
	private void PRESSKEY(int code){
		if(stratCode == null){
			KeyUiDialog.SELECT_START_KEY_ERROR.show();
			return;
		}
		if(stopCode == null){
			KeyUiDialog.SELECT_STOP_KEY_ERROR.show();
			return;
		}
		
		if(code == start_function.getFunctionCode() + stratCode.getKeyCode()){
			if(dlm == null || dlm.size() <= 0){
				KeyUiDialog.KEY_LIST_IS_NULL.show();
				return;
			}
			
			for(int i = 0; i < dlm.size(); i++){
				KeyCode kc = dlm.get(i);
				if(kc.getKeyCode() == stratCode.getKeyCode() + start_function.getFunctionCode() || 
						kc.getKeyCode() == stopCode.getKeyCode() + stop_function.getFunctionCode()){
					KeyUiDialog.START_STOP_CONFLICT_ERROR.show();
					return;
				}
			}
			
			long ms = 500;
			try {
				ms = Long.parseLong(text_ms.getText());
			} catch (NumberFormatException e) {
				KeyUiDialog.KEY_MS_ERROR.show();
				return;
			}
			
			if(ms < 10){
				KeyUiDialog.MS_VALUE_ERROR.show();
				return;
			}
			
			if(start_function.getFunctionCode() + stratCode.getKeyCode() == 
					stop_function.getFunctionCode() + stopCode.getKeyCode()){
				if(!PressKeyHandler.getInstance().getKeyTask().isRuning()){
					//启动按键
					PressKeyHandler.getInstance().start(dlm, ms, order.getOrder());
				}else{
					PressKeyHandler.getInstance().stop();
				}
				return;
			}
			
			//启动按键
			PressKeyHandler.getInstance().start(dlm, ms, order.getOrder());
			
		}else if(code == stop_function.getFunctionCode()+ stopCode.getKeyCode()){
			
			//停止按键
			PressKeyHandler.getInstance().stop();
		}
	}
}
