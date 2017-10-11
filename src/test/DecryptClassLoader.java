package test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import sun.misc.BASE64Decoder;

public class DecryptClassLoader extends ClassLoader{
	  
    public static void main(String args[]) throws Exception {
  		
    	DecryptClassLoader dcl = new DecryptClassLoader();
    	Thread.currentThread().setContextClassLoader(dcl);
  		
        Class<?> clasz = dcl.findClass("com.vincent.key.server.Main");
        Object foo = clasz.newInstance();  
        // 被调用函数的参数  
        Method m = foo.getClass().getMethod("start", String.class);
        m.invoke(foo, ""); 
      
    }
    
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
		
		return this.loadClass(name, false);
    }
    
    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        /* 要创建的Class对象 */
		Class<?> clasz = null;
		/* 如果类已经在系统缓冲之中，不必再次装入它 */
		clasz = findLoadedClass(name);

		if (clasz != null){
		    return clasz;
		}
		
		InputStream input = null;
		try{
			String path = name.replace('.', '/').concat(".hc");
			/* 读取经过加密的类文件 */
			input = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
			
			if(input != null){
				byte[] fileData = new byte[input.available()];
				input.read(fileData);
				input.close();
				
				if(fileData != null && fileData.length > 0){
					
					System.out.println("解析Class:" + path + "  length:" + fileData.length);
					
//			    	byte decryptedClassData[] = new BASE64Decoder().decodeBuffer(new String(fileData, "UTF-8"));
			        /* 再把它转换成一个类 */
			        clasz = defineClass(name, fileData, 0, fileData.length);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {}
			}
		}
		
		/* 如果上面没有成功，尝试用默认的ClassLoader装入它 */
		if (clasz == null){
		    clasz = findSystemClass(name);
		}

		/* 如有必要，则装入相关的类 */
		if (resolve && clasz != null)
		    resolveClass(clasz);
		
		return clasz;
    }
}