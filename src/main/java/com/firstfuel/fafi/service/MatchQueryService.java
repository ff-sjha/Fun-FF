package com.firstfuel.fafi.service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firstfuel.fafi.domain.Franchise;
// for static metamodels
import com.firstfuel.fafi.domain.Match;
import com.firstfuel.fafi.domain.MatchPlayers;
import com.firstfuel.fafi.domain.MatchUmpire;
import com.firstfuel.fafi.domain.Match_;
import com.firstfuel.fafi.domain.SeasonsFranchise_;
import com.firstfuel.fafi.domain.TieMatch;
import com.firstfuel.fafi.domain.TieMatchPlayers;
import com.firstfuel.fafi.domain.TieMatchSets;
import com.firstfuel.fafi.domain.Tournament_;
import com.firstfuel.fafi.repository.MatchPlayersRepository;
import com.firstfuel.fafi.repository.MatchRepository;
import com.firstfuel.fafi.service.dto.FranchisePlayersDTO;
import com.firstfuel.fafi.service.dto.MatchCriteria;
import com.firstfuel.fafi.service.dto.MatchDTO;
import com.firstfuel.fafi.service.dto.MatchPlayersDTO;
import com.firstfuel.fafi.service.dto.MatchUmpireCriteria;
import com.firstfuel.fafi.service.dto.MatchUmpireDTO;
import com.firstfuel.fafi.service.dto.TieMatchCriteria;
import com.firstfuel.fafi.service.dto.TieMatchDTO;
import com.firstfuel.fafi.service.dto.TieMatchPlayersCriteria;
import com.firstfuel.fafi.service.dto.TieMatchSetsCriteria;
import com.firstfuel.fafi.service.mapper.MatchMapper;
import com.firstfuel.fafi.service.mapper.MatchPlayersMapper;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Service for executing complex queries for Match entities in the database. The
 * main input is a {@link MatchCriteria} which get's converted to
 * {@link Specifications}, in a way that all the filters must apply. It returns
 * a {@link List} of {@link MatchDTO} or a {@link Page} of {@link MatchDTO}
 * which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatchQueryService extends QueryService<Match> {

    private final Logger log = LoggerFactory.getLogger(MatchQueryService.class);

    private final MatchRepository matchRepository;

    @Autowired
    private MatchPlayersRepository matchPlayerRepository;

    private final MatchMapper matchMapper;

    @Autowired
    private MatchPlayersMapper matchPlayerMapper;

    @Autowired
    private MatchUmpireQueryService matchUmpireQueryService;

    @Autowired
    private TieMatchQueryService tieMatchQueryService;

    @Autowired
    private TieMatchPlayersQueryService tieMatchQueryPlayerService;

    @Autowired
    private TieMatchSetsQueryService tieMatchSetQueryService;

    public MatchQueryService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    /**
     * Return a {@link List} of {@link MatchDTO} which matches the criteria from the
     * database
     * 
     * @param criteria
     *            The object which holds all the filters, which the entities should
     *            match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatchDTO> findByCriteria(MatchCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Match> specification = createSpecification(criteria);
        return matchMapper.toDto(matchRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MatchDTO} which matches the criteria from the
     * database
     * 
     * @param criteria
     *            The object which holds all the filters, which the entities should
     *            match.
     * @param page
     *            The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchDTO> findByCriteria(MatchCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Match> specification = createSpecification(criteria);
        final Page<Match> result = matchRepository.findAll(specification, page);
        return result.map(matchMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MatchDTO> getAllUpcomingMatches() {
        log.debug("getAllUpcomingMatches");
        ZonedDateTimeFilter dateFilter = new ZonedDateTimeFilter();
        dateFilter.setGreaterOrEqualThan(ZonedDateTime.now());
        Specifications<Match> specification = Specifications
                .where(buildRangeSpecification(dateFilter, Match_.endDateTime));
        final List<Match> result = matchRepository.findAll(specification, new Sort(Direction.ASC, "startDateTime"));
        return matchMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<MatchDTO> getFixtures() {
        log.debug("getFixtures");
        final List<Match> result = matchRepository
                .findByTournamentEndDateGreaterThanEqualOrderByStartDateTimeAsc(LocalDate.now());
        List<MatchDTO> response = new ArrayList<>();
        result.forEach(m -> {
            MatchDTO matchDto = matchMapper.toDto(m);
            List<MatchPlayers> matchPlayers = matchPlayerRepository.findByMatch(m);
            Map<Long, List<MatchPlayersDTO>> teamPlayers = new HashMap<>();
            matchPlayers.forEach(mp -> {
                Franchise fr = mp.getSeasonsFranchisePlayer().getSeasonsFranchise().getFranchise();
                if (!teamPlayers.containsKey(fr.getId())) {
                    teamPlayers.put(fr.getId(), new ArrayList<>());
                }
                teamPlayers.get(fr.getId()).add(matchPlayerMapper.toDto(mp));
            });
            List<FranchisePlayersDTO> fplayers = new ArrayList<>(teamPlayers.size());
            teamPlayers.forEach((k, v) -> {

            });
            if (m.getTeam1() != null) {
                Franchise franchise = m.getTeam1().getFranchise();
                fplayers.add(new FranchisePlayersDTO(franchise.getId(), franchise.getName(), m.getTeam1Points(),
                        teamPlayers.get(franchise.getId())));
            }
            if (m.getTeam2() != null) {
                Franchise franchise = m.getTeam2().getFranchise();
                fplayers.add(new FranchisePlayersDTO(franchise.getId(), franchise.getName(), m.getTeam2Points(),
                        teamPlayers.get(franchise.getId())));
            }
            if (m.getTeam3() != null) {
                Franchise franchise = m.getTeam3().getFranchise();
                fplayers.add(new FranchisePlayersDTO(franchise.getId(), franchise.getName(), m.getTeam3Points(),
                        teamPlayers.get(franchise.getId())));
            }
            if (m.getTeam4() != null) {
                Franchise franchise = m.getTeam4().getFranchise();
                fplayers.add(new FranchisePlayersDTO(franchise.getId(), franchise.getName(), m.getTeam4Points(),
                        teamPlayers.get(franchise.getId())));
            }
            matchDto.setTeamPlayers(fplayers);
            setTieMatches(m.getId(), matchDto);
            MatchUmpireCriteria criteria = new MatchUmpireCriteria();
            LongFilter matchId = new LongFilter();
            matchId.setEquals(m.getId());
            criteria.setMatchId(matchId);
            Page<MatchUmpire> page = matchUmpireQueryService.findByCriteria(criteria, new PageRequest(0, 1000));
            List<MatchUmpireDTO> umpires = new ArrayList<>();
            page.forEach(u -> {
                MatchUmpireDTO umpire = new MatchUmpireDTO();
                umpire.setId(u.getId());
                umpire.setFirstName(u.getUmpire().getFirstName());
                umpire.setLastName(u.getUmpire().getLastName());
                umpire.setMatchId(u.getMatch().getId());
                umpire.setUmpireId(u.getUmpire().getId());
                umpires.add(umpire);
            });
            matchDto.setUmpires(umpires);
            response.add(matchDto);
        });
        return response;
    }

    private void setTieMatches(Long matchId, MatchDTO matchDto) {
        TieMatchCriteria tieMatchCriteria = new TieMatchCriteria();
        LongFilter matchFilter = new LongFilter();
        matchFilter.setEquals(matchId);
        tieMatchCriteria.setMatchId(matchFilter);
        List<TieMatch> tieMatches = tieMatchQueryService.findByCriteria(tieMatchCriteria);
        List<TieMatchDTO> tieMatchDTOs = new ArrayList<>();
        tieMatches.forEach(t -> {
            LongFilter tIdFilter = new LongFilter();
            tIdFilter.setEquals(t.getId());
            TieMatchPlayersCriteria playerCriteria = new TieMatchPlayersCriteria();
            playerCriteria.setTieMatchId(tIdFilter);
            List<TieMatchPlayers> players = tieMatchQueryPlayerService.findByCriteria(playerCriteria);
            TieMatchSetsCriteria setCriteria = new TieMatchSetsCriteria();
            setCriteria.setTieMatchId(tIdFilter);
            List<TieMatchSets> sets = tieMatchSetQueryService.findByCriteria(setCriteria);
            tieMatchDTOs.add(new TieMatchDTO(t, sets, players));
        });
        matchDto.setTieMatchDTOs(tieMatchDTOs);
    }

    /**
     * Function to convert MatchCriteria to a {@link Specifications}
     */
    private Specifications<Match> createSpecification(MatchCriteria criteria) {
        Specifications<Match> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Match_.id));
            }
            if (criteria.getStartDateTime() != null) {
                specification = specification
                        .and(buildRangeSpecification(criteria.getStartDateTime(), Match_.startDateTime));
            }
            if (criteria.getEndDateTime() != null) {
                specification = specification
                        .and(buildRangeSpecification(criteria.getEndDateTime(), Match_.endDateTime));
            }
            if (criteria.getMatchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatchName(), Match_.matchName));
            }
            if (criteria.getStage() != null) {
                specification = specification.and(buildSpecification(criteria.getStage(), Match_.stage));
            }
            if (criteria.getCompleted() != null) {
                specification = specification.and(buildSpecification(criteria.getCompleted(), Match_.completed));
            }
            if (criteria.getVenue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVenue(), Match_.venue));
            }
            if (criteria.getTournamentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTournamentId(),
                        Match_.tournament, Tournament_.id));
            }
            if (criteria.getWinningFranchiseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getWinningFranchiseId(),
                        Match_.winningFranchise, SeasonsFranchise_.id));
            }

            if (criteria.getUpcomingMatches() != null) {
                if (criteria.getUpcomingMatches().getEquals()) {
                    ZonedDateTimeFilter dateFilter = new ZonedDateTimeFilter();
                    dateFilter.setGreaterOrEqualThan(ZonedDateTime.now());
                    specification = specification.and(buildRangeSpecification(dateFilter, Match_.endDateTime));
                } else {
                    ZonedDateTimeFilter dateFilter = new ZonedDateTimeFilter();
                    dateFilter.setLessThan(ZonedDateTime.now());
                    specification = specification.and(buildRangeSpecification(dateFilter, Match_.endDateTime));
                }
            }
        }
        return specification;
    }

}
