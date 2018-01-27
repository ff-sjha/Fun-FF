package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.TieMatchPlayers;
import com.firstfuel.fafi.domain.TieMatch;
import com.firstfuel.fafi.domain.SeasonsFranchisePlayer;
import com.firstfuel.fafi.repository.TieMatchPlayersRepository;
import com.firstfuel.fafi.service.TieMatchPlayersService;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.TieMatchPlayersCriteria;
import com.firstfuel.fafi.service.TieMatchPlayersQueryService;

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
 * Test class for the TieMatchPlayersResource REST controller.
 *
 * @see TieMatchPlayersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class TieMatchPlayersResourceIntTest {

    @Autowired
    private TieMatchPlayersRepository tieMatchPlayersRepository;

    @Autowired
    private TieMatchPlayersService tieMatchPlayersService;

    @Autowired
    private TieMatchPlayersQueryService tieMatchPlayersQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTieMatchPlayersMockMvc;

    private TieMatchPlayers tieMatchPlayers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TieMatchPlayersResource tieMatchPlayersResource = new TieMatchPlayersResource(tieMatchPlayersService, tieMatchPlayersQueryService);
        this.restTieMatchPlayersMockMvc = MockMvcBuilders.standaloneSetup(tieMatchPlayersResource)
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
    public static TieMatchPlayers createEntity(EntityManager em) {
        TieMatchPlayers tieMatchPlayers = new TieMatchPlayers();
        // Add required entity
        TieMatch tieMatch = TieMatchResourceIntTest.createEntity(em);
        em.persist(tieMatch);
        em.flush();
        tieMatchPlayers.setTieMatch(tieMatch);
        // Add required entity
        SeasonsFranchisePlayer seasonsFranchisePlayer = SeasonsFranchisePlayerResourceIntTest.createEntity(em);
        em.persist(seasonsFranchisePlayer);
        em.flush();
        tieMatchPlayers.setSeasonsFranchisePlayer(seasonsFranchisePlayer);
        return tieMatchPlayers;
    }

    @Before
    public void initTest() {
        tieMatchPlayers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTieMatchPlayers() throws Exception {
        int databaseSizeBeforeCreate = tieMatchPlayersRepository.findAll().size();

        // Create the TieMatchPlayers
        restTieMatchPlayersMockMvc.perform(post("/api/tie-match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatchPlayers)))
            .andExpect(status().isCreated());

        // Validate the TieMatchPlayers in the database
        List<TieMatchPlayers> tieMatchPlayersList = tieMatchPlayersRepository.findAll();
        assertThat(tieMatchPlayersList).hasSize(databaseSizeBeforeCreate + 1);
        TieMatchPlayers testTieMatchPlayers = tieMatchPlayersList.get(tieMatchPlayersList.size() - 1);
    }

    @Test
    @Transactional
    public void createTieMatchPlayersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tieMatchPlayersRepository.findAll().size();

        // Create the TieMatchPlayers with an existing ID
        tieMatchPlayers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTieMatchPlayersMockMvc.perform(post("/api/tie-match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatchPlayers)))
            .andExpect(status().isBadRequest());

        // Validate the TieMatchPlayers in the database
        List<TieMatchPlayers> tieMatchPlayersList = tieMatchPlayersRepository.findAll();
        assertThat(tieMatchPlayersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTieMatchPlayers() throws Exception {
        // Initialize the database
        tieMatchPlayersRepository.saveAndFlush(tieMatchPlayers);

        // Get all the tieMatchPlayersList
        restTieMatchPlayersMockMvc.perform(get("/api/tie-match-players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tieMatchPlayers.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTieMatchPlayers() throws Exception {
        // Initialize the database
        tieMatchPlayersRepository.saveAndFlush(tieMatchPlayers);

        // Get the tieMatchPlayers
        restTieMatchPlayersMockMvc.perform(get("/api/tie-match-players/{id}", tieMatchPlayers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tieMatchPlayers.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllTieMatchPlayersByTieMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        TieMatch tieMatch = TieMatchResourceIntTest.createEntity(em);
        em.persist(tieMatch);
        em.flush();
        tieMatchPlayers.setTieMatch(tieMatch);
        tieMatchPlayersRepository.saveAndFlush(tieMatchPlayers);
        Long tieMatchId = tieMatch.getId();

        // Get all the tieMatchPlayersList where tieMatch equals to tieMatchId
        defaultTieMatchPlayersShouldBeFound("tieMatchId.equals=" + tieMatchId);

        // Get all the tieMatchPlayersList where tieMatch equals to tieMatchId + 1
        defaultTieMatchPlayersShouldNotBeFound("tieMatchId.equals=" + (tieMatchId + 1));
    }


    @Test
    @Transactional
    public void getAllTieMatchPlayersBySeasonsFranchisePlayerIsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchisePlayer seasonsFranchisePlayer = SeasonsFranchisePlayerResourceIntTest.createEntity(em);
        em.persist(seasonsFranchisePlayer);
        em.flush();
        tieMatchPlayers.setSeasonsFranchisePlayer(seasonsFranchisePlayer);
        tieMatchPlayersRepository.saveAndFlush(tieMatchPlayers);
        Long seasonsFranchisePlayerId = seasonsFranchisePlayer.getId();

        // Get all the tieMatchPlayersList where seasonsFranchisePlayer equals to seasonsFranchisePlayerId
        defaultTieMatchPlayersShouldBeFound("seasonsFranchisePlayerId.equals=" + seasonsFranchisePlayerId);

        // Get all the tieMatchPlayersList where seasonsFranchisePlayer equals to seasonsFranchisePlayerId + 1
        defaultTieMatchPlayersShouldNotBeFound("seasonsFranchisePlayerId.equals=" + (seasonsFranchisePlayerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTieMatchPlayersShouldBeFound(String filter) throws Exception {
        restTieMatchPlayersMockMvc.perform(get("/api/tie-match-players?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tieMatchPlayers.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTieMatchPlayersShouldNotBeFound(String filter) throws Exception {
        restTieMatchPlayersMockMvc.perform(get("/api/tie-match-players?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTieMatchPlayers() throws Exception {
        // Get the tieMatchPlayers
        restTieMatchPlayersMockMvc.perform(get("/api/tie-match-players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTieMatchPlayers() throws Exception {
        // Initialize the database
        tieMatchPlayersService.save(tieMatchPlayers);

        int databaseSizeBeforeUpdate = tieMatchPlayersRepository.findAll().size();

        // Update the tieMatchPlayers
        TieMatchPlayers updatedTieMatchPlayers = tieMatchPlayersRepository.findOne(tieMatchPlayers.getId());
        // Disconnect from session so that the updates on updatedTieMatchPlayers are not directly saved in db
        em.detach(updatedTieMatchPlayers);

        restTieMatchPlayersMockMvc.perform(put("/api/tie-match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTieMatchPlayers)))
            .andExpect(status().isOk());

        // Validate the TieMatchPlayers in the database
        List<TieMatchPlayers> tieMatchPlayersList = tieMatchPlayersRepository.findAll();
        assertThat(tieMatchPlayersList).hasSize(databaseSizeBeforeUpdate);
        TieMatchPlayers testTieMatchPlayers = tieMatchPlayersList.get(tieMatchPlayersList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTieMatchPlayers() throws Exception {
        int databaseSizeBeforeUpdate = tieMatchPlayersRepository.findAll().size();

        // Create the TieMatchPlayers

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTieMatchPlayersMockMvc.perform(put("/api/tie-match-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatchPlayers)))
            .andExpect(status().isCreated());

        // Validate the TieMatchPlayers in the database
        List<TieMatchPlayers> tieMatchPlayersList = tieMatchPlayersRepository.findAll();
        assertThat(tieMatchPlayersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTieMatchPlayers() throws Exception {
        // Initialize the database
        tieMatchPlayersService.save(tieMatchPlayers);

        int databaseSizeBeforeDelete = tieMatchPlayersRepository.findAll().size();

        // Get the tieMatchPlayers
        restTieMatchPlayersMockMvc.perform(delete("/api/tie-match-players/{id}", tieMatchPlayers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TieMatchPlayers> tieMatchPlayersList = tieMatchPlayersRepository.findAll();
        assertThat(tieMatchPlayersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TieMatchPlayers.class);
        TieMatchPlayers tieMatchPlayers1 = new TieMatchPlayers();
        tieMatchPlayers1.setId(1L);
        TieMatchPlayers tieMatchPlayers2 = new TieMatchPlayers();
        tieMatchPlayers2.setId(tieMatchPlayers1.getId());
        assertThat(tieMatchPlayers1).isEqualTo(tieMatchPlayers2);
        tieMatchPlayers2.setId(2L);
        assertThat(tieMatchPlayers1).isNotEqualTo(tieMatchPlayers2);
        tieMatchPlayers1.setId(null);
        assertThat(tieMatchPlayers1).isNotEqualTo(tieMatchPlayers2);
    }
}
