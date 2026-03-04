package online.bobbylinux.bobbybook.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import online.bobbylinux.bobbybook.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {


	@Query("SELECT a FROM Author a WHERE " + "LOWER(a.firstName) LIKE LOWER(CONCAT('%', :searchString, '%')) OR "
			+ "LOWER(a.lastName) LIKE LOWER(CONCAT('%', :searchString, '%'))")
	List<Author> searchAuthors(@Param("searchString") String searchString);
}
