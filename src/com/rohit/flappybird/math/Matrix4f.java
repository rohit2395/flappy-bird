/**
 * 
 */
package com.rohit.flappybird.math;

import static com.rohit.flappybird.Constants.*;
import static java.lang.Math.*;

import java.nio.FloatBuffer;

import com.rohit.flappybird.util.BufferUtils;

/**
 * @author rajbar
 *
 */
public class Matrix4f {

	private float[] matrix = new float[4 * 4];

	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f result = identity();

		result.getMatrix()[0 + 0 * MAT_ROW_COL] = 2.0f / (right - left);

		result.getMatrix()[1 + 1 * MAT_ROW_COL] = 2.0f / (top - bottom);
		
		result.getMatrix()[2 + 2 * MAT_ROW_COL] = 2.0f / (near - far);

		result.getMatrix()[0 + 3 * MAT_ROW_COL] = (left + right) / (left - right);
		result.getMatrix()[1 + 3 * MAT_ROW_COL] = (bottom + top) / (bottom - top);
		result.getMatrix()[2 + 3 * MAT_ROW_COL] = (far + near) / (far - near);

		return result;
	}

	public static Matrix4f rotate(float angdeg) {
		Matrix4f result = identity();
		float r = (float) toRadians(angdeg);
		float cos = (float) cos(r);
		float sin = (float) sin(r);

		result.getMatrix()[0 + 0 * MAT_ROW_COL] = cos;
		result.getMatrix()[0 + 1 * MAT_ROW_COL] = -sin;

		result.getMatrix()[1 + 0 * MAT_ROW_COL] = sin;
		result.getMatrix()[1 + 1 * MAT_ROW_COL] = cos;

		return result;
	}

	public static Matrix4f translate(Vector3f vector) {
		Matrix4f result = identity();

		result.getMatrix()[0 + 3 * MAT_ROW_COL] = vector.x;
		result.getMatrix()[1 + 3 * MAT_ROW_COL] = vector.y;
		result.getMatrix()[2 + 3 * MAT_ROW_COL] = vector.z;

		return result;
	}

	public Matrix4f multiply(Matrix4f mat) {
		Matrix4f result = new Matrix4f();

		for (int y = 0; y < MAT_ROW_COL; y++) {
			for (int x = 0; x < MAT_ROW_COL; x++) {
				float sum = 0.0f;
				for (int z = 0; z < MAT_ROW_COL; z++) {
					sum += this.getMatrix()[x + z * MAT_ROW_COL] * mat.getMatrix()[z + y * MAT_ROW_COL];
				}
				result.getMatrix()[x + y * MAT_ROW_COL] = sum;
			}
		}

		return result;
	}

	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();

		for (int i = 0; i < result.getMatrix().length; i++) {
			result.getMatrix()[i] = 0.f;
		}
		result.getMatrix()[0 + 0 * MAT_ROW_COL] = 1.0f;
		result.getMatrix()[1 + 1 * MAT_ROW_COL] = 1.0f;
		result.getMatrix()[2 + 2 * MAT_ROW_COL] = 1.0f;
		result.getMatrix()[3 + 3 * MAT_ROW_COL] = 1.0f;

		return result;
	}

	public float[] getMatrix() {
		return matrix;
	}

	public void setMatrix(float[] matrix) {
		this.matrix = matrix;
	}
	
	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(matrix);
	}
	

}
