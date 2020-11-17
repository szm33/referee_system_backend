package pl.lodz.p.it.referee_system.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
public class ReplaceInformations {

    public ReplaceInformations() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "referee_function_id", referencedColumnName = "id")
    private RefereeFunctionOnMatch refereeFunctionOnMatch;
    private LocalDateTime executeTime;
    @OneToOne
    @JoinColumn(name = "referee_for_replacement_id", referencedColumnName = "id")
    private Referee refereeForReplacement;
    private Long arrivalTime;
    @Version
    private Long version;

}
