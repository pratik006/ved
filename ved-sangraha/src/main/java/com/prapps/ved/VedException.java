package com.prapps.ved;

public class VedException extends Exception {
	private static final long serialVersionUID = 1L;

	private Throwable t;
	
	public VedException() {}
	
	public VedException(Throwable t) {
		this.t = t;
	}
	
	public Throwable getCause() {
		return t;
	}
}
