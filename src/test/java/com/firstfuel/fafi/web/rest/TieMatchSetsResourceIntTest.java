package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.TieMatchSets;
import com.firstfuel.fafi.domain.TieMatch;
import com.firstfuel.fafi.repository.TieMatchSetsRepository;
import com.firstfuel.fafi.service.TieMatchSetsService;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.TieMatchSetsCriteria;
import com.firstfuel.fafi.service.TieMatchSetsQueryService;

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
 * Test class for the TieMatchSetsResource REST controller.
 *
 * @see TieMatchSetsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class TieMatchSetsResourceIntTest {

    private static final Integer DEFAULT_SET_NUMBER = 1;
    private static final Integer UPDATED_SET_NUMBER = 2;

    private static final Integer DEFAULT_TEAM_1_POINTS = 1;
    private static final Integer UPDATED_TEAM_1_POINTS = 2;

    private static final Integer DEFAULT_TEAM_2_POINTS = 1;
    private static final Integer UPDATED_TEAM_2_POINTS = 2;

    @Autowired
    private TieMatchSetsRepository tieMatchSetsRepository;

    @Autowired
    private TieMatchSetsService tieMatchSetsService;

    @Autowired
    private TieMatchSetsQueryService tieMatchSetsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTieMatchSetsMockMvc;

    private TieMatchSets tieMatchSets;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TieMatchSetsResource tieMatchSetsResource = new TieMatchSetsResource(tieMatchSetsService, tieMatchSetsQueryService);
        this.restTieMatchSetsMockMvc = MockMvcBuilders.standaloneSetup(tieMatchSetsResource)
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
    public static TieMatchSets createEntity(EntityManager em) {
        TieMatchSets tieMatchSets = new TieMatchSets()
            .setNumber(DEFAULT_SET_NUMBER)
            .team1Points(DEFAULT_TEAM_1_POINTS)
            .team2Points(DEFAULT_TEAM_2_POINTS);
        // Add required entity
        TieMatch tieMatch = TieMatchResourceIntTest.createEntity(em);
        em.persist(tieMatch);
        em.flush();
        tieMatchSets.setTieMatch(tieMatch);
        return tieMatchSets;
    }

    @Before
    public void initTest() {
        tieMatchSets = createEntity(em);
    }

    @Test
    @Transactional
    public void createTieMatchSets() throws Exception {
        int databaseSizeBeforeCreate = tieMatchSetsRepository.findAll().size();

        // Create the TieMatchSets
        restTieMatchSetsMockMvc.perform(post("/api/tie-match-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatchSets)))
            .andExpect(status().isCreated());

        // Validate the TieMatchSets in the database
        List<TieMatchSets> tieMatchSetsList = tieMatchSetsRepository.findAll();
        assertThat(tieMatchSetsList).hasSize(databaseSizeBeforeCreate + 1);
        TieMatchSets testTieMatchSets = tieMatchSetsList.get(tieMatchSetsList.size() - 1);
        assertThat(testTieMatchSets.getSetNumber()).isEqualTo(DEFAULT_SET_NUMBER);
        assertThat(testTieMatchSets.getTeam1Points()).isEqualTo(DEFAULT_TEAM_1_POINTS);
        assertThat(testTieMatchSets.getTeam2Points()).isEqualTo(DEFAULT_TEAM_2_POINTS);
    }

    @Test
    @Transactional
    public void createTieMatchSetsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tieMatchSetsRepository.findAll().size();

        // Create the TieMatchSets with an existing ID
        tieMatchSets.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTieMatchSetsMockMvc.perform(post("/api/tie-match-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatchSets)))
            .andExpect(status().isBadRequest());

        // Validate the TieMatchSets in the database
        List<TieMatchSets> tieMatchSetsList = tieMatchSetsRepository.findAll();
        assertThat(tieMatchSetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSetNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = tieMatchSetsRepository.findAll().size();
        // set the field null
        tieMatchSets.setSetNumber(null);

        // Create the TieMatchSets, which fails.

        restTieMatchSetsMockMvc.perform(post("/api/tie-match-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatchSets)))
            .andExpect(status().isBadRequest());

        List<TieMatchSets> tieMatchSetsList = tieMatchSetsRepository.findAll();
        assertThat(tieMatchSetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTieMatchSets() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList
        restTieMatchSetsMockMvc.perform(get("/api/tie-match-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tieMatchSets.getId().intValue())))
            .andExpect(jsonPath("$.[*].setNumber").value(hasItem(DEFAULT_SET_NUMBER)))
            .andExpect(jsonPath("$.[*].team1Points").value(hasItem(DEFAULT_TEAM_1_POINTS)))
            .andExpect(jsonPath("$.[*].team2Points").value(hasItem(DEFAULT_TEAM_2_POINTS)));
    }

    @Test
    @Transactional
    public void getTieMatchSets() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get the tieMatchSets
        restTieMatchSetsMockMvc.perform(get("/api/tie-match-sets/{id}", tieMatchSets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tieMatchSets.getId().intValue()))
            .andExpect(jsonPath("$.setNumber").value(DEFAULT_SET_NUMBER))
            .andExpect(jsonPath("$.team1Points").value(DEFAULT_TEAM_1_POINTS))
            .andExpect(jsonPath("$.team2Points").value(DEFAULT_TEAM_2_POINTS));
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsBySetNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where setNumber equals to DEFAULT_SET_NUMBER
        defaultTieMatchSetsShouldBeFound("setNumber.equals=" + DEFAULT_SET_NUMBER);

        // Get all the tieMatchSetsList where setNumber equals to UPDATED_SET_NUMBER
        defaultTieMatchSetsShouldNotBeFound("setNumber.equals=" + UPDATED_SET_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsBySetNumberIsInShouldWork() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where setNumber in DEFAULT_SET_NUMBER or UPDATED_SET_NUMBER
        defaultTieMatchSetsShouldBeFound("setNumber.in=" + DEFAULT_SET_NUMBER + "," + UPDATED_SET_NUMBER);

        // Get all the tieMatchSetsList where setNumber equals to UPDATED_SET_NUMBER
        defaultTieMatchSetsShouldNotBeFound("setNumber.in=" + UPDATED_SET_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsBySetNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where setNumber is not null
        defaultTieMatchSetsShouldBeFound("setNumber.specified=true");

        // Get all the tieMatchSetsList where setNumber is null
        defaultTieMatchSetsShouldNotBeFound("setNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsBySetNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where setNumber greater than or equals to DEFAULT_SET_NUMBER
        defaultTieMatchSetsShouldBeFound("setNumber.greaterOrEqualThan=" + DEFAULT_SET_NUMBER);

        // Get all the tieMatchSetsList where setNumber greater than or equals to UPDATED_SET_NUMBER
        defaultTieMatchSetsShouldNotBeFound("setNumber.greaterOrEqualThan=" + UPDATED_SET_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsBySetNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where setNumber less than or equals to DEFAULT_SET_NUMBER
        defaultTieMatchSetsShouldNotBeFound("setNumber.lessThan=" + DEFAULT_SET_NUMBER);

        // Get all the tieMatchSetsList where setNumber less than or equals to UPDATED_SET_NUMBER
        defaultTieMatchSetsShouldBeFound("setNumber.lessThan=" + UPDATED_SET_NUMBER);
    }


    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam1PointsIsEqualToSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team1Points equals to DEFAULT_TEAM_1_POINTS
        defaultTieMatchSetsShouldBeFound("team1Points.equals=" + DEFAULT_TEAM_1_POINTS);

        // Get all the tieMatchSetsList where team1Points equals to UPDATED_TEAM_1_POINTS
        defaultTieMatchSetsShouldNotBeFound("team1Points.equals=" + UPDATED_TEAM_1_POINTS);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam1PointsIsInShouldWork() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team1Points in DEFAULT_TEAM_1_POINTS or UPDATED_TEAM_1_POINTS
        defaultTieMatchSetsShouldBeFound("team1Points.in=" + DEFAULT_TEAM_1_POINTS + "," + UPDATED_TEAM_1_POINTS);

        // Get all the tieMatchSetsList where team1Points equals to UPDATED_TEAM_1_POINTS
        defaultTieMatchSetsShouldNotBeFound("team1Points.in=" + UPDATED_TEAM_1_POINTS);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam1PointsIsNullOrNotNull() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team1Points is not null
        defaultTieMatchSetsShouldBeFound("team1Points.specified=true");

        // Get all the tieMatchSetsList where team1Points is null
        defaultTieMatchSetsShouldNotBeFound("team1Points.specified=false");
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam1PointsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team1Points greater than or equals to DEFAULT_TEAM_1_POINTS
        defaultTieMatchSetsShouldBeFound("team1Points.greaterOrEqualThan=" + DEFAULT_TEAM_1_POINTS);

        // Get all the tieMatchSetsList where team1Points greater than or equals to UPDATED_TEAM_1_POINTS
        defaultTieMatchSetsShouldNotBeFound("team1Points.greaterOrEqualThan=" + UPDATED_TEAM_1_POINTS);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam1PointsIsLessThanSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team1Points less than or equals to DEFAULT_TEAM_1_POINTS
        defaultTieMatchSetsShouldNotBeFound("team1Points.lessThan=" + DEFAULT_TEAM_1_POINTS);

        // Get all the tieMatchSetsList where team1Points less than or equals to UPDATED_TEAM_1_POINTS
        defaultTieMatchSetsShouldBeFound("team1Points.lessThan=" + UPDATED_TEAM_1_POINTS);
    }


    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam2PointsIsEqualToSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team2Points equals to DEFAULT_TEAM_2_POINTS
        defaultTieMatchSetsShouldBeFound("team2Points.equals=" + DEFAULT_TEAM_2_POINTS);

        // Get all the tieMatchSetsList where team2Points equals to UPDATED_TEAM_2_POINTS
        defaultTieMatchSetsShouldNotBeFound("team2Points.equals=" + UPDATED_TEAM_2_POINTS);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam2PointsIsInShouldWork() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team2Points in DEFAULT_TEAM_2_POINTS or UPDATED_TEAM_2_POINTS
        defaultTieMatchSetsShouldBeFound("team2Points.in=" + DEFAULT_TEAM_2_POINTS + "," + UPDATED_TEAM_2_POINTS);

        // Get all the tieMatchSetsList where team2Points equals to UPDATED_TEAM_2_POINTS
        defaultTieMatchSetsShouldNotBeFound("team2Points.in=" + UPDATED_TEAM_2_POINTS);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam2PointsIsNullOrNotNull() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team2Points is not null
        defaultTieMatchSetsShouldBeFound("team2Points.specified=true");

        // Get all the tieMatchSetsList where team2Points is null
        defaultTieMatchSetsShouldNotBeFound("team2Points.specified=false");
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam2PointsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team2Points greater than or equals to DEFAULT_TEAM_2_POINTS
        defaultTieMatchSetsShouldBeFound("team2Points.greaterOrEqualThan=" + DEFAULT_TEAM_2_POINTS);

        // Get all the tieMatchSetsList where team2Points greater than or equals to UPDATED_TEAM_2_POINTS
        defaultTieMatchSetsShouldNotBeFound("team2Points.greaterOrEqualThan=" + UPDATED_TEAM_2_POINTS);
    }

    @Test
    @Transactional
    public void getAllTieMatchSetsByTeam2PointsIsLessThanSomething() throws Exception {
        // Initialize the database
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);

        // Get all the tieMatchSetsList where team2Points less than or equals to DEFAULT_TEAM_2_POINTS
        defaultTieMatchSetsShouldNotBeFound("team2Points.lessThan=" + DEFAULT_TEAM_2_POINTS);

        // Get all the tieMatchSetsList where team2Points less than or equals to UPDATED_TEAM_2_POINTS
        defaultTieMatchSetsShouldBeFound("team2Points.lessThan=" + UPDATED_TEAM_2_POINTS);
    }


    @Test
    @Transactional
    public void getAllTieMatchSetsByTieMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        TieMatch tieMatch = TieMatchResourceIntTest.createEntity(em);
        em.persist(tieMatch);
        em.flush();
        tieMatchSets.setTieMatch(tieMatch);
        tieMatchSetsRepository.saveAndFlush(tieMatchSets);
        Long tieMatchId = tieMatch.getId();

        // Get all the tieMatchSetsList where tieMatch equals to tieMatchId
        defaultTieMatchSetsShouldBeFound("tieMatchId.equals=" + tieMatchId);

        // Get all the tieMatchSetsList where tieMatch equals to tieMatchId + 1
        defaultTieMatchSetsShouldNotBeFound("tieMatchId.equals=" + (tieMatchId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTieMatchSetsShouldBeFound(String filter) throws Exception {
        restTieMatchSetsMockMvc.perform(get("/api/tie-match-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tieMatchSets.getId().intValue())))
            .andExpect(jsonPath("$.[*].setNumber").value(hasItem(DEFAULT_SET_NUMBER)))
            .andExpect(jsonPath("$.[*].team1Points").value(hasItem(DEFAULT_TEAM_1_POINTS)))
            .andExpect(jsonPath("$.[*].team2Points").value(hasItem(DEFAULT_TEAM_2_POINTS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTieMatchSetsShouldNotBeFound(String filter) throws Exception {
        restTieMatchSetsMockMvc.perform(get("/api/tie-match-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTieMatchSets() throws Exception {
        // Get the tieMatchSets
        restTieMatchSetsMockMvc.perform(get("/api/tie-match-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTieMatchSets() throws Exception {
        // Initialize the database
        tieMatchSetsService.save(tieMatchSets);

        int databaseSizeBeforeUpdate = tieMatchSetsRepository.findAll().size();

        // Update the tieMatchSets
        TieMatchSets updatedTieMatchSets = tieMatchSetsRepository.findOne(tieMatchSets.getId());
        // Disconnect from session so that the updates on updatedTieMatchSets are not directly saved in db
        em.detach(updatedTieMatchSets);
        updatedTieMatchSets
            .setNumber(UPDATED_SET_NUMBER)
            .team1Points(UPDATED_TEAM_1_POINTS)
            .team2Points(UPDATED_TEAM_2_POINTS);

        restTieMatchSetsMockMvc.perform(put("/api/tie-match-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTieMatchSets)))
            .andExpect(status().isOk());

        // Validate the TieMatchSets in the database
        List<TieMatchSets> tieMatchSetsList = tieMatchSetsRepository.findAll();
        assertThat(tieMatchSetsList).hasSize(databaseSizeBeforeUpdate);
        TieMatchSets testTieMatchSets = tieMatchSetsList.get(tieMatchSetsList.size() - 1);
        assertThat(testTieMatchSets.getSetNumber()).isEqualTo(UPDATED_SET_NUMBER);
        assertThat(testTieMatchSets.getTeam1Points()).isEqualTo(UPDATED_TEAM_1_POINTS);
        assertThat(testTieMatchSets.getTeam2Points()).isEqualTo(UPDATED_TEAM_2_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingTieMatchSets() throws Exception {
        int databaseSizeBeforeUpdate = tieMatchSetsRepository.findAll().size();

        // Create the TieMatchSets

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTieMatchSetsMockMvc.perform(put("/api/tie-match-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatchSets)))
            .andExpect(status().isCreated());

        // Validate the TieMatchSets in the database
        List<TieMatchSets> tieMatchSetsList = tieMatchSetsRepository.findAll();
        assertThat(tieMatchSetsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTieMatchSets() throws Exception {
        // Initialize the database
        tieMatchSetsService.save(tieMatchSets);

        int databaseSizeBeforeDelete = tieMatchSetsRepository.findAll().size();

        // Get the tieMatchSets
        restTieMatchSetsMockMvc.perform(delete("/api/tie-match-sets/{id}", tieMatchSets.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TieMatchSets> tieMatchSetsList = tieMatchSetsRepository.findAll();
        assertThat(tieMatchSetsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TieMatchSets.class);
        TieMatchSets tieMatchSets1 = new TieMatchSets();
        tieMatchSets1.setId(1L);
        TieMatchSets tieMatchSets2 = new TieMatchSets();
        tieMatchSets2.setId(tieMatchSets1.getId());
        assertThat(tieMatchSets1).isEqualTo(tieMatchSets2);
        tieMatchSets2.setId(2L);
        assertThat(tieMatchSets1).isNotEqualTo(tieMatchSets2);
        tieMatchSets1.setId(null);
        assertThat(tieMatchSets1).isNotEqualTo(tieMatchSets2);
    }
}
