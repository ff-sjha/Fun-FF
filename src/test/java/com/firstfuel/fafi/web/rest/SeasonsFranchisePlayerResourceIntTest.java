package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.SeasonsFranchisePlayer;
import com.firstfuel.fafi.domain.SeasonsFranchise;
import com.firstfuel.fafi.domain.Player;
import com.firstfuel.fafi.repository.SeasonsFranchisePlayerRepository;
import com.firstfuel.fafi.service.SeasonsFranchisePlayerService;
import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerDTO;
import com.firstfuel.fafi.service.mapper.SeasonsFranchisePlayerMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.SeasonsFranchisePlayerCriteria;
import com.firstfuel.fafi.service.SeasonsFranchisePlayerQueryService;

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
 * Test class for the SeasonsFranchisePlayerResource REST controller.
 *
 * @see SeasonsFranchisePlayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class SeasonsFranchisePlayerResourceIntTest {

    private static final Double DEFAULT_BID_PRICE = 1D;
    private static final Double UPDATED_BID_PRICE = 2D;

    @Autowired
    private SeasonsFranchisePlayerRepository seasonsFranchisePlayerRepository;

    @Autowired
    private SeasonsFranchisePlayerMapper seasonsFranchisePlayerMapper;

    @Autowired
    private SeasonsFranchisePlayerService seasonsFranchisePlayerService;

    @Autowired
    private SeasonsFranchisePlayerQueryService seasonsFranchisePlayerQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeasonsFranchisePlayerMockMvc;

    private SeasonsFranchisePlayer seasonsFranchisePlayer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeasonsFranchisePlayerResource seasonsFranchisePlayerResource = new SeasonsFranchisePlayerResource(seasonsFranchisePlayerService, seasonsFranchisePlayerQueryService);
        this.restSeasonsFranchisePlayerMockMvc = MockMvcBuilders.standaloneSetup(seasonsFranchisePlayerResource)
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
    public static SeasonsFranchisePlayer createEntity(EntityManager em) {
        SeasonsFranchisePlayer seasonsFranchisePlayer = new SeasonsFranchisePlayer()
            .bidPrice(DEFAULT_BID_PRICE);
        // Add required entity
        SeasonsFranchise seasonsFranchise = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(seasonsFranchise);
        em.flush();
        seasonsFranchisePlayer.setSeasonsFranchise(seasonsFranchise);
        // Add required entity
        Player player = PlayerResourceIntTest.createEntity(em);
        em.persist(player);
        em.flush();
        seasonsFranchisePlayer.setPlayer(player);
        return seasonsFranchisePlayer;
    }

    @Before
    public void initTest() {
        seasonsFranchisePlayer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeasonsFranchisePlayer() throws Exception {
        int databaseSizeBeforeCreate = seasonsFranchisePlayerRepository.findAll().size();

        // Create the SeasonsFranchisePlayer
        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO = seasonsFranchisePlayerMapper.toDto(seasonsFranchisePlayer);
        restSeasonsFranchisePlayerMockMvc.perform(post("/api/seasons-franchise-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchisePlayerDTO)))
            .andExpect(status().isCreated());

        // Validate the SeasonsFranchisePlayer in the database
        List<SeasonsFranchisePlayer> seasonsFranchisePlayerList = seasonsFranchisePlayerRepository.findAll();
        assertThat(seasonsFranchisePlayerList).hasSize(databaseSizeBeforeCreate + 1);
        SeasonsFranchisePlayer testSeasonsFranchisePlayer = seasonsFranchisePlayerList.get(seasonsFranchisePlayerList.size() - 1);
        assertThat(testSeasonsFranchisePlayer.getBidPrice()).isEqualTo(DEFAULT_BID_PRICE);
    }

    @Test
    @Transactional
    public void createSeasonsFranchisePlayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seasonsFranchisePlayerRepository.findAll().size();

        // Create the SeasonsFranchisePlayer with an existing ID
        seasonsFranchisePlayer.setId(1L);
        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO = seasonsFranchisePlayerMapper.toDto(seasonsFranchisePlayer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeasonsFranchisePlayerMockMvc.perform(post("/api/seasons-franchise-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchisePlayerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeasonsFranchisePlayer in the database
        List<SeasonsFranchisePlayer> seasonsFranchisePlayerList = seasonsFranchisePlayerRepository.findAll();
        assertThat(seasonsFranchisePlayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBidPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = seasonsFranchisePlayerRepository.findAll().size();
        // set the field null
        seasonsFranchisePlayer.setBidPrice(null);

        // Create the SeasonsFranchisePlayer, which fails.
        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO = seasonsFranchisePlayerMapper.toDto(seasonsFranchisePlayer);

        restSeasonsFranchisePlayerMockMvc.perform(post("/api/seasons-franchise-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchisePlayerDTO)))
            .andExpect(status().isBadRequest());

        List<SeasonsFranchisePlayer> seasonsFranchisePlayerList = seasonsFranchisePlayerRepository.findAll();
        assertThat(seasonsFranchisePlayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeasonsFranchisePlayers() throws Exception {
        // Initialize the database
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);

        // Get all the seasonsFranchisePlayerList
        restSeasonsFranchisePlayerMockMvc.perform(get("/api/seasons-franchise-players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seasonsFranchisePlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].bidPrice").value(hasItem(DEFAULT_BID_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getSeasonsFranchisePlayer() throws Exception {
        // Initialize the database
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);

        // Get the seasonsFranchisePlayer
        restSeasonsFranchisePlayerMockMvc.perform(get("/api/seasons-franchise-players/{id}", seasonsFranchisePlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seasonsFranchisePlayer.getId().intValue()))
            .andExpect(jsonPath("$.bidPrice").value(DEFAULT_BID_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllSeasonsFranchisePlayersByBidPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);

        // Get all the seasonsFranchisePlayerList where bidPrice equals to DEFAULT_BID_PRICE
        defaultSeasonsFranchisePlayerShouldBeFound("bidPrice.equals=" + DEFAULT_BID_PRICE);

        // Get all the seasonsFranchisePlayerList where bidPrice equals to UPDATED_BID_PRICE
        defaultSeasonsFranchisePlayerShouldNotBeFound("bidPrice.equals=" + UPDATED_BID_PRICE);
    }

    @Test
    @Transactional
    public void getAllSeasonsFranchisePlayersByBidPriceIsInShouldWork() throws Exception {
        // Initialize the database
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);

        // Get all the seasonsFranchisePlayerList where bidPrice in DEFAULT_BID_PRICE or UPDATED_BID_PRICE
        defaultSeasonsFranchisePlayerShouldBeFound("bidPrice.in=" + DEFAULT_BID_PRICE + "," + UPDATED_BID_PRICE);

        // Get all the seasonsFranchisePlayerList where bidPrice equals to UPDATED_BID_PRICE
        defaultSeasonsFranchisePlayerShouldNotBeFound("bidPrice.in=" + UPDATED_BID_PRICE);
    }

    @Test
    @Transactional
    public void getAllSeasonsFranchisePlayersByBidPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);

        // Get all the seasonsFranchisePlayerList where bidPrice is not null
        defaultSeasonsFranchisePlayerShouldBeFound("bidPrice.specified=true");

        // Get all the seasonsFranchisePlayerList where bidPrice is null
        defaultSeasonsFranchisePlayerShouldNotBeFound("bidPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllSeasonsFranchisePlayersBySeasonsFranchiseIsEqualToSomething() throws Exception {
        // Initialize the database
        SeasonsFranchise seasonsFranchise = SeasonsFranchiseResourceIntTest.createEntity(em);
        em.persist(seasonsFranchise);
        em.flush();
        seasonsFranchisePlayer.setSeasonsFranchise(seasonsFranchise);
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);
        Long seasonsFranchiseId = seasonsFranchise.getId();

        // Get all the seasonsFranchisePlayerList where seasonsFranchise equals to seasonsFranchiseId
        defaultSeasonsFranchisePlayerShouldBeFound("seasonsFranchiseId.equals=" + seasonsFranchiseId);

        // Get all the seasonsFranchisePlayerList where seasonsFranchise equals to seasonsFranchiseId + 1
        defaultSeasonsFranchisePlayerShouldNotBeFound("seasonsFranchiseId.equals=" + (seasonsFranchiseId + 1));
    }


    @Test
    @Transactional
    public void getAllSeasonsFranchisePlayersByPlayerIsEqualToSomething() throws Exception {
        // Initialize the database
        Player player = PlayerResourceIntTest.createEntity(em);
        em.persist(player);
        em.flush();
        seasonsFranchisePlayer.setPlayer(player);
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);
        Long playerId = player.getId();

        // Get all the seasonsFranchisePlayerList where player equals to playerId
        defaultSeasonsFranchisePlayerShouldBeFound("playerId.equals=" + playerId);

        // Get all the seasonsFranchisePlayerList where player equals to playerId + 1
        defaultSeasonsFranchisePlayerShouldNotBeFound("playerId.equals=" + (playerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSeasonsFranchisePlayerShouldBeFound(String filter) throws Exception {
        restSeasonsFranchisePlayerMockMvc.perform(get("/api/seasons-franchise-players?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seasonsFranchisePlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].bidPrice").value(hasItem(DEFAULT_BID_PRICE.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSeasonsFranchisePlayerShouldNotBeFound(String filter) throws Exception {
        restSeasonsFranchisePlayerMockMvc.perform(get("/api/seasons-franchise-players?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSeasonsFranchisePlayer() throws Exception {
        // Get the seasonsFranchisePlayer
        restSeasonsFranchisePlayerMockMvc.perform(get("/api/seasons-franchise-players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeasonsFranchisePlayer() throws Exception {
        // Initialize the database
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);
        int databaseSizeBeforeUpdate = seasonsFranchisePlayerRepository.findAll().size();

        // Update the seasonsFranchisePlayer
        SeasonsFranchisePlayer updatedSeasonsFranchisePlayer = seasonsFranchisePlayerRepository.findOne(seasonsFranchisePlayer.getId());
        // Disconnect from session so that the updates on updatedSeasonsFranchisePlayer are not directly saved in db
        em.detach(updatedSeasonsFranchisePlayer);
        updatedSeasonsFranchisePlayer
            .bidPrice(UPDATED_BID_PRICE);
        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO = seasonsFranchisePlayerMapper.toDto(updatedSeasonsFranchisePlayer);

        restSeasonsFranchisePlayerMockMvc.perform(put("/api/seasons-franchise-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchisePlayerDTO)))
            .andExpect(status().isOk());

        // Validate the SeasonsFranchisePlayer in the database
        List<SeasonsFranchisePlayer> seasonsFranchisePlayerList = seasonsFranchisePlayerRepository.findAll();
        assertThat(seasonsFranchisePlayerList).hasSize(databaseSizeBeforeUpdate);
        SeasonsFranchisePlayer testSeasonsFranchisePlayer = seasonsFranchisePlayerList.get(seasonsFranchisePlayerList.size() - 1);
        assertThat(testSeasonsFranchisePlayer.getBidPrice()).isEqualTo(UPDATED_BID_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeasonsFranchisePlayer() throws Exception {
        int databaseSizeBeforeUpdate = seasonsFranchisePlayerRepository.findAll().size();

        // Create the SeasonsFranchisePlayer
        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO = seasonsFranchisePlayerMapper.toDto(seasonsFranchisePlayer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeasonsFranchisePlayerMockMvc.perform(put("/api/seasons-franchise-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seasonsFranchisePlayerDTO)))
            .andExpect(status().isCreated());

        // Validate the SeasonsFranchisePlayer in the database
        List<SeasonsFranchisePlayer> seasonsFranchisePlayerList = seasonsFranchisePlayerRepository.findAll();
        assertThat(seasonsFranchisePlayerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeasonsFranchisePlayer() throws Exception {
        // Initialize the database
        seasonsFranchisePlayerRepository.saveAndFlush(seasonsFranchisePlayer);
        int databaseSizeBeforeDelete = seasonsFranchisePlayerRepository.findAll().size();

        // Get the seasonsFranchisePlayer
        restSeasonsFranchisePlayerMockMvc.perform(delete("/api/seasons-franchise-players/{id}", seasonsFranchisePlayer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SeasonsFranchisePlayer> seasonsFranchisePlayerList = seasonsFranchisePlayerRepository.findAll();
        assertThat(seasonsFranchisePlayerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeasonsFranchisePlayer.class);
        SeasonsFranchisePlayer seasonsFranchisePlayer1 = new SeasonsFranchisePlayer();
        seasonsFranchisePlayer1.setId(1L);
        SeasonsFranchisePlayer seasonsFranchisePlayer2 = new SeasonsFranchisePlayer();
        seasonsFranchisePlayer2.setId(seasonsFranchisePlayer1.getId());
        assertThat(seasonsFranchisePlayer1).isEqualTo(seasonsFranchisePlayer2);
        seasonsFranchisePlayer2.setId(2L);
        assertThat(seasonsFranchisePlayer1).isNotEqualTo(seasonsFranchisePlayer2);
        seasonsFranchisePlayer1.setId(null);
        assertThat(seasonsFranchisePlayer1).isNotEqualTo(seasonsFranchisePlayer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeasonsFranchisePlayerDTO.class);
        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO1 = new SeasonsFranchisePlayerDTO();
        seasonsFranchisePlayerDTO1.setId(1L);
        SeasonsFranchisePlayerDTO seasonsFranchisePlayerDTO2 = new SeasonsFranchisePlayerDTO();
        assertThat(seasonsFranchisePlayerDTO1).isNotEqualTo(seasonsFranchisePlayerDTO2);
        seasonsFranchisePlayerDTO2.setId(seasonsFranchisePlayerDTO1.getId());
        assertThat(seasonsFranchisePlayerDTO1).isEqualTo(seasonsFranchisePlayerDTO2);
        seasonsFranchisePlayerDTO2.setId(2L);
        assertThat(seasonsFranchisePlayerDTO1).isNotEqualTo(seasonsFranchisePlayerDTO2);
        seasonsFranchisePlayerDTO1.setId(null);
        assertThat(seasonsFranchisePlayerDTO1).isNotEqualTo(seasonsFranchisePlayerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seasonsFranchisePlayerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seasonsFranchisePlayerMapper.fromId(null)).isNull();
    }
}
