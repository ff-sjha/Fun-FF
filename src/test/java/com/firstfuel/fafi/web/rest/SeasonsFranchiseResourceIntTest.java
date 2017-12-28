package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.domain.Season;
import com.firstfuel.fafi.domain.Franchise;
import com.firstfuel.fafi.domain.Player;
import com.firstfuel.fafi.domain.Player;
import com.firstfuel.fafi.repository.SeasonsFranchiseRepository;
import com.firstfuel.fafi.service.SeasonsFranchiseService;
import com.firstfuel.fafi.service.dto.SeasonsFranchiseDTO;
import com.firstfuel.fafi.service.mapper.SeasonsFranchiseMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.SeasonsFranchiseCriteria;
import com.firstfuel.fafi.service.SeasonsFranchiseQueryService;

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
 * Test class for the SeasonsFranchiseResource REST controller.
 *
 * @see SeasonsFranchiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class SeasonsFranchiseResourceIntTest {

    @Autowired
    private SeasonsFranchiseRepository seasonsFranchiseRepository;

    @Autowired
    private SeasonsFranchiseMapper seasonsFranchiseMapper;

    @Autowired
    private SeasonsFranchiseService seasonsFranchiseService;

    @Autowired
    private SeasonsFranchiseQueryService seasonsFranchiseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeasonsFranchiseMockMvc;

    private SeasonsFranchise seasonsFranchise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeasonsFranchiseResource seasonsFranchiseResource = new SeasonsFranchiseResource(seasonsFranchiseService, seasonsFranchiseQueryService);
        this.restSeasonsFranchiseMockMvc = MockMvcBuilders.standaloneSetup(seasonsFranchiseResource)
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
    public static SeasonsFranchise createEntity(EntityManager em) {
        SeasonsFranchise seasonsFranchise = new SeasonsFranchise();
        // Add required entity
        Season season = SeasonResourceIntTest.createEntity(em);
        em.persist(season);
        em.flush();
        seasonsFranchise.setSeason(season);
        // Add required entity
        Franchise franchise = FranchiseResourceIntTest.createEntity(em);
        em.persist(franchise);
        em.flush();
        seasonsFranchise.setFranchise(franchise);
        // Add required entity
        Player owner = PlayerResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        seasonsFranchise.setOwner(owner);
        // Add required entity
        Player iconPlayer = PlayerResourceIntTest.createEntity(em);
        em.persist(iconPlayer);
        em.flush();
        seasonsFranchise.setIconPlayer(iconPlayer);
        return seasonsFranchise;
    }

    @Before
    public void initTest() {
        seasonsFranchise = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeasonsFranchise() throws Exception {
        int databaseSizeBeforeCreate = seasonsFranchiseRepository.findAll().size();

        // Create the SeasonsFranchise
        SeasonsFranchiseDTO seasonsFranchiseDTO = seasonsFranchiseMapper.toDto(seasonsFranchise);
        restSeasonsFranchiseMockMvc.perform(post("/api/seasons-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchiseDTO)))
            .andExpect(status().isCreated());

        // Validate the SeasonsFranchise in the database
        List<SeasonsFranchise> seasonsFranchiseList = seasonsFranchiseRepository.findAll();
        assertThat(seasonsFranchiseList).hasSize(databaseSizeBeforeCreate + 1);
        SeasonsFranchise testSeasonsFranchise = seasonsFranchiseList.get(seasonsFranchiseList.size() - 1);
    }

    @Test
    @Transactional
    public void createSeasonsFranchiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seasonsFranchiseRepository.findAll().size();

        // Create the SeasonsFranchise with an existing ID
        seasonsFranchise.setId(1L);
        SeasonsFranchiseDTO seasonsFranchiseDTO = seasonsFranchiseMapper.toDto(seasonsFranchise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeasonsFranchiseMockMvc.perform(post("/api/seasons-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeasonsFranchise in the database
        List<SeasonsFranchise> seasonsFranchiseList = seasonsFranchiseRepository.findAll();
        assertThat(seasonsFranchiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeasonsFranchises() throws Exception {
        // Initialize the database
        seasonsFranchiseRepository.saveAndFlush(seasonsFranchise);

        // Get all the seasonsFranchiseList
        restSeasonsFranchiseMockMvc.perform(get("/api/seasons-franchises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seasonsFranchise.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSeasonsFranchise() throws Exception {
        // Initialize the database
        seasonsFranchiseRepository.saveAndFlush(seasonsFranchise);

        // Get the seasonsFranchise
        restSeasonsFranchiseMockMvc.perform(get("/api/seasons-franchises/{id}", seasonsFranchise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seasonsFranchise.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllSeasonsFranchisesBySeasonIsEqualToSomething() throws Exception {
        // Initialize the database
        Season season = SeasonResourceIntTest.createEntity(em);
        em.persist(season);
        em.flush();
        seasonsFranchise.setSeason(season);
        seasonsFranchiseRepository.saveAndFlush(seasonsFranchise);
        Long seasonId = season.getId();

        // Get all the seasonsFranchiseList where season equals to seasonId
        defaultSeasonsFranchiseShouldBeFound("seasonId.equals=" + seasonId);

        // Get all the seasonsFranchiseList where season equals to seasonId + 1
        defaultSeasonsFranchiseShouldNotBeFound("seasonId.equals=" + (seasonId + 1));
    }


    @Test
    @Transactional
    public void getAllSeasonsFranchisesByFranchiseIsEqualToSomething() throws Exception {
        // Initialize the database
        Franchise franchise = FranchiseResourceIntTest.createEntity(em);
        em.persist(franchise);
        em.flush();
        seasonsFranchise.setFranchise(franchise);
        seasonsFranchiseRepository.saveAndFlush(seasonsFranchise);
        Long franchiseId = franchise.getId();

        // Get all the seasonsFranchiseList where franchise equals to franchiseId
        defaultSeasonsFranchiseShouldBeFound("franchiseId.equals=" + franchiseId);

        // Get all the seasonsFranchiseList where franchise equals to franchiseId + 1
        defaultSeasonsFranchiseShouldNotBeFound("franchiseId.equals=" + (franchiseId + 1));
    }


    @Test
    @Transactional
    public void getAllSeasonsFranchisesByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        Player owner = PlayerResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        seasonsFranchise.setOwner(owner);
        seasonsFranchiseRepository.saveAndFlush(seasonsFranchise);
        Long ownerId = owner.getId();

        // Get all the seasonsFranchiseList where owner equals to ownerId
        defaultSeasonsFranchiseShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the seasonsFranchiseList where owner equals to ownerId + 1
        defaultSeasonsFranchiseShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllSeasonsFranchisesByIconPlayerIsEqualToSomething() throws Exception {
        // Initialize the database
        Player iconPlayer = PlayerResourceIntTest.createEntity(em);
        em.persist(iconPlayer);
        em.flush();
        seasonsFranchise.setIconPlayer(iconPlayer);
        seasonsFranchiseRepository.saveAndFlush(seasonsFranchise);
        Long iconPlayerId = iconPlayer.getId();

        // Get all the seasonsFranchiseList where iconPlayer equals to iconPlayerId
        defaultSeasonsFranchiseShouldBeFound("iconPlayerId.equals=" + iconPlayerId);

        // Get all the seasonsFranchiseList where iconPlayer equals to iconPlayerId + 1
        defaultSeasonsFranchiseShouldNotBeFound("iconPlayerId.equals=" + (iconPlayerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSeasonsFranchiseShouldBeFound(String filter) throws Exception {
        restSeasonsFranchiseMockMvc.perform(get("/api/seasons-franchises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seasonsFranchise.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSeasonsFranchiseShouldNotBeFound(String filter) throws Exception {
        restSeasonsFranchiseMockMvc.perform(get("/api/seasons-franchises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSeasonsFranchise() throws Exception {
        // Get the seasonsFranchise
        restSeasonsFranchiseMockMvc.perform(get("/api/seasons-franchises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeasonsFranchise() throws Exception {
        // Initialize the database
        seasonsFranchiseRepository.saveAndFlush(seasonsFranchise);
        int databaseSizeBeforeUpdate = seasonsFranchiseRepository.findAll().size();

        // Update the seasonsFranchise
        SeasonsFranchise updatedSeasonsFranchise = seasonsFranchiseRepository.findOne(seasonsFranchise.getId());
        // Disconnect from session so that the updates on updatedSeasonsFranchise are not directly saved in db
        em.detach(updatedSeasonsFranchise);
        SeasonsFranchiseDTO seasonsFranchiseDTO = seasonsFranchiseMapper.toDto(updatedSeasonsFranchise);

        restSeasonsFranchiseMockMvc.perform(put("/api/seasons-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchiseDTO)))
            .andExpect(status().isOk());

        // Validate the SeasonsFranchise in the database
        List<SeasonsFranchise> seasonsFranchiseList = seasonsFranchiseRepository.findAll();
        assertThat(seasonsFranchiseList).hasSize(databaseSizeBeforeUpdate);
        SeasonsFranchise testSeasonsFranchise = seasonsFranchiseList.get(seasonsFranchiseList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSeasonsFranchise() throws Exception {
        int databaseSizeBeforeUpdate = seasonsFranchiseRepository.findAll().size();

        // Create the SeasonsFranchise
        SeasonsFranchiseDTO seasonsFranchiseDTO = seasonsFranchiseMapper.toDto(seasonsFranchise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeasonsFranchiseMockMvc.perform(put("/api/seasons-franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchiseDTO)))
            .andExpect(status().isCreated());

        // Validate the SeasonsFranchise in the database
        List<SeasonsFranchise> seasonsFranchiseList = seasonsFranchiseRepository.findAll();
        assertThat(seasonsFranchiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeasonsFranchise() throws Exception {
        // Initialize the database
        seasonsFranchiseRepository.saveAndFlush(seasonsFranchise);
        int databaseSizeBeforeDelete = seasonsFranchiseRepository.findAll().size();

        // Get the seasonsFranchise
        restSeasonsFranchiseMockMvc.perform(delete("/api/seasons-franchises/{id}", seasonsFranchise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SeasonsFranchise> seasonsFranchiseList = seasonsFranchiseRepository.findAll();
        assertThat(seasonsFranchiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeasonsFranchise.class);
        SeasonsFranchise seasonsFranchise1 = new SeasonsFranchise();
        seasonsFranchise1.setId(1L);
        SeasonsFranchise seasonsFranchise2 = new SeasonsFranchise();
        seasonsFranchise2.setId(seasonsFranchise1.getId());
        assertThat(seasonsFranchise1).isEqualTo(seasonsFranchise2);
        seasonsFranchise2.setId(2L);
        assertThat(seasonsFranchise1).isNotEqualTo(seasonsFranchise2);
        seasonsFranchise1.setId(null);
        assertThat(seasonsFranchise1).isNotEqualTo(seasonsFranchise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeasonsFranchiseDTO.class);
        SeasonsFranchiseDTO seasonsFranchiseDTO1 = new SeasonsFranchiseDTO();
        seasonsFranchiseDTO1.setId(1L);
        SeasonsFranchiseDTO seasonsFranchiseDTO2 = new SeasonsFranchiseDTO();
        assertThat(seasonsFranchiseDTO1).isNotEqualTo(seasonsFranchiseDTO2);
        seasonsFranchiseDTO2.setId(seasonsFranchiseDTO1.getId());
        assertThat(seasonsFranchiseDTO1).isEqualTo(seasonsFranchiseDTO2);
        seasonsFranchiseDTO2.setId(2L);
        assertThat(seasonsFranchiseDTO1).isNotEqualTo(seasonsFranchiseDTO2);
        seasonsFranchiseDTO1.setId(null);
        assertThat(seasonsFranchiseDTO1).isNotEqualTo(seasonsFranchiseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seasonsFranchiseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seasonsFranchiseMapper.fromId(null)).isNull();
    }
}
