package com.spring.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entities.Book;
import com.spring.service.BookService;

@RestController
public class BookAPI {
	@Autowired
	BookService bookService;
	
	@GetMapping(value= {"/api","list-book-api"})
	public List<Book> listBook(HttpServletRequest request){
		return bookService.findAllYetRemove();
	}
	
	@PutMapping(value = "/remove-book-api/{id}")
	public void removeBook( @PathVariable(name = "id") int id) {
		bookService.removeBook(id);
	}
	
	@GetMapping(value = "/list-book-removed-api")
	public List<Book> listBookRemoved(HttpServletRequest request) {
		return bookService.findAllRemoved();
	}
	
	@PutMapping(value = "/restore-book-api/{id}")
	public void restoreBook(@PathVariable(name = "id") int id) {
		bookService.restoreBook(id);
	}
	
	@DeleteMapping(value = "/delete-book-api/{id}")
	public void deleteBook(@PathVariable(name = "id") int id) {
		bookService.deleteBook(id);
	}
	
	@PostMapping(value = "/add-book-api")
	public void addBook(@RequestBody Book book) {
		bookService.addBook(book);
	}

	// Thiếu update
}
