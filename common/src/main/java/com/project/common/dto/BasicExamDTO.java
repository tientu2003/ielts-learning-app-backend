package com.project.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.common.constraints.CefrLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class BasicExamDTO {
    private String id;
    private String testName;
    private List<String> topics;
    private List<Double> difficulties;
    private List<CefrLevel> levels;
}
