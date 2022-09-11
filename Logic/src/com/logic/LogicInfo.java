package com.logic;

public class LogicInfo {
	private String logicId;
	private String logicName;
	private int height;
	private int width;
	
	public LogicInfo(String logicId, String logicName, int height, int width) {
		super();
		this.logicId = logicId;
		this.logicName = logicName;
		this.height = height;
		this.width = width;
	}

	public String getLogicId() {
		return logicId;
	}

	public String getLogicName() {
		return logicName;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
}
