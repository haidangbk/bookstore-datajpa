package com.spring.reponsitory;

import java.util.List;

import com.spring.entities.Book;

public interface BookReponsitory {

	public Book findById(int id);

	public List<Book> findAllYetRemove();

	public List<Book> findAllRemoved();
	
	public void addBook(Book book);

	public void removeBook(int id);

	public void restoreBook(int id);

	public void deleteBook(int id);

	public void updateBook(Book book);

	public List<Book> findAllBySearch(String search);

	public List<Book> sortBook(String column, String trend);
}
