package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.entities.Book;
import com.spring.service.BookService;

@Controller
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping(value = { "/", "/list-book" })
	public String listBook(HttpServletRequest request, HttpSession session) {
		session.setAttribute("search","");
		List<Book> books = bookService.findAllYetRemove();
		request.setAttribute("books", books);
		return "book/listBook";
	}

	@GetMapping(value = "/remove-book/{id}")
	public String removeBook(@PathVariable(name = "id") int id) {
		bookService.removeBook(id);
		return "redirect:/list-book";
	}

	@GetMapping(value = "/list-book-removed")
	public String listBookRemoved(HttpServletRequest request) {
		List<Book> books = bookService.findAllRemoved();
		request.setAttribute("books", books);
		return "book/listBookRemoved";
	}

	@GetMapping(value = "/restore-book/{id}")
	public String restoreBook(@PathVariable(name = "id") int id) {
		bookService.restoreBook(id);
		return "redirect:/list-book-removed";
	}

	@GetMapping(value = "/delete-book/{id}")
	public String deleteBook(@PathVariable(name = "id") int id) {
		bookService.deleteBook(id);
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
		bookService.addBook(book);
		return "redirect:/list-book";
	}

	@GetMapping(value = "/update-book/{id}")
	public String updateBook(@PathVariable(name = "id") int id, HttpServletRequest request) {
		Book book = bookService.findById(id);
		request.setAttribute("book", book);
		return "book/updateBook";
	}

	@PostMapping(value = "/update-book/{id}")
	public String updateBook(@PathVariable(name = "id") int id, @RequestParam(name = "name_book") String name_book,
			@RequestParam("price_book") double price_book) {
		Book book = bookService.findById(id);
		book.setName_book(name_book);
		book.setPrice_book(price_book);
		bookService.updateBook(book);
		return "redirect:/list-book";
	}

	@GetMapping(value = "/find-book")
	public String findBook(HttpServletRequest request, @RequestParam(name = "search") String search,
			HttpSession session) {
		session.setAttribute("search", search);
		List<Book> books = bookService.findAllBySearch(search);
		if (!books.isEmpty()) {
			request.setAttribute("books", books);
			return "book/listBook";
		}
		return "redirect:/list-book";
	}

	@GetMapping(value = "/sort-book-by-{column}-{trend}")
	public String sortBook(HttpServletRequest request, @PathVariable(name = "column") String column,
			@PathVariable(name = "trend") String trend, HttpSession session) {
		String search = (String) session.getAttribute("search");
		List<Book> books = null;
		if (search != "" || search != null) {
			List<Book> listBook = bookService.findAllBySearch(search);
			books = bookService.sortBook(listBook, column, trend);
		} else {
			List<Book> listBook = bookService.findAllYetRemove();
			books = bookService.sortBook(listBook, column, trend);
		}
		request.setAttribute("books", books);
		return "book/listBook";
	}
	
	@GetMapping(value = "/sort-book-by-{column}-{trend}/{pageid}")
	public String sortBook(HttpServletRequest request, @PathVariable(name = "column") String column,
			@PathVariable(name = "trend") String trend, HttpSession session,
			@PathVariable(name = "pageid") int pageid) {
		String search = (String) session.getAttribute("search");
		int total = (int) session.getAttribute("total");
		int indexStart = pageid;
		if (pageid == 1) {
			indexStart = 0;
		} else {
			indexStart = (indexStart - 1) * total;
		}

		List<Book> books = null;
		if (search != "" || search != null) {
			List<Book> listBook = bookService.findAllBySearch(search);
			books = bookService.sortBook(listBook, column, trend);
		} else {
			List<Book> listBook = bookService.findAllYetRemove();
			books = bookService.sortBook(listBook, column, trend);
		}
		books = bookService.pageBook(books, indexStart, total);
		request.setAttribute("books", books);
		return "book/listBook";
	}

	@GetMapping(value = "/list-book/{pageid}")
	public String pageBook(@PathVariable int pageid, HttpServletRequest request, HttpSession session) {
		int total = 5;
		int indexStart = pageid;
		if (pageid == 1) {
			indexStart = 0;
		} else {
			indexStart = (indexStart - 1) * total;
		}
		List<Book> books = bookService.findAllYetRemove();
		books = bookService.pageBook(books, indexStart, total);
		request.setAttribute("books", books);
		session.setAttribute("pageid", pageid);
		session.setAttribute("total", total);
		session.setAttribute("search","");
		return "book/listBook";
	}
}
