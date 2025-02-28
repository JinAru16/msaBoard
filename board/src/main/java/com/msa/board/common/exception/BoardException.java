package com.msa.board.common.exception;

public class BoardException extends RuntimeException {
    protected Object[] args;

    public BoardException(String message) {
        super(message);
    }

    public BoardException(String message, Object[] args) {
        super(message);
        if ( args != null ) {
            this.args = args.clone();
        }
    }

}
