package com.rohit.flappybird.level;

import java.util.Random;

import com.rohit.flappybird.Constants;
import com.rohit.flappybird.graphics.Shader;
import com.rohit.flappybird.graphics.Texture;
import com.rohit.flappybird.graphics.VertexArray;
import com.rohit.flappybird.math.Matrix4f;
import com.rohit.flappybird.math.Vector3f;

public class Level {

	private VertexArray background;
	
	private Texture bgTexture;
	
	private Bird bird;
	
	private int xScroll = -10;
	private int map = 0;
	
	private Pipe[] pipes = new Pipe[5 * 2];
	
	private int index = 0;
	
	private Random random = new Random();
	
	private float OFF_SET = 5.0f;
	
	
	
	public Level() {
		float[] vertices = new float[] { 
				-10.0f, -10.0f * Constants.ASPECT_RATIO, 0.0f,
				-10.0f,  10.0f * Constants.ASPECT_RATIO, 0.0f,
				  0.0f,  10.0f * Constants.ASPECT_RATIO, 0.0f,
				  0.0f, -10.0f * Constants.ASPECT_RATIO, 0.0f
			};

		
		byte[] indices = new byte[] {
			0,1,2,
			2,3,0
		};
		
		float[] tcs = new float[] {

				0, 1,
				0, 0,
				1, 0,
				1, 1
		};
		
		background = new VertexArray(vertices, indices,tcs);
		bgTexture = new Texture("res/bg.jpeg");
		bird = new Bird();
		bird.setBirdPosition(-3.0f, 0.0f);
		createPipes();
		
	}
	
	private void createPipes() {
		Pipe.create();
		for (int i = 0; i < 5 * 2; i += 2) {
			pipes[i] = new Pipe(OFF_SET + index * 3.0f,random.nextFloat() * 4.0f );
			pipes[i+1] = new Pipe(pipes[i].getX(),pipes[i].getY()-11.5f);
			index+=2;
		} 
	}
	
	private void updatePipes() {
		pipes[index % 10] = new Pipe(OFF_SET + index * 3.0f,random.nextFloat() * 4.0f );
		pipes[(index + 1) % 10] = new Pipe(pipes[index % 10].getX(),pipes[index % 10].getY()-11.5f);
		index += 2;
	}
	
	public void update() {
		xScroll--;
		
		if (-xScroll % 335 == 0) map++;
		if(-xScroll > 300  &&  -xScroll % 120 == 0) {
			updatePipes();
		}
		bird.update();
		if(collision())
			System.out.println("collision!");
	}
	
	public void render() {
		bgTexture.bind();
		Shader.BG.enable();
		background.bind();
		for(int i=map;i< map + 4;i++) {
			Shader.BG.setUniformMat4f("vw_matrix",Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f,0.0f,0.0f)));
			background.draw();
		}
		Shader.BG.disable();
		bgTexture.unbind();
		
		bird.render();
		renderPipes();
	}
	
	public void renderPipes() {
		Shader.PIPE.enable();
		Shader.PIPE.setUniformMat4f("vw_matrix",Matrix4f.translate(new Vector3f(xScroll * 0.05f,0.0f,0.0f)));
		Pipe.getTexture().bind();
		Pipe.getMesh().bind();
		
		for(int i=0;i<5*2;i++) {
			Shader.PIPE.setUniformMat4f("ml_matrix", pipes[i].getModelMatrix());
			Shader.PIPE.setUniform1i("top",i % 2 == 0 ? 1 : 0);
			Pipe.getMesh().draw();
		}

		Pipe.getTexture().unbind();
		Pipe.getMesh().unbind();
	}
	
	private boolean collision() {
		for(int i=0;i<5 * 2;i++) {
			float bx = (-xScroll * 0.05f ) - 3.0f;
			float by = bird.getY();
			float px = pipes[i].getX();
			float py = pipes[i].getY();
			
			float bx0 = bx - bird.getSize() / 2.0f;
			float bx1 = bx + bird.getSize() / 2.0f;
			float by0 = by - bird.getSize() / 2.0f;
			float by1 = by + bird.getSize() / 2.0f;
			
			float px0 = px;
			float px1 = px + Pipe.getWidth();
			float py0 = py;
			float py1 = py + Pipe.getHeight();
			
			if(bx1 > px0 && bx0 < px1) {
				if(by1 > py0 && by0 < py1) {
					return true;
				}
			}
		}
		return false;
	}
}
