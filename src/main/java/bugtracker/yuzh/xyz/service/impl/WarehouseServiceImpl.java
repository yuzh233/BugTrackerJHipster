package bugtracker.yuzh.xyz.service.impl;

import bugtracker.yuzh.xyz.service.WarehouseService;
import bugtracker.yuzh.xyz.domain.Warehouse;
import bugtracker.yuzh.xyz.repository.WarehouseRepository;
import bugtracker.yuzh.xyz.service.dto.WarehouseDTO;
import bugtracker.yuzh.xyz.service.mapper.WarehouseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Warehouse}.
 */
@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final Logger log = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    private final WarehouseRepository warehouseRepository;

    private final WarehouseMapper warehouseMapper;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    /**
     * Save a warehouse.
     *
     * @param warehouseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WarehouseDTO save(WarehouseDTO warehouseDTO) {
        log.debug("Request to save Warehouse : {}", warehouseDTO);
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDTO);
        warehouse = warehouseRepository.save(warehouse);
        return warehouseMapper.toDto(warehouse);
    }

    /**
     * Get all the warehouses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Warehouses");
        return warehouseRepository.findAll(pageable)
            .map(warehouseMapper::toDto);
    }


    /**
     * Get one warehouse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WarehouseDTO> findOne(Long id) {
        log.debug("Request to get Warehouse : {}", id);
        return warehouseRepository.findById(id)
            .map(warehouseMapper::toDto);
    }

    /**
     * Delete the warehouse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Warehouse : {}", id);
        warehouseRepository.deleteById(id);
    }
}
