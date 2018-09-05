package com.hackerrank.github.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class GithubDaoException extends RuntimeException {

  private HttpStatus status;

  public GithubDaoException(final String message, final HttpStatus status) {
    super(message);
    this.status = status;
  }

  public GithubDaoException(final HttpStatus status) {
    super();
    this.status = status;
  }
}
