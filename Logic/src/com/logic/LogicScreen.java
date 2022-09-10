package com.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.game.library.Cursor;
import com.game.library.Screen;

public class LogicScreen extends Screen {
	
	private String[][] defaultScreen = {
			{"□", "■", "■", "■", "□", "□", "□", "□", "■", "□"},
			{"■", "■", "■", "□", "□", "□", "□", "□", "■", "■"},
			{"■", "■", "■", "■", "□", "□", "■", "■", "■", "■"},
			{"■", "■", "□", "□", "□", "□", "□", "■", "■", "■"},
			{"■", "□", "□", "■", "□", "□", "■", "□", "■", "■"},
			{"■", "■", "□", "■", "■", "■", "■", "□", "■", "■"},
			{"□", "■", "■", "■", "■", "■", "■", "■", "■", "□"},
			{"□", "□", "■", "■", "■", "■", "■", "■", "□", "□"},
			{"■", "■", "■", "■", "■", "■", "■", "■", "■", "■"},
			{"■", "□", "■", "□", "□", "□", "□", "■", "□", "■"}
	};
	
	private List<List<String>> answer = new ArrayList<List<String>>();
	
	private String[][] screen = new String[defaultScreen.length][defaultScreen[0].length];
	
//	private String[][] screen = new String[SCREEN_HEIGHT][SCREEN_WIDTH];
	
	private List<List<Integer>> horizontalNumbers = new ArrayList<>();
	private List<List<Integer>> verticalNumbers = new ArrayList<>();
	
	private Cursor cursor = new Cursor(0, 0);
	
	private Scanner sc = new Scanner(System.in);
	
	LogicDAO logicDAO = new LogicDAO();
	
	public LogicScreen(int SCREEN_HEIGHT, int SCREEN_WIDTH) {
		super(SCREEN_HEIGHT, SCREEN_WIDTH);
		// TODO Auto-generated constructor stub
	}
	
	public void init() {
		initiateScreen();
		putArrayToList();
		getBlackBlockNumbers();
	}
	
	public void displayPieces() {
		List<LogicPiece> logicPieces = logicDAO.getLogic();
		for(LogicPiece lp: logicPieces) {
			System.out.println(lp.getQuestionNumber());
			System.out.println(lp.getX());
			System.out.println(lp.getY());
		}
	}
	
//	private void initiateScreen() {
//		for(int y = 0; y < SCREEN_HEIGHT; y++) {
//			for(int x = 0; x < SCREEN_WIDTH; x++) {
//				screen[y][x] = "　";
//			}
//		}
//	}
	
	private void initiateScreen() {
//		for(int y = 0; y < SCREEN_HEIGHT; y++) {
//			for(int x = 0; x < SCREEN_WIDTH; x++) {
//				screen[y][x] = "　";
//			}
//		}
		
		for(int i = 0; i < defaultScreen.length; i++) {
			screen[i] = defaultScreen[i].clone();
		}
	}
	
	private void makeQuestion() {
//		for(int )
	}
	
	private int getMaxSize(List<List<Integer>> horizontalNumbers2) {
		int max = 0;
		for(List<Integer> l: this.horizontalNumbers) {
			int size = l.size();
			if(size > max) {
				max = size;
			}
		}
		return max;
	}

	@Override
	public void draw() {
		super.clear();
//		2次元コレクションの要素の要素数の最大値をもとめる
		int verticalMax = getMaxSize(this.verticalNumbers);
		int horizontalMax = getMaxSize(this.horizontalNumbers);
		
//		上の数字描画
		for(int y = 0; y < verticalMax; y++) {
			
			for(int x1 = 0; x1 < horizontalMax; x1++) {
				System.out.print("　　　");/* 全角3 */
			}
//			this.verticalNumbers.size()がscreen_widthでもよい
			for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
				List<Integer> numbers = this.verticalNumbers.get(x2);
				int size = numbers.size();
				if(y >= verticalMax - size) {
//					半角数字を全角数字に変換
					int i = numbers.get(y - (verticalMax - size));
					String s;
//					1つの数字が入る枠の大きさは全角３つ分
					if(i >= 10) {
						s = convertIntToFullWidth(i);
						System.out.print(s);/* 全角１ */
					} else {
						int j = i + '０';/* 全角の0 10以上の値の変換はどうする?*/
						char c = (char)j;
						s = Character.toString(c);
						System.out.print(s + "　");/* 全角２ */
//						System.out.print("１０");
					}
				} else {
					System.out.print("　　");/* 全角2 */
				}
				
			}
			System.out.println("");
			
			if(y != verticalMax - 1) {
				for(int x1 = 0; x1 < horizontalMax; x1++) {
					System.out.print("　　　");/* 全角3 */
				}
				
				for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
					System.out.print("　　");/* 全角2 */
				}
				
				System.out.println("");	
			}
			
			
			
		}
				
		
//		下半分描画
		for(int y = 0; y < horizontalNumbers.size(); y++) {
//			水平方向の数字の描画
			for(int x1 = 0; x1 < horizontalMax; x1++) {
				List<Integer> numbers = this.horizontalNumbers.get(y);
				int size = numbers.size();
				if(x1 >= horizontalMax - size) {
//					半角数字を全角数字に変換
					int i = numbers.get(x1 - (horizontalMax - size));
					String s;
//					1つの数字が入る枠の大きさは全角３つ分
					if(i >= 10) {
						s = convertIntToFullWidth(i);
						System.out.print("　" + s);/* 全角１ */
					} else {
						int j = i + '０';/* 全角の0 10以上の値の変換はどうする?*/
						char c = (char)j;
						s = Character.toString(c);
						System.out.print("　　"+ s);/* 全角２ */
					}
					
				} else {
					System.out.print("　　　");/* 全角３ */
				}
			}
					
			
			for(int x2 = 0; x2 < verticalNumbers.size(); x2++) {
					System.out.print(screen[y][x2] + "　");
				
			}
//			右のカーソル描画 
			if (y == this.cursor.getY()) {
				System.out.print("←　");
			} else {
				System.out.print("　　");
			}
			System.out.println("");/*1行描画し終わったら改行*/
		}
//		下のカーソル描画
		for(int x1 = 0; x1 < horizontalMax; x1++) {
			System.out.print("　　　");/* 全角3 */
		}
		
		for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
				System.out.print("　　");/* 全角2 */
			
		}
		
		System.out.println("");
		for(int x1 = 0; x1 < horizontalMax; x1++) {
			System.out.print("　　　");/* 全角3 */
		}
		
		for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
			if(x2 == this.cursor.getX()) {
				System.out.print("↑　");
			} else {
				System.out.print("　　");/* 全角2 */
			}
			
		}
		System.out.println("");
	}
		

				
	
		
	
	private void drawDefaultScreen() {
		for(int y = 0; y < defaultScreen.length; y++) {
			for(int x = 0; x < defaultScreen[0].length; x++) {
				System.out.print(defaultScreen[y][x]);
			}
			System.out.println("");
		}
	}
	
	private String convertIntToFullWidth(int i) {
		StringBuilder sb = new StringBuilder();
		while(i != 0) {
			int j = i % 10 +'０';
			char c = (char)j;
			String s = Character.toString(c);
			sb.append(s);
			i /= 10;
		}
		
		return sb.reverse().toString();
	}
	
	private void putArrayToList() {
		for(int i = 0; i < defaultScreen.length; i++) {
			List<String> convertedList = new ArrayList<>(Arrays.asList(defaultScreen[i]));
			answer.add(convertedList);
		}
	}
	
	private void getBlackBlockNumbers() {
//		for(List<String> l: this.answer) {
//			int count = 0;
//			for(String m: l) {
//				if()
//			}
//		}
		
//		ますに空白しかないぎょうがあるときはどうなる？
//		水平方向の　■　数を取得する
		int height = this.answer.size();
		int width = this.answer.get(0).size();
		for(int y = 0; y < height; y++) {
			int count = 0;
			List<Integer> numbers = new ArrayList<>();
			for(int x = 0; x < width; x++) {
				if (x == width - 1 && this.answer.get(y).get(x).equals("■")) {/* 行の右はじに来たとき */
					count += 1;
					numbers.add(count);
				} else if(this.answer.get(y).get(x).equals("■")) {
					count += 1;
				} else if(x >= 1 && this.answer.get(y).get(x - 1).equals("■") 
						&& this.answer.get(y).get(x).equals("□")) {/* 行の左はじ以外で今いるマスが空白かつ直前が■のとき */
					numbers.add(count);
					count = 0;
				}
			}
			horizontalNumbers.add(numbers);
		}
		
		System.out.println("horizontal");
		for(List<Integer> hn: horizontalNumbers) {
			
			for(Integer n: hn) {
				System.out.print(n + " ");
			}
			System.out.println("");
		}
		
//		垂直方向の　■　を取得する
		for(int x = 0; x < width; x++) {
			int count = 0;
			List<Integer> numbers = new ArrayList<>();
			for(int y = 0; y < height; y++) {
				
				if (y == height - 1 && this.answer.get(y).get(x).equals("■")) {/* 行の右はじに来たとき */
					count += 1;
					numbers.add(count);
				} else if(this.answer.get(y).get(x).equals("■")) {
					count += 1;
				} else if(y >= 1 && this.answer.get(y - 1).get(x).equals("■") 
						&& this.answer.get(y).get(x).equals("□")) {/* 行の左はじ以外で今いるマスが空白かつ直前が■のとき */
					numbers.add(count);
					count = 0;
				}
			}
			verticalNumbers.add(numbers);
		}
		
		System.out.println("vertical");
		for(List<Integer> vn: verticalNumbers) {
			
			for(Integer n: vn) {
				System.out.print(n + " ");
			}
			System.out.println("");
		}
	}
	
	public void moveCursor() {
		this.cursor.move(this.sc, horizontalNumbers.size(), verticalNumbers.size());
	}
	
	public void tryToSolve() {
		int x = this.cursor.getX();
		int y = this.cursor.getY();
//database test		displayPieces();
		System.out.println("Move w:up s:down a:left d:right other than left keys:select");
		System.out.println("Select b:mark ■ v:mark × n:mark □");
		String input = this.sc.nextLine();
//		規定のキーが押された場合はカーソル移動
		switch(input) {
		case "w": this.cursor.moveUp(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "s": this.cursor.moveDown(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "a": this.cursor.moveLeft(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "d": this.cursor.moveRight(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "b": screen[y][x] = "■"; break;
		case "v": screen[y][x] = "×"; break;
		case "n": screen[y][x] = "□"; break;
		}
	}
	
}
	
	

	
