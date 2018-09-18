package com.rohit.flappybird.graphics;

import java.awt.Image;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rohit.flappybird.util.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

	private int width, height;

	private int texture;

	public Texture(String path) {
		texture = load(path);
	}

	private int load(String path) {

		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.setRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[] data = new int[width * height];
		for (int i = 0; i < width * height; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		int tex = glGenTextures();
		
		//bind texture (select texture)
		glBindTexture(GL_TEXTURE_2D,tex);
		
		//disable anti-aliasing
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
		
		//lwjgl wants buffers and not arrays
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA, width,height,0,GL_RGBA,GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
	
		
		//release texture
		glBindTexture(GL_TEXTURE_2D, 0);
		return tex;
	}
	
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getID() {
		return texture;
	}

}
