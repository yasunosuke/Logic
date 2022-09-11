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

import com.game.library.VEC2;

public class LogicDAO {
	
	
	
	private static final String URL = "*";
	private static final String USER = "*";
	private static final String PASSWORD = "*";
	
	private String insert = "INSERT INTO vectest(qname, x, y) VALUES(?, ?, ?)";
	
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
	
	public LogicPiece getLogicInfo() {
		
		return ;
	}
	
	public List<LogicPiece> getBlackPiecePositions() {
		
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
	
	public void insertBlackPiecePositions(String[][] logic) {
		
		List<VEC2> positions = new ArrayList<>();
		for(int y = 0; y < logic.length; y++) {
			for(int x = 0; x < logic[0].length; x++) {
				if(logic[y][x].equals("■")) {
					positions.add(new VEC2(x,y));
				}
			}
		}

		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement ps = con.prepareStatement(insert);) {

			for(VEC2 v: positions) {
				ps.setInt(1, 2);
				ps.setInt(2, v.getX());
				ps.setInt(3, v.getY());
				ps.addBatch();
				System.out.println(ps.toString());
			}
			
			int[] count = ps.executeBatch();
			System.out.println("count" + count.length);
			
		} catch(SQLException e) {
			System.out.println("エラー" + e.getErrorCode());
		}
	}
	
public void insertBlackPiecePositions(List<VEC2> positions) {

		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement ps = con.prepareStatement(insert);) {

			for(VEC2 v: positions) {
				ps.setInt(1, 2);
				ps.setInt(2, v.getX());
				ps.setInt(3, v.getY());
				ps.addBatch();
				System.out.println(ps.toString());
			}
			
			int[] count = ps.executeBatch();
			System.out.println("count" + count.length);
			
		} catch(SQLException e) {
			System.out.println("エラー" + e.getErrorCode());
		}
	}
}
			

