package online.bobbylinux.bobbybook.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.bobbylinux.bobbybook.enums.ContentType;

@Entity
@Table(name = "photos", uniqueConstraints = @UniqueConstraint(name = "unique_photos_path", columnNames = {
        "path" }))
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class Photo extends BaseEntity {
    @Column(name = "path", nullable = false, length = 500)
    private String path;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    public Photo(String path, String fileName, ContentType contentType) {
        this.path = path;
        this.fileName = fileName;
        this.contentType = contentType;
    }
}
