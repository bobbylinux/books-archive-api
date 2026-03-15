package online.bobbylinux.bobbybook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import online.bobbylinux.bobbybook.entities.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query("SELECT p FROM Photo p WHERE "
            + "LOWER(p.fileName) LIKE LOWER(CONCAT('%', :searchString, '%')) AND deletedAt IS NULL ORDER BY fileName")
    List<Photo> searchPhoto(@Param("searchString") String searchString);

    @Modifying
    @Transactional
    @Query("UPDATE Photo p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.id = :id")
    void deleteById(@Param("id") Long id);

    @Query("SELECT p FROM Photo p WHERE id = :id AND deletedAt IS NULL")
    Optional<Photo> findById(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END "
            + "FROM Photo p WHERE p.id = :id AND p.deletedAt IS NULL")
    boolean existsById(@Param("id") Long id);

    @Query("SELECT p FROM Photo p WHERE deletedAt IS NULL ORDER BY fileName")
    List<Photo> getAllPhotos();
}
