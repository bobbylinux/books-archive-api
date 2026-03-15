package online.bobbylinux.bobbybook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import online.bobbylinux.bobbybook.entities.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

	@Query("SELECT p FROM Publisher p WHERE " + "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchString, '%')) AND deletedAt IS NULL ORDER BY name")
	List<Publisher> searchPublisher(@Param("searchString") String searchString);

    @Modifying
    @Transactional
	@Query("UPDATE Publisher p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.id = :id")
	void deleteById(@Param("id") Long id);

	@Query("SELECT p FROM Publisher p WHERE id = :id AND deletedAt IS NULL")
	Optional<Publisher> findById(@Param("id") Long id);

	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END "
			+ "FROM Publisher p WHERE p.id = :id AND p.deletedAt IS NULL")
	boolean existsById(@Param("id") Long id);

	@Query("SELECT p FROM Publisher p WHERE deletedAt IS NULL ORDER BY name")
	List<Publisher> getAllPublishers();

}
