package com.project.readingservice.external.user;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class UserAnswer {

    private String testId;

    private Date createdAt;

    private Duration timeTaken;

    private List<String> answers;

}
