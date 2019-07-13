package bugtracker.yuzh.xyz.service.mapper;

import bugtracker.yuzh.xyz.domain.*;
import bugtracker.yuzh.xyz.service.dto.GoodsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Goods} and its DTO {@link GoodsDTO}.
 */
@Mapper(componentModel = "spring", uses = {WarehouseMapper.class})
public interface GoodsMapper extends EntityMapper<GoodsDTO, Goods> {

    @Mapping(source = "warehouse.id", target = "warehouseId")
    GoodsDTO toDto(Goods goods);

    @Mapping(source = "warehouseId", target = "warehouse")
    Goods toEntity(GoodsDTO goodsDTO);

    default Goods fromId(Long id) {
        if (id == null) {
            return null;
        }
        Goods goods = new Goods();
        goods.setId(id);
        return goods;
    }
}
