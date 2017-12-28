package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.Tournament;
import com.firstfuel.fafi.domain.Season;
import com.firstfuel.fafi.domain.Match;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.domain.SeasonsFranchisePlayer;
import com.firstfuel.fafi.repository.TournamentRepository;
import com.firstfuel.fafi.service.TournamentService;
import com.firstfuel.fafi.service.dto.TournamentDTO;
import com.firstfuel.fafi.service.mapper.TournamentMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.TournamentCriteria;
import com.firstfuel.fafi.service.TournamentQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.firstfuel.fafi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.firstfuel.fafi.domain.enumeration.Games;
/**
 * Test class for the TournamentResource REST controller.
 *
 * @see TournamentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class TournamentResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Games DEFAULT_TYPE = Games.FOOTBALL;
    private static final Games UPDATED_TYPE = Games.CHESS;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TournamentMapper tournamentMapper;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TournamentQueryService tournamentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTournamentMockMvc;

    private Tournament tournament;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TournamentResource tournamentResource = new TournamentResource(tournamentService, tournamentQueryService);
        this.restTournamentMockMvc = MockMvcBuilders.standaloneSetup(tournamentResource)
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
    public static Tournament createEntity(EntityManager em) {
        Tournament tournament = new Tournament()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .type(DEFAULT_TYPE);
        return tournament;
    }

    @Before
    public void initTest() {
        tournament = createEntity(em);
    }

    @Test
    @Transactional
    public void createTournament() throws Exception {
        int databaseSizeBeforeCreate = tournamentRepository.findAll().size();

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);
        restTournamentMockMvc.perform(post("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentDTO)))
            .andExpect(status().isCreated());

        // Validate the Tournament in the database
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeCreate + 1);
        Tournament testTournament = tournamentList.get(tournamentList.size() - 1);
        assertThat(testTournament.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTournament.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTournament.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTournamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tournamentRepository.findAll().size();

        // Create the Tournament with an existing ID
        tournament.setId(1L);
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTournamentMockMvc.perform(post("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tournament in the database
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tournamentRepository.findAll().size();
        // set the field null
        tournament.setType(null);

        // Create the Tournament, which fails.
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        restTournamentMockMvc.perform(post("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentDTO)))
            .andExpect(status().isBadRequest());

        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTournaments() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList
        restTournamentMockMvc.perform(get("/api/tournaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tournament.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get the tournament
        restTournamentMockMvc.perform(get("/api/tournaments/{id}", tournament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tournament.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllTournamentsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where startDate equals to DEFAULT_START_DATE
        defaultTournamentShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the tournamentList where startDate equals to UPDATED_START_DATE
        defaultTournamentShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllTournamentsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultTournamentShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the tournamentList where startDate equals to UPDATED_START_DATE
        defaultTournamentShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllTournamentsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where startDate is not null
        defaultTournamentShouldBeFound("startDate.specified=true");

        // Get all the tournamentList where startDate is null
        defaultTournamentShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTournamentsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where startDate greater than or equals to DEFAULT_START_DATE
        defaultTournamentShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the tournamentList where startDate greater than or equals to UPDATED_START_DATE
        defaultTournamentShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllTournamentsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where startDate less than or equals to DEFAULT_START_DATE
        defaultTournamentShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the tournamentList where startDate less than or equals to UPDATED_START_DATE
        defaultTournamentShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllTournamentsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where endDate equals to DEFAULT_END_DATE
        defaultTournamentShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the tournamentList where endDate equals to UPDATED_END_DATE
        defaultTournamentShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllTournamentsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultTournamentShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the tournamentList where endDate equals to UPDATED_END_DATE
        defaultTournamentShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllTournamentsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where endDate is not null
        defaultTournamentShouldBeFound("endDate.specified=true");

        // Get all the tournamentList where endDate is null
        defaultTournamentShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTournamentsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where endDate greater than or equals to DEFAULT_END_DATE
        defaultTournamentShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the tournamentList where endDate greater than or equals to UPDATED_END_DATE
        defaultTournamentShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllTournamentsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where endDate less than or equals to DEFAULT_END_DATE
        defaultTournamentShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the tournamentList where endDate less than or equals to UPDATED_END_DATE
        defaultTournamentShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllTournamentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where type equals to DEFAULT_TYPE
        defaultTournamentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the tournamentList where type equals to UPDATED_TYPE
        defaultTournamentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTournamentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTournamentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the tournamentList where type equals to UPDATED_TYPE
        defaultTournamentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTournamentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);

        // Get all the tournamentList where type is not null
        defaultTournamentShouldBeFound("type.specified=true");

        // Get all the tournamentList where type is null
        defaultTournamentShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllTournamentsBySeasonIsEqualToSomething() throws Exception {
        // Initialize the database
        Season season = SeasonResourceIntTest.createEntity(em);
        em.persist(season);
        em.flush();
        tournament.setSeason(season);
        tournamentRepository.saveAndFlush(tournament);
        Long seasonId = season.getId();

        // Get all the tournamentList where season equals to seasonId
        defaultTournamentShouldBeFound("seasonId.equals=" + seasonId);

        // Get all the tournamentList where season equals to seasonId + 1
        defaultTournamentShouldNotBeFound("seasonId.equals=" + (seasonId + 1));
    }


    @Test
    @Transactional
    public void getAllTournamentsByMatchesIsEqualToSomething() throws Exception {
        // Initialize the database
        Match matches = MatchResourceIntTest.createEntity(em);
        em.persist(matches);
        em.flush();
        tournament.addMatches(matches);
        tournamentRepository.saveAndFlush(tournament);
        Long matchesId = matches.getId();

        // Get all the tournamentList where matches equals to matchesId
        defaultTournamentShouldBeFound("matchesId.equals=" + matchesId);

        // Get all the tournamentList where matches equals to matchesId + 1
        defaultTournamentShouldNotBeFound("matchesId.equals=" + (matchesId + 1));
    }


    @Test
    @Transactional
    public void getAllTournamentsByWinningFranchiseIsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchise winningFranchise = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(winningFranchise);
        em.flush();
        tournament.setWinningFranchise(winningFranchise);
        tournamentRepository.saveAndFlush(tournament);
        Long winningFranchiseId = winningFranchise.getId();

        // Get all the tournamentList where winningFranchise equals to winningFranchiseId
        defaultTournamentShouldBeFound("winningFranchiseId.equals=" + winningFranchiseId);

        // Get all the tournamentList where winningFranchise equals to winningFranchiseId + 1
        defaultTournamentShouldNotBeFound("winningFranchiseId.equals=" + (winningFranchiseId + 1));
    }


    @Test
    @Transactional
    public void getAllTournamentsByPlayerOfTournamentIsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchisePlayer playerOfTournament = SeasonsFranchisePlayerResourceIntTest.createEntity(em);
        em.persist(playerOfTournament);
        em.flush();
        tournament.setPlayerOfTournament(playerOfTournament);
        tournamentRepository.saveAndFlush(tournament);
        Long playerOfTournamentId = playerOfTournament.getId();

        // Get all the tournamentList where playerOfTournament equals to playerOfTournamentId
        defaultTournamentShouldBeFound("playerOfTournamentId.equals=" + playerOfTournamentId);

        // Get all the tournamentList where playerOfTournament equals to playerOfTournamentId + 1
        defaultTournamentShouldNotBeFound("playerOfTournamentId.equals=" + (playerOfTournamentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTournamentShouldBeFound(String filter) throws Exception {
        restTournamentMockMvc.perform(get("/api/tournaments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tournament.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTournamentShouldNotBeFound(String filter) throws Exception {
        restTournamentMockMvc.perform(get("/api/tournaments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTournament() throws Exception {
        // Get the tournament
        restTournamentMockMvc.perform(get("/api/tournaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);
        int databaseSizeBeforeUpdate = tournamentRepository.findAll().size();

        // Update the tournament
        Tournament updatedTournament = tournamentRepository.findOne(tournament.getId());
        // Disconnect from session so that the updates on updatedTournament are not directly saved in db
        em.detach(updatedTournament);
        updatedTournament
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .type(UPDATED_TYPE);
        TournamentDTO tournamentDTO = tournamentMapper.toDto(updatedTournament);

        restTournamentMockMvc.perform(put("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentDTO)))
            .andExpect(status().isOk());

        // Validate the Tournament in the database
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeUpdate);
        Tournament testTournament = tournamentList.get(tournamentList.size() - 1);
        assertThat(testTournament.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTournament.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTournament.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTournament() throws Exception {
        int databaseSizeBeforeUpdate = tournamentRepository.findAll().size();

        // Create the Tournament
        TournamentDTO tournamentDTO = tournamentMapper.toDto(tournament);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTournamentMockMvc.perform(put("/api/tournaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentDTO)))
            .andExpect(status().isCreated());

        // Validate the Tournament in the database
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTournament() throws Exception {
        // Initialize the database
        tournamentRepository.saveAndFlush(tournament);
        int databaseSizeBeforeDelete = tournamentRepository.findAll().size();

        // Get the tournament
        restTournamentMockMvc.perform(delete("/api/tournaments/{id}", tournament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tournament> tournamentList = tournamentRepository.findAll();
        assertThat(tournamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tournament.class);
        Tournament tournament1 = new Tournament();
        tournament1.setId(1L);
        Tournament tournament2 = new Tournament();
        tournament2.setId(tournament1.getId());
        assertThat(tournament1).isEqualTo(tournament2);
        tournament2.setId(2L);
        assertThat(tournament1).isNotEqualTo(tournament2);
        tournament1.setId(null);
        assertThat(tournament1).isNotEqualTo(tournament2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TournamentDTO.class);
        TournamentDTO tournamentDTO1 = new TournamentDTO();
        tournamentDTO1.setId(1L);
        TournamentDTO tournamentDTO2 = new TournamentDTO();
        assertThat(tournamentDTO1).isNotEqualTo(tournamentDTO2);
        tournamentDTO2.setId(tournamentDTO1.getId());
        assertThat(tournamentDTO1).isEqualTo(tournamentDTO2);
        tournamentDTO2.setId(2L);
        assertThat(tournamentDTO1).isNotEqualTo(tournamentDTO2);
        tournamentDTO1.setId(null);
        assertThat(tournamentDTO1).isNotEqualTo(tournamentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tournamentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tournamentMapper.fromId(null)).isNull();
    }
}
