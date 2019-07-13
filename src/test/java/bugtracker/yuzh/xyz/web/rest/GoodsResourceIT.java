package bugtracker.yuzh.xyz.web.rest;

import bugtracker.yuzh.xyz.BugTrackerJHipsterApp;
import bugtracker.yuzh.xyz.domain.Goods;
import bugtracker.yuzh.xyz.repository.GoodsRepository;
import bugtracker.yuzh.xyz.service.GoodsService;
import bugtracker.yuzh.xyz.service.dto.GoodsDTO;
import bugtracker.yuzh.xyz.service.mapper.GoodsMapper;
import bugtracker.yuzh.xyz.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static bugtracker.yuzh.xyz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bugtracker.yuzh.xyz.domain.enumeration.GoodsType;
/**
 * Integration tests for the {@Link GoodsResource} REST controller.
 */
@SpringBootTest(classes = BugTrackerJHipsterApp.class)
public class GoodsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SKU = 1L;
    private static final Long UPDATED_SKU = 2L;

    private static final GoodsType DEFAULT_TYPE = GoodsType.FINE;
    private static final GoodsType UPDATED_TYPE = GoodsType.BAD;

    private static final LocalDate DEFAULT_GMT_CREATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GMT_CREATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_GMT_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GMT_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DELETED = 1;
    private static final Integer UPDATED_DELETED = 2;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restGoodsMockMvc;

    private Goods goods;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoodsResource goodsResource = new GoodsResource(goodsService);
        this.restGoodsMockMvc = MockMvcBuilders.standaloneSetup(goodsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goods createEntity(EntityManager em) {
        Goods goods = new Goods()
            .name(DEFAULT_NAME)
            .sku(DEFAULT_SKU)
            .type(DEFAULT_TYPE)
            .gmtCreate(DEFAULT_GMT_CREATE)
            .gmtModified(DEFAULT_GMT_MODIFIED)
            .deleted(DEFAULT_DELETED);
        return goods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goods createUpdatedEntity(EntityManager em) {
        Goods goods = new Goods()
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .type(UPDATED_TYPE)
            .gmtCreate(UPDATED_GMT_CREATE)
            .gmtModified(UPDATED_GMT_MODIFIED)
            .deleted(UPDATED_DELETED);
        return goods;
    }

    @BeforeEach
    public void initTest() {
        goods = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoods() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isCreated());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate + 1);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoods.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testGoods.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGoods.getGmtCreate()).isEqualTo(DEFAULT_GMT_CREATE);
        assertThat(testGoods.getGmtModified()).isEqualTo(DEFAULT_GMT_MODIFIED);
        assertThat(testGoods.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createGoodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods with an existing ID
        goods.setId(1L);
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get all the goodsList
        restGoodsMockMvc.perform(get("/api/goods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gmtCreate").value(hasItem(DEFAULT_GMT_CREATE.toString())))
            .andExpect(jsonPath("$.[*].gmtModified").value(hasItem(DEFAULT_GMT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)));
    }
    
    @Test
    @Transactional
    public void getGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", goods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goods.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.gmtCreate").value(DEFAULT_GMT_CREATE.toString()))
            .andExpect(jsonPath("$.gmtModified").value(DEFAULT_GMT_MODIFIED.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED));
    }

    @Test
    @Transactional
    public void getNonExistingGoods() throws Exception {
        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Update the goods
        Goods updatedGoods = goodsRepository.findById(goods.getId()).get();
        // Disconnect from session so that the updates on updatedGoods are not directly saved in db
        em.detach(updatedGoods);
        updatedGoods
            .name(UPDATED_NAME)
            .sku(UPDATED_SKU)
            .type(UPDATED_TYPE)
            .gmtCreate(UPDATED_GMT_CREATE)
            .gmtModified(UPDATED_GMT_MODIFIED)
            .deleted(UPDATED_DELETED);
        GoodsDTO goodsDTO = goodsMapper.toDto(updatedGoods);

        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isOk());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoods.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testGoods.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGoods.getGmtCreate()).isEqualTo(UPDATED_GMT_CREATE);
        assertThat(testGoods.getGmtModified()).isEqualTo(UPDATED_GMT_MODIFIED);
        assertThat(testGoods.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingGoods() throws Exception {
        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Create the Goods
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        int databaseSizeBeforeDelete = goodsRepository.findAll().size();

        // Delete the goods
        restGoodsMockMvc.perform(delete("/api/goods/{id}", goods.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goods.class);
        Goods goods1 = new Goods();
        goods1.setId(1L);
        Goods goods2 = new Goods();
        goods2.setId(goods1.getId());
        assertThat(goods1).isEqualTo(goods2);
        goods2.setId(2L);
        assertThat(goods1).isNotEqualTo(goods2);
        goods1.setId(null);
        assertThat(goods1).isNotEqualTo(goods2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodsDTO.class);
        GoodsDTO goodsDTO1 = new GoodsDTO();
        goodsDTO1.setId(1L);
        GoodsDTO goodsDTO2 = new GoodsDTO();
        assertThat(goodsDTO1).isNotEqualTo(goodsDTO2);
        goodsDTO2.setId(goodsDTO1.getId());
        assertThat(goodsDTO1).isEqualTo(goodsDTO2);
        goodsDTO2.setId(2L);
        assertThat(goodsDTO1).isNotEqualTo(goodsDTO2);
        goodsDTO1.setId(null);
        assertThat(goodsDTO1).isNotEqualTo(goodsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(goodsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(goodsMapper.fromId(null)).isNull();
    }
}
