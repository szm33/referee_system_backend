package pl.lodz.p.it.referee_system.entity;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.enums.PermissionClass;

import javax.persistence.*;

@Entity(name = "referee")
@Data
public class Referee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "permission_class")
    @Enumerated(EnumType.STRING)
    private PermissionClass permissionClass;



}
