package pl.lodz.p.it.referee_system.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReplaceInformations {

    public ReplaceInformations(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "referee_function_id", referencedColumnName = "id")
    private RefereeFunctionOnMatch refereeFunctionOnMatch;
    @Column(nullable = false)
    private LocalDateTime executeTime;
    @OneToMany(mappedBy = "replaceInformations", cascade = CascadeType.ALL)
    private List<ReplacementCandidate> candidates;
//    @OneToOne
//    @JoinColumn(name = "referee_for_replacement_id", referencedColumnName = "id")
//    private Referee refereeForReplacement;
//    private Long arrivalTime;
    @Version
    @Column(nullable = false)
    private Long version;

}
