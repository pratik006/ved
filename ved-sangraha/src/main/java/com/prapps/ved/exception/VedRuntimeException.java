package com.prapps.ved.exception;

public class VedRuntimeException extends RuntimeException {
    private Throwable t;
    private String msg;

    public VedRuntimeException() {}
    public VedRuntimeException(String msg, Throwable t) {
        this.msg = msg;
        this.t = t;
    }
}
