package com.hackerrank.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "actor")
@Data
public class Actor {

  @Id
  private Long id;

  private String login;

  @Column(name = "avatar_url")
  @JsonProperty("avatar_url")
  private String avatarUrl;


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Actor)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final Actor actor = (Actor) o;
    return Objects.equals(id, actor.id) && Objects.equals(login, actor.login);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), id, login);
  }
}
