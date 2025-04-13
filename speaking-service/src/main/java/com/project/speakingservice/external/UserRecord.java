package com.project.speakingservice.external;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRecord {
    private String examId;
    private SpeakingExam speakingExam;
    private UserAnswer userAnswer;
    private String partOneUser;
    private String partTwoUser;
    private String partThreeUser;
    private Double score;
}
