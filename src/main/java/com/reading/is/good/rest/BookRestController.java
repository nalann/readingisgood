package com.reading.is.good.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reading.is.good.dto.BookDTO;
import com.reading.is.good.service.BookService;

@RestController
@RequestMapping("/books")
public class BookRestController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping(value = "/save")
	public ResponseEntity<?> save(@RequestBody BookDTO bookDTO){
		return new ResponseEntity<>(bookService.saveOrUpdate(bookDTO), HttpStatus.CREATED);
	}

}
