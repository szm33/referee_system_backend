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
public class ReplacementInformation {

    public ReplacementInformation(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "referee_function_id", referencedColumnName = "id", unique = true)
    private RefereeFunctionOnMatch refereeFunctionOnMatch;
    @Column(nullable = false)
    private LocalDateTime executeTime;
    @OneToMany(mappedBy = "replacementInformation", cascade = CascadeType.ALL)
    private List<ReplacementCandidate> candidates;
    @Version
    @Column(nullable = false)
    private Long version;

}
