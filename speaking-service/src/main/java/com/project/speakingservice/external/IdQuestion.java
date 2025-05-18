package com.project.speakingservice.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdQuestion {
    private String number;
    private String topic;
    private String question;
    private String url;
}
