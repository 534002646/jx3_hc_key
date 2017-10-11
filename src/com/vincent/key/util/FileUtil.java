package com.vincent.key.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class FileUtil {
	
	  /** 将文件读入byte数组 */
    public static byte[] fileReadToByteArray(String fileName) throws Exception {
    	File file = new File(fileName);
         return fileReadToByteArray(file);
    }
    
	  /** 将文件读入byte数组 */
    public static byte[] fileReadToByteArray(File file) throws Exception {
         FileInputStream fis = null;
         byte[] data = null;
         try {
        	 fis = new FileInputStream(file);
			 data = new byte[fis.available()];  
			 fis.read(data);
		}finally{
			if(fis != null){
				fis.close();
			}
		}
        return data;
    }
    
    /** 将byte数组写入到文件 */
    public static void byteArrayWriteToFile(String fileName, byte[] data) throws Exception {
        FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(data);
			fos.flush();
		} finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {}
			}
		}
    }
}
