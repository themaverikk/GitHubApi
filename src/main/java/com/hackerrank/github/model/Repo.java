package com.hackerrank.github.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "repo")
@Data
public class Repo {

  @Id
  private Long id;
  private String name;
  private String url;
}
