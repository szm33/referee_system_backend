package pl.lodz.p.it.referee_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "isGuest"})})
public class TeamOnMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private boolean isGuest;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;
    @Version
    @Column(nullable = false)
    private long version;
}
