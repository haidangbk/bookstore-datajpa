package com.spring.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.entities.Book;
import com.spring.reponsitory.BookReponsi;
import com.spring.reponsitory.BookReponsitory;

@Controller
public class BookController {
	@Autowired
	BookReponsitory bookReponsitory;

	@Autowired
	BookReponsi bookReponsi;
	
	@GetMapping(value = { "/", "/list-book" })
	public String listBook(HttpServletRequest request) {
		List<Book> books = bookReponsitory.findAllYetRemove();
		request.setAttribute("books", books);
		return "book/listBook";
	}

	@GetMapping(value = "/remove-book/{id}")
	public String removeBook(HttpServletRequest request, @PathVariable(name = "id") int id) {
		bookReponsitory.removeBook(id);
		return "redirect:/list-book";
	}

	@GetMapping(value = "/list-book-removed")
	public String listBookRemoved(HttpServletRequest request) {
		List<Book> books = bookReponsitory.findAllRemove();
		request.setAttribute("books", books);
		return "book/listBookRemoved";
	}

	@GetMapping(value = "/restore-book/{id}")
	public String restoreBook(@PathVariable(name = "id") int id) {
		bookReponsitory.restoreBook(id);
		return "redirect:/list-book-removed";
	}

	@GetMapping(value = "/delete-book/{id}")
	public String deleteBook(@PathVariable(name = "id") int id) {
		bookReponsitory.deleteBook(id);
		return "redirect:/list-book-removed";
	}

	@GetMapping(value = "/add-book")
	public String addBook(Model model) {
		Book book = new Book();
		model.addAttribute(book);
		return "book/addBook";
	}

	@PostMapping(value = "/add-book")
	public String addBook(@ModelAttribute(name = "book") Book book) {
		bookReponsitory.save(book); // or saveAndFlush
		return "redirect:/list-book";
	}

	@GetMapping(value = "/update-book/{id}")
	public String updateBook(@PathVariable(name = "id") int id, HttpServletRequest request) {
		Optional<Book> book = bookReponsitory.findById(id);
		request.setAttribute("book", book.get());
		return "book/updateBook";
	}

	@PostMapping(value = "/update-book/{id}")
	public String updateBook(@PathVariable(name = "id") int id, @RequestParam(name = "name_book") String name_book,
			@RequestParam(name = "price_book") double price_book) {
		bookReponsitory.updateBook(name_book, price_book, id);
		return "redirect:/list-book";
	}

	@GetMapping(value = "/find-book")
	public String findBook(HttpServletRequest request, @RequestParam(name = "search") String search) {
		Collection<Book> books = bookReponsitory.findAllBySearch(search);
		if (!books.isEmpty()) {
			request.setAttribute("books", books);
			request.setAttribute("search", search);
			return "book/listBook";
		}
		return "redirect:/list-book";
	}
	
	@GetMapping(value = "/sort-book-by-{column}-{trend}")
	public String sortBook(HttpServletRequest request,@PathVariable (name="column") String column,@PathVariable(name="trend") String trend) {
		List<Book> books = bookReponsi.sortBook(column,trend);
		request.setAttribute("books", books);
		return "book/listBook";
	}
}
