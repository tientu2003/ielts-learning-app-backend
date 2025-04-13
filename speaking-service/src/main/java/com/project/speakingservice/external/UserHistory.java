package com.project.speakingservice.external;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserHistory {
    private String id;
    private String name;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
}
