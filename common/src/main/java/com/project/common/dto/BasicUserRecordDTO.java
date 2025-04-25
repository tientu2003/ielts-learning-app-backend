package com.project.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.common.constraints.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicUserRecordDTO {
    String id;
    String name;
    Double score;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    Date date;

    List<Topic> topics;
}
