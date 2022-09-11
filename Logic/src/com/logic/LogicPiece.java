package com.logic;

import com.game.library.VEC2;

public class LogicPiece extends VEC2 {
	private String logicId;

	public LogicPiece(String logicId, int x, int y) {
		super(x, y);
		this.logicId = logicId;
	}

	public String getLogicId() {
		return logicId;
	}
}
