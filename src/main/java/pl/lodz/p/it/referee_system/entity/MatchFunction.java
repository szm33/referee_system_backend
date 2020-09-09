package pl.lodz.p.it.referee_system.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class MatchFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "referee_id", referencedColumnName = "id")
    private Referee referee;
    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;
    @NotNull
    @Version
    @Column
    private long version;
}
