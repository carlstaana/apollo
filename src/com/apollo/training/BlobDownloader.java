package com.apollo.training;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlobDownloader {
	public static void main(String[] args) {
		// MYSQL -- database mgmt
    	String mysqlUrl = "jdbc:mysql://localhost:3306/game";
    	String mySqlUser = "root";
    	String mySqlPassword = "root";
    	Connection mysqlCon = null;
    	Statement mySqlst = null;
    	ResultSet mySqlrs = null;
    	
    	try {
			mysqlCon = DriverManager.getConnection(mysqlUrl, mySqlUser, mySqlPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String sql = "SELECT * FROM card";
    	PreparedStatement stmt = null;
		try {
			stmt = mysqlCon.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			mySqlrs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			while (mySqlrs.next()) {
			    String name = mySqlrs.getString(1);
			    String description = mySqlrs.getString(2);
			    File image = new File("/home/apollo/Desktop/1.png");
			    FileOutputStream fos = new FileOutputStream(image);

			    byte[] buffer = new byte[1];
			    InputStream is = mySqlrs.getBinaryStream(3);
			    while (is.read(buffer) > 0) {
			      fos.write(buffer);
			    }
			    fos.close();
			  }
		} catch (SQLException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
          try {
			mysqlCon.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
