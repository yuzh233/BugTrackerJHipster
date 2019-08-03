package bugtracker.yuzh.xyz.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

import bugtracker.yuzh.xyz.domain.Warehouse;
import bugtracker.yuzh.xyz.domain.enumeration.GoodsType;

/**
 * A DTO for the {@link bugtracker.yuzh.xyz.domain.Goods} entity.
 */
public class GoodsDTO implements Serializable {

    private Long id;

    private String name;

    private Long sku;

    private GoodsType type;

    private LocalDate gmtCreate;

    private LocalDate gmtModified;

    private Integer deleted;

    private Warehouse warehouse;

    private Long warehouseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public LocalDate getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDate gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDate getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDate gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoodsDTO goodsDTO = (GoodsDTO) o;
        if (goodsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), goodsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GoodsDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", sku=" + sku +
            ", type=" + type +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
            ", deleted=" + deleted +
            ", warehouse=" + warehouse +
            ", warehouseId=" + warehouseId +
            '}';
    }
}
