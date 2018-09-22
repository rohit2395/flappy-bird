package com.rohit.flappybird.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.rohit.flappybird.math.Matrix4f;
import com.rohit.flappybird.math.Vector3f;
import com.rohit.flappybird.util.ShaderUtils;

public class Shader {
	
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader BG;
	
	private boolean enabled = false;
	private final int ID;
	private Map<String,Integer> locationCache = new HashMap<>();
	
	public Shader(String vertPath,String fragPath) {
		ID = ShaderUtils.load(vertPath, fragPath);
	}
	
	public static void loadAll() {
		BG = new Shader("shaders/bg.vert","shaders/bg.frag");
	}
	
	public int getUniform(String name) {
		if(locationCache.containsKey(name)) {
			return locationCache.get(name);
		}
		int result = glGetUniformLocation(ID, name);
		if(result == -1) {
			System.err.println("Could not find the uniform variable 1"+name+"'!");
		}else {
			locationCache.put(name,result);
		}
		
		return result;
	}
	
	public void setUniform1i(String name,int value) {
		if(!enabled) enable();
		glUniform1i(getUniform(name),value);
	}
	
	public void setUniform1f(String name,float value) {
		if(!enabled) enable();
		glUniform1f(getUniform(name),value);
	}
	
	public void setUniform2f(String name,float v1,float v2) {
		if(!enabled) enable();
		glUniform2f(getUniform(name),v1,v2);
	}
	
	public void setUniform3f(String name,Vector3f value) {
		if(!enabled) enable();
		glUniform3f(getUniform(name),value.x,value.y,value.z);
	}
	
	public void setUniformMat4f(String name,Matrix4f matrix) {
		if(!enabled) enable();
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	
	public void enable() {
		enabled = true;
		glUseProgram(ID);
	}
	
	public void disable() {
		enabled = false;
		glUseProgram(0);
	}

}
