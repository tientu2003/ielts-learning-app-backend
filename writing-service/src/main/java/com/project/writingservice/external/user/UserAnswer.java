package com.project.writingservice.external.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserAnswer {
    private String examId;
    private String answer;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    private String duration;
}
