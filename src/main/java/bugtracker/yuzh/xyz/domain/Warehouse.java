package bugtracker.yuzh.xyz.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Warehouse.
 */
@Entity
@Table(name = "warehouse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "owner")
    private String owner;

    @Column(name = "gmt_create")
    private LocalDate gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDate gmtModified;

    @Column(name = "deleted")
    private Integer deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Warehouse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Warehouse address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getStock() {
        return stock;
    }

    public Warehouse stock(Long stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getOwner() {
        return owner;
    }

    public Warehouse owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDate getGmtCreate() {
        return gmtCreate;
    }

    public Warehouse gmtCreate(LocalDate gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public void setGmtCreate(LocalDate gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDate getGmtModified() {
        return gmtModified;
    }

    public Warehouse gmtModified(LocalDate gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public void setGmtModified(LocalDate gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public Warehouse deleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warehouse)) {
            return false;
        }
        return id != null && id.equals(((Warehouse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
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
