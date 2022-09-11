package com.logic;

public enum Pieces {
	
	Black("��"), White("��"), Cross("�~");
	
	private String shape;

	private Pieces(String shape) {
		this.shape = shape;
	}

	public String getShape() {
		return shape;
	}
	
}
