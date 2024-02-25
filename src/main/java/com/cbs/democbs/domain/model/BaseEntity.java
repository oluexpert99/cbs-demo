package com.cbs.democbs.domain.model;

import jakarta.persistence.*;
import jakarta.persistence.Version;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = -6342630857637389028L;

  @Version protected int version;

  @CreatedDate protected LocalDateTime createdAt;

  @LastModifiedDate protected LocalDateTime lastModifiedAt;

  @CreatedBy protected String createdBy;

  @LastModifiedBy protected String lastModifiedBy;
}
