package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileTest {
	public static void main(String[] args) {
		try {
			Map<String,byte[]> fileMap = new HashMap<>();
			File file = new File("C:\\Users\\Vincent\\Desktop\\resources\\");
			InputStream input = null;
			for(File f : file.listFiles()){
				try {
					System.out.println(f.getPath());
					input = new FileInputStream(f);
					byte[] classData = new byte[input.available()];
					input.read(classData);
					fileMap.put(f.getName(), classData);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					if(input != null){
						input.close();
					}
				}
			}
			File file1 =new File("resources.dat");
			FileOutputStream out = new FileOutputStream(file1);
			ObjectOutputStream objOut=new ObjectOutputStream(out);
			objOut.writeObject(fileMap);
			objOut.flush();
			objOut.close();
			out.close();
			
			for(byte[] key : fileMap.values()){
				System.out.println("KEY1:"+key.toString());
			}
			
			
//			FileInputStream in = new FileInputStream(file1);
//            ObjectInputStream objIn=new ObjectInputStream(in);
//            fileMap = (Map<String, byte[]>) objIn.readObject();
//            objIn.close();
//            
//            for(byte[] key : fileMap.values()){
//				System.out.println("KEY2:"+key.toString());
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
