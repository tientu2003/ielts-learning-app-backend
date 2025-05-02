package com.project.readingservice.external.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserAnswer {

    private String testId;

    private String timeTaken;

    private List<String> answers;

}
