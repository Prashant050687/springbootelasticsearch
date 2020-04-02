package com.prashant.elasticsearch.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

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

  LocalDateTime modifiedDateTime;

  LocalDateTime createdDateTime;

  @PrePersist
  public void prePersist() {
    this.createdDateTime = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.modifiedDateTime = LocalDateTime.now();
  }

}
