package com.igeekhome.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertiesClass {

	public static void main(String[] args) throws Exception {
		Properties p=new Properties();
		FileInputStream in = new  FileInputStream("resource/db.properties");
		p.load(in);
		String url=p.getProperty("url");
		String user=p.getProperty("user");
		String password=p.getProperty("password");
		String driverName=p.getProperty("driverName");
		System.out.println(url);
		System.out.println(user);
		System.out.println(password);
		System.out.println(driverName);
	}

}
