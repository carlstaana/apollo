package com.apollo.training;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class BlobUploader {
	
    public static void main(String[] args) {
    	// MYSQL -- database mgmt
    	String mysqlUrl = "jdbc:mysql://localhost:3306/game";
    	String mySqlUser = "root";
    	String mySqlPassword = "root";
    	Connection mysqlCon = null;
    	Statement mySqlst = null;
    	ResultSet mySqlrs = null;
    	
    	String INSERT_PICTURE = "insert into card(rank, suit, image) values (?, ?, ?)";
  
        FileInputStream fis = null;
        java.sql.PreparedStatement ps = null;
        
        try {
			mysqlCon = DriverManager.getConnection(mysqlUrl, mySqlUser, mySqlPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
          mysqlCon.setAutoCommit(false);
          File file = new File("/home/apollo/Downloads/classic-cards/45.png");
          fis = new FileInputStream(file);
          ps = mysqlCon.prepareStatement(INSERT_PICTURE);
          ps.setInt(1, 3);
          ps.setString(2, "CLUBS");
          ps.setBinaryStream(3, fis, (int) file.length());
          ps.executeUpdate();
          mysqlCon.commit();
          System.out.println("Image uploaded successfully...");
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
          try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
	}
}
