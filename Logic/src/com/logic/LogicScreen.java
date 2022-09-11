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

//	�Q�[��������������
	public void init() {
		String selectedLogicId = String.valueOf(selectLogicRandaomly());
		this.logicInfo = logicService.getLogicInfo(selectedLogicId);
		putWhitePieceIntoPlainLogic();
		initiateScreen();
		putBlackPieceIntoPlainLogic();
		putArrayToList();
		getBlackPieceNumbers();
	}
	
//	���W�b�N�������_���ɑI��
	private int selectLogicRandaomly() {
		int selectedLogicId = random.nextInt(logicService.getRecordNumberFromLogicInfo()) + 1;
		return selectedLogicId;
	}
	
//	�X�N���[���ɕ\������鐔�����擾���邽�߂̑O�����A��́@���@�̕�����~������
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
	
//	screen�@���������A�@"��"�@�Ŗ��߂�
	private void initiateScreen() {

		int height = this.logicInfo.getHeight();
		int width = this.logicInfo.getWidth();
		
		this.screen = new String[height][width];
		
		for(int i = 0; i < height; i++) {
			screen[i] = this.answer[i].clone();
		}
		
	}
	
//	�� ����������
	private void putBlackPieceIntoPlainLogic() {
		this.logicBlackPiecePositions = logicService.getBlackPiecePositions(this.logicInfo.getLogicId());
		for(LogicPiece lbpp: this.logicBlackPiecePositions) {
			this.answer[lbpp.getY()][lbpp.getX()] = Black.getShape();
		}
	}
	
//	�z������X�g�ɕϊ�����
	private void putArrayToList() {
		for(int i = 0; i < this.answer.length; i++) {
			List<String> convertedList = new ArrayList<>(Arrays.asList(answer[i]));
			convertedAnswer.add(convertedList);
		}
	}
	
//	�X�N���[���ɕ\�����鐔�����擾����
	private void getBlackPieceNumbers() {
		
		int height = this.convertedAnswer.size();
		int width = this.convertedAnswer.get(0).size();
//		���������́@���@�����擾����
		for(int y = 0; y < height; y++) {
			int count = 0;
			List<Integer> numbers = new ArrayList<>();
			for(int x = 0; x < width; x++) {
				String s = this.convertedAnswer.get(y).get(x);
				if (x == width - 1 && s.equals("��")) {/* �s�̉E�͂��ɗ����Ƃ� */
					count += 1;
					numbers.add(count);
				} else if(s.equals("��")) {
					count += 1;
				} else if(x >= 1 && this.convertedAnswer.get(y).get(x - 1).equals("��") 
						&& s.equals("��")) {/* �s�̍��͂��ȊO�ō�����}�X���󔒂����O�����̂Ƃ� */
					numbers.add(count);
					count = 0;
				}
			}
			
			horizontalNumbers.add(numbers);
		}
		
//		���������́@���@���擾����
		for(int x = 0; x < width; x++) {
			int count = 0;
			List<Integer> numbers = new ArrayList<>();
			for(int y = 0; y < height; y++) {
				String s = this.convertedAnswer.get(y).get(x);
				if (y == height - 1 && s.equals("��")) {/* �s�̉E�͂��ɗ����Ƃ� */
					count += 1;
					numbers.add(count);
				} else if(s.equals("��")) {
					count += 1;
				} else if(y >= 1 && this.convertedAnswer.get(y - 1).get(x).equals("��") 
						&& s.equals("��")) {/* �s�̍��͂��ȊO�ō�����}�X���󔒂����O�����̂Ƃ� */
					numbers.add(count);
					count = 0;
				}
			}
			
			verticalNumbers.add(numbers);
		}
		
	}
	
	@Override
	public void draw() {
//		��ʃN���A
		super.clear();
//		2�����R���N�V�����̗v�f�̗v�f���̍ő�l�����Ƃ߂�
		int verticalMax = getMaxSize(this.verticalNumbers);
		int horizontalMax = getMaxSize(this.horizontalNumbers);
		
//		��̐����`��
		for(int y = 0; y < verticalMax; y++) {
			
//			�����`��
			for(int x1 = 0; x1 < horizontalMax; x1++) {
				System.out.print("�@�@�@");/* �S�p3 */
			}
			
//			�E���`��
			for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
				List<Integer> numbers = this.verticalNumbers.get(x2);
				int size = numbers.size();
				if(y >= verticalMax - size) {
//					���p������S�p�����ɕϊ�
					int i = numbers.get(y - (verticalMax - size));
					String s;
//					1�̐���������g�̑傫���͑S�p�R��
					if(i >= 10) {
						s = convertIntToFullWidth(i);
						System.out.print(s);/* �S�p�P */
					} else {
						int j = i + '�O';/* �S�p��0 10�ȏ�̒l�̕ϊ��͂ǂ�����?*/
						char c = (char)j;
						s = Character.toString(c);
						System.out.print(s + "�@");/* �S�p�Q */
//						System.out.print("�P�O");
					}
				} else {
					System.out.print("�@�@");/* �S�p2 */
				}
				
			}
			System.out.println("");/* �P�s�I���܂ŗ�������s */
			
//			�����̊ԂP�s�󂯂�
			if(y != verticalMax - 1) {
				for(int x1 = 0; x1 < horizontalMax; x1++) {
					System.out.print("�@�@�@");/* �S�p3 */
				}
				
				for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
					System.out.print("�@�@");/* �S�p2 */
				}
				
				System.out.println("");	
			}
			
		}
			
//		�������`��
		for(int y = 0; y < horizontalNumbers.size(); y++) {
			
//			���������̐����̕`��
			for(int x1 = 0; x1 < horizontalMax; x1++) {
				List<Integer> numbers = this.horizontalNumbers.get(y);
				int size = numbers.size();
				if(x1 >= horizontalMax - size) {
//					���p������S�p�����ɕϊ�
					int i = numbers.get(x1 - (horizontalMax - size));
					String s;
//					1�̐���������g�̑傫���͑S�p�R��
					if(i >= 10) {
						s = convertIntToFullWidth(i);
						System.out.print("�@" + s);/* �S�p�P */
					} else {
						int j = i + '�O';/* �S�p��0 10�ȏ�̒l�̕ϊ��͂ǂ�����?*/
						char c = (char)j;
						s = Character.toString(c);
						System.out.print("�@�@"+ s);/* �S�p�Q */
					}
					
				} else {
					System.out.print("�@�@�@");/* �S�p�R */
				}
			}
			
//			���W�b�N�̃}�X�`��
			for(int x2 = 0; x2 < verticalNumbers.size(); x2++) {
				System.out.print(this.screen[y][x2] + "�@");
				
			}
			
//			�E�̃J�[�\���`�� 
			if (y == this.cursor.getY()) {
				System.out.print("���@");
			} else {
				System.out.print("�@�@");
			}
			System.out.println("");/*1�s�`�悵�I���������s*/
		}
		
//		�P�s�󂯂�
		for(int x1 = 0; x1 < horizontalMax; x1++) {
			System.out.print("�@�@�@");/* �S�p3 */
		}
		
		for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
			System.out.print("�@�@");/* �S�p2 */
			
		}
		System.out.println("");/* �P�s�I���܂ŗ�������s */
		
//		���̃J�[�\���`��
		for(int x1 = 0; x1 < horizontalMax; x1++) {
			System.out.print("�@�@�@");/* �S�p3 */
		}
		
		for(int x2 = 0; x2 < this.verticalNumbers.size(); x2++) {
			if(x2 == this.cursor.getX()) {
				System.out.print("���@");
			} else {
				System.out.print("�@�@");/* �S�p2 */
			}
			
		}
		System.out.println("");/* �P�s�I���܂ŗ�������s */
			
	}
	
//	2�������X�g�̓����̗v�f�̗v�f�����ő�̂��̂��݂���
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
	
//  ���p�̐�����S�p�̐����֕ϊ�����
	private String convertIntToFullWidth(int i) {
		StringBuilder sb = new StringBuilder();
		while(i != 0) {
			int j = i % 10 +'�O';
			char c = (char)j;
			String s = Character.toString(c);
			sb.append(s);
			i /= 10;
		}
		
		return sb.reverse().toString();
	}
	
//	���[�U�[���L�[���������Ƃ��̃A�N�V����
	public void tryToSolve() {
//		�J�[�\���̈ʒu���擾����
		int x = this.cursor.getX();
		int y = this.cursor.getY();
		
		System.out.println("Move w:up s:down a:left d:right other than left keys:select");
		System.out.println("Select b:mark �� v:mark �~ n:mark ��");
		String input = this.sc.nextLine();
//		�K��̃L�[�������ꂽ�ꍇ�̓J�[�\���ړ�
		switch(input) {
		case "w": this.cursor.moveUp(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "s": this.cursor.moveDown(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "a": this.cursor.moveLeft(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "d": this.cursor.moveRight(horizontalNumbers.size(), verticalNumbers.size()); break;
		case "b": this.screen[y][x] = "��"; break;
		case "v": this.screen[y][x] = "�~"; break;
		case "n": this.screen[y][x] = "��"; break;
		}
	}
	
//	�������������肷��
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
	
//	������`�悷��
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
	
//	���݂̃f�[�^���Z�[�u����
//	public void save() {
//		
//	}
	
//	���ꂩ��������W�b�N�����߂�
//	public void selectLogic() {
//		
//	}
	
}
	
