package com.spring.reponsitory.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.entities.Book;
import com.spring.reponsitory.BookReponsi;

@Repository
public class BookReponsiImpl implements BookReponsi{

	@Autowired
	EntityManager entityManager;
	
	@Override
	public List<Book> sortBook(String column, String trend) {
		String jql = "SELECT b FROM Book b WHERE b.flag_delete = 0 ORDER BY "+column+" "+trend;
		Query query = entityManager.createQuery(jql);
		return query.getResultList();
	}

}
