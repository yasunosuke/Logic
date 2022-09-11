package com.logic;

public enum Pieces {
	
	Black("Å°"), White("Å†"), Cross("Å~");
	
	private String shape;

	private Pieces(String shape) {
		this.shape = shape;
	}

	public String getShape() {
		return shape;
	}
	
}
