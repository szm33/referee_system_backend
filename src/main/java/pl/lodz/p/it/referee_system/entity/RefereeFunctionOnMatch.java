package pl.lodz.p.it.referee_system.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class RefereeFunctionOnMatch {
//dodanie unikalnosci na id meczu i funkcji
    // dodanie unikalnosci na sedza mecz
    // check constraint czy druzyny nie sa te same
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "match_function_id", referencedColumnName = "id")
    private MatchFunction matchFunction;
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
