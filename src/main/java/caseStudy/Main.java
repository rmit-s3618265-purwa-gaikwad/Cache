package caseStudy;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import caseStudy.Document;
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
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;

@SpringBootApplication
@Controller
public class Main {

	private LoadingCache<Integer,Document> cache;
	
	public Main()
	{
		cache = CacheBuilder.newBuilder()
		    .expireAfterWrite(30, TimeUnit.SECONDS)
		    .build(new CacheLoader<Integer, Document>(){
		
		      @Override
		      public Document load(Integer arg0) throws Exception {
		          // TODO Auto-generated method stub
		          return cache.getIfPresent(arg0);
		      }
		    }       
		);
	}
	
	public static void main(String[] args) throws Exception{
			SpringApplication.run(Main.class, args);
	  }
	
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public ResponseEntity<String> storeDocument(@Valid @RequestBody Document documentRequest, Errors errors) throws DocumentException
	{
		if(errors.hasErrors())
		{
			throw new DocumentException(errors);
		}
		cache.put(documentRequest.getId(), documentRequest);
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDocument(@PathVariable Integer id) throws DocumentException
	{
		Document responseDocument = cache.getIfPresent(id);
		if(responseDocument == null)
			throw new DocumentException("Resource not found");
		
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
	
	@RequestMapping(value = "/test", method = RequestMethod.POST )
	public @ResponseBody String getMethod(@RequestBody Document documentRequest)
	{
		return documentRequest.toString();
	}
}
