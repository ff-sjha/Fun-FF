package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.TournamentFranchisePoints;
import com.firstfuel.fafi.domain.Tournament;
import com.firstfuel.fafi.domain.Franchise;
import com.firstfuel.fafi.repository.TournamentFranchisePointsRepository;
import com.firstfuel.fafi.service.TournamentFranchisePointsService;
import com.firstfuel.fafi.service.dto.TournamentFranchisePointsDTO;
import com.firstfuel.fafi.service.mapper.TournamentFranchisePointsMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.TournamentFranchisePointsCriteria;
import com.firstfuel.fafi.service.TournamentFranchisePointsQueryService;

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
 * Test class for the TournamentFranchisePointsResource REST controller.
 *
 * @see TournamentFranchisePointsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class TournamentFranchisePointsResourceIntTest {

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    @Autowired
    private TournamentFranchisePointsRepository tournamentFranchisePointsRepository;

    @Autowired
    private TournamentFranchisePointsMapper tournamentFranchisePointsMapper;

    @Autowired
    private TournamentFranchisePointsService tournamentFranchisePointsService;

    @Autowired
    private TournamentFranchisePointsQueryService tournamentFranchisePointsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTournamentFranchisePointsMockMvc;

    private TournamentFranchisePoints tournamentFranchisePoints;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TournamentFranchisePointsResource tournamentFranchisePointsResource = new TournamentFranchisePointsResource(tournamentFranchisePointsService, tournamentFranchisePointsQueryService);
        this.restTournamentFranchisePointsMockMvc = MockMvcBuilders.standaloneSetup(tournamentFranchisePointsResource)
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
    public static TournamentFranchisePoints createEntity(EntityManager em) {
        TournamentFranchisePoints tournamentFranchisePoints = new TournamentFranchisePoints()
            .points(DEFAULT_POINTS);
        // Add required entity
        Tournament tournament = TournamentResourceIntTest.createEntity(em);
        em.persist(tournament);
        em.flush();
        tournamentFranchisePoints.setTournament(tournament);
        // Add required entity
        Franchise franchise = FranchiseResourceIntTest.createEntity(em);
        em.persist(franchise);
        em.flush();
        tournamentFranchisePoints.setFranchise(franchise);
        return tournamentFranchisePoints;
    }

    @Before
    public void initTest() {
        tournamentFranchisePoints = createEntity(em);
    }

    @Test
    @Transactional
    public void createTournamentFranchisePoints() throws Exception {
        int databaseSizeBeforeCreate = tournamentFranchisePointsRepository.findAll().size();

        // Create the TournamentFranchisePoints
        TournamentFranchisePointsDTO tournamentFranchisePointsDTO = tournamentFranchisePointsMapper.toDto(tournamentFranchisePoints);
        restTournamentFranchisePointsMockMvc.perform(post("/api/tournament-franchise-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentFranchisePointsDTO)))
            .andExpect(status().isCreated());

        // Validate the TournamentFranchisePoints in the database
        List<TournamentFranchisePoints> tournamentFranchisePointsList = tournamentFranchisePointsRepository.findAll();
        assertThat(tournamentFranchisePointsList).hasSize(databaseSizeBeforeCreate + 1);
        TournamentFranchisePoints testTournamentFranchisePoints = tournamentFranchisePointsList.get(tournamentFranchisePointsList.size() - 1);
        assertThat(testTournamentFranchisePoints.getPoints()).isEqualTo(DEFAULT_POINTS);
    }

    @Test
    @Transactional
    public void createTournamentFranchisePointsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tournamentFranchisePointsRepository.findAll().size();

        // Create the TournamentFranchisePoints with an existing ID
        tournamentFranchisePoints.setId(1L);
        TournamentFranchisePointsDTO tournamentFranchisePointsDTO = tournamentFranchisePointsMapper.toDto(tournamentFranchisePoints);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTournamentFranchisePointsMockMvc.perform(post("/api/tournament-franchise-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentFranchisePointsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TournamentFranchisePoints in the database
        List<TournamentFranchisePoints> tournamentFranchisePointsList = tournamentFranchisePointsRepository.findAll();
        assertThat(tournamentFranchisePointsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = tournamentFranchisePointsRepository.findAll().size();
        // set the field null
        tournamentFranchisePoints.setPoints(null);

        // Create the TournamentFranchisePoints, which fails.
        TournamentFranchisePointsDTO tournamentFranchisePointsDTO = tournamentFranchisePointsMapper.toDto(tournamentFranchisePoints);

        restTournamentFranchisePointsMockMvc.perform(post("/api/tournament-franchise-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentFranchisePointsDTO)))
            .andExpect(status().isBadRequest());

        List<TournamentFranchisePoints> tournamentFranchisePointsList = tournamentFranchisePointsRepository.findAll();
        assertThat(tournamentFranchisePointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTournamentFranchisePoints() throws Exception {
        // Initialize the database
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);

        // Get all the tournamentFranchisePointsList
        restTournamentFranchisePointsMockMvc.perform(get("/api/tournament-franchise-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tournamentFranchisePoints.getId().intValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS.doubleValue())));
    }

    @Test
    @Transactional
    public void getTournamentFranchisePoints() throws Exception {
        // Initialize the database
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);

        // Get the tournamentFranchisePoints
        restTournamentFranchisePointsMockMvc.perform(get("/api/tournament-franchise-points/{id}", tournamentFranchisePoints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tournamentFranchisePoints.getId().intValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllTournamentFranchisePointsByPointsIsEqualToSomething() throws Exception {
        // Initialize the database
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);

        // Get all the tournamentFranchisePointsList where points equals to DEFAULT_POINTS
        defaultTournamentFranchisePointsShouldBeFound("points.equals=" + DEFAULT_POINTS);

        // Get all the tournamentFranchisePointsList where points equals to UPDATED_POINTS
        defaultTournamentFranchisePointsShouldNotBeFound("points.equals=" + UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void getAllTournamentFranchisePointsByPointsIsInShouldWork() throws Exception {
        // Initialize the database
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);

        // Get all the tournamentFranchisePointsList where points in DEFAULT_POINTS or UPDATED_POINTS
        defaultTournamentFranchisePointsShouldBeFound("points.in=" + DEFAULT_POINTS + "," + UPDATED_POINTS);

        // Get all the tournamentFranchisePointsList where points equals to UPDATED_POINTS
        defaultTournamentFranchisePointsShouldNotBeFound("points.in=" + UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void getAllTournamentFranchisePointsByPointsIsNullOrNotNull() throws Exception {
        // Initialize the database
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);

        // Get all the tournamentFranchisePointsList where points is not null
        defaultTournamentFranchisePointsShouldBeFound("points.specified=true");

        // Get all the tournamentFranchisePointsList where points is null
        defaultTournamentFranchisePointsShouldNotBeFound("points.specified=false");
    }

    @Test
    @Transactional
    public void getAllTournamentFranchisePointsByTournamentIsEqualToSomething() throws Exception {
        // Initialize the database
        Tournament tournament = TournamentResourceIntTest.createEntity(em);
        em.persist(tournament);
        em.flush();
        tournamentFranchisePoints.setTournament(tournament);
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);
        Long tournamentId = tournament.getId();

        // Get all the tournamentFranchisePointsList where tournament equals to tournamentId
        defaultTournamentFranchisePointsShouldBeFound("tournamentId.equals=" + tournamentId);

        // Get all the tournamentFranchisePointsList where tournament equals to tournamentId + 1
        defaultTournamentFranchisePointsShouldNotBeFound("tournamentId.equals=" + (tournamentId + 1));
    }


    @Test
    @Transactional
    public void getAllTournamentFranchisePointsByFranchiseIsEqualToSomething() throws Exception {
        // Initialize the database
        Franchise franchise = FranchiseResourceIntTest.createEntity(em);
        em.persist(franchise);
        em.flush();
        tournamentFranchisePoints.setFranchise(franchise);
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);
        Long franchiseId = franchise.getId();

        // Get all the tournamentFranchisePointsList where franchise equals to franchiseId
        defaultTournamentFranchisePointsShouldBeFound("franchiseId.equals=" + franchiseId);

        // Get all the tournamentFranchisePointsList where franchise equals to franchiseId + 1
        defaultTournamentFranchisePointsShouldNotBeFound("franchiseId.equals=" + (franchiseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTournamentFranchisePointsShouldBeFound(String filter) throws Exception {
        restTournamentFranchisePointsMockMvc.perform(get("/api/tournament-franchise-points?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tournamentFranchisePoints.getId().intValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTournamentFranchisePointsShouldNotBeFound(String filter) throws Exception {
        restTournamentFranchisePointsMockMvc.perform(get("/api/tournament-franchise-points?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTournamentFranchisePoints() throws Exception {
        // Get the tournamentFranchisePoints
        restTournamentFranchisePointsMockMvc.perform(get("/api/tournament-franchise-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTournamentFranchisePoints() throws Exception {
        // Initialize the database
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);
        int databaseSizeBeforeUpdate = tournamentFranchisePointsRepository.findAll().size();

        // Update the tournamentFranchisePoints
        TournamentFranchisePoints updatedTournamentFranchisePoints = tournamentFranchisePointsRepository.findOne(tournamentFranchisePoints.getId());
        // Disconnect from session so that the updates on updatedTournamentFranchisePoints are not directly saved in db
        em.detach(updatedTournamentFranchisePoints);
        updatedTournamentFranchisePoints
            .points(UPDATED_POINTS);
        TournamentFranchisePointsDTO tournamentFranchisePointsDTO = tournamentFranchisePointsMapper.toDto(updatedTournamentFranchisePoints);

        restTournamentFranchisePointsMockMvc.perform(put("/api/tournament-franchise-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentFranchisePointsDTO)))
            .andExpect(status().isOk());

        // Validate the TournamentFranchisePoints in the database
        List<TournamentFranchisePoints> tournamentFranchisePointsList = tournamentFranchisePointsRepository.findAll();
        assertThat(tournamentFranchisePointsList).hasSize(databaseSizeBeforeUpdate);
        TournamentFranchisePoints testTournamentFranchisePoints = tournamentFranchisePointsList.get(tournamentFranchisePointsList.size() - 1);
        assertThat(testTournamentFranchisePoints.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingTournamentFranchisePoints() throws Exception {
        int databaseSizeBeforeUpdate = tournamentFranchisePointsRepository.findAll().size();

        // Create the TournamentFranchisePoints
        TournamentFranchisePointsDTO tournamentFranchisePointsDTO = tournamentFranchisePointsMapper.toDto(tournamentFranchisePoints);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTournamentFranchisePointsMockMvc.perform(put("/api/tournament-franchise-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tournamentFranchisePointsDTO)))
            .andExpect(status().isCreated());

        // Validate the TournamentFranchisePoints in the database
        List<TournamentFranchisePoints> tournamentFranchisePointsList = tournamentFranchisePointsRepository.findAll();
        assertThat(tournamentFranchisePointsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTournamentFranchisePoints() throws Exception {
        // Initialize the database
        tournamentFranchisePointsRepository.saveAndFlush(tournamentFranchisePoints);
        int databaseSizeBeforeDelete = tournamentFranchisePointsRepository.findAll().size();

        // Get the tournamentFranchisePoints
        restTournamentFranchisePointsMockMvc.perform(delete("/api/tournament-franchise-points/{id}", tournamentFranchisePoints.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TournamentFranchisePoints> tournamentFranchisePointsList = tournamentFranchisePointsRepository.findAll();
        assertThat(tournamentFranchisePointsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TournamentFranchisePoints.class);
        TournamentFranchisePoints tournamentFranchisePoints1 = new TournamentFranchisePoints();
        tournamentFranchisePoints1.setId(1L);
        TournamentFranchisePoints tournamentFranchisePoints2 = new TournamentFranchisePoints();
        tournamentFranchisePoints2.setId(tournamentFranchisePoints1.getId());
        assertThat(tournamentFranchisePoints1).isEqualTo(tournamentFranchisePoints2);
        tournamentFranchisePoints2.setId(2L);
        assertThat(tournamentFranchisePoints1).isNotEqualTo(tournamentFranchisePoints2);
        tournamentFranchisePoints1.setId(null);
        assertThat(tournamentFranchisePoints1).isNotEqualTo(tournamentFranchisePoints2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TournamentFranchisePointsDTO.class);
        TournamentFranchisePointsDTO tournamentFranchisePointsDTO1 = new TournamentFranchisePointsDTO();
        tournamentFranchisePointsDTO1.setId(1L);
        TournamentFranchisePointsDTO tournamentFranchisePointsDTO2 = new TournamentFranchisePointsDTO();
        assertThat(tournamentFranchisePointsDTO1).isNotEqualTo(tournamentFranchisePointsDTO2);
        tournamentFranchisePointsDTO2.setId(tournamentFranchisePointsDTO1.getId());
        assertThat(tournamentFranchisePointsDTO1).isEqualTo(tournamentFranchisePointsDTO2);
        tournamentFranchisePointsDTO2.setId(2L);
        assertThat(tournamentFranchisePointsDTO1).isNotEqualTo(tournamentFranchisePointsDTO2);
        tournamentFranchisePointsDTO1.setId(null);
        assertThat(tournamentFranchisePointsDTO1).isNotEqualTo(tournamentFranchisePointsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tournamentFranchisePointsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tournamentFranchisePointsMapper.fromId(null)).isNull();
    }
}
