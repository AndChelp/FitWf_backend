package info.andchelp.fitwf.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {
    @Column(nullable = false)
    private String role;
}
