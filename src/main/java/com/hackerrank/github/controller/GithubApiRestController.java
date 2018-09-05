package com.hackerrank.github.controller;


import com.hackerrank.github.exceptions.GithubDaoException;
import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.service.GitHubService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class GithubApiRestController {

  @Autowired
  private GitHubService gitHubService;

  @DeleteMapping("/erase")
  public ResponseEntity<?> eraseAllEvents() {
    gitHubService.deleteEvents();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/events")
  public ResponseEntity<?> addEvent(@RequestBody Event event) {
    try {
      gitHubService.addEvent(event);
    } catch (GithubDaoException ex) {
      return new ResponseEntity<>(ex.getStatus());
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/events")
  @ResponseBody
  public List<Event> findAllEventsSorted() {
    return gitHubService.findAllEventsSorted();
  }

  @GetMapping("/events/actors/{actorID}")
  public ResponseEntity<List<Event>> getEventByActorId(@PathVariable long actorID) {
    try {
      return new ResponseEntity<>(gitHubService.findEventsForActor(actorID), HttpStatus.OK);
    } catch (GithubDaoException ex) {
      return new ResponseEntity<>(ex.getStatus());
    }
  }


  @PutMapping("/actors")
  public ResponseEntity<?> updateAvtarUrl(@RequestBody Actor actor) {
    try {
      gitHubService.updateActor(actor);
    } catch (GithubDaoException ex) {
      return new ResponseEntity<>(ex.getStatus());
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/actors")
  @ResponseBody
  public List<Actor> getActorByEvent() {
    return gitHubService.fetchActorsByEventsUpdatedAtUrl();
  }


  @GetMapping("/actors/streak")
  @ResponseBody
  public List<Actor> getActorByStreak() {
    return gitHubService.getActorsByStreak();
  }
}