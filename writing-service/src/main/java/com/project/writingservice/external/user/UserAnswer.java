package com.project.writingservice.external.user;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserAnswer {
    private String userId;
    private String answer;
    private Date createdAt;
    private String duration;
}
