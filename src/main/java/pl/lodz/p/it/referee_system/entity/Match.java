package pl.lodz.p.it.referee_system.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "match")
    private List<RefereeFunctionOnMatch> referees = new ArrayList<>();
    @OneToMany(mappedBy = "match")
    private List<TeamOnMatch> teams = new ArrayList<>();
    @Column(name = "date")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate dateOfMatch;
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
