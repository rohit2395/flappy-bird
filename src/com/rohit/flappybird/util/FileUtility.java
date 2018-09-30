package com.rohit.flappybird.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

public class FileUtility {

	private FileUtility() {

	}

	public static String loadAsString(String file) {
		String result = "";
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = bf.readLine()) != null) {
				result += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public static ByteBuffer loadAsByteBuffer (String ImageName) throws IOException {
		try {
            BufferedImage bi = ImageIO.read(new java.io.File(ImageName));
            byte[] iconData = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
            ByteBuffer ib = BufferUtils.createByteBuffer(iconData);
            ib.order(ByteOrder.nativeOrder());
            ib.put(iconData, 0, iconData.length);
            ib.flip();
            return ib;
        }
        catch (Exception e){
            System.out.println("Couldn't open icon image..." + e.toString());
            return null;
        }
		}

}
