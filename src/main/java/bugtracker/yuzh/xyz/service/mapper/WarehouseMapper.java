package bugtracker.yuzh.xyz.service.mapper;

import bugtracker.yuzh.xyz.domain.*;
import bugtracker.yuzh.xyz.service.dto.WarehouseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Warehouse} and its DTO {@link WarehouseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WarehouseMapper extends EntityMapper<WarehouseDTO, Warehouse> {



    default Warehouse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        return warehouse;
    }
}
