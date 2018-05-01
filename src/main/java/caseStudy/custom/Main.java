package caseStudy.custom;

import caseStudy.custom.Document;
import caseStudy.exception.DocumentException;
import caseStudy.exception.ErrorResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Date;
import java.util.HashMap;
import javax.validation.Valid;

@SpringBootApplication
@Controller
public class Main {

	private static HashMap<Integer, Document> cache;

	public static void main(String[] args)
	{
		cache = new HashMap<>();
		SpringApplication.run(Main.class, args);
	}
	
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public ResponseEntity<String> storeDocument(@Valid @RequestBody Document documentRequest, Errors errors) throws DocumentException
	{
		if(errors.hasErrors())
			throw new DocumentException(errors);
		
		documentRequest.setDate(new Date());
		if(documentRequest.getTTL() == null)
			documentRequest.setTTL(30000l);
		cache.put(documentRequest.getId(), documentRequest);
		return new ResponseEntity<>("Successfully added",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
	public ResponseEntity<Document> getDocument(@PathVariable Integer id) throws DocumentException
	{
		Document responseDocument  = cache.get(id);
		
		if(responseDocument == null)
			throw new DocumentException("Resource not found");
		
		Date currentDate = new Date();
		if((currentDate.getTime() - responseDocument.getDate().getTime()) > responseDocument.getTTL())
		{
			cache.remove(id);	
			throw new DocumentException("Resource not found");
		}
		
		return new ResponseEntity<>(responseDocument,HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		if(ex instanceof DocumentException)
			error.setErrorCode(((DocumentException) ex).getErrorCode());
		else
			error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
			
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}
}
