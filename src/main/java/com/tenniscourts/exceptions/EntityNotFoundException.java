package com.tenniscourts.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * The type Entity not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
  /**
   * Instantiates a new Entity not found exception.
   *
   * @param msg the msg
   */
  public EntityNotFoundException(String msg){
        super(msg);
    }

    private EntityNotFoundException(){}
}
