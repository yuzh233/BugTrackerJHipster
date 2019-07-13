package bugtracker.yuzh.xyz.repository;

import bugtracker.yuzh.xyz.domain.Warehouse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Warehouse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

}
