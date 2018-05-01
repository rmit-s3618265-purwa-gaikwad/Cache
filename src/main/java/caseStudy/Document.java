package caseStudy;

import javax.validation.constraints.NotNull;

public class Document {
	@NotNull
	private Integer id;
	@NotNull
	private String message;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Document [id=" + id + ", message=" + message + "]";
	}
}
