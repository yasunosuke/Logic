package com.logic;

import com.game.library.Screen;

public class Logic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogicScreen logicScreen = new LogicScreen();
		logicScreen.init();
		
		while(true) {
			logicScreen.draw();
			
			if(logicScreen.isCompleted()) {
				break;
			}
			logicScreen.tryToSolve();
		}
		
		logicScreen.drawAnswer();
		
	}
		
}

