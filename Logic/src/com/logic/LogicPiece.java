package com.logic;

import com.game.library.VEC2;

public class LogicPiece extends VEC2 {
	private int questionNumber;

	public LogicPiece(int questionNumber, int x, int y) {
		super(x, y);
		this.questionNumber = questionNumber;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}
}
