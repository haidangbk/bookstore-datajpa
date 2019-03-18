package com.spring.reponsitory;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entities.Book;

@Transactional
public interface BookReponsitory extends JpaRepository<Book, Integer> {

	@Query("SELECT b FROM Book b WHERE b.flag_delete = 0")
	public List<Book> findAllYetRemove();
	
	@Query("SELECT b FROM Book b WHERE b.flag_delete = 1")
	public List<Book> findAllRemove();
	
	@Modifying
	@Query("UPDATE Book b SET b.flag_delete = 1 WHERE b.id_book = ?1")
	public void removeBook(int id); 
	
	@Modifying
	@Query("UPDATE Book b SET b.flag_delete = 0 WHERE b.id_book = ?1")
	public void restoreBook(int id);
	
	@Modifying // fai có modifying để xác thực sẽ thay dổi dữ liệu
	@Query("DELETE FROM Book b WHERE id= ?1")
	public void deleteBook(int id);

	@Modifying
	@Query("UPDATE Book b SET b.name_book = ?1,b.price_book = ?2 WHERE b.id_book = ?3")
	public void updateBook(String nameBook,double priceBook,int idBook);

	@Query("SELECT b FROM Book b WHERE b.name_book LIKE %:search% AND b.flag_delete = 0 OR b.price_book LIKE %:search% AND b.flag_delete = 0 ")
	public Collection<Book> findAllBySearch(@Param("search")String search);
	
////	Sắp xếp book tồn tại theo tên 
//// tên cật và tham số như ASC hay DESC không đươc là tham số, TÌM HIỂU "QueryDSL"
//	@Query(value="SELECT b FROM Book b WHERE b.flag_delete = 0 ORDER BY :column :trend",nativeQuery=true)
//	public List<Book> sortBook(@Param("column")String column,@Param("trend")String trend);
}
