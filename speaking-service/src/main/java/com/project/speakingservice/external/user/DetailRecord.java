package com.project.speakingservice.external.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetailRecord {
    private String id;
    private String testName;
    private String score;

    List<UserAnswerDetail> partOne;
    UserAnswerDetail partTwo;
    List<UserAnswerDetail> partThree;

    @Data
    @Builder
    public static class UserAnswerDetail {
        private Integer number;
        private String topic;
        private String question;
        private String url;
    }
}
