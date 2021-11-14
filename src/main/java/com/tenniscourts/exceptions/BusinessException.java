package com.tenniscourts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Business exception.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {
  /**
   * Instantiates a new Business exception.
   *
   * @param msg the msg
   */
  public BusinessException(String msg){
        super(msg);
    }

    private BusinessException(){}
}
