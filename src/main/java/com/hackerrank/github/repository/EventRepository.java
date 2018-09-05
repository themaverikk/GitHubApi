package com.hackerrank.github.repository;

import com.hackerrank.github.model.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  Event findById(Long id);

  @Transactional
  @Modifying
  @Query(value = "delete from Event")
  void deleteAll();

  @Query(value = "FROM Event order by id ASC")
  List<Event> findAllSorted();

  @Query(value = "FROM Event where actor.id=:actorId order by id ASC")
  List<Event> findEventsForActor(@Param("actorId") long actorId);

  List<Event> findAllByOrderByActorIdAscCreatedAtDesc();

}
