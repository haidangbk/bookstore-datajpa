package com.spring.reponsitory.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.spring.entities.Book;
import com.spring.reponsitory.BookReponsitory;

@Transactional
@Repository
public class BookReponsitoryImpl implements BookReponsitory{

	@Autowired
	EntityManager entityManager;
	
	@Override
	public Book findById(int id) {
		String jql = "SELECT b FROM Book b WHERE b.id_book = "+id;
		Query query = entityManager.createQuery(jql);
		return (Book) query.getSingleResult();
	}
	
	@Override
	public List<Book> findAllYetRemove() {
		String jql = "SELECT b FROM Book b WHERE b.flag_delete = 0";
		Query query = entityManager.createQuery(jql);
		return query.getResultList();
	}

	@Override
	public List<Book> findAllRemoved() {
		String jql = "SELECT b FROM Book b WHERE b.flag_delete = 1";
		Query query = entityManager.createQuery(jql);
		return query.getResultList();
	}

	@Override
	public void addBook(Book book) {
		entityManager.persist(book);
	}
	
	@Override
	public void removeBook(int id) {
		String jql = "UPDATE Book b SET b.flag_delete = 1 WHERE b.id_book = "+id;
		Query query = entityManager.createQuery(jql);
		query.executeUpdate();
	}

	@Override
	public void restoreBook(int id) {
		String jql = "UPDATE Book b SET b.flag_delete = 0 WHERE b.id_book = "+id;
		Query query = entityManager.createQuery(jql);
		query.executeUpdate();
	}

	@Override
	public void deleteBook(int id) {
		String jql = "DELETE FROM Book b WHERE id= "+id;
		Query query = entityManager.createQuery(jql);
		query.executeUpdate();
	}

	@Override
	public void updateBook(Book book) {
		entityManager.merge(book);
	}

	@Override
	public List<Book> findAllBySearch(String search) {
//		String jql = "SELECT b FROM Book b WHERE b.name_book LIKE %:search AND b.flag_delete = 0 OR b.price_book LIKE %:search AND b.flag_delete = 0";
//		Query query = entityManager.createQuery(jql,Book.class).setParameter("search", search);
		String jql = "SELECT b FROM Book b WHERE b.name_book LIKE '%"+search+"%' AND b.flag_delete = 0 OR b.price_book LIKE '%"+search+"%' AND b.flag_delete = 0";
		Query query = entityManager.createQuery(jql,Book.class);
		return query.getResultList();
	}

	@Override
	public List<Book> sortBook(String column, String trend) {
		String jql = "SELECT b FROM Book b WHERE b.flag_delete = 0 ORDER BY "+column+" "+trend;
		Query query = entityManager.createQuery(jql);
		return query.getResultList();
	}

}
