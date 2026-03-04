package online.bobbylinux.bobbybook.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authors", uniqueConstraints = @UniqueConstraint(
        name = "unique_authors_first_name_last_name",
        columnNames = {"first_name", "last_name"}
    ))
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name", nullable = false, length = 255)
	private String firstName;
	@Column(name = "last_name", nullable = false, length = 255)
	private String lastName;
	@Column(name = "created_at", updatable = false, insertable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
	
	public Author(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

}
