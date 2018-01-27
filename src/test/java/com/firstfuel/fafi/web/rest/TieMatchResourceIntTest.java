package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.TieMatch;
import com.firstfuel.fafi.domain.Match;
import com.firstfuel.fafi.repository.TieMatchRepository;
import com.firstfuel.fafi.service.TieMatchService;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.TieMatchCriteria;
import com.firstfuel.fafi.service.TieMatchQueryService;

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

import com.firstfuel.fafi.domain.enumeration.TieType;
/**
 * Test class for the TieMatchResource REST controller.
 *
 * @see TieMatchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class TieMatchResourceIntTest {

    private static final TieType DEFAULT_TIE_TYPE = TieType.SINGLES;
    private static final TieType UPDATED_TIE_TYPE = TieType.DOUBLES;

    @Autowired
    private TieMatchRepository tieMatchRepository;

    @Autowired
    private TieMatchService tieMatchService;

    @Autowired
    private TieMatchQueryService tieMatchQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTieMatchMockMvc;

    private TieMatch tieMatch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TieMatchResource tieMatchResource = new TieMatchResource(tieMatchService, tieMatchQueryService);
        this.restTieMatchMockMvc = MockMvcBuilders.standaloneSetup(tieMatchResource)
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
    public static TieMatch createEntity(EntityManager em) {
        TieMatch tieMatch = new TieMatch()
            .tieType(DEFAULT_TIE_TYPE);
        // Add required entity
        Match match = MatchResourceIntTest.createEntity(em);
        em.persist(match);
        em.flush();
        tieMatch.setMatch(match);
        return tieMatch;
    }

    @Before
    public void initTest() {
        tieMatch = createEntity(em);
    }

    @Test
    @Transactional
    public void createTieMatch() throws Exception {
        int databaseSizeBeforeCreate = tieMatchRepository.findAll().size();

        // Create the TieMatch
        restTieMatchMockMvc.perform(post("/api/tie-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatch)))
            .andExpect(status().isCreated());

        // Validate the TieMatch in the database
        List<TieMatch> tieMatchList = tieMatchRepository.findAll();
        assertThat(tieMatchList).hasSize(databaseSizeBeforeCreate + 1);
        TieMatch testTieMatch = tieMatchList.get(tieMatchList.size() - 1);
        assertThat(testTieMatch.getTieType()).isEqualTo(DEFAULT_TIE_TYPE);
    }

    @Test
    @Transactional
    public void createTieMatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tieMatchRepository.findAll().size();

        // Create the TieMatch with an existing ID
        tieMatch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTieMatchMockMvc.perform(post("/api/tie-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatch)))
            .andExpect(status().isBadRequest());

        // Validate the TieMatch in the database
        List<TieMatch> tieMatchList = tieMatchRepository.findAll();
        assertThat(tieMatchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTieTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tieMatchRepository.findAll().size();
        // set the field null
        tieMatch.setTieType(null);

        // Create the TieMatch, which fails.

        restTieMatchMockMvc.perform(post("/api/tie-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatch)))
            .andExpect(status().isBadRequest());

        List<TieMatch> tieMatchList = tieMatchRepository.findAll();
        assertThat(tieMatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTieMatches() throws Exception {
        // Initialize the database
        tieMatchRepository.saveAndFlush(tieMatch);

        // Get all the tieMatchList
        restTieMatchMockMvc.perform(get("/api/tie-matches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tieMatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].tieType").value(hasItem(DEFAULT_TIE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTieMatch() throws Exception {
        // Initialize the database
        tieMatchRepository.saveAndFlush(tieMatch);

        // Get the tieMatch
        restTieMatchMockMvc.perform(get("/api/tie-matches/{id}", tieMatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tieMatch.getId().intValue()))
            .andExpect(jsonPath("$.tieType").value(DEFAULT_TIE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllTieMatchesByTieTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        tieMatchRepository.saveAndFlush(tieMatch);

        // Get all the tieMatchList where tieType equals to DEFAULT_TIE_TYPE
        defaultTieMatchShouldBeFound("tieType.equals=" + DEFAULT_TIE_TYPE);

        // Get all the tieMatchList where tieType equals to UPDATED_TIE_TYPE
        defaultTieMatchShouldNotBeFound("tieType.equals=" + UPDATED_TIE_TYPE);
    }

    @Test
    @Transactional
    public void getAllTieMatchesByTieTypeIsInShouldWork() throws Exception {
        // Initialize the database
        tieMatchRepository.saveAndFlush(tieMatch);

        // Get all the tieMatchList where tieType in DEFAULT_TIE_TYPE or UPDATED_TIE_TYPE
        defaultTieMatchShouldBeFound("tieType.in=" + DEFAULT_TIE_TYPE + "," + UPDATED_TIE_TYPE);

        // Get all the tieMatchList where tieType equals to UPDATED_TIE_TYPE
        defaultTieMatchShouldNotBeFound("tieType.in=" + UPDATED_TIE_TYPE);
    }

    @Test
    @Transactional
    public void getAllTieMatchesByTieTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tieMatchRepository.saveAndFlush(tieMatch);

        // Get all the tieMatchList where tieType is not null
        defaultTieMatchShouldBeFound("tieType.specified=true");

        // Get all the tieMatchList where tieType is null
        defaultTieMatchShouldNotBeFound("tieType.specified=false");
    }

    @Test
    @Transactional
    public void getAllTieMatchesByMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        Match match = MatchResourceIntTest.createEntity(em);
        em.persist(match);
        em.flush();
        tieMatch.setMatch(match);
        tieMatchRepository.saveAndFlush(tieMatch);
        Long matchId = match.getId();

        // Get all the tieMatchList where match equals to matchId
        defaultTieMatchShouldBeFound("matchId.equals=" + matchId);

        // Get all the tieMatchList where match equals to matchId + 1
        defaultTieMatchShouldNotBeFound("matchId.equals=" + (matchId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTieMatchShouldBeFound(String filter) throws Exception {
        restTieMatchMockMvc.perform(get("/api/tie-matches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tieMatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].tieType").value(hasItem(DEFAULT_TIE_TYPE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTieMatchShouldNotBeFound(String filter) throws Exception {
        restTieMatchMockMvc.perform(get("/api/tie-matches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTieMatch() throws Exception {
        // Get the tieMatch
        restTieMatchMockMvc.perform(get("/api/tie-matches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTieMatch() throws Exception {
        // Initialize the database
        tieMatchService.save(tieMatch);

        int databaseSizeBeforeUpdate = tieMatchRepository.findAll().size();

        // Update the tieMatch
        TieMatch updatedTieMatch = tieMatchRepository.findOne(tieMatch.getId());
        // Disconnect from session so that the updates on updatedTieMatch are not directly saved in db
        em.detach(updatedTieMatch);
        updatedTieMatch
            .tieType(UPDATED_TIE_TYPE);

        restTieMatchMockMvc.perform(put("/api/tie-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTieMatch)))
            .andExpect(status().isOk());

        // Validate the TieMatch in the database
        List<TieMatch> tieMatchList = tieMatchRepository.findAll();
        assertThat(tieMatchList).hasSize(databaseSizeBeforeUpdate);
        TieMatch testTieMatch = tieMatchList.get(tieMatchList.size() - 1);
        assertThat(testTieMatch.getTieType()).isEqualTo(UPDATED_TIE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTieMatch() throws Exception {
        int databaseSizeBeforeUpdate = tieMatchRepository.findAll().size();

        // Create the TieMatch

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTieMatchMockMvc.perform(put("/api/tie-matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tieMatch)))
            .andExpect(status().isCreated());

        // Validate the TieMatch in the database
        List<TieMatch> tieMatchList = tieMatchRepository.findAll();
        assertThat(tieMatchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTieMatch() throws Exception {
        // Initialize the database
        tieMatchService.save(tieMatch);

        int databaseSizeBeforeDelete = tieMatchRepository.findAll().size();

        // Get the tieMatch
        restTieMatchMockMvc.perform(delete("/api/tie-matches/{id}", tieMatch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TieMatch> tieMatchList = tieMatchRepository.findAll();
        assertThat(tieMatchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TieMatch.class);
        TieMatch tieMatch1 = new TieMatch();
        tieMatch1.setId(1L);
        TieMatch tieMatch2 = new TieMatch();
        tieMatch2.setId(tieMatch1.getId());
        assertThat(tieMatch1).isEqualTo(tieMatch2);
        tieMatch2.setId(2L);
        assertThat(tieMatch1).isNotEqualTo(tieMatch2);
        tieMatch1.setId(null);
        assertThat(tieMatch1).isNotEqualTo(tieMatch2);
    }
}
