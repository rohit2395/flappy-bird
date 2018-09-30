package com.rohit.flappybird.level;

import static org.lwjgl.glfw.GLFW.*;

import com.rohit.flappybird.Constants;
import com.rohit.flappybird.graphics.Shader;
import com.rohit.flappybird.graphics.Texture;
import com.rohit.flappybird.graphics.VertexArray;
import com.rohit.flappybird.input.Input;
import com.rohit.flappybird.math.Matrix4f;
import com.rohit.flappybird.math.Vector3f;

public class Bird {
	
	private float SIZE = 1.0f;
	
	float rot;
	float delta;
	float angle;
	private VertexArray mesh;
	private Texture texture;
	
	private Vector3f position = new Vector3f();
	
	public Bird() {
		
		float[] vertices = new float[] {
				-(SIZE  / 2.0f) ,-(SIZE / 2.0f) ,0.1f,
				-(SIZE  / 2.0f) ,(SIZE / 2.0f) , 0.1f,
				(SIZE  / 2.0f) ,  (SIZE / 2.0f), 0.1f,
				(SIZE  / 2.0f) , -(SIZE / 2.0f), 0.1f
		};
			
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		float[] tcs = new float[] {
			0, 1,
			0, 0,
			1, 0,
			1, 1
		};
		
		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/bird.png");
	}
	
	public void setBirdPosition(float locationOffsetX,float locationOffsetY) {
		position.x = locationOffsetX;
		position.y = locationOffsetY * Constants.ASPECT_RATIO;
	}
	
	
	public void update() {
		position.y -= delta;
		if(Input.isKeyDown(GLFW_KEY_SPACE)) {
			delta = -0.13f;
			angle = -0.5f;
		}
		else {
			delta += 0.01f;
			angle += 0.015f;
		}
		rot = -angle * 90.0f;
	}

	
	public void render() {
		Shader.BIRD.enable();
		Shader.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));

		texture.bind();
		mesh.render();
		Shader.BIRD.disable();
	}
	
	private void fall() {
		delta = -0.15f;
	}

	public float getY() {
		return position.y;
	}
	
	public float getX() {
		return position.x;
	}

	public float getSize() {
		return SIZE;
	}

}
