package pl.lodz.p.it.referee_system.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class League {

    @Id
    private Long id;
    @Column
    private String name;
}
