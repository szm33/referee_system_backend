package pl.lodz.p.it.referee_system.mapper;

import pl.lodz.p.it.referee_system.dto.TeamCreateDTO;
import pl.lodz.p.it.referee_system.dto.TeamListDTO;
import pl.lodz.p.it.referee_system.entity.League;
import pl.lodz.p.it.referee_system.entity.Team;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

public class TeamMapper {

    public static Team map(TeamCreateDTO teamDTO) {
        Team team = new Team();
        League league = new League();
        league.setName(teamDTO.getLeague());
        team.setName(teamDTO.getName());
        team.setLeague(league);
        return team;
    }

    public static Team map(TeamListDTO teamDTO) {
        Team team = new Team();
        League league = new League();
        league.setName(teamDTO.getLeague());
        team.setName(teamDTO.getName());
        team.setVersion(ContextUtills.decrypt(teamDTO.getVersion()));
        team.setLeague(league);
        return team;
    }
}
