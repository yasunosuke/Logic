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

	//singletonにするこのクラス

	private static LogicDAO logicDAO = new LogicDAO();

	private static final String URL = "************";
	private static final String USER = "***********";
	private static final String PASSWORD = "*******";

	private LogicDAO() {
		super();
	}

	public static LogicDAO getInstance() {
		return logicDAO;
	}

	public int getRecordNumberFromLogicInfo() {

		int recordNumber = 0;

		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD); 
				Statement stmt = con.createStatement();) {

			try(ResultSet rs = stmt.executeQuery("SELECT count(*) FROM LogicInfo;")) {
				while(rs.next()) {

					recordNumber = rs.getInt(1);

				}
			} catch(SQLException e) {
				System.out.println("エラーコードは" + e.getErrorCode());
			}

		} catch(SQLException e) {
			System.out.println("エラー" + e.getErrorCode());
		}

		return recordNumber;

	}

	public LogicInfo getLogicInfo(String logicId) {
		
		LogicInfo logicInfo = null;

		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM LogicInfo WHERE logic_id = ?");) {

			pstmt.setString(1, logicId);

			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {

					String id = rs.getString(1);
					String name = rs.getString(2);
					int height = rs.getInt(3);
					int width = rs.getInt(4);

					logicInfo = new LogicInfo(id, name, height, width);

				}
			} catch(SQLException e) {
				System.out.println("エラーコードは" + e.getErrorCode());
			}

		} catch(SQLException e) {
			System.out.println("エラー" + e.getErrorCode());
		}

		return logicInfo;
		
	}

	public List<LogicPiece> getBlackPiecePositions(String logicId) {

		List<LogicPiece> logicBlackPieces = new ArrayList<>();

		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD); 
				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM BlackPiecePositions WHERE logic_id = ?");) {

			pstmt.setString(1, logicId);

			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {

					String id = rs.getString(1);
					int x = rs.getInt(2);
					int y = rs.getInt(3);

					logicBlackPieces.add(new LogicPiece(id, x, y));

				}
			} catch(SQLException e) {
				System.out.println("エラーコードは" + e.getErrorCode());
			}

		} catch(SQLException e) {
			System.out.println("エラー" + e.getErrorCode());
		}

		return logicBlackPieces;
		
	}

}


