package com.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LogicDAO {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/eclipseuser";
	private static final String USER = "eclipseuser";
	private static final String PASSWORD = "eclipseuser";
	
//	static {
//		Properties props = new Properties();
//		
//		try {
////			props.load(new FileInputStream("src/sql.properties"));
//			props.load(new FileInputStream("C:/Users/yasuhiro/Desktop/JavaExperiments/Logic_lib/sql.properties"));
//		} catch (IOException e) {
//			System.out.println("file load error");
//		}
//		
//		URL = props.getProperty("url");
//		USER = props.getProperty("user");
//		PASSWORD = props.getProperty("password");
//	}
	
	public List<LogicPiece> getLogic() {
		
		List<LogicPiece> logicPieces = new ArrayList<>();
		
//		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD); 
//				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM vectest");) {
//				
////				pstmt.setString(1, name);
//				
//				try(ResultSet rs = pstmt.executeQuery()) {
//					while(rs.next()) {
//						
//						int qnumber = rs.getInt(1);
//						int x = rs.getInt(2);
//						int y = rs.getInt(3);
//						
//						logicPieces.add(new LogicPiece(qnumber, x, y));
//						
//					}
//				} catch(SQLException e) {
//					System.out.println("エラーコードは" + e.getErrorCode());
//				}
//
//			} catch(SQLException e) {
//				System.out.println("エラー" + e.getErrorCode());
//			}
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD); 
				Statement stmt = con.createStatement();) {
				
//				pstmt.setString(1, name);
				
				try(ResultSet rs = stmt.executeQuery("SELECT * FROM vectest;")) {
					while(rs.next()) {
						
						int qnumber = rs.getInt(1);
						int x = rs.getInt(2);
						int y = rs.getInt(3);
						
						logicPieces.add(new LogicPiece(qnumber, x, y));
						
					}
				} catch(SQLException e) {
					System.out.println("エラーコードは" + e.getErrorCode());
				}

			} catch(SQLException e) {
				System.out.println("エラー" + e.getErrorCode());
			}
		
		return logicPieces;
	}
}
