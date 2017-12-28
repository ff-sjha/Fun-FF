package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.MatchPlayers;
import com.firstfuel.fafi.domain.Match;
import com.firstfuel.fafi.domain.SeasonsFranchisePlayer;
import com.firstfuel.fafi.repository.MatchPlayersRepository;
import com.firstfuel.fafi.service.MatchPlayersService;
import com.firstfuel.fafi.service.dto.MatchPlayersDTO;
import com.firstfuel.fafi.service.mapper.MatchPlayersMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.MatchPlayersCriteria;
import com.firstfuel.fafi.service.MatchPlayersQueryService;

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
import java.util.List;

import static com.firstfuel.fafi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MatchPlayersResource REST controller.
 *
 * @see MatchPlayersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class MatchPlayersResourceIntTest {

    private static final Boolean DEFAULT_PLAYER_OF_THE_MATCH = false;
    private static final Boolean UPDATED_PLAYER_OF_THE_MATCH = true;

    private static final Integer DEFAULT_PLAYER_POINTS_EARNED = 1;
    private static final Integer UPDATED_PLAYER_POINTS_EARNED = 2;

    @Autowired
    private MatchPlayersRepository matchPlayersRepository;

    @Autowired
    private MatchPlayersMapper matchPlayersMapper;

    @Autowired
    private MatchPlayersService matchPlayersService;

    @Autowired
    private MatchPlayersQueryService matchPlayersQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMatchPlayersMockMvc;

    private MatchPlayers matchPlayers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchPlayersResource matchPlayersResource = new MatchPlayersResource(matchPlayersService, matchPlayersQueryService);
        this.restMatchPlayersMockMvc = MockMvcBuilders.standaloneSetup(matchPlayersResource)
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
    public static MatchPlayers createEntity(EntityManager em) {
        MatchPlayers matchPlayers = new MatchPlayers()
            .playerOfTheMatch(DEFAULT_PLAYER_OF_THE_MATCH)
            .playerPointsEarned(DEFAULT_PLAYER_POINTS_EARNED);
        // Add required entity
        Match match = MatchResourceIntTest.createEntity(em);
        em.persist(match);
        em.flush();
        matchPlayers.setMatch(match);
        // Add required entity
        SeasonsFranchisePlayer seasonsFranchisePlayer = SeasonsFranchisePlayerResourceIntTest.createEntity(em);
        em.persist(seasonsFranchisePlayer);
        em.flush();
        matchPlayers.setSeasonsFranchisePlayer(seasonsFranchisePlayer);
        return matchPlayers;
    }

    @Before
    public void initTest() {
        matchPlayers = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchPlayers() throws Exception {
        int databaseSizeBeforeCreate = matchPlayersRepository.findAll().size();

        // Create the MatchPlayers
        MatchPlayersDTO matchPlayersDTO = matchPlayersMapper.toDto(matchPlayers);
        restMatchPlayersMockMvc.perform(post("/api/match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchPlayersDTO)))
            .andExpect(status().isCreated());

        // Validate the MatchPlayers in the database
        List<MatchPlayers> matchPlayersList = matchPlayersRepository.findAll();
        assertThat(matchPlayersList).hasSize(databaseSizeBeforeCreate + 1);
        MatchPlayers testMatchPlayers = matchPlayersList.get(matchPlayersList.size() - 1);
        assertThat(testMatchPlayers.isPlayerOfTheMatch()).isEqualTo(DEFAULT_PLAYER_OF_THE_MATCH);
        assertThat(testMatchPlayers.getPlayerPointsEarned()).isEqualTo(DEFAULT_PLAYER_POINTS_EARNED);
    }

    @Test
    @Transactional
    public void createMatchPlayersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchPlayersRepository.findAll().size();

        // Create the MatchPlayers with an existing ID
        matchPlayers.setId(1L);
        MatchPlayersDTO matchPlayersDTO = matchPlayersMapper.toDto(matchPlayers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchPlayersMockMvc.perform(post("/api/match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchPlayersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchPlayers in the database
        List<MatchPlayers> matchPlayersList = matchPlayersRepository.findAll();
        assertThat(matchPlayersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPlayerOfTheMatchIsRequired() throws Exception {
        int databaseSizeBeforeTest = matchPlayersRepository.findAll().size();
        // set the field null
        matchPlayers.setPlayerOfTheMatch(null);

        // Create the MatchPlayers, which fails.
        MatchPlayersDTO matchPlayersDTO = matchPlayersMapper.toDto(matchPlayers);

        restMatchPlayersMockMvc.perform(post("/api/match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchPlayersDTO)))
            .andExpect(status().isBadRequest());

        List<MatchPlayers> matchPlayersList = matchPlayersRepository.findAll();
        assertThat(matchPlayersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatchPlayers() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList
        restMatchPlayersMockMvc.perform(get("/api/match-players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchPlayers.getId().intValue())))
            .andExpect(jsonPath("$.[*].playerOfTheMatch").value(hasItem(DEFAULT_PLAYER_OF_THE_MATCH.booleanValue())))
            .andExpect(jsonPath("$.[*].playerPointsEarned").value(hasItem(DEFAULT_PLAYER_POINTS_EARNED)));
    }

    @Test
    @Transactional
    public void getMatchPlayers() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get the matchPlayers
        restMatchPlayersMockMvc.perform(get("/api/match-players/{id}", matchPlayers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchPlayers.getId().intValue()))
            .andExpect(jsonPath("$.playerOfTheMatch").value(DEFAULT_PLAYER_OF_THE_MATCH.booleanValue()))
            .andExpect(jsonPath("$.playerPointsEarned").value(DEFAULT_PLAYER_POINTS_EARNED));
    }

    @Test
    @Transactional
    public void getAllMatchPlayersByPlayerOfTheMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList where playerOfTheMatch equals to DEFAULT_PLAYER_OF_THE_MATCH
        defaultMatchPlayersShouldBeFound("playerOfTheMatch.equals=" + DEFAULT_PLAYER_OF_THE_MATCH);

        // Get all the matchPlayersList where playerOfTheMatch equals to UPDATED_PLAYER_OF_THE_MATCH
        defaultMatchPlayersShouldNotBeFound("playerOfTheMatch.equals=" + UPDATED_PLAYER_OF_THE_MATCH);
    }

    @Test
    @Transactional
    public void getAllMatchPlayersByPlayerOfTheMatchIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList where playerOfTheMatch in DEFAULT_PLAYER_OF_THE_MATCH or UPDATED_PLAYER_OF_THE_MATCH
        defaultMatchPlayersShouldBeFound("playerOfTheMatch.in=" + DEFAULT_PLAYER_OF_THE_MATCH + "," + UPDATED_PLAYER_OF_THE_MATCH);

        // Get all the matchPlayersList where playerOfTheMatch equals to UPDATED_PLAYER_OF_THE_MATCH
        defaultMatchPlayersShouldNotBeFound("playerOfTheMatch.in=" + UPDATED_PLAYER_OF_THE_MATCH);
    }

    @Test
    @Transactional
    public void getAllMatchPlayersByPlayerOfTheMatchIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList where playerOfTheMatch is not null
        defaultMatchPlayersShouldBeFound("playerOfTheMatch.specified=true");

        // Get all the matchPlayersList where playerOfTheMatch is null
        defaultMatchPlayersShouldNotBeFound("playerOfTheMatch.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchPlayersByPlayerPointsEarnedIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList where playerPointsEarned equals to DEFAULT_PLAYER_POINTS_EARNED
        defaultMatchPlayersShouldBeFound("playerPointsEarned.equals=" + DEFAULT_PLAYER_POINTS_EARNED);

        // Get all the matchPlayersList where playerPointsEarned equals to UPDATED_PLAYER_POINTS_EARNED
        defaultMatchPlayersShouldNotBeFound("playerPointsEarned.equals=" + UPDATED_PLAYER_POINTS_EARNED);
    }

    @Test
    @Transactional
    public void getAllMatchPlayersByPlayerPointsEarnedIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList where playerPointsEarned in DEFAULT_PLAYER_POINTS_EARNED or UPDATED_PLAYER_POINTS_EARNED
        defaultMatchPlayersShouldBeFound("playerPointsEarned.in=" + DEFAULT_PLAYER_POINTS_EARNED + "," + UPDATED_PLAYER_POINTS_EARNED);

        // Get all the matchPlayersList where playerPointsEarned equals to UPDATED_PLAYER_POINTS_EARNED
        defaultMatchPlayersShouldNotBeFound("playerPointsEarned.in=" + UPDATED_PLAYER_POINTS_EARNED);
    }

    @Test
    @Transactional
    public void getAllMatchPlayersByPlayerPointsEarnedIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList where playerPointsEarned is not null
        defaultMatchPlayersShouldBeFound("playerPointsEarned.specified=true");

        // Get all the matchPlayersList where playerPointsEarned is null
        defaultMatchPlayersShouldNotBeFound("playerPointsEarned.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchPlayersByPlayerPointsEarnedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList where playerPointsEarned greater than or equals to DEFAULT_PLAYER_POINTS_EARNED
        defaultMatchPlayersShouldBeFound("playerPointsEarned.greaterOrEqualThan=" + DEFAULT_PLAYER_POINTS_EARNED);

        // Get all the matchPlayersList where playerPointsEarned greater than or equals to UPDATED_PLAYER_POINTS_EARNED
        defaultMatchPlayersShouldNotBeFound("playerPointsEarned.greaterOrEqualThan=" + UPDATED_PLAYER_POINTS_EARNED);
    }

    @Test
    @Transactional
    public void getAllMatchPlayersByPlayerPointsEarnedIsLessThanSomething() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);

        // Get all the matchPlayersList where playerPointsEarned less than or equals to DEFAULT_PLAYER_POINTS_EARNED
        defaultMatchPlayersShouldNotBeFound("playerPointsEarned.lessThan=" + DEFAULT_PLAYER_POINTS_EARNED);

        // Get all the matchPlayersList where playerPointsEarned less than or equals to UPDATED_PLAYER_POINTS_EARNED
        defaultMatchPlayersShouldBeFound("playerPointsEarned.lessThan=" + UPDATED_PLAYER_POINTS_EARNED);
    }


    @Test
    @Transactional
    public void getAllMatchPlayersByMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        Match match = MatchResourceIntTest.createEntity(em);
        em.persist(match);
        em.flush();
        matchPlayers.setMatch(match);
        matchPlayersRepository.saveAndFlush(matchPlayers);
        Long matchId = match.getId();

        // Get all the matchPlayersList where match equals to matchId
        defaultMatchPlayersShouldBeFound("matchId.equals=" + matchId);

        // Get all the matchPlayersList where match equals to matchId + 1
        defaultMatchPlayersShouldNotBeFound("matchId.equals=" + (matchId + 1));
    }


    @Test
    @Transactional
    public void getAllMatchPlayersBySeasonsFranchisePlayerIsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchisePlayer seasonsFranchisePlayer = SeasonsFranchisePlayerResourceIntTest.createEntity(em);
        em.persist(seasonsFranchisePlayer);
        em.flush();
        matchPlayers.setSeasonsFranchisePlayer(seasonsFranchisePlayer);
        matchPlayersRepository.saveAndFlush(matchPlayers);
        Long seasonsFranchisePlayerId = seasonsFranchisePlayer.getId();

        // Get all the matchPlayersList where seasonsFranchisePlayer equals to seasonsFranchisePlayerId
        defaultMatchPlayersShouldBeFound("seasonsFranchisePlayerId.equals=" + seasonsFranchisePlayerId);

        // Get all the matchPlayersList where seasonsFranchisePlayer equals to seasonsFranchisePlayerId + 1
        defaultMatchPlayersShouldNotBeFound("seasonsFranchisePlayerId.equals=" + (seasonsFranchisePlayerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMatchPlayersShouldBeFound(String filter) throws Exception {
        restMatchPlayersMockMvc.perform(get("/api/match-players?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchPlayers.getId().intValue())))
            .andExpect(jsonPath("$.[*].playerOfTheMatch").value(hasItem(DEFAULT_PLAYER_OF_THE_MATCH.booleanValue())))
            .andExpect(jsonPath("$.[*].playerPointsEarned").value(hasItem(DEFAULT_PLAYER_POINTS_EARNED)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMatchPlayersShouldNotBeFound(String filter) throws Exception {
        restMatchPlayersMockMvc.perform(get("/api/match-players?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMatchPlayers() throws Exception {
        // Get the matchPlayers
        restMatchPlayersMockMvc.perform(get("/api/match-players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchPlayers() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);
        int databaseSizeBeforeUpdate = matchPlayersRepository.findAll().size();

        // Update the matchPlayers
        MatchPlayers updatedMatchPlayers = matchPlayersRepository.findOne(matchPlayers.getId());
        // Disconnect from session so that the updates on updatedMatchPlayers are not directly saved in db
        em.detach(updatedMatchPlayers);
        updatedMatchPlayers
            .playerOfTheMatch(UPDATED_PLAYER_OF_THE_MATCH)
            .playerPointsEarned(UPDATED_PLAYER_POINTS_EARNED);
        MatchPlayersDTO matchPlayersDTO = matchPlayersMapper.toDto(updatedMatchPlayers);

        restMatchPlayersMockMvc.perform(put("/api/match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchPlayersDTO)))
            .andExpect(status().isOk());

        // Validate the MatchPlayers in the database
        List<MatchPlayers> matchPlayersList = matchPlayersRepository.findAll();
        assertThat(matchPlayersList).hasSize(databaseSizeBeforeUpdate);
        MatchPlayers testMatchPlayers = matchPlayersList.get(matchPlayersList.size() - 1);
        assertThat(testMatchPlayers.isPlayerOfTheMatch()).isEqualTo(UPDATED_PLAYER_OF_THE_MATCH);
        assertThat(testMatchPlayers.getPlayerPointsEarned()).isEqualTo(UPDATED_PLAYER_POINTS_EARNED);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchPlayers() throws Exception {
        int databaseSizeBeforeUpdate = matchPlayersRepository.findAll().size();

        // Create the MatchPlayers
        MatchPlayersDTO matchPlayersDTO = matchPlayersMapper.toDto(matchPlayers);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMatchPlayersMockMvc.perform(put("/api/match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchPlayersDTO)))
            .andExpect(status().isCreated());

        // Validate the MatchPlayers in the database
        List<MatchPlayers> matchPlayersList = matchPlayersRepository.findAll();
        assertThat(matchPlayersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMatchPlayers() throws Exception {
        // Initialize the database
        matchPlayersRepository.saveAndFlush(matchPlayers);
        int databaseSizeBeforeDelete = matchPlayersRepository.findAll().size();

        // Get the matchPlayers
        restMatchPlayersMockMvc.perform(delete("/api/match-players/{id}", matchPlayers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MatchPlayers> matchPlayersList = matchPlayersRepository.findAll();
        assertThat(matchPlayersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchPlayers.class);
        MatchPlayers matchPlayers1 = new MatchPlayers();
        matchPlayers1.setId(1L);
        MatchPlayers matchPlayers2 = new MatchPlayers();
        matchPlayers2.setId(matchPlayers1.getId());
        assertThat(matchPlayers1).isEqualTo(matchPlayers2);
        matchPlayers2.setId(2L);
        assertThat(matchPlayers1).isNotEqualTo(matchPlayers2);
        matchPlayers1.setId(null);
        assertThat(matchPlayers1).isNotEqualTo(matchPlayers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchPlayersDTO.class);
        MatchPlayersDTO matchPlayersDTO1 = new MatchPlayersDTO();
        matchPlayersDTO1.setId(1L);
        MatchPlayersDTO matchPlayersDTO2 = new MatchPlayersDTO();
        assertThat(matchPlayersDTO1).isNotEqualTo(matchPlayersDTO2);
        matchPlayersDTO2.setId(matchPlayersDTO1.getId());
        assertThat(matchPlayersDTO1).isEqualTo(matchPlayersDTO2);
        matchPlayersDTO2.setId(2L);
        assertThat(matchPlayersDTO1).isNotEqualTo(matchPlayersDTO2);
        matchPlayersDTO1.setId(null);
        assertThat(matchPlayersDTO1).isNotEqualTo(matchPlayersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(matchPlayersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(matchPlayersMapper.fromId(null)).isNull();
    }
}
