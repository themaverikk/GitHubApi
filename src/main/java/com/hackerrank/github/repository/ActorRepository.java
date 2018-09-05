package com.hackerrank.github.repository;

import com.hackerrank.github.model.Actor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

  Actor findById(Long id);

  @Query(value = "select actor.id, actor.login, actor.avatar_url from event JOIN actor on event.actor_id = actor.id group by actor.id order by count(event.id) desc, max(created_at) desc, actor.login", nativeQuery = true)
  List<Actor> fetchActorsByMaxEvents();

  @Query(value = "select actor.id, actor.login, actor.avatar_url from event JOIN actor on event.actor_id = actor.id order by event.created_at desc", nativeQuery = true)
  List<Actor> fetchActorsCreatedAtDesc();

}
