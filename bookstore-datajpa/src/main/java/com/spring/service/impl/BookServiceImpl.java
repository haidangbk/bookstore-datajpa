package com.spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.spring.entities.Book;
import com.spring.reponsitory.BookReponsitory;
import com.spring.service.BookService;

@Service

public class BookServiceImpl implements BookService{

	@Autowired
	BookReponsitory bookReponsitory;
	
	@Override
	public Book findById(int id) {
		return bookReponsitory.findById(id);
	}

	@Override
	public List<Book> findAllYetRemove() {
		return bookReponsitory.findAllYetRemove();
	}

	@Override
	public List<Book> findAllRemoved() {
		return bookReponsitory.findAllRemoved();
	}

	@Override
	public void addBook(Book book) {
		bookReponsitory.addBook(book);
	}

	@Override
	public void removeBook(int id) {
		bookReponsitory.removeBook(id);
	}

	@Override
	public void restoreBook(int id) {
		bookReponsitory.restoreBook(id);
	}

	@Override
	public void deleteBook(int id) {
		bookReponsitory.deleteBook(id);
	}

	@Override
	public void updateBook(Book book) {
		bookReponsitory.updateBook(book);
	}

	@Override
	public List<Book> findAllBySearch(String search) {
		return bookReponsitory.findAllBySearch(search);
	}

	@Override
	public List<Book> sortBook(List<Book>books,String column, String trend) {
		return bookReponsitory.sortBook(books,column, trend);
	}

}
