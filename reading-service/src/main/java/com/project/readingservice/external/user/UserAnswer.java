package com.project.readingservice.external.user;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
@Builder
public class UserAnswer {

    private String testId;

    private Duration timeTaken;

    private List<String> answers;

}
