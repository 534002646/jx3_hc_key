package com.vincent.key.util;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class AudioUtil {
	//播放音频文件
	public static void play(int market) {
		AudioInputStream ais = null;
		SourceDataLine sdl = null;
		ByteArrayInputStream buffer = null;
		try {
			buffer = new ByteArrayInputStream(ResourceUtil.getAudioData(market));
			ais = AudioSystem.getAudioInputStream(buffer);
			AudioFormat af = ais.getFormat();
			DataLine.Info dataLine_info = new DataLine.
					Info(SourceDataLine.class, af);
			sdl = (SourceDataLine) AudioSystem.getLine(dataLine_info);
			byte[] b = new byte[1024];
			int len = 0;
			sdl.open(af, 1024);
			sdl.start();
			while ((len = ais.read(b)) > 0) {
				sdl.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ais != null){
				try {
					ais.close();
				} catch (IOException e) {}
			}
			if(buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {}
			}
			if(sdl != null){
				sdl.drain();
				sdl.close();
			}
		}
		
	}
}