package com.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.game.library.Cursor;
import com.game.library.Screen;

import static com.logic.Pieces.*;

public class LogicScreen extends Screen {
	
	private Random random = new Random();
	
	LogicService logicService = LogicService.getInstance();
	LogicInfo logicInfo;
	
	private String[][] answer;
	private String[][] screen;
	
	List<LogicPiece> logicBlackPiecePositions;
	
	private List<List<String>> convertedAnswer = new ArrayList<List<String>>();
	
	private List<List<Integer>> horizontalNumbers = new ArrayList<>();
	private List<List<Integer>> verticalNumbers = new ArrayList<>();
	
	private Cursor cursor = new Cursor(0, 0);
	private Scanner sc = new Scanner(System.in);
	
	public LogicScreen() {
		super(0, 0);
	}

//	ゲームを初期化する
	public void init() {
		String selectedLogicId = String.valueOf(selectLogicRandaomly());
		this.logicInfo = logicService.getLogicInfo(selectedLogicId);
		putWhitePieceIntoPlainLogic();
		initiateScreen();
		putBlackPieceIntoPlainLogic();
		putArrayToList();
		getBlackPieceNumbers();
	}
	
//	ロジックをランダムに選ぶ
	private int selectLogicRandaomly() {
		int selectedLogicId = random.nextInt(logicService.getRecordNumberFromLogicInfo()) + 1;
		return selectedLogicId;
	}
	
//	スクリーンに表示される数字を取得するための前処理、後の　■　の部分を掻きこむ
	private void putWhitePieceIntoPlainLogic() {
		
		int height = this.logicInfo.getHeight();
		int width = this.logicInfo.getWidth();
		
		this.answer = new String[height][width];
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				this.answer[y][x] = White.getShape();
			}
		}
		
	}
	
//	screen　を初期化、　"□"　で埋める
	private void initiateScreen() {

		int height = this.logicInfo.getHeight();
		int width = this.logicInfo.getWidth();
		
		this.screen = new String[height][width];
		
		for(int i = 0; i < height; i++) {
			screen[i] = this.answer[i].clone();
		}
		
	}
	
//	■ を書きこむ
	private void putBlackPieceIntoPlainLogic() {
		this.logicBlackPiecePositions = logicService.getBlackPiecePositions(this.logicInfo.getLogicId());
		for(LogicPiece lbpp: this.logicBlackPiecePositions) {
			this.answer[lbpp.getY()][lbpp.getX()] = Black.getShape();
		}
	}
	
//	配列をリストに変換する
	private void putArrayToList() {
		for(int i = 0; i < this.answer.length; i++) {
			List<String> convertedList = new ArrayList<>(Arrays.asList(answer[i]));
			convertedAnswer.add(convertedList);
		}
	}
	
//	スクリーンに表示する数字を取得する
	private void getBlackPieceNumbers() {
		
		int height = this.convertedAnswer.size();
		int width = this.convertedAnswer.get(0).size();
//		水平方向の　■　数を取得する
		for(int y = 0; y < height; y++) {
			int count = 0;
			List<Integer> numbers = new ArrayList<>();
			for(int x = 0; x < width; x++) {
				String s = this.convertedAnswer.get(y).get(x);
				if (x == width - 1 && s.equals("■")) {/* 行の右はじに来たとき */
					count += 1;
					numbers.add(count);
				} else if(s.equals("■")) {
					count += 1;
				} else if(x >= 1 && this.convertedAnswer.get(y).get(x - 1).equals("■") 
						&& s.equals("□")) {/* 行の左はじ以外で今いるマスが空白かつ直前が■のとき */
					numbers.add(count);
					count = 0;
				}
			}
			
			horizontalNumbers.add(numbers);
		}
		
//		垂直方向の　■　を取得する
		for(int x = 0; x < width; x++) {
			int count = 0;
			List<Integer> numbers = new ArrayList<>();
			for(int y = 0; y < height; y++) {
				String s = this.convertedAnswer.get(y).get(x);
				if (y == height - 1 && s.equals("■")) {/* 行の右はじに来たとき */
					count += 1;
					numbers.add(count);
				} else if(s.equals("■")) {
					count += 1;
				} else if(y >= 1 && this.convertedAnswer.get(y - 1).get(x).equals("■") 
						&& s.equals("□")) {/* 行の左はじ以外で今いるマスが空白かつ直前が■のとき */
					numbers.add(count);
					count = 0;
				}
			}
			
			verticalNumbers.add(numbers);
		}
		
	}
	
	@Override
	public void draw() {
//		画面クリア
		super.clear();
//		2次元コレクションの要素の要素数の最大値をもとめる
		int verticalMax = getMaxSize(this.verticalNumbers);
		int horizontalMax = getMaxSize(this.horizontalNumbers);
		
//		上の数字描画
		for(int y = 0; y < verticalMax; y++) {
			
//			左側描画
			for(int x1 = 0; x1 < horizontalMax; x1++) {
				System.out.print("　　　");/* 全角3 */
			}
			
//			右側描画
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
			System.out.println("");/* １行終わりまで来たら改行 */
			
//			数字の間１行空ける
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
			
//			ロジックのマス描画
			for(int x2 = 0; x2 < verticalNumbers.size(); x2++) {
				System.out.print(this.screen[y][x2] + "　");
				
			}
			
//			右のカーソル描画 
			if (y == this.cursor.getY()) {
				System.out.print("←　");
			} else {
				System.out.print("　　");
			}
			System.out.println("");/*1行描画し終わったら改行*/
		}
		
//		１行空ける
		for(int x1 = 0; x1 < horizontalMax; x1++) {
			System.out.print("　　　");/* 全角3 */
		}
		
		for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
			System.out.print("　　");/* 全角2 */
			
		}
		System.out.println("");/* １行終わりまで来たら改行 */
		
//		下のカーソル描画
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
		System.out.println("");/* １行終わりまで来たら改行 */
			
	}
	
//	2次元リストの内側の要素の要素数が最大のものをみつける
	private int getMaxSize(List<List<Integer>> numbers) {
		int max = 0;
		for(List<Integer> n: numbers) {
			int size = n.size();
			if(size > max) {
				max = size;
			}
		}
		return max;
	}
	
//  半角の数字を全角の数字へ変換する
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
	
//	ユーザーがキーを押したときのアクション
	public void tryToSolve() {
//		カーソルの位置を取得する
		int x = this.cursor.getX();
		int y = this.cursor.getY();
		
		System.out.println("Move w:up s:down a:left d:right other than left keys:select");
		System.out.println("Select b:mark ■ v:mark × n:mark □");
		String input = this.sc.nextLine();
//		規定のキーが押された場合はカーソル移動
		switch(input) {
		case "w": this.cursor.moveUp(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "s": this.cursor.moveDown(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "a": this.cursor.moveLeft(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "d": this.cursor.moveRight(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "b": this.screen[y][x] = "■"; break;
		case "v": this.screen[y][x] = "×"; break;
		case "n": this.screen[y][x] = "□"; break;
		}
	}
	
//	完成したか判定する
	public boolean isCompleted() {
		
		for(LogicPiece lp: this.logicBlackPiecePositions) {
			int x = lp.getX();
			int y = lp.getY();
			if(!this.answer[y][x].equals(screen[y][x])) {
				return false;
			}
		}
		
		return true;
	}
	
//	正解を描画する
	public void drawAnswer() {
		
		celebrateCompletion();
		
		for(int y = 0; y < this.answer.length; y++) {
			for(int x = 0; x < this.answer[0].length; x++) {
				System.out.print(this.answer[y][x]);
			}
			System.out.println("");
		}
		
		System.out.println(this.logicInfo.getLogicName());
	}
	
	private void celebrateCompletion() {
		System.out.println("Congratulations! You completed!");
	}
	
//	現在のデータをセーブする
//	public void save() {
//		
//	}
	
//	これから解くロジックを決める
//	public void selectLogic() {
//		
//	}
	
}
	
