/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
		          return getDocumentById(arg0);
		      }
		    }       
		);
	}
	
	public static void main(String[] args) throws Exception {
	    SpringApplication.run(Main.class, args);
	  }
	
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public ResponseEntity<String> storeDocument(@RequestBody Document documentRequest)
	{
		cache.put(documentRequest.getId(), documentRequest);
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDocument(@PathVariable Integer id)
	{
		try {
			return new ResponseEntity<>(getDocumentById(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Resource Not Found",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST )
	public @ResponseBody String getMethod(@RequestBody Document documentRequest)
	{
		return documentRequest.toString();
	}
	
	Document getDocumentById(Integer id) throws ExecutionException
	{
		return cache.get(id);
	}
}
