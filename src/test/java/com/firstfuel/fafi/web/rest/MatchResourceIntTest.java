package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.Match;
import com.firstfuel.fafi.domain.Tournament;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.repository.MatchRepository;
import com.firstfuel.fafi.service.MatchService;
import com.firstfuel.fafi.service.dto.MatchDTO;
import com.firstfuel.fafi.service.mapper.MatchMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.MatchCriteria;
import com.firstfuel.fafi.service.MatchQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.firstfuel.fafi.web.rest.TestUtil.sameInstant;
import static com.firstfuel.fafi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.firstfuel.fafi.domain.enumeration.Stage;
/**
 * Test class for the MatchResource REST controller.
 *
 * @see MatchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class MatchResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_POINTS_EARNED_BY_FRANCHISE = 1;
    private static final Integer UPDATED_POINTS_EARNED_BY_FRANCHISE = 2;

    private static final String DEFAULT_MATCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATCH_NAME = "BBBBBBBBBB";

    private static final Stage DEFAULT_STAGE = Stage.LEAGUE;
    private static final Stage UPDATED_STAGE = Stage.QUARTER_FINAL;

    private static final String DEFAULT_VENUE = "AAAAAAAAAA";
    private static final String UPDATED_VENUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COMPLETED = false;
    private static final Boolean UPDATED_COMPLETED = true;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchQueryService matchQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMatchMockMvc;

    private Match match;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchResource matchResource = new MatchResource(matchService, matchQueryService);
        this.restMatchMockMvc = MockMvcBuilders.standaloneSetup(matchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createEntity(EntityManager em) {
        Match match = new Match()
            .startDateTime(DEFAULT_START_DATE_TIME)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .matchName(DEFAULT_MATCH_NAME)
            .stage(DEFAULT_STAGE)
            .venue(DEFAULT_VENUE)
            .completed(DEFAULT_COMPLETED);
        return match;
    }

    @Before
    public void initTest() {
        match = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatch() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);
        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate + 1);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getStartDateTime()).isEqualTo(DEFAULT_START_DATE_TIME);
        assertThat(testMatch.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testMatch.getPointsEarnedByFranchise()).isEqualTo(DEFAULT_POINTS_EARNED_BY_FRANCHISE);
        assertThat(testMatch.getMatchName()).isEqualTo(DEFAULT_MATCH_NAME);
        assertThat(testMatch.getStage()).isEqualTo(DEFAULT_STAGE);
        assertThat(testMatch.getVenue()).isEqualTo(DEFAULT_VENUE);
        assertThat(testMatch.isCompleted()).isEqualTo(DEFAULT_COMPLETED);
    }

    @Test
    @Transactional
    public void createMatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // Create the Match with an existing ID
        match.setId(1L);
        MatchDTO matchDTO = matchMapper.toDto(match);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMatchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = matchRepository.findAll().size();
        // set the field null
        match.setMatchName(null);

        // Create the Match, which fails.
        MatchDTO matchDTO = matchMapper.toDto(match);

        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStageIsRequired() throws Exception {
        int databaseSizeBeforeTest = matchRepository.findAll().size();
        // set the field null
        match.setStage(null);

        // Create the Match, which fails.
        MatchDTO matchDTO = matchMapper.toDto(match);

        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = matchRepository.findAll().size();
        // set the field null
        match.setCompleted(null);

        // Create the Match, which fails.
        MatchDTO matchDTO = matchMapper.toDto(match);

        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatches() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(sameInstant(DEFAULT_START_DATE_TIME))))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(sameInstant(DEFAULT_END_DATE_TIME))))
            .andExpect(jsonPath("$.[*].matchName").value(hasItem(DEFAULT_MATCH_NAME.toString())))
            .andExpect(jsonPath("$.[*].stage").value(hasItem(DEFAULT_STAGE.toString())))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE.toString())))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(match.getId().intValue()))
            .andExpect(jsonPath("$.startDateTime").value(sameInstant(DEFAULT_START_DATE_TIME)))
            .andExpect(jsonPath("$.endDateTime").value(sameInstant(DEFAULT_END_DATE_TIME)))
            .andExpect(jsonPath("$.pointsEarnedByFranchise").value(DEFAULT_POINTS_EARNED_BY_FRANCHISE))
            .andExpect(jsonPath("$.matchName").value(DEFAULT_MATCH_NAME.toString()))
            .andExpect(jsonPath("$.stage").value(DEFAULT_STAGE.toString()))
            .andExpect(jsonPath("$.venue").value(DEFAULT_VENUE.toString()))
            .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllMatchesByStartDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where startDateTime equals to DEFAULT_START_DATE_TIME
        defaultMatchShouldBeFound("startDateTime.equals=" + DEFAULT_START_DATE_TIME);

        // Get all the matchList where startDateTime equals to UPDATED_START_DATE_TIME
        defaultMatchShouldNotBeFound("startDateTime.equals=" + UPDATED_START_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllMatchesByStartDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where startDateTime in DEFAULT_START_DATE_TIME or UPDATED_START_DATE_TIME
        defaultMatchShouldBeFound("startDateTime.in=" + DEFAULT_START_DATE_TIME + "," + UPDATED_START_DATE_TIME);

        // Get all the matchList where startDateTime equals to UPDATED_START_DATE_TIME
        defaultMatchShouldNotBeFound("startDateTime.in=" + UPDATED_START_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllMatchesByStartDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where startDateTime is not null
        defaultMatchShouldBeFound("startDateTime.specified=true");

        // Get all the matchList where startDateTime is null
        defaultMatchShouldNotBeFound("startDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchesByStartDateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where startDateTime greater than or equals to DEFAULT_START_DATE_TIME
        defaultMatchShouldBeFound("startDateTime.greaterOrEqualThan=" + DEFAULT_START_DATE_TIME);

        // Get all the matchList where startDateTime greater than or equals to UPDATED_START_DATE_TIME
        defaultMatchShouldNotBeFound("startDateTime.greaterOrEqualThan=" + UPDATED_START_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllMatchesByStartDateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where startDateTime less than or equals to DEFAULT_START_DATE_TIME
        defaultMatchShouldNotBeFound("startDateTime.lessThan=" + DEFAULT_START_DATE_TIME);

        // Get all the matchList where startDateTime less than or equals to UPDATED_START_DATE_TIME
        defaultMatchShouldBeFound("startDateTime.lessThan=" + UPDATED_START_DATE_TIME);
    }


    @Test
    @Transactional
    public void getAllMatchesByEndDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where endDateTime equals to DEFAULT_END_DATE_TIME
        defaultMatchShouldBeFound("endDateTime.equals=" + DEFAULT_END_DATE_TIME);

        // Get all the matchList where endDateTime equals to UPDATED_END_DATE_TIME
        defaultMatchShouldNotBeFound("endDateTime.equals=" + UPDATED_END_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllMatchesByEndDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where endDateTime in DEFAULT_END_DATE_TIME or UPDATED_END_DATE_TIME
        defaultMatchShouldBeFound("endDateTime.in=" + DEFAULT_END_DATE_TIME + "," + UPDATED_END_DATE_TIME);

        // Get all the matchList where endDateTime equals to UPDATED_END_DATE_TIME
        defaultMatchShouldNotBeFound("endDateTime.in=" + UPDATED_END_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllMatchesByEndDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where endDateTime is not null
        defaultMatchShouldBeFound("endDateTime.specified=true");

        // Get all the matchList where endDateTime is null
        defaultMatchShouldNotBeFound("endDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchesByEndDateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where endDateTime greater than or equals to DEFAULT_END_DATE_TIME
        defaultMatchShouldBeFound("endDateTime.greaterOrEqualThan=" + DEFAULT_END_DATE_TIME);

        // Get all the matchList where endDateTime greater than or equals to UPDATED_END_DATE_TIME
        defaultMatchShouldNotBeFound("endDateTime.greaterOrEqualThan=" + UPDATED_END_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllMatchesByEndDateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where endDateTime less than or equals to DEFAULT_END_DATE_TIME
        defaultMatchShouldNotBeFound("endDateTime.lessThan=" + DEFAULT_END_DATE_TIME);

        // Get all the matchList where endDateTime less than or equals to UPDATED_END_DATE_TIME
        defaultMatchShouldBeFound("endDateTime.lessThan=" + UPDATED_END_DATE_TIME);
    }


    @Test
    @Transactional
    public void getAllMatchesByPointsEarnedByFranchiseIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where pointsEarnedByFranchise equals to DEFAULT_POINTS_EARNED_BY_FRANCHISE
        defaultMatchShouldBeFound("pointsEarnedByFranchise.equals=" + DEFAULT_POINTS_EARNED_BY_FRANCHISE);

        // Get all the matchList where pointsEarnedByFranchise equals to UPDATED_POINTS_EARNED_BY_FRANCHISE
        defaultMatchShouldNotBeFound("pointsEarnedByFranchise.equals=" + UPDATED_POINTS_EARNED_BY_FRANCHISE);
    }

    @Test
    @Transactional
    public void getAllMatchesByPointsEarnedByFranchiseIsInShouldWork() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where pointsEarnedByFranchise in DEFAULT_POINTS_EARNED_BY_FRANCHISE or UPDATED_POINTS_EARNED_BY_FRANCHISE
        defaultMatchShouldBeFound("pointsEarnedByFranchise.in=" + DEFAULT_POINTS_EARNED_BY_FRANCHISE + "," + UPDATED_POINTS_EARNED_BY_FRANCHISE);

        // Get all the matchList where pointsEarnedByFranchise equals to UPDATED_POINTS_EARNED_BY_FRANCHISE
        defaultMatchShouldNotBeFound("pointsEarnedByFranchise.in=" + UPDATED_POINTS_EARNED_BY_FRANCHISE);
    }

    @Test
    @Transactional
    public void getAllMatchesByPointsEarnedByFranchiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where pointsEarnedByFranchise is not null
        defaultMatchShouldBeFound("pointsEarnedByFranchise.specified=true");

        // Get all the matchList where pointsEarnedByFranchise is null
        defaultMatchShouldNotBeFound("pointsEarnedByFranchise.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchesByPointsEarnedByFranchiseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where pointsEarnedByFranchise greater than or equals to DEFAULT_POINTS_EARNED_BY_FRANCHISE
        defaultMatchShouldBeFound("pointsEarnedByFranchise.greaterOrEqualThan=" + DEFAULT_POINTS_EARNED_BY_FRANCHISE);

        // Get all the matchList where pointsEarnedByFranchise greater than or equals to UPDATED_POINTS_EARNED_BY_FRANCHISE
        defaultMatchShouldNotBeFound("pointsEarnedByFranchise.greaterOrEqualThan=" + UPDATED_POINTS_EARNED_BY_FRANCHISE);
    }

    @Test
    @Transactional
    public void getAllMatchesByPointsEarnedByFranchiseIsLessThanSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where pointsEarnedByFranchise less than or equals to DEFAULT_POINTS_EARNED_BY_FRANCHISE
        defaultMatchShouldNotBeFound("pointsEarnedByFranchise.lessThan=" + DEFAULT_POINTS_EARNED_BY_FRANCHISE);

        // Get all the matchList where pointsEarnedByFranchise less than or equals to UPDATED_POINTS_EARNED_BY_FRANCHISE
        defaultMatchShouldBeFound("pointsEarnedByFranchise.lessThan=" + UPDATED_POINTS_EARNED_BY_FRANCHISE);
    }


    @Test
    @Transactional
    public void getAllMatchesByMatchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where matchName equals to DEFAULT_MATCH_NAME
        defaultMatchShouldBeFound("matchName.equals=" + DEFAULT_MATCH_NAME);

        // Get all the matchList where matchName equals to UPDATED_MATCH_NAME
        defaultMatchShouldNotBeFound("matchName.equals=" + UPDATED_MATCH_NAME);
    }

    @Test
    @Transactional
    public void getAllMatchesByMatchNameIsInShouldWork() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where matchName in DEFAULT_MATCH_NAME or UPDATED_MATCH_NAME
        defaultMatchShouldBeFound("matchName.in=" + DEFAULT_MATCH_NAME + "," + UPDATED_MATCH_NAME);

        // Get all the matchList where matchName equals to UPDATED_MATCH_NAME
        defaultMatchShouldNotBeFound("matchName.in=" + UPDATED_MATCH_NAME);
    }

    @Test
    @Transactional
    public void getAllMatchesByMatchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where matchName is not null
        defaultMatchShouldBeFound("matchName.specified=true");

        // Get all the matchList where matchName is null
        defaultMatchShouldNotBeFound("matchName.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchesByStageIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where stage equals to DEFAULT_STAGE
        defaultMatchShouldBeFound("stage.equals=" + DEFAULT_STAGE);

        // Get all the matchList where stage equals to UPDATED_STAGE
        defaultMatchShouldNotBeFound("stage.equals=" + UPDATED_STAGE);
    }

    @Test
    @Transactional
    public void getAllMatchesByStageIsInShouldWork() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where stage in DEFAULT_STAGE or UPDATED_STAGE
        defaultMatchShouldBeFound("stage.in=" + DEFAULT_STAGE + "," + UPDATED_STAGE);

        // Get all the matchList where stage equals to UPDATED_STAGE
        defaultMatchShouldNotBeFound("stage.in=" + UPDATED_STAGE);
    }

    @Test
    @Transactional
    public void getAllMatchesByStageIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where stage is not null
        defaultMatchShouldBeFound("stage.specified=true");

        // Get all the matchList where stage is null
        defaultMatchShouldNotBeFound("stage.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchesByVenueIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where venue equals to DEFAULT_VENUE
        defaultMatchShouldBeFound("venue.equals=" + DEFAULT_VENUE);

        // Get all the matchList where venue equals to UPDATED_VENUE
        defaultMatchShouldNotBeFound("venue.equals=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    public void getAllMatchesByVenueIsInShouldWork() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where venue in DEFAULT_VENUE or UPDATED_VENUE
        defaultMatchShouldBeFound("venue.in=" + DEFAULT_VENUE + "," + UPDATED_VENUE);

        // Get all the matchList where venue equals to UPDATED_VENUE
        defaultMatchShouldNotBeFound("venue.in=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    public void getAllMatchesByVenueIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where venue is not null
        defaultMatchShouldBeFound("venue.specified=true");

        // Get all the matchList where venue is null
        defaultMatchShouldNotBeFound("venue.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchesByCompletedIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where completed equals to DEFAULT_COMPLETED
        defaultMatchShouldBeFound("completed.equals=" + DEFAULT_COMPLETED);

        // Get all the matchList where completed equals to UPDATED_COMPLETED
        defaultMatchShouldNotBeFound("completed.equals=" + UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    public void getAllMatchesByCompletedIsInShouldWork() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where completed in DEFAULT_COMPLETED or UPDATED_COMPLETED
        defaultMatchShouldBeFound("completed.in=" + DEFAULT_COMPLETED + "," + UPDATED_COMPLETED);

        // Get all the matchList where completed equals to UPDATED_COMPLETED
        defaultMatchShouldNotBeFound("completed.in=" + UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    public void getAllMatchesByCompletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where completed is not null
        defaultMatchShouldBeFound("completed.specified=true");

        // Get all the matchList where completed is null
        defaultMatchShouldNotBeFound("completed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchesByTournamentIsEqualToSomething() throws Exception {
        // Initialize the database
        Tournament tournament = TournamentResourceIntTest.createEntity(em);
        em.persist(tournament);
        em.flush();
        match.setTournament(tournament);
        matchRepository.saveAndFlush(match);
        Long tournamentId = tournament.getId();

        // Get all the matchList where tournament equals to tournamentId
        defaultMatchShouldBeFound("tournamentId.equals=" + tournamentId);

        // Get all the matchList where tournament equals to tournamentId + 1
        defaultMatchShouldNotBeFound("tournamentId.equals=" + (tournamentId + 1));
    }


    @Test
    @Transactional
    public void getAllMatchesByWinningFranchiseIsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchise winningFranchise = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(winningFranchise);
        em.flush();
        match.setWinningFranchise(winningFranchise);
        matchRepository.saveAndFlush(match);
        Long winningFranchiseId = winningFranchise.getId();

        // Get all the matchList where winningFranchise equals to winningFranchiseId
        defaultMatchShouldBeFound("winningFranchiseId.equals=" + winningFranchiseId);

        // Get all the matchList where winningFranchise equals to winningFranchiseId + 1
        defaultMatchShouldNotBeFound("winningFranchiseId.equals=" + (winningFranchiseId + 1));
    }


    @Test
    @Transactional
    public void getAllMatchesByTeam1IsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchise team1 = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(team1);
        em.flush();
        match.setTeam1(team1);
        matchRepository.saveAndFlush(match);
        Long team1Id = team1.getId();

        // Get all the matchList where team1 equals to team1Id
        defaultMatchShouldBeFound("team1Id.equals=" + team1Id);

        // Get all the matchList where team1 equals to team1Id + 1
        defaultMatchShouldNotBeFound("team1Id.equals=" + (team1Id + 1));
    }


    @Test
    @Transactional
    public void getAllMatchesByTeam2IsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchise team2 = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(team2);
        em.flush();
        match.setTeam2(team2);
        matchRepository.saveAndFlush(match);
        Long team2Id = team2.getId();

        // Get all the matchList where team2 equals to team2Id
        defaultMatchShouldBeFound("team2Id.equals=" + team2Id);

        // Get all the matchList where team2 equals to team2Id + 1
        defaultMatchShouldNotBeFound("team2Id.equals=" + (team2Id + 1));
    }


    @Test
    @Transactional
    public void getAllMatchesByTeam3IsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchise team3 = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(team3);
        em.flush();
        match.setTeam3(team3);
        matchRepository.saveAndFlush(match);
        Long team3Id = team3.getId();

        // Get all the matchList where team3 equals to team3Id
        defaultMatchShouldBeFound("team3Id.equals=" + team3Id);

        // Get all the matchList where team3 equals to team3Id + 1
        defaultMatchShouldNotBeFound("team3Id.equals=" + (team3Id + 1));
    }


    @Test
    @Transactional
    public void getAllMatchesByTeam4IsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchise team4 = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(team4);
        em.flush();
        match.setTeam4(team4);
        matchRepository.saveAndFlush(match);
        Long team4Id = team4.getId();

        // Get all the matchList where team4 equals to team4Id
        defaultMatchShouldBeFound("team4Id.equals=" + team4Id);

        // Get all the matchList where team4 equals to team4Id + 1
        defaultMatchShouldNotBeFound("team4Id.equals=" + (team4Id + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMatchShouldBeFound(String filter) throws Exception {
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(sameInstant(DEFAULT_START_DATE_TIME))))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(sameInstant(DEFAULT_END_DATE_TIME))))
            .andExpect(jsonPath("$.[*].pointsEarnedByFranchise").value(hasItem(DEFAULT_POINTS_EARNED_BY_FRANCHISE)))
            .andExpect(jsonPath("$.[*].matchName").value(hasItem(DEFAULT_MATCH_NAME.toString())))
            .andExpect(jsonPath("$.[*].stage").value(hasItem(DEFAULT_STAGE.toString())))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE.toString())))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMatchShouldNotBeFound(String filter) throws Exception {
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMatch() throws Exception {
        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Update the match
        Match updatedMatch = matchRepository.findOne(match.getId());
        // Disconnect from session so that the updates on updatedMatch are not directly saved in db
        em.detach(updatedMatch);
        updatedMatch
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .matchName(UPDATED_MATCH_NAME)
            .stage(UPDATED_STAGE)
            .venue(UPDATED_VENUE)
            .completed(UPDATED_COMPLETED);
        MatchDTO matchDTO = matchMapper.toDto(updatedMatch);

        restMatchMockMvc.perform(put("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testMatch.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testMatch.getPointsEarnedByFranchise()).isEqualTo(UPDATED_POINTS_EARNED_BY_FRANCHISE);
        assertThat(testMatch.getMatchName()).isEqualTo(UPDATED_MATCH_NAME);
        assertThat(testMatch.getStage()).isEqualTo(UPDATED_STAGE);
        assertThat(testMatch.getVenue()).isEqualTo(UPDATED_VENUE);
        assertThat(testMatch.isCompleted()).isEqualTo(UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    public void updateNonExistingMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMatchMockMvc.perform(put("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);
        int databaseSizeBeforeDelete = matchRepository.findAll().size();

        // Get the match
        restMatchMockMvc.perform(delete("/api/matches/{id}", match.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Match.class);
        Match match1 = new Match();
        match1.setId(1L);
        Match match2 = new Match();
        match2.setId(match1.getId());
        assertThat(match1).isEqualTo(match2);
        match2.setId(2L);
        assertThat(match1).isNotEqualTo(match2);
        match1.setId(null);
        assertThat(match1).isNotEqualTo(match2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchDTO.class);
        MatchDTO matchDTO1 = new MatchDTO();
        matchDTO1.setId(1L);
        MatchDTO matchDTO2 = new MatchDTO();
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
        matchDTO2.setId(matchDTO1.getId());
        assertThat(matchDTO1).isEqualTo(matchDTO2);
        matchDTO2.setId(2L);
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
        matchDTO1.setId(null);
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(matchMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(matchMapper.fromId(null)).isNull();
    }
}
