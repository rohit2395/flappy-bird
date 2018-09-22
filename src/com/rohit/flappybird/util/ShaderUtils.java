package com.rohit.flappybird.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
	
	private ShaderUtils() {
		
	}
	public static int load(String vertPath,String fragPath) {
		String vert = FileUtility.loadAsString(vertPath);
		String frag = FileUtility.loadAsString(fragPath);
		return create(vert, frag);
	}

	public static int create(String vert, String frag) {
		int program = glCreateProgram();
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		
		//get shader source code
		glShaderSource(vertID, vert);
		glShaderSource(fragID, frag);
		
		//compile shader source code
		glCompileShader(vertID);
		if(glGetShaderi(vertID,GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID, 2048));
			return -1;
		}
		
		glCompileShader(fragID);
		if(glGetShaderi(fragID,GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragID, 2048));
			return -1;
		}
		
		//attach compiled shader
		glAttachShader(program, vertID);
		glAttachShader(program, fragID);
		
		glLinkProgram(program);
		glValidateProgram(program);
		
		return program;
		
	}

}
