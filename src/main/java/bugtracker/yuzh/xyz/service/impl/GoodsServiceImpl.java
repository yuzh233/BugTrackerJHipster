package bugtracker.yuzh.xyz.service.impl;

import bugtracker.yuzh.xyz.service.GoodsService;
import bugtracker.yuzh.xyz.domain.Goods;
import bugtracker.yuzh.xyz.repository.GoodsRepository;
import bugtracker.yuzh.xyz.service.dto.GoodsDTO;
import bugtracker.yuzh.xyz.service.mapper.GoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Goods}.
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    private final Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);

    private final GoodsRepository goodsRepository;

    private final GoodsMapper goodsMapper;

    public GoodsServiceImpl(GoodsRepository goodsRepository, GoodsMapper goodsMapper) {
        this.goodsRepository = goodsRepository;
        this.goodsMapper = goodsMapper;
    }

    /**
     * Save a goods.
     *
     * @param goodsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GoodsDTO save(GoodsDTO goodsDTO) {
        log.debug("Request to save Goods : {}", goodsDTO);
        Goods goods = goodsMapper.toEntity(goodsDTO);
        goods = goodsRepository.save(goods);
        return goodsMapper.toDto(goods);
    }

    /**
     * Get all the goods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GoodsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Goods");
        Page<Goods> all = goodsRepository.findAll(pageable);
        all.forEach(System.out::println);
        return all.map(goodsMapper::toDto);
    }


    /**
     * Get one goods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GoodsDTO> findOne(Long id) {
        log.debug("Request to get Goods : {}", id);
        return goodsRepository.findById(id)
            .map(goodsMapper::toDto);
    }

    /**
     * Delete the goods by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Goods : {}", id);
        goodsRepository.deleteById(id);
    }
}
