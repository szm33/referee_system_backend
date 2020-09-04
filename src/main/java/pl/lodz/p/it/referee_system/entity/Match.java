package pl.lodz.p.it.referee_system.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Match {

    @Id
    private Long id;
    @OneToMany(mappedBy = "match")
    private List<MatchFunction> refereeFunctions = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id")
    private Team awayTeam;
    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id")
    private Team homeTeam;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfMatch;
    @Column
    private Integer homeScore;
    @Column
    private Integer awayScore;
    @Column
    private String description;
    @NotNull
    @Version
    @Column
    private long version;
}
