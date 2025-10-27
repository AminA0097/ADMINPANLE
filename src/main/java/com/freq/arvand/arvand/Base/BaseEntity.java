package com.freq.arvand.arvand.Base;


import com.freq.arvand.arvand.annotation.BooleanToNumberConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "FLD_DESC")
    private String description;
    @Column(name = "FLD_CREATED_BY")
    private String createdBy;
    @Column(name = "FLD_UPDATED_BY")
    private String modifiedBy;
    @Column(name = "FLD_CREATED_DATE")
    private Date createdDate;
    @Column(name = "FLD_UPDATED_DATE")
    private Date modifiedDate;
    @Column(name = "FLD_DELETED")
    @Convert(converter = BooleanToNumberConverter.class)
    private boolean deleted;
    @Column(name = "FLD_ACTIVE")
    @Convert(converter = BooleanToNumberConverter.class)
    private boolean active;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract Long getId();

}