package caseStudy;

public class Document {
	@Override
	public String toString() {
		return "Document [id=" + id + ", message=" + message + "]";
	}
	private int id;
	private String message;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
