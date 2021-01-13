package br.edu.ifsp.entregafacil.service.exceptions;

public class InvalidOldPassowordException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidOldPassowordException(String msg) {
		super(msg);
	}

	public InvalidOldPassowordException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
