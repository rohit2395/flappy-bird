package com.rohit.flappybird.level;

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
	}
	
	public void update() {
		xScroll--;
		if (-xScroll % 335 == 0) map++;
		bird.update();
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
	}
}
