package nl.kmartin.springbootapiboilerplate.exceptionhandler.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

	// Description
	private String description;


	public ForbiddenException(String description) {
		this.description = description;
	}

}
