package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.MatchUmpire;
import com.firstfuel.fafi.domain.Match;
import com.firstfuel.fafi.domain.Player;
import com.firstfuel.fafi.repository.MatchUmpireRepository;
import com.firstfuel.fafi.service.MatchUmpireService;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.MatchUmpireCriteria;
import com.firstfuel.fafi.service.MatchUmpireQueryService;

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
 * Test class for the MatchUmpireResource REST controller.
 *
 * @see MatchUmpireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class MatchUmpireResourceIntTest {

    @Autowired
    private MatchUmpireRepository matchUmpireRepository;

    @Autowired
    private MatchUmpireService matchUmpireService;

    @Autowired
    private MatchUmpireQueryService matchUmpireQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMatchUmpireMockMvc;

    private MatchUmpire matchUmpire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchUmpireResource matchUmpireResource = new MatchUmpireResource(matchUmpireService, matchUmpireQueryService);
        this.restMatchUmpireMockMvc = MockMvcBuilders.standaloneSetup(matchUmpireResource)
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
    public static MatchUmpire createEntity(EntityManager em) {
        MatchUmpire matchUmpire = new MatchUmpire();
        // Add required entity
        Match match = MatchResourceIntTest.createEntity(em);
        em.persist(match);
        em.flush();
        matchUmpire.setMatch(match);
        // Add required entity
        Player umpire = PlayerResourceIntTest.createEntity(em);
        em.persist(umpire);
        em.flush();
        matchUmpire.setUmpire(umpire);
        return matchUmpire;
    }

    @Before
    public void initTest() {
        matchUmpire = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchUmpire() throws Exception {
        int databaseSizeBeforeCreate = matchUmpireRepository.findAll().size();

        // Create the MatchUmpire
        restMatchUmpireMockMvc.perform(post("/api/match-umpires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchUmpire)))
            .andExpect(status().isCreated());

        // Validate the MatchUmpire in the database
        List<MatchUmpire> matchUmpireList = matchUmpireRepository.findAll();
        assertThat(matchUmpireList).hasSize(databaseSizeBeforeCreate + 1);
        MatchUmpire testMatchUmpire = matchUmpireList.get(matchUmpireList.size() - 1);
    }

    @Test
    @Transactional
    public void createMatchUmpireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchUmpireRepository.findAll().size();

        // Create the MatchUmpire with an existing ID
        matchUmpire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchUmpireMockMvc.perform(post("/api/match-umpires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchUmpire)))
            .andExpect(status().isBadRequest());

        // Validate the MatchUmpire in the database
        List<MatchUmpire> matchUmpireList = matchUmpireRepository.findAll();
        assertThat(matchUmpireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMatchUmpires() throws Exception {
        // Initialize the database
        matchUmpireRepository.saveAndFlush(matchUmpire);

        // Get all the matchUmpireList
        restMatchUmpireMockMvc.perform(get("/api/match-umpires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchUmpire.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMatchUmpire() throws Exception {
        // Initialize the database
        matchUmpireRepository.saveAndFlush(matchUmpire);

        // Get the matchUmpire
        restMatchUmpireMockMvc.perform(get("/api/match-umpires/{id}", matchUmpire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchUmpire.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllMatchUmpiresByMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        Match match = MatchResourceIntTest.createEntity(em);
        em.persist(match);
        em.flush();
        matchUmpire.setMatch(match);
        matchUmpireRepository.saveAndFlush(matchUmpire);
        Long matchId = match.getId();

        // Get all the matchUmpireList where match equals to matchId
        defaultMatchUmpireShouldBeFound("matchId.equals=" + matchId);

        // Get all the matchUmpireList where match equals to matchId + 1
        defaultMatchUmpireShouldNotBeFound("matchId.equals=" + (matchId + 1));
    }


    @Test
    @Transactional
    public void getAllMatchUmpiresByUmpireIsEqualToSomething() throws Exception {
        // Initialize the database
        Player umpire = PlayerResourceIntTest.createEntity(em);
        em.persist(umpire);
        em.flush();
        matchUmpire.setUmpire(umpire);
        matchUmpireRepository.saveAndFlush(matchUmpire);
        Long umpireId = umpire.getId();

        // Get all the matchUmpireList where umpire equals to umpireId
        defaultMatchUmpireShouldBeFound("umpireId.equals=" + umpireId);

        // Get all the matchUmpireList where umpire equals to umpireId + 1
        defaultMatchUmpireShouldNotBeFound("umpireId.equals=" + (umpireId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMatchUmpireShouldBeFound(String filter) throws Exception {
        restMatchUmpireMockMvc.perform(get("/api/match-umpires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchUmpire.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMatchUmpireShouldNotBeFound(String filter) throws Exception {
        restMatchUmpireMockMvc.perform(get("/api/match-umpires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMatchUmpire() throws Exception {
        // Get the matchUmpire
        restMatchUmpireMockMvc.perform(get("/api/match-umpires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchUmpire() throws Exception {
        // Initialize the database
        matchUmpireService.save(matchUmpire);

        int databaseSizeBeforeUpdate = matchUmpireRepository.findAll().size();

        // Update the matchUmpire
        MatchUmpire updatedMatchUmpire = matchUmpireRepository.findOne(matchUmpire.getId());
        // Disconnect from session so that the updates on updatedMatchUmpire are not directly saved in db
        em.detach(updatedMatchUmpire);

        restMatchUmpireMockMvc.perform(put("/api/match-umpires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatchUmpire)))
            .andExpect(status().isOk());

        // Validate the MatchUmpire in the database
        List<MatchUmpire> matchUmpireList = matchUmpireRepository.findAll();
        assertThat(matchUmpireList).hasSize(databaseSizeBeforeUpdate);
        MatchUmpire testMatchUmpire = matchUmpireList.get(matchUmpireList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchUmpire() throws Exception {
        int databaseSizeBeforeUpdate = matchUmpireRepository.findAll().size();

        // Create the MatchUmpire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMatchUmpireMockMvc.perform(put("/api/match-umpires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchUmpire)))
            .andExpect(status().isCreated());

        // Validate the MatchUmpire in the database
        List<MatchUmpire> matchUmpireList = matchUmpireRepository.findAll();
        assertThat(matchUmpireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMatchUmpire() throws Exception {
        // Initialize the database
        matchUmpireService.save(matchUmpire);

        int databaseSizeBeforeDelete = matchUmpireRepository.findAll().size();

        // Get the matchUmpire
        restMatchUmpireMockMvc.perform(delete("/api/match-umpires/{id}", matchUmpire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MatchUmpire> matchUmpireList = matchUmpireRepository.findAll();
        assertThat(matchUmpireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchUmpire.class);
        MatchUmpire matchUmpire1 = new MatchUmpire();
        matchUmpire1.setId(1L);
        MatchUmpire matchUmpire2 = new MatchUmpire();
        matchUmpire2.setId(matchUmpire1.getId());
        assertThat(matchUmpire1).isEqualTo(matchUmpire2);
        matchUmpire2.setId(2L);
        assertThat(matchUmpire1).isNotEqualTo(matchUmpire2);
        matchUmpire1.setId(null);
        assertThat(matchUmpire1).isNotEqualTo(matchUmpire2);
    }
}
