package com.spring.reponsitory.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
public class BookReponsitoryImpl implements BookReponsitory {

	@Autowired
	EntityManager entityManager;

	@Override
	public Book findById(int id) {
		String jql = "SELECT b FROM Book b WHERE b.id_book = " + id;
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
		String jql = "UPDATE Book b SET b.flag_delete = 1 WHERE b.id_book = " + id;
		Query query = entityManager.createQuery(jql);
		query.executeUpdate();
	}

	@Override
	public void restoreBook(int id) {
		String jql = "UPDATE Book b SET b.flag_delete = 0 WHERE b.id_book = " + id;
		Query query = entityManager.createQuery(jql);
		query.executeUpdate();
	}

	@Override
	public void deleteBook(int id) {
		String jql = "DELETE FROM Book b WHERE id= " + id;
		Query query = entityManager.createQuery(jql);
		query.executeUpdate();
	}

	@Override
	public void updateBook(Book book) {
		entityManager.merge(book);
	}

	@Override
	public List<Book> findAllBySearch(String search) {
		String jql = "SELECT b FROM Book b WHERE b.name_book LIKE '%" + search
				+ "%' AND b.flag_delete = 0 OR b.price_book LIKE '%" + search + "%' AND b.flag_delete = 0";
		Query query = entityManager.createQuery(jql, Book.class);
		return query.getResultList();
	}

	@Override
	public List<Book> sortBook(List<Book> books, String column, String trend) {

		Collections.sort(books, new Comparator<Book>() {

			@Override
			public int compare(Book o1, Book o2) {
				switch (column) {
				case "id_book":
					if (trend.equalsIgnoreCase("asc")) {
						return o1.getId_book() - o2.getId_book();
					} else {
						return -1 * (o1.getId_book() - o2.getId_book());
					}
				case "name_book":
					if (trend.equalsIgnoreCase("asc")) {
						return o1.getName_book().compareToIgnoreCase(o2.getName_book());
					} else {
						return -1 * o1.getName_book().compareToIgnoreCase(o2.getName_book());
					}

				case "price_book":
					if (trend.equalsIgnoreCase("asc")) {
						return (int) (o1.getPrice_book() - o2.getPrice_book());
					} else {
						return -1 * (int) (o1.getPrice_book() - o2.getPrice_book());
					}
				default:
					return 0;
				}
			}
		});
		return books;
	}

	@Override
	public List<Book> pageBook(List<Book> books, int indexStart, int total) {
		List<Book> listBook = new ArrayList<>();
		for (int i = indexStart; i < indexStart + total && i < books.size(); i++) {
			listBook.add(books.get(i));
		}
		return listBook;
	}
}
