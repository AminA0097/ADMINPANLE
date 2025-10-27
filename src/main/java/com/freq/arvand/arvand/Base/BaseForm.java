package com.freq.arvand.arvand.Base;

import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public abstract class BaseForm {
    private String createdBy;
    private String updatedBy;
    private Boolean deleted;
    private Boolean enabled;
    private Date createdData;
    private Date updatedData;
    private Date deletedData;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedData() {
        return createdData;
    }

    public void setCreatedData(Date createdData) {
        this.createdData = createdData;
    }

    public Date getUpdatedData() {
        return updatedData;
    }

    public void setUpdatedData(Date updatedData) {
        this.updatedData = updatedData;
    }

    public Date getDeletedData() {
        return deletedData;
    }

    public void setDeletedData(Date deletedData) {
        this.deletedData = deletedData;
    }

    public abstract Long getId();


}
