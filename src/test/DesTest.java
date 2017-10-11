package test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.vincent.key.util.FileUtil;

import sun.misc.BASE64Encoder;

public class DesTest {
	
	public static String key = "8o0KM6xt2to760s3";
	public static String iv = "8o0KM6xt2to760s3";
	
	public static void main(String[] args) {
//	 try {
//			String keyFileName = "hc_key.dat";
//			 String algorithm = "DES";
//
//			 /* 生成密钥 */
//			 SecureRandom secureRandom = new SecureRandom();
//			 KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
//			 keyGenerator.init(secureRandom);
//			 SecretKey secretKey = keyGenerator.generateKey();
//			 /* 将密钥数据保存到文件 */
//			 FileUtil.byteArrayWriteToFile(keyFileName, secretKey.getEncoded());
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		try {
//			String keyFileName = "hc_key.dat";
//			String algorithm = "DES";
//
//			/* 生成密钥 */
//			SecureRandom secureRandom = new SecureRandom();
//			byte rawKey[] = FileUtil.fileReadToByteArray(keyFileName);
//			
//			DESKeySpec desKeySpec = new DESKeySpec(rawKey);
//			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
//			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//
//			/* 创建用于实际加密的Cipher对象 */
//			Cipher cipher = Cipher.getInstance(algorithm);
//			cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
			
			File file = new File("C:\\Users\\Vincent\\Desktop\\presskey_project\\bin");
			test(file);
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (InvalidKeySpecException e) {
//			e.printStackTrace();
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		
//		//解密
//		try {
//			InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("com/vincent/key/hc_key.dat");
//			byte rawKey[] = new byte[input.available()];
//			input.read(rawKey);
//			
//			DESKeySpec dks = new DESKeySpec(Base64.decode(new String(rawKey)));
//			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//			SecretKey key = keyFactory.generateSecret(dks);
//			SecureRandom sr = new SecureRandom();
//			
//			Cipher cipher = Cipher.getInstance("DES");
//			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
//			
//			File file = new File("HotkeyListener.hc");
//			InputStream iss = new FileInputStream(file);
//			byte[] classData = new byte[iss.available()];
//			iss.read(classData);
//			  /* 解密 */
//	        byte decryptedClassData[] = cipher.doFinal(classData);
	        
//		} catch (InvalidKeyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidKeySpecException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalBlockSizeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (BadPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Base64DecodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
	}
	
	static void test(File file){
		if(file.exists()){
			File[] files = file.listFiles();
			for(File f : files){
				if(f.isDirectory()){
					createDir(f.getPath().replace("bin", "test"));
					test(f);
				}else{
					if(f.getName().endsWith("hc")){
			  			continue;
			  		}
					System.out.println("文件路径：" + f.getPath());
					test2(f);
				}
			}
		}
	}
	
	static void test2(File file){
	  	try {
		    /* 读入类文件 */
		    byte classData[] = FileUtil.fileReadToByteArray(file);
//		    byte[] encryptedClassData = new BASE64Encoder().encode(classData).getBytes("UTF-8");
		    //生成到test文件夹，以hc结尾
		    String path = file.getPath().replace("bin", "test");

		    path = path.replace("class", "hc");
		    
		    if(path.endsWith("Main.hc")){
		    	System.out.println("Main.HC.length:"+classData.length);
		    }
		    
		    /* 保存加密后的文件 */
		    FileUtil.byteArrayWriteToFile(path , classData);
		    System.out.println("***Encrypted " + path + " ***");
		    
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void createDir(String path){
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
}
