/**
 * 
 */
package com.rohit.flappybird;

import static com.rohit.flappybird.Constants.HEIGHT;
import static com.rohit.flappybird.Constants.WIDTH;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetString;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.rohit.flappybird.util.FileUtility;
import com.rohit.flappybird.util.ShaderUtils;

/**
 * @author rajbar
 *
 */
public class Game implements Runnable {

	private int width = WIDTH;
	private int height = HEIGHT;
	private String title = "Flappy Bird";

	private boolean running = false;
	private Thread thread;

	
	private void init() {
		String version = glGetString(GL_VERSION);
		System.out.println("OpenGL version: "+version);
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	private void update() {
		
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void run() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			ContextAttribs context = new ContextAttribs(3, 3);
			Display.create(new PixelFormat(), context.withProfileCore(true));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
//		int shader = ShaderUtils.load("shaders/shader.vert","shaders/shader.frag");
		
		init();

		while (running) {
			Display.update();
			render();
			if (Display.isCloseRequested())
				running = false;
		}
		Display.destroy();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public Game() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Game().start();
	}

}
