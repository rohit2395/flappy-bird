/**
 * 
 */
package com.rohit.flappybird;

import static com.rohit.flappybird.util.RGBToOpenGLColorCode.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.rohit.flappybird.graphics.Shader;
import com.rohit.flappybird.input.Input;
import com.rohit.flappybird.level.Level;
import com.rohit.flappybird.math.Matrix4f;

/**
 * @author rajbar
 *
 */
public class Game implements Runnable {

	private boolean running = false;
	private Thread thread;

	private long window;
	
	private Level level;
	
	private void init() {
		if (!glfwInit()) {
			// TODO: handle it
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(Constants.WIDTH, Constants.HEIGHT, Constants.GAME_TITLE, 0l, 0l);
		
		if (window == 0) {
			//handle it
			return;
		}
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidMode.width() - Constants.WIDTH) / 2, (vidMode.height() - Constants.HEIGHT) / 2);
		glfwSetKeyCallback(window, new Input());
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();

		glClearColor(getOpenGLValue(92), getOpenGLValue(177), getOpenGLValue(243), 0.1f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		System.out.println("OpenGL Version :" + glGetString(GL_VERSION));
		Shader.loadAll();

		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * Constants.ASPECT_RATIO, 10.0f * Constants.ASPECT_RATIO ,-1.0f, 1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex",1);
		
		Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BIRD.setUniform1i("tex",1);
		
		level = new Level();
		
		int error = glGetError();
		if(error  != GL_NO_ERROR) {
			System.out.println(error);
		}
		
	}
	

	private void update() {
		glfwPollEvents();
		level.update();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
		glfwSwapBuffers(window);
	}
	
	@Override
	public void run() {
		System.out.println("Game started...");
		init();
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		int i=0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				try {
					String title = Constants.GAME_TITLE + " [ UPS : "+updates+" | FPS : "+frames+" ]";
					glfwSetWindowTitle(window,title);
				}catch(Exception e) {
					
				}
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window)) {
				running = false;
			}
		}
	}


	public void start() {
		running = true;
		thread = new Thread(this, "Game");
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
