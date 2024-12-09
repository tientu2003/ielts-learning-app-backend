package com.project.readingservice.external.user;


import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class BasicReadingHistory {

    private UUID id;

    private String recordId;

    private Date createdAt;

    private String testName;

    private Double score;

}
