package com.tea.regDoctor.utils;

import java.io.*;

public class FileUtil {
	public static String read(String path) throws IOException {
		String txt = "";
		File file = new File(path);
		long timeout = 30*60;
		while(!(file.isFile() && file.exists())){
			file = new File(path);
			try {
				Thread.sleep(100);
				timeout -= 100;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader bReader = new BufferedReader(read);
			String temptxt = "";
			txt = "";
			while((temptxt=bReader.readLine())!=null){
				txt += temptxt;
			}
			bReader.close();
			read.close();
		}
		return txt;
	}
	
	public static void creatPath(String path) throws IOException {
		File file = new File(path);
		file.mkdir();
	}
	
	public static void delete(String path) throws IOException {
		File file = new File(path);
		String[] list = file.list();
		File tempFile = null;
		for(String temp : list){
			tempFile = new File(path+temp);
			tempFile.delete();
		}
		file.delete();
	}
}
