package com.project.writingservice.external.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserAnswer {
    private String examId;
    private List<String> answer;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String duration;
}
