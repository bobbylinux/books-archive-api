package online.bobbylinux.bobbybook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import online.bobbylinux.bobbybook.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	@Query("SELECT a FROM Author a WHERE " + "LOWER(a.firstName) LIKE LOWER(CONCAT('%', :searchString, '%')) OR "
			+ "LOWER(a.lastName) LIKE LOWER(CONCAT('%', :searchString, '%')) AND deletedAt IS NULL")
	List<Author> searchAuthors(@Param("searchString") String searchString);

    @Modifying
    @Transactional
	@Query("UPDATE Author a SET a.deletedAt = CURRENT_TIMESTAMP WHERE a.id = :id")
	void deleteById(@Param("id") Long id);

	@Query("SELECT a FROM Author a WHERE id = :id AND deletedAt IS NULL")
	Optional<Author> findById(@Param("id") Long id);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END "
			+ "FROM Author a WHERE a.id = :id AND a.deletedAt IS NULL")
	boolean existsById(@Param("id") Long id);

}
