package online.bobbylinux.bobbybook.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "photos", uniqueConstraints = @UniqueConstraint(name = "unique_photos_path", columnNames = {
        "path" }))
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class Photo extends BaseEntity {
    @Column(name = "path")
    private String path;

    public Photo(String path) {
        this.path = path;
    }
}
