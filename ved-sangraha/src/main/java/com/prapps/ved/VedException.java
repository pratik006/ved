package com.prapps.ved;

public class VedException extends Exception {
	private static final long serialVersionUID = 1L;

	private Throwable t;
	private String msg;
	
	public VedException() {}
	public VedException(String msg) {this.msg = msg;}

	
	public VedException(Throwable t) {
		this.t = t;
	}
	
	public Throwable getCause() {
		return t;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
