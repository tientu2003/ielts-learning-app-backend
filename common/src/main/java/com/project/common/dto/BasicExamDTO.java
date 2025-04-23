package com.project.common.dto;

import com.project.common.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BasicExamDTO {
    private String id;
    private String testName;
    private List<Topic> topic;
}
