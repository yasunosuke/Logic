package com.logic;

import java.util.List;

public class LogicService {
	
	private static LogicService ls = new LogicService();
	
	private LogicDAO logicDAO = LogicDAO.getInstance();
	
	private LogicService() {
		super();
	}
	
	public static LogicService getInstance() {
		return ls;
	}
	
	public int getRecordNumberFromLogicInfo() {
		return logicDAO.getRecordNumberFromLogicInfo();
	}
	
	public LogicInfo getLogicInfo(String logicId) {
		return logicDAO.getLogicInfo(logicId);
	}
	
	public List<LogicPiece> getBlackPiecePositions(String logicId) {
		return logicDAO.getBlackPiecePositions(logicId);
	}
}
