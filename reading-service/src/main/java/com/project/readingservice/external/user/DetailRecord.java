package com.project.readingservice.external.user;

import com.project.readingservice.external.data.ReadingAnswer;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class DetailRecord {

    private String id;

    private String userId;

    private ReadingAnswer answer;

    private Double score;

    private List<Result> results;

    private Date date;


    @Data
    @Builder
    public static class Result {

        private Boolean check;

        private String userAnswer;

    }
}
