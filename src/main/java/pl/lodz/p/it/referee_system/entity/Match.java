package pl.lodz.p.it.referee_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<RefereeFunctionOnMatch> referees = new ArrayList<>();
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<TeamOnMatch> teams = new ArrayList<>();
    @Column(name = "date_of_match", nullable = false)
    private LocalDate dateOfMatch;
    @Column(nullable = false)
    private LocalTime matchTime;
    @Column
    private String description;
    @Version
    @Column(nullable = false)
    private long version;
}
