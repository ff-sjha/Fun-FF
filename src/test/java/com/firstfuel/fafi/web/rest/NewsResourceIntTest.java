package com.firstfuel.fafi.web.rest;

import com.firstfuel.fafi.FafiApp;

import com.firstfuel.fafi.domain.News;
import com.firstfuel.fafi.repository.NewsRepository;
import com.firstfuel.fafi.service.NewsService;
import com.firstfuel.fafi.service.dto.NewsDTO;
import com.firstfuel.fafi.service.mapper.NewsMapper;
import com.firstfuel.fafi.web.rest.errors.ExceptionTranslator;
import com.firstfuel.fafi.service.dto.NewsCriteria;
import com.firstfuel.fafi.service.NewsQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.firstfuel.fafi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NewsResource REST controller.
 *
 * @see NewsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FafiApp.class)
public class NewsResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVE_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ATIVE_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATIVE_TILL = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsQueryService newsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNewsMockMvc;

    private News news;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NewsResource newsResource = new NewsResource(newsService, newsQueryService);
        this.restNewsMockMvc = MockMvcBuilders.standaloneSetup(newsResource)
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
    public static News createEntity(EntityManager em) {
        News news = new News()
            .title(DEFAULT_TITLE)
            .details(DEFAULT_DETAILS)
            .activeFrom(DEFAULT_ACTIVE_FROM)
            .ativeTill(DEFAULT_ATIVE_TILL);
        return news;
    }

    @Before
    public void initTest() {
        news = createEntity(em);
    }

    @Test
    @Transactional
    public void createNews() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);
        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate + 1);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNews.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testNews.getActiveFrom()).isEqualTo(DEFAULT_ACTIVE_FROM);
        assertThat(testNews.getAtiveTill()).isEqualTo(DEFAULT_ATIVE_TILL);
    }

    @Test
    @Transactional
    public void createNewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // Create the News with an existing ID
        news.setId(1L);
        NewsDTO newsDTO = newsMapper.toDto(news);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setTitle(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setActiveFrom(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtiveTillIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setAtiveTill(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);

        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList
        restNewsMockMvc.perform(get("/api/news?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].ativeTill").value(hasItem(DEFAULT_ATIVE_TILL.toString())));
    }

    @Test
    @Transactional
    public void getNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", news.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(news.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.activeFrom").value(DEFAULT_ACTIVE_FROM.toString()))
            .andExpect(jsonPath("$.ativeTill").value(DEFAULT_ATIVE_TILL.toString()));
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title equals to DEFAULT_TITLE
        defaultNewsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the newsList where title equals to UPDATED_TITLE
        defaultNewsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNewsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the newsList where title equals to UPDATED_TITLE
        defaultNewsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title is not null
        defaultNewsShouldBeFound("title.specified=true");

        // Get all the newsList where title is null
        defaultNewsShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsByActiveFromIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where activeFrom equals to DEFAULT_ACTIVE_FROM
        defaultNewsShouldBeFound("activeFrom.equals=" + DEFAULT_ACTIVE_FROM);

        // Get all the newsList where activeFrom equals to UPDATED_ACTIVE_FROM
        defaultNewsShouldNotBeFound("activeFrom.equals=" + UPDATED_ACTIVE_FROM);
    }

    @Test
    @Transactional
    public void getAllNewsByActiveFromIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where activeFrom in DEFAULT_ACTIVE_FROM or UPDATED_ACTIVE_FROM
        defaultNewsShouldBeFound("activeFrom.in=" + DEFAULT_ACTIVE_FROM + "," + UPDATED_ACTIVE_FROM);

        // Get all the newsList where activeFrom equals to UPDATED_ACTIVE_FROM
        defaultNewsShouldNotBeFound("activeFrom.in=" + UPDATED_ACTIVE_FROM);
    }

    @Test
    @Transactional
    public void getAllNewsByActiveFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where activeFrom is not null
        defaultNewsShouldBeFound("activeFrom.specified=true");

        // Get all the newsList where activeFrom is null
        defaultNewsShouldNotBeFound("activeFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsByActiveFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where activeFrom greater than or equals to DEFAULT_ACTIVE_FROM
        defaultNewsShouldBeFound("activeFrom.greaterOrEqualThan=" + DEFAULT_ACTIVE_FROM);

        // Get all the newsList where activeFrom greater than or equals to UPDATED_ACTIVE_FROM
        defaultNewsShouldNotBeFound("activeFrom.greaterOrEqualThan=" + UPDATED_ACTIVE_FROM);
    }

    @Test
    @Transactional
    public void getAllNewsByActiveFromIsLessThanSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where activeFrom less than or equals to DEFAULT_ACTIVE_FROM
        defaultNewsShouldNotBeFound("activeFrom.lessThan=" + DEFAULT_ACTIVE_FROM);

        // Get all the newsList where activeFrom less than or equals to UPDATED_ACTIVE_FROM
        defaultNewsShouldBeFound("activeFrom.lessThan=" + UPDATED_ACTIVE_FROM);
    }


    @Test
    @Transactional
    public void getAllNewsByAtiveTillIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where ativeTill equals to DEFAULT_ATIVE_TILL
        defaultNewsShouldBeFound("ativeTill.equals=" + DEFAULT_ATIVE_TILL);

        // Get all the newsList where ativeTill equals to UPDATED_ATIVE_TILL
        defaultNewsShouldNotBeFound("ativeTill.equals=" + UPDATED_ATIVE_TILL);
    }

    @Test
    @Transactional
    public void getAllNewsByAtiveTillIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where ativeTill in DEFAULT_ATIVE_TILL or UPDATED_ATIVE_TILL
        defaultNewsShouldBeFound("ativeTill.in=" + DEFAULT_ATIVE_TILL + "," + UPDATED_ATIVE_TILL);

        // Get all the newsList where ativeTill equals to UPDATED_ATIVE_TILL
        defaultNewsShouldNotBeFound("ativeTill.in=" + UPDATED_ATIVE_TILL);
    }

    @Test
    @Transactional
    public void getAllNewsByAtiveTillIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where ativeTill is not null
        defaultNewsShouldBeFound("ativeTill.specified=true");

        // Get all the newsList where ativeTill is null
        defaultNewsShouldNotBeFound("ativeTill.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewsByAtiveTillIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where ativeTill greater than or equals to DEFAULT_ATIVE_TILL
        defaultNewsShouldBeFound("ativeTill.greaterOrEqualThan=" + DEFAULT_ATIVE_TILL);

        // Get all the newsList where ativeTill greater than or equals to UPDATED_ATIVE_TILL
        defaultNewsShouldNotBeFound("ativeTill.greaterOrEqualThan=" + UPDATED_ATIVE_TILL);
    }

    @Test
    @Transactional
    public void getAllNewsByAtiveTillIsLessThanSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where ativeTill less than or equals to DEFAULT_ATIVE_TILL
        defaultNewsShouldNotBeFound("ativeTill.lessThan=" + DEFAULT_ATIVE_TILL);

        // Get all the newsList where ativeTill less than or equals to UPDATED_ATIVE_TILL
        defaultNewsShouldBeFound("ativeTill.lessThan=" + UPDATED_ATIVE_TILL);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNewsShouldBeFound(String filter) throws Exception {
        restNewsMockMvc.perform(get("/api/news?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].ativeTill").value(hasItem(DEFAULT_ATIVE_TILL.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNewsShouldNotBeFound(String filter) throws Exception {
        restNewsMockMvc.perform(get("/api/news?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingNews() throws Exception {
        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news
        News updatedNews = newsRepository.findOne(news.getId());
        // Disconnect from session so that the updates on updatedNews are not directly saved in db
        em.detach(updatedNews);
        updatedNews
            .title(UPDATED_TITLE)
            .details(UPDATED_DETAILS)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .ativeTill(UPDATED_ATIVE_TILL);
        NewsDTO newsDTO = newsMapper.toDto(updatedNews);

        restNewsMockMvc.perform(put("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNews.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testNews.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testNews.getAtiveTill()).isEqualTo(UPDATED_ATIVE_TILL);
    }

    @Test
    @Transactional
    public void updateNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNewsMockMvc.perform(put("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);
        int databaseSizeBeforeDelete = newsRepository.findAll().size();

        // Get the news
        restNewsMockMvc.perform(delete("/api/news/{id}", news.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(News.class);
        News news1 = new News();
        news1.setId(1L);
        News news2 = new News();
        news2.setId(news1.getId());
        assertThat(news1).isEqualTo(news2);
        news2.setId(2L);
        assertThat(news1).isNotEqualTo(news2);
        news1.setId(null);
        assertThat(news1).isNotEqualTo(news2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsDTO.class);
        NewsDTO newsDTO1 = new NewsDTO();
        newsDTO1.setId(1L);
        NewsDTO newsDTO2 = new NewsDTO();
        assertThat(newsDTO1).isNotEqualTo(newsDTO2);
        newsDTO2.setId(newsDTO1.getId());
        assertThat(newsDTO1).isEqualTo(newsDTO2);
        newsDTO2.setId(2L);
        assertThat(newsDTO1).isNotEqualTo(newsDTO2);
        newsDTO1.setId(null);
        assertThat(newsDTO1).isNotEqualTo(newsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(newsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(newsMapper.fromId(null)).isNull();
    }
}
