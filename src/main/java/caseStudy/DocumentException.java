package caseStudy;

import org.springframework.validation.Errors;

public class DocumentException extends Exception{
	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	private String errorMessage;
 
	public String getErrorMessage() {
		return errorMessage;
	}
	public DocumentException(String errorMessage) {
		super(errorMessage);
		this.setErrorCode(404);
		this.errorMessage = errorMessage;
	}
	public DocumentException() {
		super();
	}
	public DocumentException(Errors errors) {
		super(errors.getFieldError().getField() + " " +errors.getFieldError().getDefaultMessage());
		this.setErrorCode(400);
		errorMessage = errors.getFieldError().getField() + " " + errors.getFieldError().getDefaultMessage();
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
}
