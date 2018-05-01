package caseStudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class DocumentException extends Exception{
	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	private String errorMessage;
 
	public DocumentException() {
		super();
	}
	
	public DocumentException(String errorMessage) {
		super(errorMessage);
		this.setErrorCode(HttpStatus.NOT_FOUND.value());
		this.errorMessage = errorMessage;
	}
	
	public DocumentException(Errors errors) {
		super(errors.getFieldError().getField() + " " +errors.getFieldError().getDefaultMessage());
		this.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorMessage = errors.getFieldError().getField() + " " + errors.getFieldError().getDefaultMessage();
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
