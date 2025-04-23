package com.project.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.common.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class BasicExamDTO {
    private String id;
    private String testName;
    private List<Topic> topic;
}
