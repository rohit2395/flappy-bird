package com.rohit.flappybird.util;

public class RGBToOpenGLColorCode {
	
	private RGBToOpenGLColorCode() {
		
	}
	
	public static float getOpenGLValue(int val) {
		float res = 0.0f;
		
		res = val/255.0f;
		
		return res;
	}

}
