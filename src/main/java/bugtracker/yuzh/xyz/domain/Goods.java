package bugtracker.yuzh.xyz.domain;

import bugtracker.yuzh.xyz.domain.enumeration.GoodsType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Goods.
 */
@Entity
@Table(name = "goods")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private Long sku;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private GoodsType type;

    @Column(name = "gmt_create")
    private LocalDate gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDate gmtModified;

    @Column(name = "deleted")
    private Integer deleted;

    @ManyToOne
    // @JsonIgnoreProperties("goods")
    private Warehouse warehouse;

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

    public Goods name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSku() {
        return sku;
    }

    public Goods sku(Long sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public GoodsType getType() {
        return type;
    }

    public Goods type(GoodsType type) {
        this.type = type;
        return this;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public LocalDate getGmtCreate() {
        return gmtCreate;
    }

    public Goods gmtCreate(LocalDate gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public void setGmtCreate(LocalDate gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDate getGmtModified() {
        return gmtModified;
    }

    public Goods gmtModified(LocalDate gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public void setGmtModified(LocalDate gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public Goods deleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Goods warehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Goods)) {
            return false;
        }
        return id != null && id.equals(((Goods) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Goods{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", sku=" + sku +
            ", type=" + type +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
            ", deleted=" + deleted +
            ", warehouse=" + warehouse +
            '}';
    }
}
