package com.project.speakingservice.external.user;

import com.project.speakingservice.external.IdQuestion;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserAnswer {
    private String id;
    private String testName;
    private List<IdQuestion> answersOne;
    private IdQuestion answersTwo;
    private List<IdQuestion> answersThree;
}
