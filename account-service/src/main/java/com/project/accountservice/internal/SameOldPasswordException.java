package com.project.accountservice.internal;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SameOldPasswordException extends RuntimeException {

  public SameOldPasswordException() {
    super();
    log.warn("New Password Is Same The Old One");
  }

}
