package com.project.readingservice.external.user;

import com.project.readingservice.external.data.AnswerData;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Builder
@Data
public class DetailReadingTestRecord {

    private String id;

    private String userId;

    private List<Result> userAnswers;

    private AnswerData answerData;

    private Duration timeTaken;

    private Date createAt;

    private Double score;

    @Data
    @Builder
    public static class Result {

        private Boolean check;

        private String userAnswer;

    }
}
