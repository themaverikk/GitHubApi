package com.hackerrank.github.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "event")
@Data
public class Event {

  @Id
  private Long id;
  private String type;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @PrimaryKeyJoinColumn
  private Actor actor;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Repo repo;

  @JsonProperty("created_at")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
  private Timestamp createdAt;

}
