package pl.lodz.p.it.referee_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReplacementCandidate {

    public ReplacementCandidate() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "referee_for_replacement_id", referencedColumnName = "id")
    private Referee refereeForReplacement;
    @Column(nullable = false)
    private Long arrivalTime;
    @ManyToOne
    @JoinColumn(name = "replecment_information_id", referencedColumnName = "id")
    private ReplacementInformation replacementInformation;
    @Version
    @Column(nullable = false)
    private Long version;
}
