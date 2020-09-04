package pl.lodz.p.it.referee_system.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Team {

    @Id
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "league_id", referencedColumnName = "id")
    private League league;
    @Column(name = "home_team")
    @OneToMany(mappedBy = "homeTeam")
    private List<Match> home_matches = new ArrayList<>();
    @Column(name = "away_team")
    @OneToMany(mappedBy = "awayTeam")
    private List<Match> away_matches = new ArrayList<>();
    @NotNull
    @Version
    @Column
    private long version;
}
