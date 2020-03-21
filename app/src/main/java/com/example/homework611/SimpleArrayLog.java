package com.example.homework611;

import androidx.annotation.NonNull;

public class SimpleArrayLog {
	private static SimpleArrayLog singleton = new SimpleArrayLog(8);

	private int size;
	private int elementsWritten;
	private String[] data;
	private long lastWrite;

	private SimpleArrayLog(int capacity) {
		size = capacity;
		data = new String[size];
	}

	private void write(String s) {
		long time = System.currentTimeMillis();
		if (time - lastWrite < 150) {
			data[(elementsWritten - 1) % size] += ", " + s;
		} else {
			data[elementsWritten % size] = s;
			elementsWritten++;
		}
		lastWrite = time;
	}

	@NonNull
	@Override
	public String toString() {
		if (elementsWritten == 0)
			return "No elements yet";
		StringBuilder result = new StringBuilder();
		for (int i = Math.max(0, elementsWritten - size); i < elementsWritten; i++)
			result.append("\n\n").append(i + 1).append(". ").append(data[i % size]);
		return result.substring(2); // cut first \n\n
	}

	public static void log(String s) {
		singleton.write(s);
	}

	public static String get() {
		return singleton.toString();
	}
}
