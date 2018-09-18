package com.rohit.flappybird.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtility {

	private FileUtility() {

	}

	public static String loadAsString(String file) {
		String result = "";
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = bf.readLine()) != null) {
				result += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
