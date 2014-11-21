package com.apollo.training.blobconverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.PreparedStatement;

import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class BlobConverter {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// poeadb.tables that has blobs
		// -- archive_message.msg, inbox.msg, key.pkfile, key.certfile, keystore.key, outbox.msg
		
		// POSTGRESQL -- database mgmt
		Connection con = null;
	    Statement st = null;
	    ResultSet rs = null;
	    String url = "jdbc:postgresql://192.168.7.222:5432/poeadb";
	    String user = "postgres";
	    String password = "";
	    
	    // MYSQL -- database mgmt
	    String mysqlUrl = "jdbc:mysql://localhost:3306/poeadb";
	    String mySqlUser = "root";
	    String mySqlPassword = "root";
	    Connection mysqlCon = null;
	    Statement mySqlst = null;
	    ResultSet mySqlrs = null;
	    
	    try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM keystore LIMIT 1");
			String partyID;
			byte[] keyData = null;
			
			X509Certificate cert;
			try {
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				if(rs.next())
				{	
				//rs.getBinaryStream("key");
				cert = (X509Certificate) cf.generateCertificate(rs.getBinaryStream("key"));
				System.out.println(cert);
				partyID = rs.getString("party_id");
				keyData = rs.getBytes("key");
				}
			} catch (CertificateException e1) {
				e1.printStackTrace();
			}

			
			///*while (*/rs.next();/*) {*/

			mysqlCon = DriverManager.getConnection(mysqlUrl, mySqlUser, mySqlPassword);
			mySqlst = mysqlCon.createStatement();
			mySqlrs = mySqlst.executeQuery("SELECT * FROM keystore LIMIT 1");
			mySqlrs.next();
			String mySqlPartyId = mySqlrs.getString("party_id");
			System.out.println(mySqlPartyId);

			Blob blob = (Blob) mysqlCon.createBlob();
			blob.setBytes(1, keyData);
			PreparedStatement ps = (PreparedStatement) mysqlCon.prepareStatement("UPDATE keystore SET `key`=? WHERE party_id=?");
			ps.setBlob(1, blob);
			ps.setString(2, mySqlPartyId);
			ps.executeUpdate();
			
			mySqlst = mysqlCon.createStatement();
			mySqlrs = mySqlst.executeQuery("SELECT * FROM keystore LIMIT 1");
			X509Certificate cert2;
			try {
				CertificateFactory cf2 = CertificateFactory.getInstance("X.509");
				if(mySqlrs.next())
				{	
				//rs.getBinaryStream("key");
				cert2 = (X509Certificate) cf2.generateCertificate(mySqlrs.getBinaryStream("key"));
				System.out.println(cert2);
				}
			} catch (CertificateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
