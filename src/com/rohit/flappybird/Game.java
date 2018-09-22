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
	@Override
	public void run() {
		System.out.println("Game started...");
		init();
		while (running) {
			update();
			render();

			if (glfwWindowShouldClose(window)) {
				running = false;
			}
		}
	}

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

		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f,  10.0f, -10.0f ,  10.0f  ,-1.0f, 1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex",1);
		
		level = new Level();
		
		int error = glGetError();
		if(error  != GL_NO_ERROR) {
			System.out.println(error);
		}
	}

	private void update() {
		glfwPollEvents();
		if (Input.keys[GLFW_KEY_SPACE]) {
			System.out.println("FLAP!");
		}
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
		glfwSwapBuffers(window);
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
