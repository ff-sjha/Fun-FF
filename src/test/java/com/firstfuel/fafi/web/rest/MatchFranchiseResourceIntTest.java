package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.MatchFranchise;
import com.firstfuel.fafi.domain.Match;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.repository.MatchFranchiseRepository;
import com.firstfuel.fafi.service.MatchFranchiseService;
import com.firstfuel.fafi.service.dto.MatchFranchiseDTO;
import com.firstfuel.fafi.service.mapper.MatchFranchiseMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.MatchFranchiseCriteria;
import com.firstfuel.fafi.service.MatchFranchiseQueryService;

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
 * Test class for the MatchFranchiseResource REST controller.
 *
 * @see MatchFranchiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class MatchFranchiseResourceIntTest {

    @Autowired
    private MatchFranchiseRepository matchFranchiseRepository;

    @Autowired
    private MatchFranchiseMapper matchFranchiseMapper;

    @Autowired
    private MatchFranchiseService matchFranchiseService;

    @Autowired
    private MatchFranchiseQueryService matchFranchiseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMatchFranchiseMockMvc;

    private MatchFranchise matchFranchise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchFranchiseResource matchFranchiseResource = new MatchFranchiseResource(matchFranchiseService, matchFranchiseQueryService);
        this.restMatchFranchiseMockMvc = MockMvcBuilders.standaloneSetup(matchFranchiseResource)
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
    public static MatchFranchise createEntity(EntityManager em) {
        MatchFranchise matchFranchise = new MatchFranchise();
        // Add required entity
        Match match = MatchResourceIntTest.createEntity(em);
        em.persist(match);
        em.flush();
        matchFranchise.setMatch(match);
        // Add required entity
        SeasonsFranchise seasonsFranchise = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(seasonsFranchise);
        em.flush();
        matchFranchise.setSeasonsFranchise(seasonsFranchise);
        return matchFranchise;
    }

    @Before
    public void initTest() {
        matchFranchise = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchFranchise() throws Exception {
        int databaseSizeBeforeCreate = matchFranchiseRepository.findAll().size();

        // Create the MatchFranchise
        MatchFranchiseDTO matchFranchiseDTO = matchFranchiseMapper.toDto(matchFranchise);
        restMatchFranchiseMockMvc.perform(post("/api/match-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchFranchiseDTO)))
            .andExpect(status().isCreated());

        // Validate the MatchFranchise in the database
        List<MatchFranchise> matchFranchiseList = matchFranchiseRepository.findAll();
        assertThat(matchFranchiseList).hasSize(databaseSizeBeforeCreate + 1);
        MatchFranchise testMatchFranchise = matchFranchiseList.get(matchFranchiseList.size() - 1);
    }

    @Test
    @Transactional
    public void createMatchFranchiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchFranchiseRepository.findAll().size();

        // Create the MatchFranchise with an existing ID
        matchFranchise.setId(1L);
        MatchFranchiseDTO matchFranchiseDTO = matchFranchiseMapper.toDto(matchFranchise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchFranchiseMockMvc.perform(post("/api/match-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchFranchiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchFranchise in the database
        List<MatchFranchise> matchFranchiseList = matchFranchiseRepository.findAll();
        assertThat(matchFranchiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMatchFranchises() throws Exception {
        // Initialize the database
        matchFranchiseRepository.saveAndFlush(matchFranchise);

        // Get all the matchFranchiseList
        restMatchFranchiseMockMvc.perform(get("/api/match-franchises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchFranchise.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMatchFranchise() throws Exception {
        // Initialize the database
        matchFranchiseRepository.saveAndFlush(matchFranchise);

        // Get the matchFranchise
        restMatchFranchiseMockMvc.perform(get("/api/match-franchises/{id}", matchFranchise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchFranchise.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllMatchFranchisesByMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        Match match = MatchResourceIntTest.createEntity(em);
        em.persist(match);
        em.flush();
        matchFranchise.setMatch(match);
        matchFranchiseRepository.saveAndFlush(matchFranchise);
        Long matchId = match.getId();

        // Get all the matchFranchiseList where match equals to matchId
        defaultMatchFranchiseShouldBeFound("matchId.equals=" + matchId);

        // Get all the matchFranchiseList where match equals to matchId + 1
        defaultMatchFranchiseShouldNotBeFound("matchId.equals=" + (matchId + 1));
    }


    @Test
    @Transactional
    public void getAllMatchFranchisesBySeasonsFranchiseIsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchise seasonsFranchise = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(seasonsFranchise);
        em.flush();
        matchFranchise.setSeasonsFranchise(seasonsFranchise);
        matchFranchiseRepository.saveAndFlush(matchFranchise);
        Long seasonsFranchiseId = seasonsFranchise.getId();

        // Get all the matchFranchiseList where seasonsFranchise equals to seasonsFranchiseId
        defaultMatchFranchiseShouldBeFound("seasonsFranchiseId.equals=" + seasonsFranchiseId);

        // Get all the matchFranchiseList where seasonsFranchise equals to seasonsFranchiseId + 1
        defaultMatchFranchiseShouldNotBeFound("seasonsFranchiseId.equals=" + (seasonsFranchiseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMatchFranchiseShouldBeFound(String filter) throws Exception {
        restMatchFranchiseMockMvc.perform(get("/api/match-franchises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchFranchise.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMatchFranchiseShouldNotBeFound(String filter) throws Exception {
        restMatchFranchiseMockMvc.perform(get("/api/match-franchises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMatchFranchise() throws Exception {
        // Get the matchFranchise
        restMatchFranchiseMockMvc.perform(get("/api/match-franchises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchFranchise() throws Exception {
        // Initialize the database
        matchFranchiseRepository.saveAndFlush(matchFranchise);
        int databaseSizeBeforeUpdate = matchFranchiseRepository.findAll().size();

        // Update the matchFranchise
        MatchFranchise updatedMatchFranchise = matchFranchiseRepository.findOne(matchFranchise.getId());
        // Disconnect from session so that the updates on updatedMatchFranchise are not directly saved in db
        em.detach(updatedMatchFranchise);
        MatchFranchiseDTO matchFranchiseDTO = matchFranchiseMapper.toDto(updatedMatchFranchise);

        restMatchFranchiseMockMvc.perform(put("/api/match-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchFranchiseDTO)))
            .andExpect(status().isOk());

        // Validate the MatchFranchise in the database
        List<MatchFranchise> matchFranchiseList = matchFranchiseRepository.findAll();
        assertThat(matchFranchiseList).hasSize(databaseSizeBeforeUpdate);
        MatchFranchise testMatchFranchise = matchFranchiseList.get(matchFranchiseList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchFranchise() throws Exception {
        int databaseSizeBeforeUpdate = matchFranchiseRepository.findAll().size();

        // Create the MatchFranchise
        MatchFranchiseDTO matchFranchiseDTO = matchFranchiseMapper.toDto(matchFranchise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMatchFranchiseMockMvc.perform(put("/api/match-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchFranchiseDTO)))
            .andExpect(status().isCreated());

        // Validate the MatchFranchise in the database
        List<MatchFranchise> matchFranchiseList = matchFranchiseRepository.findAll();
        assertThat(matchFranchiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMatchFranchise() throws Exception {
        // Initialize the database
        matchFranchiseRepository.saveAndFlush(matchFranchise);
        int databaseSizeBeforeDelete = matchFranchiseRepository.findAll().size();

        // Get the matchFranchise
        restMatchFranchiseMockMvc.perform(delete("/api/match-franchises/{id}", matchFranchise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MatchFranchise> matchFranchiseList = matchFranchiseRepository.findAll();
        assertThat(matchFranchiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFranchise.class);
        MatchFranchise matchFranchise1 = new MatchFranchise();
        matchFranchise1.setId(1L);
        MatchFranchise matchFranchise2 = new MatchFranchise();
        matchFranchise2.setId(matchFranchise1.getId());
        assertThat(matchFranchise1).isEqualTo(matchFranchise2);
        matchFranchise2.setId(2L);
        assertThat(matchFranchise1).isNotEqualTo(matchFranchise2);
        matchFranchise1.setId(null);
        assertThat(matchFranchise1).isNotEqualTo(matchFranchise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchFranchiseDTO.class);
        MatchFranchiseDTO matchFranchiseDTO1 = new MatchFranchiseDTO();
        matchFranchiseDTO1.setId(1L);
        MatchFranchiseDTO matchFranchiseDTO2 = new MatchFranchiseDTO();
        assertThat(matchFranchiseDTO1).isNotEqualTo(matchFranchiseDTO2);
        matchFranchiseDTO2.setId(matchFranchiseDTO1.getId());
        assertThat(matchFranchiseDTO1).isEqualTo(matchFranchiseDTO2);
        matchFranchiseDTO2.setId(2L);
        assertThat(matchFranchiseDTO1).isNotEqualTo(matchFranchiseDTO2);
        matchFranchiseDTO1.setId(null);
        assertThat(matchFranchiseDTO1).isNotEqualTo(matchFranchiseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(matchFranchiseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(matchFranchiseMapper.fromId(null)).isNull();
    }
}
