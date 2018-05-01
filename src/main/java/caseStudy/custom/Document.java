package caseStudy.custom;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Document {
	@NotNull
	private Integer id;
	@NotNull
	private String message;
	@JsonIgnore
	private Date date;
	@JsonIgnore
	private Long ttl;
	
	public Integer getId() 
	{
		return id;
	}
	public void setId(Integer id) 
	{
		this.id = id;
	}
	public String getMessage() 
	{
		return message;
	}
	public void setMessage(String message) 
	{
		this.message = message;
	}
	public Date getDate() 
	{
		return date;
	}
	public void setDate(Date date) 
	{
		this.date = date;
	}
	public Long getTTL() 
	{
		return ttl;
	}
	public void setTTL(Long tTL) 
	{
		ttl = tTL;
	}
	@Override
	public String toString() 
	{
		return "Document [id=" + id + ", message=" + message + "]";
	}
}

