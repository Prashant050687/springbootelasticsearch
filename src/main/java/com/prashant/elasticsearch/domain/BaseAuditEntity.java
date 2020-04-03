package com.prashant.elasticsearch.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseAuditEntity implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Version
  int version;

  LocalDate modifiedDate;

  LocalDate createdDate;

  @PrePersist
  public void prePersist() {
    this.createdDate = LocalDate.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.modifiedDate = LocalDate.now();
  }

}
