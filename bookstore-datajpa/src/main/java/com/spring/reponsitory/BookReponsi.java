package com.spring.reponsitory;

import java.util.List;

import com.spring.entities.Book;

public interface BookReponsi {
	
	public List<Book> sortBook(String column, String trend);
}
