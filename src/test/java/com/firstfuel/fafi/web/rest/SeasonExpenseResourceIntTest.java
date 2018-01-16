package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.SeasonExpense;
import com.firstfuel.fafi.domain.Season;
import com.firstfuel.fafi.repository.SeasonExpenseRepository;
import com.firstfuel.fafi.service.SeasonExpenseService;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.SeasonExpenseCriteria;
import com.firstfuel.fafi.service.SeasonExpenseQueryService;

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

/**
 * Test class for the SeasonExpenseResource REST controller.
 *
 * @see SeasonExpenseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class SeasonExpenseResourceIntTest {

    private static final LocalDate DEFAULT_INCURRED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INCURRED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    @Autowired
    private SeasonExpenseRepository seasonExpenseRepository;

    @Autowired
    private SeasonExpenseService seasonExpenseService;

    @Autowired
    private SeasonExpenseQueryService seasonExpenseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeasonExpenseMockMvc;

    private SeasonExpense seasonExpense;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeasonExpenseResource seasonExpenseResource = new SeasonExpenseResource(seasonExpenseService, seasonExpenseQueryService);
        this.restSeasonExpenseMockMvc = MockMvcBuilders.standaloneSetup(seasonExpenseResource)
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
    public static SeasonExpense createEntity(EntityManager em) {
        SeasonExpense seasonExpense = new SeasonExpense()
            .incurredDate(DEFAULT_INCURRED_DATE)
            .description(DEFAULT_DESCRIPTION)
            .amount(DEFAULT_AMOUNT);
        // Add required entity
        Season season = SeasonResourceIntTest.createEntity(em);
        em.persist(season);
        em.flush();
        seasonExpense.setSeason(season);
        return seasonExpense;
    }

    @Before
    public void initTest() {
        seasonExpense = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeasonExpense() throws Exception {
        int databaseSizeBeforeCreate = seasonExpenseRepository.findAll().size();

        // Create the SeasonExpense
        restSeasonExpenseMockMvc.perform(post("/api/season-expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonExpense)))
            .andExpect(status().isCreated());

        // Validate the SeasonExpense in the database
        List<SeasonExpense> seasonExpenseList = seasonExpenseRepository.findAll();
        assertThat(seasonExpenseList).hasSize(databaseSizeBeforeCreate + 1);
        SeasonExpense testSeasonExpense = seasonExpenseList.get(seasonExpenseList.size() - 1);
        assertThat(testSeasonExpense.getIncurredDate()).isEqualTo(DEFAULT_INCURRED_DATE);
        assertThat(testSeasonExpense.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSeasonExpense.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createSeasonExpenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seasonExpenseRepository.findAll().size();

        // Create the SeasonExpense with an existing ID
        seasonExpense.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeasonExpenseMockMvc.perform(post("/api/season-expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonExpense)))
            .andExpect(status().isBadRequest());

        // Validate the SeasonExpense in the database
        List<SeasonExpense> seasonExpenseList = seasonExpenseRepository.findAll();
        assertThat(seasonExpenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIncurredDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = seasonExpenseRepository.findAll().size();
        // set the field null
        seasonExpense.setIncurredDate(null);

        // Create the SeasonExpense, which fails.

        restSeasonExpenseMockMvc.perform(post("/api/season-expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonExpense)))
            .andExpect(status().isBadRequest());

        List<SeasonExpense> seasonExpenseList = seasonExpenseRepository.findAll();
        assertThat(seasonExpenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = seasonExpenseRepository.findAll().size();
        // set the field null
        seasonExpense.setDescription(null);

        // Create the SeasonExpense, which fails.

        restSeasonExpenseMockMvc.perform(post("/api/season-expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonExpense)))
            .andExpect(status().isBadRequest());

        List<SeasonExpense> seasonExpenseList = seasonExpenseRepository.findAll();
        assertThat(seasonExpenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = seasonExpenseRepository.findAll().size();
        // set the field null
        seasonExpense.setAmount(null);

        // Create the SeasonExpense, which fails.

        restSeasonExpenseMockMvc.perform(post("/api/season-expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonExpense)))
            .andExpect(status().isBadRequest());

        List<SeasonExpense> seasonExpenseList = seasonExpenseRepository.findAll();
        assertThat(seasonExpenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeasonExpenses() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList
        restSeasonExpenseMockMvc.perform(get("/api/season-expenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seasonExpense.getId().intValue())))
            .andExpect(jsonPath("$.[*].incurredDate").value(hasItem(DEFAULT_INCURRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getSeasonExpense() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get the seasonExpense
        restSeasonExpenseMockMvc.perform(get("/api/season-expenses/{id}", seasonExpense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seasonExpense.getId().intValue()))
            .andExpect(jsonPath("$.incurredDate").value(DEFAULT_INCURRED_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByIncurredDateIsEqualToSomething() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where incurredDate equals to DEFAULT_INCURRED_DATE
        defaultSeasonExpenseShouldBeFound("incurredDate.equals=" + DEFAULT_INCURRED_DATE);

        // Get all the seasonExpenseList where incurredDate equals to UPDATED_INCURRED_DATE
        defaultSeasonExpenseShouldNotBeFound("incurredDate.equals=" + UPDATED_INCURRED_DATE);
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByIncurredDateIsInShouldWork() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where incurredDate in DEFAULT_INCURRED_DATE or UPDATED_INCURRED_DATE
        defaultSeasonExpenseShouldBeFound("incurredDate.in=" + DEFAULT_INCURRED_DATE + "," + UPDATED_INCURRED_DATE);

        // Get all the seasonExpenseList where incurredDate equals to UPDATED_INCURRED_DATE
        defaultSeasonExpenseShouldNotBeFound("incurredDate.in=" + UPDATED_INCURRED_DATE);
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByIncurredDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where incurredDate is not null
        defaultSeasonExpenseShouldBeFound("incurredDate.specified=true");

        // Get all the seasonExpenseList where incurredDate is null
        defaultSeasonExpenseShouldNotBeFound("incurredDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByIncurredDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where incurredDate greater than or equals to DEFAULT_INCURRED_DATE
        defaultSeasonExpenseShouldBeFound("incurredDate.greaterOrEqualThan=" + DEFAULT_INCURRED_DATE);

        // Get all the seasonExpenseList where incurredDate greater than or equals to UPDATED_INCURRED_DATE
        defaultSeasonExpenseShouldNotBeFound("incurredDate.greaterOrEqualThan=" + UPDATED_INCURRED_DATE);
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByIncurredDateIsLessThanSomething() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where incurredDate less than or equals to DEFAULT_INCURRED_DATE
        defaultSeasonExpenseShouldNotBeFound("incurredDate.lessThan=" + DEFAULT_INCURRED_DATE);

        // Get all the seasonExpenseList where incurredDate less than or equals to UPDATED_INCURRED_DATE
        defaultSeasonExpenseShouldBeFound("incurredDate.lessThan=" + UPDATED_INCURRED_DATE);
    }


    @Test
    @Transactional
    public void getAllSeasonExpensesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where description equals to DEFAULT_DESCRIPTION
        defaultSeasonExpenseShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the seasonExpenseList where description equals to UPDATED_DESCRIPTION
        defaultSeasonExpenseShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSeasonExpenseShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the seasonExpenseList where description equals to UPDATED_DESCRIPTION
        defaultSeasonExpenseShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where description is not null
        defaultSeasonExpenseShouldBeFound("description.specified=true");

        // Get all the seasonExpenseList where description is null
        defaultSeasonExpenseShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where amount equals to DEFAULT_AMOUNT
        defaultSeasonExpenseShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the seasonExpenseList where amount equals to UPDATED_AMOUNT
        defaultSeasonExpenseShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultSeasonExpenseShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the seasonExpenseList where amount equals to UPDATED_AMOUNT
        defaultSeasonExpenseShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        seasonExpenseRepository.saveAndFlush(seasonExpense);

        // Get all the seasonExpenseList where amount is not null
        defaultSeasonExpenseShouldBeFound("amount.specified=true");

        // Get all the seasonExpenseList where amount is null
        defaultSeasonExpenseShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSeasonExpensesBySeasonIsEqualToSomething() throws Exception {
        // Initialize the database
        Season season = SeasonResourceIntTest.createEntity(em);
        em.persist(season);
        em.flush();
        seasonExpense.setSeason(season);
        seasonExpenseRepository.saveAndFlush(seasonExpense);
        Long seasonId = season.getId();

        // Get all the seasonExpenseList where season equals to seasonId
        defaultSeasonExpenseShouldBeFound("seasonId.equals=" + seasonId);

        // Get all the seasonExpenseList where season equals to seasonId + 1
        defaultSeasonExpenseShouldNotBeFound("seasonId.equals=" + (seasonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSeasonExpenseShouldBeFound(String filter) throws Exception {
        restSeasonExpenseMockMvc.perform(get("/api/season-expenses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seasonExpense.getId().intValue())))
            .andExpect(jsonPath("$.[*].incurredDate").value(hasItem(DEFAULT_INCURRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSeasonExpenseShouldNotBeFound(String filter) throws Exception {
        restSeasonExpenseMockMvc.perform(get("/api/season-expenses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSeasonExpense() throws Exception {
        // Get the seasonExpense
        restSeasonExpenseMockMvc.perform(get("/api/season-expenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeasonExpense() throws Exception {
        // Initialize the database
        seasonExpenseService.save(seasonExpense);

        int databaseSizeBeforeUpdate = seasonExpenseRepository.findAll().size();

        // Update the seasonExpense
        SeasonExpense updatedSeasonExpense = seasonExpenseRepository.findOne(seasonExpense.getId());
        // Disconnect from session so that the updates on updatedSeasonExpense are not directly saved in db
        em.detach(updatedSeasonExpense);
        updatedSeasonExpense
            .incurredDate(UPDATED_INCURRED_DATE)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT);

        restSeasonExpenseMockMvc.perform(put("/api/season-expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeasonExpense)))
            .andExpect(status().isOk());

        // Validate the SeasonExpense in the database
        List<SeasonExpense> seasonExpenseList = seasonExpenseRepository.findAll();
        assertThat(seasonExpenseList).hasSize(databaseSizeBeforeUpdate);
        SeasonExpense testSeasonExpense = seasonExpenseList.get(seasonExpenseList.size() - 1);
        assertThat(testSeasonExpense.getIncurredDate()).isEqualTo(UPDATED_INCURRED_DATE);
        assertThat(testSeasonExpense.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSeasonExpense.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingSeasonExpense() throws Exception {
        int databaseSizeBeforeUpdate = seasonExpenseRepository.findAll().size();

        // Create the SeasonExpense

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeasonExpenseMockMvc.perform(put("/api/season-expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonExpense)))
            .andExpect(status().isCreated());

        // Validate the SeasonExpense in the database
        List<SeasonExpense> seasonExpenseList = seasonExpenseRepository.findAll();
        assertThat(seasonExpenseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeasonExpense() throws Exception {
        // Initialize the database
        seasonExpenseService.save(seasonExpense);

        int databaseSizeBeforeDelete = seasonExpenseRepository.findAll().size();

        // Get the seasonExpense
        restSeasonExpenseMockMvc.perform(delete("/api/season-expenses/{id}", seasonExpense.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SeasonExpense> seasonExpenseList = seasonExpenseRepository.findAll();
        assertThat(seasonExpenseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeasonExpense.class);
        SeasonExpense seasonExpense1 = new SeasonExpense();
        seasonExpense1.setId(1L);
        SeasonExpense seasonExpense2 = new SeasonExpense();
        seasonExpense2.setId(seasonExpense1.getId());
        assertThat(seasonExpense1).isEqualTo(seasonExpense2);
        seasonExpense2.setId(2L);
        assertThat(seasonExpense1).isNotEqualTo(seasonExpense2);
        seasonExpense1.setId(null);
        assertThat(seasonExpense1).isNotEqualTo(seasonExpense2);
    }
}
