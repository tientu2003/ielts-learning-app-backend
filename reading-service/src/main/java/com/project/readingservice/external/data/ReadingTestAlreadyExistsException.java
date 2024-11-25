package com.project.readingservice.external.data;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadingTestAlreadyExistsException extends RuntimeException {
    public ReadingTestAlreadyExistsException(String message) {
        super(message);
        log.debug(message);
    }
}
