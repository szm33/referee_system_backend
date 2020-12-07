package pl.lodz.p.it.referee_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"match_id", "match_function_id"}),
        @UniqueConstraint(columnNames = {"match_id", "referee_id"})})
public class RefereeFunctionOnMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "match_function_id", referencedColumnName = "id")
    private MatchFunction matchFunction;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "referee_id", referencedColumnName = "id")
    private Referee referee;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;
    @OneToOne(mappedBy = "refereeFunctionOnMatch", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private ReplaceInformations replaceInformations;
    @Version
    @Column(nullable = false)
    private long version;
}
