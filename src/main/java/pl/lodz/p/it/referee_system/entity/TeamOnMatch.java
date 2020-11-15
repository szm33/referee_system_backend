package pl.lodz.p.it.referee_system.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class TeamOnMatch {
//unikalnosc na id maczeu i is guest
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
    @NotNull
    @Version
    @Column
    private long version;
}
