package com.project.speakingservice.external;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserAnswer {
    private String userId;
    private String examId;
    private Date createAt;
    private String duration;
    byte[] partOne;
    byte[] partTwo;
    byte[] partThree;
}
