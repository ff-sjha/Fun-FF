package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.SportInfo;
import com.firstfuel.fafi.repository.SportInfoRepository;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.firstfuel.fafi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.firstfuel.fafi.domain.enumeration.Games;
/**
 * Test class for the SportInfoResource REST controller.
 *
 * @see SportInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class SportInfoResourceIntTest {

    private static final Games DEFAULT_GAME = Games.FOOTBALL;
    private static final Games UPDATED_GAME = Games.CHESS;

    private static final String DEFAULT_RULES = "AAAAAAAAAA";
    private static final String UPDATED_RULES = "BBBBBBBBBB";

    private static final String DEFAULT_SCORING_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SCORING_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_POINTS_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_POINTS_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_MATCH_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_MATCH_SYSTEM = "BBBBBBBBBB";

    @Autowired
    private SportInfoRepository sportInfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSportInfoMockMvc;

    private SportInfo sportInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SportInfoResource sportInfoResource = new SportInfoResource(sportInfoRepository);
        this.restSportInfoMockMvc = MockMvcBuilders.standaloneSetup(sportInfoResource)
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
    public static SportInfo createEntity(EntityManager em) {
        SportInfo sportInfo = new SportInfo()
            .game(DEFAULT_GAME)
            .rules(DEFAULT_RULES)
            .scoring_system(DEFAULT_SCORING_SYSTEM)
            .points_system(DEFAULT_POINTS_SYSTEM)
            .match_system(DEFAULT_MATCH_SYSTEM);
        return sportInfo;
    }

    @Before
    public void initTest() {
        sportInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createSportInfo() throws Exception {
        int databaseSizeBeforeCreate = sportInfoRepository.findAll().size();

        // Create the SportInfo
        restSportInfoMockMvc.perform(post("/api/sport-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportInfo)))
            .andExpect(status().isCreated());

        // Validate the SportInfo in the database
        List<SportInfo> sportInfoList = sportInfoRepository.findAll();
        assertThat(sportInfoList).hasSize(databaseSizeBeforeCreate + 1);
        SportInfo testSportInfo = sportInfoList.get(sportInfoList.size() - 1);
        assertThat(testSportInfo.getGame()).isEqualTo(DEFAULT_GAME);
        assertThat(testSportInfo.getRules()).isEqualTo(DEFAULT_RULES);
        assertThat(testSportInfo.getScoring_system()).isEqualTo(DEFAULT_SCORING_SYSTEM);
        assertThat(testSportInfo.getPoints_system()).isEqualTo(DEFAULT_POINTS_SYSTEM);
        assertThat(testSportInfo.getMatch_system()).isEqualTo(DEFAULT_MATCH_SYSTEM);
    }

    @Test
    @Transactional
    public void createSportInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sportInfoRepository.findAll().size();

        // Create the SportInfo with an existing ID
        sportInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportInfoMockMvc.perform(post("/api/sport-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportInfo)))
            .andExpect(status().isBadRequest());

        // Validate the SportInfo in the database
        List<SportInfo> sportInfoList = sportInfoRepository.findAll();
        assertThat(sportInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportInfoRepository.findAll().size();
        // set the field null
        sportInfo.setGame(null);

        // Create the SportInfo, which fails.

        restSportInfoMockMvc.perform(post("/api/sport-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportInfo)))
            .andExpect(status().isBadRequest());

        List<SportInfo> sportInfoList = sportInfoRepository.findAll();
        assertThat(sportInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSportInfos() throws Exception {
        // Initialize the database
        sportInfoRepository.saveAndFlush(sportInfo);

        // Get all the sportInfoList
        restSportInfoMockMvc.perform(get("/api/sport-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())))
            .andExpect(jsonPath("$.[*].rules").value(hasItem(DEFAULT_RULES.toString())))
            .andExpect(jsonPath("$.[*].scoring_system").value(hasItem(DEFAULT_SCORING_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].points_system").value(hasItem(DEFAULT_POINTS_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].match_system").value(hasItem(DEFAULT_MATCH_SYSTEM.toString())));
    }

    @Test
    @Transactional
    public void getSportInfo() throws Exception {
        // Initialize the database
        sportInfoRepository.saveAndFlush(sportInfo);

        // Get the sportInfo
        restSportInfoMockMvc.perform(get("/api/sport-infos/{id}", sportInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sportInfo.getId().intValue()))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()))
            .andExpect(jsonPath("$.rules").value(DEFAULT_RULES.toString()))
            .andExpect(jsonPath("$.scoring_system").value(DEFAULT_SCORING_SYSTEM.toString()))
            .andExpect(jsonPath("$.points_system").value(DEFAULT_POINTS_SYSTEM.toString()))
            .andExpect(jsonPath("$.match_system").value(DEFAULT_MATCH_SYSTEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSportInfo() throws Exception {
        // Get the sportInfo
        restSportInfoMockMvc.perform(get("/api/sport-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSportInfo() throws Exception {
        // Initialize the database
        sportInfoRepository.saveAndFlush(sportInfo);
        int databaseSizeBeforeUpdate = sportInfoRepository.findAll().size();

        // Update the sportInfo
        SportInfo updatedSportInfo = sportInfoRepository.findOne(sportInfo.getId());
        // Disconnect from session so that the updates on updatedSportInfo are not directly saved in db
        em.detach(updatedSportInfo);
        updatedSportInfo
            .game(UPDATED_GAME)
            .rules(UPDATED_RULES)
            .scoring_system(UPDATED_SCORING_SYSTEM)
            .points_system(UPDATED_POINTS_SYSTEM)
            .match_system(UPDATED_MATCH_SYSTEM);

        restSportInfoMockMvc.perform(put("/api/sport-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSportInfo)))
            .andExpect(status().isOk());

        // Validate the SportInfo in the database
        List<SportInfo> sportInfoList = sportInfoRepository.findAll();
        assertThat(sportInfoList).hasSize(databaseSizeBeforeUpdate);
        SportInfo testSportInfo = sportInfoList.get(sportInfoList.size() - 1);
        assertThat(testSportInfo.getGame()).isEqualTo(UPDATED_GAME);
        assertThat(testSportInfo.getRules()).isEqualTo(UPDATED_RULES);
        assertThat(testSportInfo.getScoring_system()).isEqualTo(UPDATED_SCORING_SYSTEM);
        assertThat(testSportInfo.getPoints_system()).isEqualTo(UPDATED_POINTS_SYSTEM);
        assertThat(testSportInfo.getMatch_system()).isEqualTo(UPDATED_MATCH_SYSTEM);
    }

    @Test
    @Transactional
    public void updateNonExistingSportInfo() throws Exception {
        int databaseSizeBeforeUpdate = sportInfoRepository.findAll().size();

        // Create the SportInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSportInfoMockMvc.perform(put("/api/sport-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportInfo)))
            .andExpect(status().isCreated());

        // Validate the SportInfo in the database
        List<SportInfo> sportInfoList = sportInfoRepository.findAll();
        assertThat(sportInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSportInfo() throws Exception {
        // Initialize the database
        sportInfoRepository.saveAndFlush(sportInfo);
        int databaseSizeBeforeDelete = sportInfoRepository.findAll().size();

        // Get the sportInfo
        restSportInfoMockMvc.perform(delete("/api/sport-infos/{id}", sportInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SportInfo> sportInfoList = sportInfoRepository.findAll();
        assertThat(sportInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SportInfo.class);
        SportInfo sportInfo1 = new SportInfo();
        sportInfo1.setId(1L);
        SportInfo sportInfo2 = new SportInfo();
        sportInfo2.setId(sportInfo1.getId());
        assertThat(sportInfo1).isEqualTo(sportInfo2);
        sportInfo2.setId(2L);
        assertThat(sportInfo1).isNotEqualTo(sportInfo2);
        sportInfo1.setId(null);
        assertThat(sportInfo1).isNotEqualTo(sportInfo2);
    }
}
