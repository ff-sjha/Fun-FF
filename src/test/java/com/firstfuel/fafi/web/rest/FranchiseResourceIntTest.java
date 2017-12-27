package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.Franchise;
import com.firstfuel.fafi.repository.FranchiseRepository;
import com.firstfuel.fafi.service.FranchiseService;
import com.firstfuel.fafi.service.dto.FranchiseDTO;
import com.firstfuel.fafi.service.mapper.FranchiseMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.FranchiseCriteria;
import com.firstfuel.fafi.service.FranchiseQueryService;

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
 * Test class for the FranchiseResource REST controller.
 *
 * @see FranchiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class FranchiseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private FranchiseMapper franchiseMapper;

    @Autowired
    private FranchiseService franchiseService;

    @Autowired
    private FranchiseQueryService franchiseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFranchiseMockMvc;

    private Franchise franchise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FranchiseResource franchiseResource = new FranchiseResource(franchiseService, franchiseQueryService);
        this.restFranchiseMockMvc = MockMvcBuilders.standaloneSetup(franchiseResource)
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
    public static Franchise createEntity(EntityManager em) {
        Franchise franchise = new Franchise()
            .name(DEFAULT_NAME)
            .logoPath(DEFAULT_LOGO_PATH)
            .active(DEFAULT_ACTIVE);
        return franchise;
    }

    @Before
    public void initTest() {
        franchise = createEntity(em);
    }

    @Test
    @Transactional
    public void createFranchise() throws Exception {
        int databaseSizeBeforeCreate = franchiseRepository.findAll().size();

        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);
        restFranchiseMockMvc.perform(post("/api/franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(franchiseDTO)))
            .andExpect(status().isCreated());

        // Validate the Franchise in the database
        List<Franchise> franchiseList = franchiseRepository.findAll();
        assertThat(franchiseList).hasSize(databaseSizeBeforeCreate + 1);
        Franchise testFranchise = franchiseList.get(franchiseList.size() - 1);
        assertThat(testFranchise.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFranchise.getLogoPath()).isEqualTo(DEFAULT_LOGO_PATH);
        assertThat(testFranchise.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createFranchiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = franchiseRepository.findAll().size();

        // Create the Franchise with an existing ID
        franchise.setId(1L);
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseMockMvc.perform(post("/api/franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        List<Franchise> franchiseList = franchiseRepository.findAll();
        assertThat(franchiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = franchiseRepository.findAll().size();
        // set the field null
        franchise.setName(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc.perform(post("/api/franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        List<Franchise> franchiseList = franchiseRepository.findAll();
        assertThat(franchiseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = franchiseRepository.findAll().size();
        // set the field null
        franchise.setActive(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc.perform(post("/api/franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        List<Franchise> franchiseList = franchiseRepository.findAll();
        assertThat(franchiseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFranchises() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList
        restFranchiseMockMvc.perform(get("/api/franchises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchise.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].logoPath").value(hasItem(DEFAULT_LOGO_PATH.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getFranchise() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get the franchise
        restFranchiseMockMvc.perform(get("/api/franchises/{id}", franchise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(franchise.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.logoPath").value(DEFAULT_LOGO_PATH.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllFranchisesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where name equals to DEFAULT_NAME
        defaultFranchiseShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the franchiseList where name equals to UPDATED_NAME
        defaultFranchiseShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFranchisesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFranchiseShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the franchiseList where name equals to UPDATED_NAME
        defaultFranchiseShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFranchisesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where name is not null
        defaultFranchiseShouldBeFound("name.specified=true");

        // Get all the franchiseList where name is null
        defaultFranchiseShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllFranchisesByLogoPathIsEqualToSomething() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where logoPath equals to DEFAULT_LOGO_PATH
        defaultFranchiseShouldBeFound("logoPath.equals=" + DEFAULT_LOGO_PATH);

        // Get all the franchiseList where logoPath equals to UPDATED_LOGO_PATH
        defaultFranchiseShouldNotBeFound("logoPath.equals=" + UPDATED_LOGO_PATH);
    }

    @Test
    @Transactional
    public void getAllFranchisesByLogoPathIsInShouldWork() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where logoPath in DEFAULT_LOGO_PATH or UPDATED_LOGO_PATH
        defaultFranchiseShouldBeFound("logoPath.in=" + DEFAULT_LOGO_PATH + "," + UPDATED_LOGO_PATH);

        // Get all the franchiseList where logoPath equals to UPDATED_LOGO_PATH
        defaultFranchiseShouldNotBeFound("logoPath.in=" + UPDATED_LOGO_PATH);
    }

    @Test
    @Transactional
    public void getAllFranchisesByLogoPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where logoPath is not null
        defaultFranchiseShouldBeFound("logoPath.specified=true");

        // Get all the franchiseList where logoPath is null
        defaultFranchiseShouldNotBeFound("logoPath.specified=false");
    }

    @Test
    @Transactional
    public void getAllFranchisesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where active equals to DEFAULT_ACTIVE
        defaultFranchiseShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the franchiseList where active equals to UPDATED_ACTIVE
        defaultFranchiseShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllFranchisesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultFranchiseShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the franchiseList where active equals to UPDATED_ACTIVE
        defaultFranchiseShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllFranchisesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where active is not null
        defaultFranchiseShouldBeFound("active.specified=true");

        // Get all the franchiseList where active is null
        defaultFranchiseShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFranchiseShouldBeFound(String filter) throws Exception {
        restFranchiseMockMvc.perform(get("/api/franchises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchise.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].logoPath").value(hasItem(DEFAULT_LOGO_PATH.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFranchiseShouldNotBeFound(String filter) throws Exception {
        restFranchiseMockMvc.perform(get("/api/franchises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingFranchise() throws Exception {
        // Get the franchise
        restFranchiseMockMvc.perform(get("/api/franchises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFranchise() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);
        int databaseSizeBeforeUpdate = franchiseRepository.findAll().size();

        // Update the franchise
        Franchise updatedFranchise = franchiseRepository.findOne(franchise.getId());
        // Disconnect from session so that the updates on updatedFranchise are not directly saved in db
        em.detach(updatedFranchise);
        updatedFranchise
            .name(UPDATED_NAME)
            .logoPath(UPDATED_LOGO_PATH)
            .active(UPDATED_ACTIVE);
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(updatedFranchise);

        restFranchiseMockMvc.perform(put("/api/franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(franchiseDTO)))
            .andExpect(status().isOk());

        // Validate the Franchise in the database
        List<Franchise> franchiseList = franchiseRepository.findAll();
        assertThat(franchiseList).hasSize(databaseSizeBeforeUpdate);
        Franchise testFranchise = franchiseList.get(franchiseList.size() - 1);
        assertThat(testFranchise.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFranchise.getLogoPath()).isEqualTo(UPDATED_LOGO_PATH);
        assertThat(testFranchise.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingFranchise() throws Exception {
        int databaseSizeBeforeUpdate = franchiseRepository.findAll().size();

        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFranchiseMockMvc.perform(put("/api/franchises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(franchiseDTO)))
            .andExpect(status().isCreated());

        // Validate the Franchise in the database
        List<Franchise> franchiseList = franchiseRepository.findAll();
        assertThat(franchiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFranchise() throws Exception {
        // Initialize the database
        franchiseRepository.saveAndFlush(franchise);
        int databaseSizeBeforeDelete = franchiseRepository.findAll().size();

        // Get the franchise
        restFranchiseMockMvc.perform(delete("/api/franchises/{id}", franchise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Franchise> franchiseList = franchiseRepository.findAll();
        assertThat(franchiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Franchise.class);
        Franchise franchise1 = new Franchise();
        franchise1.setId(1L);
        Franchise franchise2 = new Franchise();
        franchise2.setId(franchise1.getId());
        assertThat(franchise1).isEqualTo(franchise2);
        franchise2.setId(2L);
        assertThat(franchise1).isNotEqualTo(franchise2);
        franchise1.setId(null);
        assertThat(franchise1).isNotEqualTo(franchise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseDTO.class);
        FranchiseDTO franchiseDTO1 = new FranchiseDTO();
        franchiseDTO1.setId(1L);
        FranchiseDTO franchiseDTO2 = new FranchiseDTO();
        assertThat(franchiseDTO1).isNotEqualTo(franchiseDTO2);
        franchiseDTO2.setId(franchiseDTO1.getId());
        assertThat(franchiseDTO1).isEqualTo(franchiseDTO2);
        franchiseDTO2.setId(2L);
        assertThat(franchiseDTO1).isNotEqualTo(franchiseDTO2);
        franchiseDTO1.setId(null);
        assertThat(franchiseDTO1).isNotEqualTo(franchiseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(franchiseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(franchiseMapper.fromId(null)).isNull();
    }
}
