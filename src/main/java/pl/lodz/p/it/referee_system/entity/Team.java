package pl.lodz.p.it.referee_system.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "league_id", referencedColumnName = "id")
    private League league;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamOnMatch> matches = new ArrayList<>();
    @NotNull
    @Version
    @Column
    private long version;
}
