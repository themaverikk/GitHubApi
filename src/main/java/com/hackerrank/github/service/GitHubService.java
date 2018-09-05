package com.hackerrank.github.service;

import com.hackerrank.github.exceptions.GithubDaoException;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import com.hackerrank.github.repository.RepoRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GitHubService {

  @Autowired
  private ActorRepository actorRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private RepoRepository repoRepository;


  public void deleteEvents() {
    eventRepository.deleteAll();
  }

  public Event addEvent(Event newEvent) {
    if (eventRepository.exists(newEvent.getId())) {
      throw new GithubDaoException(HttpStatus.BAD_REQUEST);
    }

    try {
      return eventRepository.save(newEvent);
    } catch (Exception ex) {
      throw new GithubDaoException(HttpStatus.BAD_REQUEST);
    }
  }

  public List<Event> findAllEventsSorted() {
    return eventRepository.findAllSorted();
  }

  public List<Event> findEventsForActor(long actorId) {
    List<Event> filteredEvents = eventRepository.findEventsForActor(actorId);

    if (CollectionUtils.isEmpty(filteredEvents)) {
      throw new GithubDaoException(HttpStatus.NOT_FOUND);
    }

    return filteredEvents;
  }

  public Actor updateActor(Actor actor) {
    if (actor == null || actor.getId() == null || !actorRepository.exists(actor.getId())) {
      throw new GithubDaoException(HttpStatus.NOT_FOUND);
    }

    Actor persistedActor = actorRepository.findById(actor.getId());

    if (!actor.equals(persistedActor)) {
      throw new GithubDaoException(HttpStatus.BAD_REQUEST);
    }

    return actorRepository.save(actor);
  }

  public List<Actor> fetchActorsByEventsUpdatedAtUrl() {
    return actorRepository.fetchActorsByMaxEvents();
  }

  public List<Actor> getActorsByStreak() {
    Map<Actor, Integer> maxActorStreaks = new HashMap<>();
    Map<Actor, Event> recentActorEvent = new HashMap<>();

    List<Event> events = eventRepository.findAllByOrderByActorIdAscCreatedAtDesc();

    int maxStreak = 0, currentStreak = 0;
    Actor previousActor = null;
    LocalDateTime previousDate = null;

    for (Event event : events) {

      Actor currentActor = event.getActor();
      LocalDateTime currentDate = event.getCreatedAt().toLocalDateTime();

      if (currentActor.equals(previousActor)
          && ChronoUnit.DAYS.between(currentDate, previousDate) == 1) {
        currentStreak++;
      } else {
        currentStreak = 1;
        previousActor = currentActor;
      }

      if (!recentActorEvent.containsKey(currentActor)) {
        recentActorEvent.put(currentActor, event);
      }

      maxStreak = Math.max(currentStreak, maxStreak);
      maxActorStreaks.put(currentActor, maxStreak);
      previousDate = currentDate;
    }

    List<Actor> actors = new ArrayList<>(maxActorStreaks.keySet());

    Collections.sort(actors, (a1, a2) -> maxActorStreaks.get(a1).equals(maxActorStreaks.get(a2)) ?
        ChronoUnit.DAYS.between(recentActorEvent.get(a1).getCreatedAt().toLocalDateTime(),
            recentActorEvent.get(a2).getCreatedAt().toLocalDateTime()) == 0 ? a1.getLogin()
            .compareTo(a2.getLogin()) : recentActorEvent.get(a2).getCreatedAt()
            .compareTo(recentActorEvent.get(a1).getCreatedAt())
        : maxActorStreaks.get(a2) - maxActorStreaks.get(a1));

    return actors;
  }
}
