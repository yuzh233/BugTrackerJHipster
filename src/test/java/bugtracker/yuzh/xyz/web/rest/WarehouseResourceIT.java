package bugtracker.yuzh.xyz.web.rest;

import bugtracker.yuzh.xyz.BugTrackerJHipsterApp;
import bugtracker.yuzh.xyz.domain.Warehouse;
import bugtracker.yuzh.xyz.repository.WarehouseRepository;
import bugtracker.yuzh.xyz.service.WarehouseService;
import bugtracker.yuzh.xyz.service.dto.WarehouseDTO;
import bugtracker.yuzh.xyz.service.mapper.WarehouseMapper;
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

/**
 * Integration tests for the {@Link WarehouseResource} REST controller.
 */
@SpringBootTest(classes = BugTrackerJHipsterApp.class)
public class WarehouseResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_STOCK = 1L;
    private static final Long UPDATED_STOCK = 2L;

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GMT_CREATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GMT_CREATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_GMT_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GMT_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DELETED = 1;
    private static final Integer UPDATED_DELETED = 2;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private WarehouseService warehouseService;

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

    private MockMvc restWarehouseMockMvc;

    private Warehouse warehouse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WarehouseResource warehouseResource = new WarehouseResource(warehouseService);
        this.restWarehouseMockMvc = MockMvcBuilders.standaloneSetup(warehouseResource)
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
    public static Warehouse createEntity(EntityManager em) {
        Warehouse warehouse = new Warehouse()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .stock(DEFAULT_STOCK)
            .owner(DEFAULT_OWNER)
            .gmtCreate(DEFAULT_GMT_CREATE)
            .gmtModified(DEFAULT_GMT_MODIFIED)
            .deleted(DEFAULT_DELETED);
        return warehouse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Warehouse createUpdatedEntity(EntityManager em) {
        Warehouse warehouse = new Warehouse()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .stock(UPDATED_STOCK)
            .owner(UPDATED_OWNER)
            .gmtCreate(UPDATED_GMT_CREATE)
            .gmtModified(UPDATED_GMT_MODIFIED)
            .deleted(UPDATED_DELETED);
        return warehouse;
    }

    @BeforeEach
    public void initTest() {
        warehouse = createEntity(em);
    }

    @Test
    @Transactional
    public void createWarehouse() throws Exception {
        int databaseSizeBeforeCreate = warehouseRepository.findAll().size();

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);
        restWarehouseMockMvc.perform(post("/api/warehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isCreated());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeCreate + 1);
        Warehouse testWarehouse = warehouseList.get(warehouseList.size() - 1);
        assertThat(testWarehouse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWarehouse.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWarehouse.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testWarehouse.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testWarehouse.getGmtCreate()).isEqualTo(DEFAULT_GMT_CREATE);
        assertThat(testWarehouse.getGmtModified()).isEqualTo(DEFAULT_GMT_MODIFIED);
        assertThat(testWarehouse.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createWarehouseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = warehouseRepository.findAll().size();

        // Create the Warehouse with an existing ID
        warehouse.setId(1L);
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarehouseMockMvc.perform(post("/api/warehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWarehouses() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList
        restWarehouseMockMvc.perform(get("/api/warehouses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())))
            .andExpect(jsonPath("$.[*].gmtCreate").value(hasItem(DEFAULT_GMT_CREATE.toString())))
            .andExpect(jsonPath("$.[*].gmtModified").value(hasItem(DEFAULT_GMT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)));
    }
    
    @Test
    @Transactional
    public void getWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get the warehouse
        restWarehouseMockMvc.perform(get("/api/warehouses/{id}", warehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(warehouse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()))
            .andExpect(jsonPath("$.gmtCreate").value(DEFAULT_GMT_CREATE.toString()))
            .andExpect(jsonPath("$.gmtModified").value(DEFAULT_GMT_MODIFIED.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED));
    }

    @Test
    @Transactional
    public void getNonExistingWarehouse() throws Exception {
        // Get the warehouse
        restWarehouseMockMvc.perform(get("/api/warehouses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();

        // Update the warehouse
        Warehouse updatedWarehouse = warehouseRepository.findById(warehouse.getId()).get();
        // Disconnect from session so that the updates on updatedWarehouse are not directly saved in db
        em.detach(updatedWarehouse);
        updatedWarehouse
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .stock(UPDATED_STOCK)
            .owner(UPDATED_OWNER)
            .gmtCreate(UPDATED_GMT_CREATE)
            .gmtModified(UPDATED_GMT_MODIFIED)
            .deleted(UPDATED_DELETED);
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(updatedWarehouse);

        restWarehouseMockMvc.perform(put("/api/warehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isOk());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
        Warehouse testWarehouse = warehouseList.get(warehouseList.size() - 1);
        assertThat(testWarehouse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWarehouse.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWarehouse.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testWarehouse.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testWarehouse.getGmtCreate()).isEqualTo(UPDATED_GMT_CREATE);
        assertThat(testWarehouse.getGmtModified()).isEqualTo(UPDATED_GMT_MODIFIED);
        assertThat(testWarehouse.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingWarehouse() throws Exception {
        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarehouseMockMvc.perform(put("/api/warehouses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        int databaseSizeBeforeDelete = warehouseRepository.findAll().size();

        // Delete the warehouse
        restWarehouseMockMvc.perform(delete("/api/warehouses/{id}", warehouse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Warehouse.class);
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setId(1L);
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setId(warehouse1.getId());
        assertThat(warehouse1).isEqualTo(warehouse2);
        warehouse2.setId(2L);
        assertThat(warehouse1).isNotEqualTo(warehouse2);
        warehouse1.setId(null);
        assertThat(warehouse1).isNotEqualTo(warehouse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarehouseDTO.class);
        WarehouseDTO warehouseDTO1 = new WarehouseDTO();
        warehouseDTO1.setId(1L);
        WarehouseDTO warehouseDTO2 = new WarehouseDTO();
        assertThat(warehouseDTO1).isNotEqualTo(warehouseDTO2);
        warehouseDTO2.setId(warehouseDTO1.getId());
        assertThat(warehouseDTO1).isEqualTo(warehouseDTO2);
        warehouseDTO2.setId(2L);
        assertThat(warehouseDTO1).isNotEqualTo(warehouseDTO2);
        warehouseDTO1.setId(null);
        assertThat(warehouseDTO1).isNotEqualTo(warehouseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(warehouseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(warehouseMapper.fromId(null)).isNull();
    }
}
