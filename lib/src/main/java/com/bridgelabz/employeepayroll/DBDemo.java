package com.bridgelabz.employeepayroll;

import java.sql.DriverManager;
import java.util.Enumeration;
import java.sql.Connection;
import java.sql.*;

public class DBDemo {
	public static void main(String [] args) {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String password = "Perfios@2021";
		Connection connection;
		listDrivers();
		try {
			System.out.println("Connecting to database:"+jdbcURL);
			connection =  DriverManager.getConnection(jdbcURL,userName,password);
			System.out.println("Connection is successful!!!!"+connection);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}

	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driverClass = (Driver)driverList.nextElement();
			System.out.println(" "+driverClass.getClass().getName());
		}
	}
}