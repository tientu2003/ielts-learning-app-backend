package com.project.listeningservice.external.user;

import com.project.listeningservice.external.data.ListeningAnswer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DetailRecord {

    private String id;

    private String userId;

    private ListeningAnswer answer;

    private Double score;

    private List<Result> results;

    private String date;

    @Data
    @Builder
    public static class Result {
        private Boolean check;
        private String userAnswer;
    }

}
