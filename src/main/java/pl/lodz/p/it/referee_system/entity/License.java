package pl.lodz.p.it.referee_system.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String type;
}
