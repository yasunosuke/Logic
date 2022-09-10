package com.logic;

import com.game.library.Screen;

public class Logic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogicScreen logicScreen = new LogicScreen(30, 30);
		logicScreen.init();
//		logicScreen.displayPieces();
		while(true) {
			logicScreen.draw();
			logicScreen.tryToSolve();
		}
		
		
		
		
	}

}
