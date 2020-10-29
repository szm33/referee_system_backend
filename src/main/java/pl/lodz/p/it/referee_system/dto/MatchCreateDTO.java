package pl.lodz.p.it.referee_system.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MatchCreateDTO {

    private Long awayTeamId;
    private Long homeTeamId;
    private String description;
    private LocalDate date;
    private List<FreeRefereeDTO> referees = new ArrayList<>();
}