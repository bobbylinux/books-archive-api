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
@Table(name = "publishers", uniqueConstraints = @UniqueConstraint(name = "unique_publishers_name", columnNames = {
        "name" }))
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Publisher extends BaseEntity {
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public Publisher(String name) {
        this.name = name;
    }
}
