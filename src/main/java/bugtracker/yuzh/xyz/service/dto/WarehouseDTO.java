package bugtracker.yuzh.xyz.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link bugtracker.yuzh.xyz.domain.Warehouse} entity.
 */
public class WarehouseDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private Long stock;

    private String owner;

    private LocalDate gmtCreate;

    private LocalDate gmtModified;

    private Integer deleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WarehouseDTO warehouseDTO = (WarehouseDTO) o;
        if (warehouseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), warehouseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WarehouseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", stock=" + getStock() +
            ", owner='" + getOwner() + "'" +
            ", gmtCreate='" + getGmtCreate() + "'" +
            ", gmtModified='" + getGmtModified() + "'" +
            ", deleted=" + getDeleted() +
            "}";
    }
}
